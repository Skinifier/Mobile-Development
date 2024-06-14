package com.capstone.skinifier.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.skinifier.data.repository.UserRepository
import com.capstone.skinifier.di.ResultState
import kotlinx.coroutines.launch

class SignupViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<ResultState<String>>()
    val registerResult: LiveData<ResultState<String>> = _registerResult

    fun register(email: String, username: String, fullname: String, number: String, skinType: String, password: String) {
        _registerResult.value = ResultState.Loading
        viewModelScope.launch {
            try {
                val response = userRepository.register(email, username, fullname, number, skinType, password)
                _registerResult.value = ResultState.Success(response.message)
            } catch (e: Exception) {
                _registerResult.value = ResultState.Error(e.message.toString())
            }
        }
    }

//
//    fun register(name: String, email: String, password: String) {
//        _registerResult.value = ResultState.Loading
//        viewModelScope.launch {
//            try {
////                val response = userRepository.apiService.register(name, email, password)
//                val response = userRepository.register(name, email, password)
//                if (!response.error) {
//                    _registerResult.value = ResultState.Success(response.message)
//                } else {
//                    _registerResult.value = ResultState.Error(response.message)
//                }
//            } catch (e: Exception) {
//                _registerResult.value = ResultState.Error(e.message ?: "An error occurred")
//            }
//        }
//    }
}