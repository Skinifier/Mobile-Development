package com.capstone.skinifier.data.repository

import android.util.Log
import com.capstone.skinifier.data.api.NewApiConfig
import com.capstone.skinifier.data.mapper.ArticleMapper.articleDataResponseToMapper
import com.capstone.skinifier.data.model.ArticleDataModel
import com.capstone.skinifier.data.response.article.ArticleDataResponse

class ArticleRepository {
    private val articleApiService = NewApiConfig.getArticleApiService()

    suspend fun fetchArticles(token: String): List<ArticleDataModel> {
        var response: List<ArticleDataResponse> = listOf()
        try {
            response = articleApiService.getAllProducts("Bearer $token")
        } catch (e: Exception) {
            Log.e("AR", e.message.toString())
        }
        return response.map {
            articleDataResponseToMapper(it)
        }
    }
}