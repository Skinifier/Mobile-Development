package com.capstone.skinifier.view.profile

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
}