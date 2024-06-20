package com.capstone.skinifier.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.skinifier.data.pref.UserModel
import com.capstone.skinifier.data.repository.UserRepository
import com.capstone.skinifier.data.response.ProfileResponse
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _profileResult = MutableLiveData<Result<ProfileResponse>>()
    val profileResult: LiveData<Result<ProfileResponse>> = _profileResult

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getProfile() {
        viewModelScope.launch {
            try {
                val response = repository.getProfile()
                _profileResult.value = Result.success(response)
            } catch (e: Exception) {
                _profileResult.value = Result.failure(e)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}