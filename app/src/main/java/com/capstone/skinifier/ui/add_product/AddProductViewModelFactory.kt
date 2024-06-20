package com.capstone.skinifier.ui.add_product

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.skinifier.data.repository.ProductRepository
import com.capstone.skinifier.data.repository.UserRepository
import com.capstone.skinifier.di.Injection

class AddProductViewModelFactory(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository,
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddProductViewModel(productRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object{
        fun getInstance(context: Context) = AddProductViewModelFactory(
            productRepository = ProductRepository(),
            userRepository= Injection.provideRepository(context)
        )
    }
}