package com.capstone.skinifier.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.capstone.skinifier.databinding.ActivityMainBinding
import com.capstone.skinifier.ui.home.NavigationActivity
import com.capstone.skinifier.view.login.LoginActivity
import com.capstone.skinifier.view.viewModelFactory.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var keepSplashScreen = true
        getActionBar()?.hide()
        installSplashScreen().setKeepOnScreenCondition { keepSplashScreen }

        setContentView(binding.root)

        //check session
        mainViewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                startActivity(Intent(this, NavigationActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            keepSplashScreen = false
        }
    }
}