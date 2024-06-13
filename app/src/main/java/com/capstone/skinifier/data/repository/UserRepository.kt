package com.capstone.skinifier.data.repository

import androidx.lifecycle.LiveData
import com.capstone.skinifier.data.api.ApiService
import com.capstone.skinifier.data.pref.UserModel
import com.capstone.skinifier.data.pref.UserPreference
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

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

//    suspend fun getStories(): Result<StoryResponse> {
//        return try {
//            val response = apiService.getStories()
//            Result.success(response)
//        } catch (e: HttpException) {
//            Result.failure(e)
//        } catch (e: IOException) {
//            Result.failure(e)
//        }
//    }
//
//    fun getStories(): LiveData<PagingData<ListStoryItem>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5,
//            ),
//            pagingSourceFactory = {
//                PagingSource(apiService)
//            }
//        ).liveData
//    }
//
//    suspend fun getStoriesWithLocation(): Result<StoryResponse> {
//        return try {
//            val response = apiService.getStoriesWithLocation()
//            Result.success(response)
//        } catch (e: HttpException) {
//            Result.failure(e)
//        } catch (e: IOException) {
//            Result.failure(e)
//        }
//    }
//
//    suspend fun getStoryDetail(id: String): DetailResponse {
//        return apiService.getStoryDetail(id)
//    }
//
//    fun uploadImage(imageFile: File, description: String) = liveData {
//        emit(ResultState.Loading)
//        val requestBody = description.toRequestBody("text/plain".toMediaType())
//        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
//        val multipartBody = MultipartBody.Part.createFormData(
//            "photo",
//            imageFile.name,
//            requestImageFile
//        )
//        try {
//            val successResponse = apiService.uploadImage(multipartBody, requestBody)
//            emit(ResultState.Success(successResponse))
//        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = Gson().fromJson(errorBody, UploadResponse::class.java)
//            emit(ResultState.Error(errorResponse.message))
//        }
//
//    }
//
//    fun guestUploadImage(imageFile: File, description: String) = liveData {
//        emit(ResultState.Loading)
//        val requestBody = description.toRequestBody("text/plain".toMediaType())
//        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
//        val multipartBody = MultipartBody.Part.createFormData(
//            "photo",
//            imageFile.name,
//            requestImageFile
//        )
//        try {
//            val successResponse = apiService.guestUploadImage(multipartBody, requestBody)
//            emit(ResultState.Success(successResponse))
//        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = Gson().fromJson(errorBody, UploadResponse::class.java)
//            emit(ResultState.Error(errorResponse.message))
//        }
//
//    }


    companion object {
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ) = UserRepository(apiService, userPreference)
    }
}