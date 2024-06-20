package com.capstone.skinifier.data.api.profile

import com.capstone.skinifier.data.api.Product
import com.capstone.skinifier.data.response.profile.ProfileDataResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ProfileApiService {
    @GET("users")
    suspend fun getProfile(
        @Header("Authorization") token: String,
    ): ProfileDataResponse
}