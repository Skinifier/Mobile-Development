package com.capstone.skinifier.view.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.capstone.skinifier.R
import com.capstone.skinifier.databinding.ActivitySignupBinding
import com.capstone.skinifier.di.ResultState
import com.capstone.skinifier.view.custom.MyButtonOutline
import com.capstone.skinifier.view.login.LoginActivity
import com.capstone.skinifier.view.viewModelFactory.ViewModelFactory

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var myButtonLogin: MyButtonOutline
    private val signupViewModel: SignupViewModel by viewModels { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Set up the dropdown
        val skinType = resources.getStringArray(R.array.skin_type)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, skinType)
        binding.skinTypeField.setAdapter(arrayAdapter)
        supportActionBar?.hide()
        setupAction()
    }

    private fun setupAction() {
        // Intent to login activity
        myButtonLogin = binding.loginButton
        myButtonLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
//        binding.signupButton.setOnClickListener {
////            val email = binding.emailField.text.toString()
////            val username = binding.usernameField.text.toString()
////            val fullname = binding.fullnameField.text.toString()
////            val number = binding.numberField.text.toString()
////            val skinType = binding.skinTypeField.text.toString()
////            val password = binding.passwordField.text.toString()
//
//            val email = "tesregis22@gmail.com"
//            val username = "testestestestes"
//            val fullname = "testestestestes"
//            val number = "123123123123"
//            val skinType = "Oily"
//            val password = "123123123123"
//
//            Log.d("SignupActivity", "email: $email, username: $username, name: $fullname, number: $number, skinType: $skinType, password: $password")
//
//
//            if (email.isNotEmpty() && username.isNotEmpty() && fullname.isNotEmpty() && number.isNotEmpty() && skinType.isNotEmpty() && password.isNotEmpty()) {
//                signupViewModel.register(email, username, fullname, number, skinType, password)
//            } else {
//                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        signupViewModel.registerResult.observe(this) { result ->
//            when (result) {
//                is ResultState.Success -> {
//                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
//                }
//                is ResultState.Error -> {
//                    Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
//                }
//                is ResultState.Loading -> {
//                }
//            }
//        }
        binding.signupButton.setOnClickListener {

            val email = binding.emailField.text.toString()
            val username = binding.usernameField.text.toString()
            val fullname = binding.fullnameField.text.toString()
            val number = binding.numberField.text.toString()
            val skinType = binding.skinTypeField.text.toString()
            val password = binding.passwordField.text.toString()

            Log.d("SignupActivity", "email: $email, username: $username, name: $fullname, number: $number, skinType: $skinType, password: $password")

            if (email.isNotEmpty() && username.isNotEmpty() && fullname.isNotEmpty() && number.isNotEmpty() && skinType.isNotEmpty() && password.isNotEmpty()) {
                signupViewModel.register(email, username, fullname, number, skinType, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        signupViewModel.registerResult.observe(this) { result ->
            when (result) {
                is ResultState.Success -> {
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                }
                is ResultState.Error -> {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                }
                is ResultState.Loading -> {
                }
            }
        }

    }

}
