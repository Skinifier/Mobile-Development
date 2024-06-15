package com.capstone.skinifier.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.skinifier.data.pref.UserModel
import com.capstone.skinifier.data.repository.AuthUserRepository
import com.capstone.skinifier.data.response.LoginResponse
import com.capstone.skinifier.di.ResultState
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: AuthUserRepository) : ViewModel() {

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

//    private val _loginResult = MutableLiveData<ResultState<LoginResponse>>()
//    val loginResult: LiveData<ResultState<LoginResponse>> = _loginResult

    private val _loginResult = MutableLiveData<ResultState<String>>()
    val loginResult: LiveData<ResultState<String>> = _loginResult

    fun login(email: String, password: String) {
        _loginResult.value = ResultState.Loading
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                val user = UserModel(email, response.token, true)
                repository.saveSession(user)
                _loginResult.value = ResultState.Success("Login successful")
            } catch (e: Exception) {
                _loginResult.value = ResultState.Error(e.message.toString())
            }
        }
    }

}