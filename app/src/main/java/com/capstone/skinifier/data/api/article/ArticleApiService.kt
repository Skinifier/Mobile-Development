package com.capstone.skinifier.data.api.article

import com.capstone.skinifier.data.response.article.ArticleDataResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ArticleApiService {
    @GET("articles")
    suspend fun getAllProducts(
        @Header("Authorization") token: String,
    ): List<ArticleDataResponse>

}