package com.capstone.skinifier.view.editProfile

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.capstone.skinifier.R
import com.capstone.skinifier.data.pref.ProfileData
import com.capstone.skinifier.data.response.ProfileResponse
import com.capstone.skinifier.databinding.ActivityEditProfileBinding
import com.capstone.skinifier.databinding.ActivitySignupBinding
import com.capstone.skinifier.view.custom.MyButtonOutline
import com.capstone.skinifier.view.home.NavigationActivity
import com.capstone.skinifier.view.register.SignupViewModel
import com.capstone.skinifier.view.viewModelFactory.ViewModelFactory

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the dropdown
        val skinType = resources.getStringArray(R.array.skin_type)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, skinType)
        binding.skinTypeField.setAdapter(arrayAdapter)

        // Observe profile data
        editProfileViewModel.profileData.observe(this) { profile ->
            if (profile != null) {
                populateProfileFields(profile)
            }
        }

        editProfileViewModel.fetchProfile()

        binding.save.setOnClickListener {
            updateProfile()
        }

        binding.backButton.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        editProfileViewModel.navigateBackToProfileFragment.observe(this) { navigate ->
            if (navigate) {
                navigateBackToProfileFragment()
            }
        }
    }

    private fun populateProfileFields(profile: ProfileResponse) {
        binding.emailField.setText(profile.email)
        binding.usernameField.setText(profile.username)
        binding.fullnameField.setText(profile.fullname)
        binding.numberField.setText(profile.noHp)
        binding.skinTypeField.setText(profile.skinType, false)

        Glide.with(this)
            .load(profile.photo)
            .placeholder(R.drawable.placeholder)
            .circleCrop()
            .into(binding.photo)
    }

    private fun updateProfile() {
        val profileData = ProfileData(
            email = binding.emailField.text.toString(),
            username = binding.usernameField.text.toString(),
            fullname = binding.fullnameField.text.toString(),
            noHp = binding.numberField.text.toString(),
            skinType = binding.skinTypeField.text.toString()
        )

        editProfileViewModel.updateProfile(profileData)
    }

    private fun navigateBackToProfileFragment() {
        // Navigate back to ProfileFragment
        val intent = Intent(this, NavigationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}