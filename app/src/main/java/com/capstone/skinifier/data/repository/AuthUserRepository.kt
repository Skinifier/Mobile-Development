package com.capstone.skinifier.data.repository

import com.capstone.skinifier.data.api.ApiService
import com.capstone.skinifier.data.pref.UserModel
import com.capstone.skinifier.data.pref.UserPreference
import com.capstone.skinifier.data.response.LoginResponse

class AuthUserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
) {

    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    companion object {
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ) = AuthUserRepository(apiService, userPreference)
    }
}