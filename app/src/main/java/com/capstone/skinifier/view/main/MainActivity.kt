package com.capstone.skinifier.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
                mainViewModel.getProfile()
            } else {
                navigateToLogin()
                keepSplashScreen = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        mainViewModel.profileResult.observe(this) { result ->
            result.onSuccess {
                navigateToNavigation()
            }.onFailure { exception ->
                mainViewModel.logout()
                navigateToLogin()
            }
            keepSplashScreen = false
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun navigateToNavigation() {
        startActivity(Intent(this, NavigationActivity::class.java))
        finish()
    }
}