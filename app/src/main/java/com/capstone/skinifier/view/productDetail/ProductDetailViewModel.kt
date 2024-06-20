package com.capstone.skinifier.view.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.skinifier.data.repository.UserRepository
import com.capstone.skinifier.data.response.AddWishlistResponse
import com.capstone.skinifier.data.response.DeleteWishlistResponse
import com.capstone.skinifier.data.response.GetWishlistResponseItem
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val repository: UserRepository) : ViewModel() {

    fun getWishlistItems(onResult: (List<GetWishlistResponseItem>) -> Unit) {
        viewModelScope.launch {
            val wishlistItems = repository.getWishlist()
            onResult(wishlistItems)
        }
    }

    fun addWishlistItem(productId: String, quantity: Int, onResult: (AddWishlistResponse) -> Unit) {
        viewModelScope.launch {
            val response = repository.addWishlistItem(productId, quantity)
            onResult(response)
        }
    }

    fun deleteWishlistItem(wishlistId: String, onResult: (DeleteWishlistResponse) -> Unit) {
        viewModelScope.launch {
            val response = repository.deleteWishlistItem(wishlistId)
            onResult(response)
        }
    }
}