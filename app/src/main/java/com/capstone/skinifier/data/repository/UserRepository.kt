package com.capstone.skinifier.data.repository

import com.capstone.skinifier.data.api.ApiService
import com.capstone.skinifier.data.api.RegisterRequest
import com.capstone.skinifier.data.pref.ProfileData
import com.capstone.skinifier.data.pref.UserModel
import com.capstone.skinifier.data.pref.UserPreference
import com.capstone.skinifier.data.response.DetailBarangResponse
import com.capstone.skinifier.data.response.EditProfileResponse
import com.capstone.skinifier.data.response.GetWishlistResponseItem
import com.capstone.skinifier.data.response.ProfileResponse
import com.capstone.skinifier.data.response.RegisterResponse
import com.capstone.skinifier.data.response.SoldProductResponseItem
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class UserRepository private constructor(
    private val apiService: ApiService,
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

    suspend fun getWishlist(): List<GetWishlistResponseItem> {
        return apiService.getWishlist()
    }

    suspend fun getItemDetail(idBarang: String): DetailBarangResponse {
        return apiService.getItemDetail(idBarang)
    }

    suspend fun getSoldProducts(): List<SoldProductResponseItem> {
        return apiService.getSoldProduct()
    }


//    suspend fun updateProfile(profileData: ProfileData): EditProfileResponse {
//        val formData = MultipartBody.Builder()
//            .setType(MultipartBody.FORM)
//            .addFormDataPart("email", profileData.email)
//            .addFormDataPart("username", profileData.username)
//            .addFormDataPart("fullname", profileData.fullname)
//            .addFormDataPart("no_hp", profileData.noHp)
//            .addFormDataPart("skin_type", profileData.skinType)
//            .build()
//
//        return apiService.updateProfile(formData)
//    }

    suspend fun updateProfile(profileData: ProfileData): EditProfileResponse {
        val formDataBuilder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("email", profileData.email)
            .addFormDataPart("username", profileData.username)
            .addFormDataPart("fullname", profileData.fullname)
            .addFormDataPart("no_hp", profileData.noHp)
            .addFormDataPart("skin_type", profileData.skinType)

        profileData.foto?.let { file ->
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            formDataBuilder.addFormDataPart("foto", file.name, requestFile)
        }

        val formData = formDataBuilder.build()
        return apiService.updateProfile(formData)
    }



    companion object {
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ) = UserRepository(apiService, userPreference)
    }
}