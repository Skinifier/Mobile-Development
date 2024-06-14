package com.capstone.skinifier.view.viewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.skinifier.data.repository.AuthUserRepository
import com.capstone.skinifier.di.AuthInjection
import com.capstone.skinifier.view.login.LoginViewModel

class AuthViewModelFactory(private val repository: AuthUserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context)= AuthViewModelFactory(AuthInjection.provideRepository(context))
    }
}