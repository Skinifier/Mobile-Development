package com.capstone.skinifier.ui.article.detail

import com.capstone.skinifier.data.repository.ArticleRepository
import com.capstone.skinifier.data.repository.UserRepository

class ArticleDetailViewModel(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository
) {
}