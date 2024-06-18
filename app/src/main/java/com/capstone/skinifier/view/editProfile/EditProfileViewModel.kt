package com.capstone.skinifier.view.editProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.skinifier.data.pref.ProfileData
import com.capstone.skinifier.data.repository.UserRepository
import com.capstone.skinifier.data.response.ProfileResponse
import kotlinx.coroutines.launch
import java.io.File

class EditProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _profileData = MutableLiveData<ProfileResponse>()
    val profileData: LiveData<ProfileResponse> = _profileData

    fun fetchProfile() {
        viewModelScope.launch {
            try {
                val profile = userRepository.getProfile()
                _profileData.postValue(profile)
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }

    private val _updateProfileResult = MutableLiveData<String>()
    val updateProfileResult: LiveData<String> = _updateProfileResult

    fun updateProfile(profileData: ProfileData) {
        viewModelScope.launch {
            try {
                val response = userRepository.updateProfile(profileData)
                if (response.message == "Profile updated successfully") {
                    _navigateBackToProfileFragment.postValue(true)
                }
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }


    private val _navigateBackToProfileFragment = MutableLiveData<Boolean>()
    val navigateBackToProfileFragment: LiveData<Boolean> = _navigateBackToProfileFragment
}