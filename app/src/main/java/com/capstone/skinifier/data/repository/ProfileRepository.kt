package com.capstone.skinifier.data.repository

import android.util.Log
import com.capstone.skinifier.data.api.NewApiConfig
import com.capstone.skinifier.data.mapper.ProfileMapper.mapProfileResponseToModel
import com.capstone.skinifier.data.model.ProfileDataModel
import com.capstone.skinifier.data.response.profile.ProfileDataResponse

class ProfileRepository {
    private val profileApiService = NewApiConfig.getProfileApiService()

    suspend fun fetchProfileData(token: String): ProfileDataModel {
        var response = ProfileDataResponse(
            noHp = null,
            photo = null,
            id = null,
            fullname = null,
            skinType = null,
            email = null,
            username = null
        )
        try {
            response = profileApiService.getProfile(token = "Bearer $token")

        } catch (e: Exception) {
            Log.e("PR", e.toString())
        }
        return response.run {
            mapProfileResponseToModel(this)
        }
    }
}