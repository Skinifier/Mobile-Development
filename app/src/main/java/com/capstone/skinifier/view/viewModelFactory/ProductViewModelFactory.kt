package com.capstone.skinifier.view.viewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.skinifier.data.repository.ArticleRepository
import com.capstone.skinifier.data.repository.ProductRepository
import com.capstone.skinifier.data.repository.ProfileRepository
import com.capstone.skinifier.data.repository.UserRepository
import com.capstone.skinifier.di.Injection
import com.capstone.skinifier.ui.home.HomeViewModel
import com.capstone.skinifier.ui.product.ProductViewModel

class ProductViewModelFactory(
    private val repository: ProductRepository,
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository,
    private val articleRepository: ArticleRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository, userRepository) as T
        }
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(profileRepository, userRepository,
                productRepository = repository, articleRepository = articleRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object{
        fun getInstance(context: Context) = ProductViewModelFactory(
            repository = ProductRepository(),
            userRepository=Injection.provideRepository(context),
            profileRepository = ProfileRepository(),
            articleRepository = ArticleRepository()
        )
    }
}
