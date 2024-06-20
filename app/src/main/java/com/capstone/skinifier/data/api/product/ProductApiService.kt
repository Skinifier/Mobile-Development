package com.capstone.skinifier.data.api.product

import com.capstone.skinifier.data.api.Product
import com.capstone.skinifier.data.response.product.ProductDataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {
    @GET("barang")
    suspend fun getAllProducts(
        @Header("Authorization") token: String,
        @Query("skin_type") skinType: String,
        @Query("name_brand") brandName: String
    ): List<ProductDataResponse>

    @GET("barang")
    suspend fun getProductsBySkinType(
        @Query("skin_type") skinType: String,
    ): List<Product>

    @GET("barang/{id}")
    suspend fun getProductDetails(
        @Header("Authorization") token: String,
        @Path("id") productId: String
    ): Product

    @Multipart
    @POST("barang")
    suspend fun uploadProduct(
        @Header("Authorization") token: String,
        @Part("nama_brand") namaBrand: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("no_hp") noHp: RequestBody,
        @Part foto: MultipartBody.Part,
        @Part("bahan") bahan: RequestBody,
        @Part("jenis_produk") jenisProduk: RequestBody,
        @Part("nama_barang") namaBarang: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("skin_type") skinType: RequestBody,
        @Part("domisili") domisili: RequestBody
    )
}