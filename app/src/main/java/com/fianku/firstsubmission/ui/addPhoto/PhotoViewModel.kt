package com.fianku.firstsubmission.ui.addPhoto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fianku.firstsubmission.network.ApiConfig
import com.fianku.firstsubmission.network.RegisterResponse
import com.fianku.firstsubmission.customView.Event
import com.fianku.firstsubmission.customView.SettingPreferences
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class PhotoViewModel(private val pref: SettingPreferences): ViewModel() {
    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText
    private val _upload = MutableLiveData<RegisterResponse>()
    val upload: LiveData<RegisterResponse> = _upload
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun getUserTokens(): LiveData<String?> {
        return pref.getUserToken().asLiveData()
    }
    fun uploadImage(token: String, imageMultipart: MultipartBody.Part, description: RequestBody){
        _isLoading.value = true
        val service = ApiConfig.getApiService().uploadImage(token,imageMultipart, description)
        service.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error!!) {
                        _upload.value = response.body()
                        _snackbarText.value = Event(response.body()?.message.toString())
                    }
                } else {
                    _snackbarText.value = Event(response.message().toString())
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }
        })
    }
    fun reduceFileImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }
}