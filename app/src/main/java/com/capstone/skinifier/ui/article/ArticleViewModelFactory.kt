package com.capstone.skinifier.ui.article

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.skinifier.data.repository.ArticleRepository
import com.capstone.skinifier.data.repository.UserRepository
import com.capstone.skinifier.di.Injection
import com.capstone.skinifier.ui.article.detail.ArticleDetailViewModel

class ArticleViewModelFactory(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArticleViewModel(articleRepository, userRepository) as T
        }
        if (modelClass.isAssignableFrom(ArticleDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArticleDetailViewModel(articleRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object{
        fun getInstance(context: Context) = ArticleViewModelFactory(
            userRepository= Injection.provideRepository(context),
            articleRepository = ArticleRepository()
        )
    }
}