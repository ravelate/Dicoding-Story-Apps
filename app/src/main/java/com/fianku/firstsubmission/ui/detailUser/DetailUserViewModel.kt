package com.fianku.firstsubmission.ui.detailUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fianku.firstsubmission.customView.SettingPreferences
import kotlinx.coroutines.launch

class DetailUserViewModel(private val pref: SettingPreferences): ViewModel() {
    fun getUserNames(): LiveData<String?> {
        return pref.getUserName().asLiveData()
    }
    fun getUserIds(): LiveData<String?> {
        return pref.getUserId().asLiveData()
    }
    fun getUserTokens(): LiveData<String?> {
        return pref.getUserToken().asLiveData()
    }
    fun deleteAllDatas() {
        viewModelScope.launch {
            pref.deleteAllData()
        }
    }
}