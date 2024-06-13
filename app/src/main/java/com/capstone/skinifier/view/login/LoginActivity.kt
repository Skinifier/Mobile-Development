package com.capstone.skinifier.view.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.skinifier.databinding.ActivityLoginBinding
import com.capstone.skinifier.view.custom.MyButtonOutline
import com.capstone.skinifier.view.register.SignupActivity

class LoginActivity : AppCompatActivity() {

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

    }
}