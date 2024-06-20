package com.capstone.skinifier.data.api

import com.capstone.skinifier.data.api.article.ArticleApiService
import com.capstone.skinifier.data.api.product.ProductApiService
import com.capstone.skinifier.data.api.profile.ProfileApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewApiConfig {
    private val retrofit = RetrofitBuilder.getRetrofitBuild()
    fun getProductApiService(): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }

    fun getProfileApiService(): ProfileApiService {
        return retrofit.create(ProfileApiService::class.java)
    }

    fun getArticleApiService(): ArticleApiService {
        return retrofit.create(ArticleApiService::class.java)
    }
}