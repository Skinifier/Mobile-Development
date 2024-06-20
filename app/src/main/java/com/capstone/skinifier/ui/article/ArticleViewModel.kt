package com.capstone.skinifier.ui.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.skinifier.data.model.ArticleDataModel
import com.capstone.skinifier.data.repository.ArticleRepository
import com.capstone.skinifier.data.repository.UserRepository
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val _articles = MutableLiveData<List<ArticleDataModel>>()
    val articles = _articles

    fun fetchArticles() {
        viewModelScope.launch {
            userRepository.getSession().collect{
                if (it.token.isNotEmpty()){
                    try {
                        val articles = articleRepository.fetchArticles(
                            token = it.token,
                        )
                        _articles.postValue(articles)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        }
    }
}