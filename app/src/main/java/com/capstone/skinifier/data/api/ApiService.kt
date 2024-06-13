package com.capstone.skinifier.data.api

import com.capstone.skinifier.data.response.LoginResponse
import com.capstone.skinifier.data.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("fullname") name: String,
        @Field("no_hp") noHp: String,
        @Field("skin_type") skinType: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
}