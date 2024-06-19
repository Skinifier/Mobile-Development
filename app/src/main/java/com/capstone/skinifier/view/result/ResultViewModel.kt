package com.capstone.skinifier.view.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.skinifier.data.repository.UserRepository
import com.capstone.skinifier.data.response.GetAllBarangResponseItem
import kotlinx.coroutines.launch

class ResultViewModel(private val repository: UserRepository) : ViewModel() {

    private val _recommendedProducts = MutableLiveData<List<GetAllBarangResponseItem>>()
    val recommendedProducts: LiveData<List<GetAllBarangResponseItem>> = _recommendedProducts

    fun fetchRecommendedProducts(skinCondition: String) {
        viewModelScope.launch {
            try {
                val products = repository.getRecomendedBarang()
                val filteredProducts = products.filter { it.skinType == skinCondition }.shuffled().take(3)
                val randomProducts = products.filter { it.skinType != skinCondition }.shuffled().take(3 - filteredProducts.size)
                _recommendedProducts.postValue(filteredProducts + randomProducts)
            } catch (e: Exception) {
                // Handle error
                _recommendedProducts.postValue(emptyList()) // Or handle error properly
            }
        }
    }
}