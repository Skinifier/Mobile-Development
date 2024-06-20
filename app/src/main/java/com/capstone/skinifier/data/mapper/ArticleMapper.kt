package com.capstone.skinifier.data.mapper

import com.capstone.skinifier.data.model.ArticleDataModel
import com.capstone.skinifier.data.response.article.ArticleDataResponse

object ArticleMapper {
    fun articleDataResponseToMapper(q: ArticleDataResponse) = ArticleDataModel(
        foto = q.foto ?: "",
        updatedAt = q.updatedAt ?: "",
        createdAt = q.createdAt ?: "",
        id = q.id ?: "",
        deskripsi = q.deskripsi ?: "",
        judul = q.judul ?: ""
    )
}