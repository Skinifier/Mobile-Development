package com.capstone.skinifier.data.repository

import androidx.lifecycle.LiveData
import com.capstone.skinifier.data.api.ApiService
import com.capstone.skinifier.data.api.RegisterRequest
import com.capstone.skinifier.data.pref.UserModel
import com.capstone.skinifier.data.pref.UserPreference
import com.capstone.skinifier.data.response.ProfileResponse
import com.capstone.skinifier.data.response.RegisterResponse
import com.capstone.skinifier.di.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import java.io.File

class UserRepository private constructor(
    val apiService: ApiService,
    private val userPreference: UserPreference,
) {



    suspend fun register(email: String, username: String, fullname: String, number: String, skinType: String, password: String): RegisterResponse {
        val registerRequest = RegisterRequest(email, username, fullname, number, skinType, password)
        return apiService.register(registerRequest)
    }



    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun getProfile(): ProfileResponse {
        return apiService.getProfile()
    }


    companion object {
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ) = UserRepository(apiService, userPreference)
    }
}