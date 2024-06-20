package com.capstone.skinifier.view.wishlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.skinifier.data.repository.UserRepository
import com.capstone.skinifier.data.response.DetailBarangResponse
import com.capstone.skinifier.data.response.GetWishlistResponseItem
import com.capstone.skinifier.data.response.whislist.WhislistResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WishlistViewModel(private val userRepository: UserRepository) : ViewModel() {
    val wishlistItems = MutableLiveData<List<DetailBarangResponse>>()

    fun fetchWishlist() {
        viewModelScope.launch {
            try {
                Log.d("WishlistViewModel", "Fetching wishlist")
                val wishlistResponse: List<WhislistResponseItem> = userRepository.getWishlist()
                Log.d("WishlistViewModel", "Wishlist response received: $wishlistResponse")

                val itemList = mutableListOf<DetailBarangResponse>()

                for (item in wishlistResponse) {
                    item.idBarang?.let { idBarang ->
                        try {
                            Log.d("WishlistViewModel", "Fetching details for item: $idBarang")
                            val itemDetail = userRepository.getItemDetail(idBarang)
                            Log.d("WishlistViewModel", "Received item detail: $itemDetail")
                            itemList.add(itemDetail)
                        } catch (e: Exception) {
                            // Log the error for fetching item details
                            e.printStackTrace()
                            Log.e("WishlistViewModel", "Error fetching item details: ${e.message}")
                        }
                    }
                }

                withContext(Dispatchers.Main) {
                    wishlistItems.postValue(itemList)
                    Log.d("WishlistViewModel", "Wishlist items updated")
                }
            } catch (e: Exception) {
                // Handle exceptions
                e.printStackTrace()
                Log.e("WishlistViewModel", "Error fetching wishlist: ${e.message}")
            }
        }
    }
}