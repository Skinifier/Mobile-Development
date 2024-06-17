package com.capstone.skinifier.view.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.capstone.skinifier.R
import com.capstone.skinifier.databinding.ActivityMainBinding
import com.capstone.skinifier.view.login.LoginActivity
import com.capstone.skinifier.view.register.SignupActivity
import com.capstone.skinifier.view.viewModelFactory.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.setupActionBarWithNavController


class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var keepSplashScreen = true
        installSplashScreen().setKeepOnScreenCondition { keepSplashScreen }

        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //check session
        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            keepSplashScreen = false
        }

//        setupAction()
    }

//    private fun setupAction() {
//        binding.logoutButton.setOnClickListener {
//            val confirmation = AlertDialog.Builder(this)
//                .setTitle(getString(R.string.logout_confirmation))
//                .setMessage(getString(R.string.are_you_sure_you_want_to_logout))
//                .setPositiveButton(getString(R.string.yes)) { _, _ ->
//                    mainViewModel.logout()
//                }
//                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
//                    dialog.dismiss()
//                }
//                .create()
//            confirmation.show()
//        }
//    }


}