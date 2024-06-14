package com.capstone.skinifier.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.capstone.skinifier.R
import com.capstone.skinifier.data.pref.UserModel
import com.capstone.skinifier.databinding.ActivityLoginBinding
import com.capstone.skinifier.di.ResultState
import com.capstone.skinifier.view.MainActivity
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

        setupAction()
    }

    private fun setupAction() {
        //intent ke registerAct
        myButtonSignup = binding.signupButton
        myButtonSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        //Login
        binding.loginButton.setOnClickListener {
            val email = binding.emailField.text.toString().trim()
            val password = binding.passwordField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and password are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginViewModel.login(email, password)
        }

        loginViewModel.loginResult.observe(this, Observer { result ->
            when (result) {
                is ResultState.Success -> {
                    val user = result.data.user
                    val token = result.data.token
                    if (user != null && token.isNotEmpty()) {
                        loginViewModel.saveSession(UserModel(user.email ?: "", token, true))
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
                is ResultState.Error -> {
                    Toast.makeText(this, "login error", Toast.LENGTH_SHORT).show()
                }
                is ResultState.Loading -> {
                    // Show loading indicator if needed
                }
            }
        })

    }

}