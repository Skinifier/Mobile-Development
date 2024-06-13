package com.capstone.skinifier.view.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
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

        //buat dropdown
        val skinType = resources.getStringArray(R.array.skin_type)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, skinType)
        binding.skinTypeField.setAdapter(arrayAdapter)

        observeViewModel()
        setupAction()
    }
    private fun setupAction() {

        //intent loginAct
        myButtonLogin = binding.loginButton
        myButtonLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        //action signup
        binding.signupButton.setOnClickListener {
            val email = binding.emailField.text.toString()
            val username = binding.usernameField.text.toString()
            val name = binding.fullnameField.text.toString()
            val number = binding.numberField.text.toString()
            val skinTypes = binding.skinTypeField.text.toString()
            val password = binding.passwordField.text.toString()
            if (email.isEmpty() || username.isEmpty() || name.isEmpty() || number.isEmpty() || skinTypes.isEmpty() || password.isEmpty()) {
                showToast(getString(R.string.empty_fields_warning))
            } else {
                signupViewModel.register(email, username, name, number, skinTypes, password)
            }
        }

    }

    private fun observeViewModel() {
        signupViewModel.registerResult.observe(this) { result ->
            when (result) {
                is ResultState.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }
                is ResultState.Success -> {
                    binding.progressIndicator.visibility = View.GONE
                    AlertDialog.Builder(this).apply {
                        setTitle(getString(R.string.signup_successful))
                        setMessage(result.data)
                        setPositiveButton(getString(R.string.continue_main)) { _, _ ->
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                }
                is ResultState.Error -> {

                    binding.progressIndicator.visibility = View.GONE
                    AlertDialog.Builder(this).apply {
                        setTitle("Error")
                        setMessage("Login failed: ${result.error}")
                        setPositiveButton("OK", null)
                        create()
                        show()
                    }
                }
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}