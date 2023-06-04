package com.fianku.firstsubmission.ui.login

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.fianku.firstsubmission.network.ApiConfig
import com.fianku.firstsubmission.network.LoginResponse
import com.fianku.firstsubmission.customView.Event
import com.fianku.firstsubmission.customView.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: SettingPreferences): ViewModel() {
    private val _login = MutableLiveData<LoginResponse>()
    val login: LiveData<LoginResponse> = _login

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserNames(): LiveData<String?> {
        return pref.getUserName().asLiveData()
    }

    fun setUserNames(data: String) {
        viewModelScope.launch {
            pref.setUserName(data)
        }
    }
    fun setUserIds(data: String) {
        viewModelScope.launch {
            pref.setUserId(data)
        }
    }
    fun setUserTokens(data: String) {
        viewModelScope.launch {
            pref.setUserToken(data)
        }
    }
    fun loginUser(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().loginUser(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value =false
                if (response.isSuccessful && response.body() != null) {
                    _login.value = response.body()
                    _snackbarText.value = Event(response.body()?.message.toString())
                } else {
                    _snackbarText.value = Event(response.message())
                    Log.e(ContentValues.TAG, "onFailure but response: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}