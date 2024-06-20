package com.capstone.skinifier.data.api

import com.capstone.skinifier.data.response.AddWishlistResponse
import com.capstone.skinifier.data.response.DeleteWishlistResponse
import com.capstone.skinifier.data.response.DetailBarangResponse
import com.capstone.skinifier.data.response.EditProfileResponse
import com.capstone.skinifier.data.response.GetAllBarangResponseItem
import com.capstone.skinifier.data.response.GetWishlistResponseItem
import com.capstone.skinifier.data.response.LoginResponse
import com.capstone.skinifier.data.response.PredictResponse
import com.capstone.skinifier.data.response.ProfileResponse
import com.capstone.skinifier.data.response.RegisterResponse
import com.capstone.skinifier.data.response.SoldProductResponseItem
import com.capstone.skinifier.data.response.whislist.WhislistResponseItem
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
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

data class AddWishlistRequest(
    val id_barang: String,
    val jumlah_barang: Int
)

data class Product(
    val id: String,
    val nama_brand: String,
    val harga: String,
    val no_hp: String,
    val foto: String,
    val bahan: String,
    val jenis_produk: String,
    val nama_barang: String,
    val id_user: String,
    val deskripsi: String,
    val skin_type: String,
    val domisili: String,
    val updated_at: String,
    val created_at: String
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

    @POST("wishlist")
    suspend fun addWishlist(
        @Body addWishlistRequest: AddWishlistRequest
    ): AddWishlistResponse

    @GET("wishlist")
    suspend fun getWishlist(): List<WhislistResponseItem>

    @DELETE("wishlist/{id_wishlist}")
    suspend fun deleteWishlist(
        @Path("id_wishlist") idWishlist: String
    ): DeleteWishlistResponse

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

    @POST("predict")
    suspend fun predictML(
        @Body formData: MultipartBody
    ): PredictResponse

    @GET("barang")
    suspend fun getRecomendedBarang(): List<GetAllBarangResponseItem>
}