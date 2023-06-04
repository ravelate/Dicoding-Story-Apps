package com.fianku.firstsubmission.ui.register

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fianku.firstsubmission.network.ApiConfig
import com.fianku.firstsubmission.network.RegisterResponse
import com.fianku.firstsubmission.customView.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel: ViewModel() {
    private val _regist = MutableLiveData<RegisterResponse>()
    val regist: LiveData<RegisterResponse> = _regist

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun registerUser(username: String, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().registUser(username, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value =false
                if (response.isSuccessful && response.body() != null) {
                    _regist.value = response.body()
                    _snackbarText.value = Event(response.body()?.message.toString())
                } else {
                    _snackbarText.value = Event(response.message())
                    Log.e(TAG, "onFailure but response: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}