package com.fianku.firstsubmission.customView

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fianku.firstsubmission.di.Injection
import com.fianku.firstsubmission.ui.addPhoto.PhotoViewModel
import com.fianku.firstsubmission.ui.detailUser.DetailUserViewModel
import com.fianku.firstsubmission.ui.login.LoginViewModel
import com.fianku.firstsubmission.ui.main.MainViewModel
import com.fianku.firstsubmission.ui.map.MapsViewModel

class ViewModelFactory(private val pref: SettingPreferences,private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref, Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(DetailUserViewModel::class.java) -> {
                DetailUserViewModel(pref) as T
            }
            modelClass.isAssignableFrom(PhotoViewModel::class.java) -> {
                PhotoViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}