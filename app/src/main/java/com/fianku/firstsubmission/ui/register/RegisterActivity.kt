package com.fianku.firstsubmission.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.fianku.firstsubmission.network.RegisterResponse
import com.fianku.firstsubmission.ui.login.LoginActivity
import com.fianku.firstsubmission.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val registerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(RegisterViewModel::class.java)
        registerViewModel.regist.observe(this) {
            userRegister(it)
        }
        registerViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        setMyButtonEnable()
        binding.registUsername.addTextChangedListener {
            setMyButtonEnable()
        }
        binding.registEmail.addTextChangedListener {
            setMyButtonEnable()
        }
        binding.registPassword.addTextChangedListener {
            setMyButtonEnable()
        }
        binding.registbutton.setOnClickListener {
            val usern = binding.registUsername.text.toString()
            val email = binding.registEmail.text.toString()
            val pass = binding.registPassword.text.toString()
            registerViewModel.registerUser(usern,email,pass)
        }
        binding.register.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
        }

    }
    private fun userRegister(Regist: RegisterResponse) {
        if(Regist.error == false){
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            },2500)
        }
    }

    private fun setMyButtonEnable() {
        val usern = binding.registUsername.text
        val email = binding.registEmail.text
        val pass = binding.registPassword.text
        binding.registbutton.isEnabled = usern != null && usern.toString().isNotEmpty() &&  email != null && email.toString().isNotEmpty() && pass != null && pass.toString().isNotEmpty()
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}