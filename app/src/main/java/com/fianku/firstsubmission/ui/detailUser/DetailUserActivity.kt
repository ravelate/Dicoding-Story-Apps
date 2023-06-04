package com.fianku.firstsubmission.ui.detailUser

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.fianku.firstsubmission.ui.login.LoginActivity
import com.fianku.firstsubmission.customView.SettingPreferences
import com.fianku.firstsubmission.customView.ViewModelFactory
import com.fianku.firstsubmission.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val pref = SettingPreferences.getInstance(dataStore)
        val detailUserViewModel = ViewModelProvider(this, ViewModelFactory(pref,this)).get(
            DetailUserViewModel::class.java
        )
        detailUserViewModel.getUserNames().observe(this) {
           binding.tvUserName.text = it
        }
        detailUserViewModel.getUserIds().observe(this) {
            binding.tvUserId.text = it
        }
        detailUserViewModel.getUserTokens().observe(this) {
            binding.tvToken.text = it
        }
        binding.btnLogout.setOnClickListener {
            detailUserViewModel.deleteAllDatas()
            val i = Intent(this, LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
        setupAction()
    }
    private fun setupAction() {
        binding.settingImageView.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
}