package com.capstone.skinifier.view.profile

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.skinifier.data.repository.UserRepository
import com.capstone.skinifier.data.response.ProfileResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

    private val _profileData = MutableLiveData<ProfileResponse>()
    val profileData: LiveData<ProfileResponse> = _profileData

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> = _navigateToLogin

    fun fetchProfile() {
        viewModelScope.launch {
            try {
                val response = repository.getProfile()
                _profileData.value = response
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _navigateToLogin.value = true  // Trigger navigation event
        }
    }

    // Call this function when navigation is completed
    fun navigationComplete() {
        _navigateToLogin.value = false
    }
}