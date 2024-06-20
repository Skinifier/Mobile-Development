package com.capstone.skinifier.view.editProfile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.capstone.skinifier.R
import com.capstone.skinifier.data.pref.ProfileData
import com.capstone.skinifier.data.response.ProfileResponse
import com.capstone.skinifier.databinding.ActivityEditProfileBinding
import com.capstone.skinifier.ui.home.NavigationActivity
import com.capstone.skinifier.view.camera.CameraActivity
import com.capstone.skinifier.view.camera.CameraActivity.Companion.CAMERAX_RESULT
import com.capstone.skinifier.view.camera.reduceFileImage
import com.capstone.skinifier.view.camera.uriToFile
import com.capstone.skinifier.view.viewModelFactory.ViewModelFactory
import java.io.File

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private var currentImageUri: Uri? = null
    private val editProfileViewModel: EditProfileViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private var photoFile: File? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


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

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        editProfileViewModel.fetchProfile()


        binding.backButton.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        editProfileViewModel.navigateBackToProfileFragment.observe(this) { navigate ->
            if (navigate) {
                navigateBackToProfileFragment()
            }
        }

        binding.save.setOnClickListener {
            updateProfile()
        }

        editProfileViewModel.updateProfileResult.observe(this) { result ->
            if (result == "Profile updated successfully") {
                navigateBackToProfileFragment()
            } else {
                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            }
        }


        popupMenu()
    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun popupMenu() {
        val popupMenu = PopupMenu(applicationContext,binding.flFrame)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.camera -> {
                    startCameraX()
                    true
                }
                R.id.gallery -> {
                    startGallery()
                    true

                }
                else -> true
            }
        }
        val clickListener = View.OnClickListener {
            try{
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
            } catch (e: Exception){
                e.printStackTrace()
            } finally {
                popupMenu.show()
            }
        }

        binding.flFrame.setOnClickListener(clickListener)
        binding.photo.setOnClickListener(clickListener)
        binding.editButton.setOnClickListener(clickListener)
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.photo.setImageURI(it)
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
            skinType = binding.skinTypeField.text.toString(),
            foto = currentImageUri?.let { uriToFile(it, this).reduceFileImage() }
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

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}