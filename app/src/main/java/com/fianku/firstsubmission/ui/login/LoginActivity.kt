package com.fianku.firstsubmission.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.fianku.firstsubmission.ui.main.MainActivity
import com.fianku.firstsubmission.ui.register.RegisterActivity
import com.fianku.firstsubmission.customView.SettingPreferences
import com.fianku.firstsubmission.customView.ViewModelFactory
import com.fianku.firstsubmission.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val pref = SettingPreferences.getInstance(dataStore)
        val loginViewModel = ViewModelProvider(this, ViewModelFactory(pref,this)).get(
            LoginViewModel::class.java
        )
        loginViewModel.getUserNames().observe(this) {
           if (!it.isNullOrEmpty()){
               startActivity(Intent(this, MainActivity::class.java))
               finish()
           }
        }
        loginViewModel.login.observe(this) {
            if (it.error== false) {
                it.loginResult?.name?.let { it1 -> loginViewModel.setUserNames(it1) }
                it.loginResult?.userId?.let { it2 -> loginViewModel.setUserIds(it2) }
                it.loginResult?.token?.let { it3 -> loginViewModel.setUserTokens(it3) }
                userLogin()
            }
        }
        loginViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        setMyButtonEnable()
        binding.Loginbutton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val pass = binding.loginPassword.text.toString()
            loginViewModel.loginUser(email,pass)
        }
        binding.register.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
            finish()
        }
        binding.loginEmail.addTextChangedListener {
            setMyButtonEnable()
        }
        binding.loginPassword.addTextChangedListener {
            setMyButtonEnable()
        }

    }
    private fun userLogin() {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },2500)
    }
    private fun setMyButtonEnable() {
        val usern = binding.loginEmail.text
        val pass = binding.loginPassword.text
        binding.Loginbutton.isEnabled = usern != null && usern.toString().isNotEmpty() && pass != null && pass.toString().isNotEmpty()
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
}