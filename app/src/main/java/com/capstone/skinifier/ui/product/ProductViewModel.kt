package com.capstone.skinifier.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.skinifier.data.model.ProductModelItem
import com.capstone.skinifier.data.repository.ProductRepository
import com.capstone.skinifier.data.repository.UserRepository
import com.capstone.skinifier.data.util.SkinType
import kotlinx.coroutines.launch

class ProductViewModel(

    private val repository: ProductRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _products = MutableLiveData<List<ProductModelItem>>()
    val products: LiveData<List<ProductModelItem>> get() = _products

    fun fetchProductsByName(name: String) {
        viewModelScope.launch {
            userRepository.getSession().collect{
                if (it.token.isNotEmpty()){
                    try {
                        val products = repository.getAllProducts(
                            token = it.token,
                            brandName = name
                        )
                        _products.postValue(products)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        }
    }

    fun fetchProductsByType(type: SkinType) {
        viewModelScope.launch {
            userRepository.getSession().collect{
                if (it.token.isNotEmpty()){
                    try {
                        val products = repository.getAllProducts(
                            token = it.token,
                            skinType = type.skinType
                        )
                        _products.postValue(products)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        }
    }

    fun fetchAllProducts() {
        viewModelScope.launch {
            userRepository.getSession().collect{
                if (it.token.isNotEmpty()){
                    try {
                        val products = repository.getAllProducts(it.token)
                        _products.postValue(products)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        }
    }
}

