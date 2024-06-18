package com.capstone.skinifier.data.api

import com.capstone.skinifier.data.response.DetailBarangResponse
import com.capstone.skinifier.data.response.EditProfileResponse
import com.capstone.skinifier.data.response.GetWishlistResponseItem
import com.capstone.skinifier.data.response.LoginResponse
import com.capstone.skinifier.data.response.ProfileResponse
import com.capstone.skinifier.data.response.RegisterResponse
import com.capstone.skinifier.data.response.SoldProductResponseItem
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


data class RegisterRequest(
    val email: String,
    val username: String,
    val fullname: String,
    val no_hp: String,
    val skin_type: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

interface ApiService {
    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("users")
    suspend fun getProfile(): ProfileResponse

    @GET("wishlist")
    suspend fun getWishlist(): List<GetWishlistResponseItem>

    @GET("barang/{id_barang}")
    suspend fun getItemDetail(
        @Path("id_barang") idBarang: String
    ): DetailBarangResponse

    @GET("barang/users")
    suspend fun getSoldProduct(): List<SoldProductResponseItem>

    @PUT("users")
    suspend fun updateProfile(
        @Body formData: MultipartBody
    ): EditProfileResponse
}