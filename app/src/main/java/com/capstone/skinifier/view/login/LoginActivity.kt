package com.capstone.skinifier.view.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.skinifier.databinding.ActivityLoginBinding
import com.capstone.skinifier.di.ResultState
import com.capstone.skinifier.view.main.MainActivity
import com.capstone.skinifier.view.custom.MyButtonOutline
import com.capstone.skinifier.view.register.SignupActivity
import com.capstone.skinifier.view.viewModelFactory.AuthViewModelFactory

class LoginActivity : AppCompatActivity() {
    private val loginViewModel by viewModels<LoginViewModel> {
        AuthViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var myButtonSignup: MyButtonOutline
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupAction()
        observeViewModel()
    }

    private fun setupAction() {
        //intent ke registerAct
        myButtonSignup = binding.signupButton
        myButtonSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        //Login
        binding.loginButton.setOnClickListener {
            val email = binding.emailField.text.toString()
            val password = binding.passwordField.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.login(email, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun observeViewModel() {
        loginViewModel.loginResult.observe(this) { result ->
            when (result) {
                is ResultState.Success -> {
                    Toast.makeText(this, result.data, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is ResultState.Error -> {
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                }
                is ResultState.Loading -> {
                }
            }
        }
    }

}