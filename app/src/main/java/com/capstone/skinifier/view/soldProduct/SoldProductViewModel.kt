package com.capstone.skinifier.view.soldProduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.skinifier.data.repository.UserRepository
import com.capstone.skinifier.data.response.SoldProductResponseItem
import kotlinx.coroutines.launch


class SoldProductViewModel(private val userRepository: UserRepository) : ViewModel() {
    val soldProductItems = MutableLiveData<List<SoldProductResponseItem>>()

    fun fetchSoldProducts() {
        viewModelScope.launch {
            try {
                val soldProductResponse = userRepository.getSoldProducts()
                soldProductItems.postValue(soldProductResponse)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}