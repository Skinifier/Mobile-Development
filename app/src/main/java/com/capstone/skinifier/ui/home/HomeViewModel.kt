package com.capstone.skinifier.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.skinifier.data.model.ArticleDataModel
import com.capstone.skinifier.data.model.ProductModelItem
import com.capstone.skinifier.data.model.ProfileDataModel
import com.capstone.skinifier.data.repository.ArticleRepository
import com.capstone.skinifier.data.repository.ProductRepository
import com.capstone.skinifier.data.repository.ProfileRepository
import com.capstone.skinifier.data.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val profileRepository: ProfileRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _profile = MutableLiveData<ProfileDataModel>()
    val profile = _profile

    private val _products = MutableLiveData<List<ProductModelItem>>()
    val products = _products

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

    fun fetchRecProducts(skinType: String) {
        viewModelScope.launch {
            userRepository.getSession().collect{
                if (it.token.isNotEmpty()){
                    try {
                        val products = productRepository.getAllProducts(
                            token = it.token,
                            skinType = skinType
                        )
                        _products.postValue(products)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        }
    }

    fun fetchProfile() {
        viewModelScope.launch {
            userRepository.getSession().collect {
                if (it.token.isNotEmpty()) {
                    val response = profileRepository.fetchProfileData(it.token)
                    _profile.value = response
                }
            }
        }
    }
}