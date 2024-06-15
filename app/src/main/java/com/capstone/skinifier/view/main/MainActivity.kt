package com.capstone.skinifier.view.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.skinifier.R
import com.capstone.skinifier.databinding.ActivityMainBinding
import com.capstone.skinifier.view.login.LoginActivity
import com.capstone.skinifier.view.register.SignupActivity
import com.capstone.skinifier.view.viewModelFactory.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ini jangan diubah, buat check session
        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        mainViewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it+ getString(R.string.loginAgain), Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        //ubah darisini
        setupAction()
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            val confirmation = AlertDialog.Builder(this)
                .setTitle(getString(R.string.logout_confirmation))
                .setMessage(getString(R.string.are_you_sure_you_want_to_logout))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    mainViewModel.logout()
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
            confirmation.show()
        }
    }


}