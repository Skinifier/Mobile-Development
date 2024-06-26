package com.capstone.skinifier.view.viewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.skinifier.data.repository.UserRepository
import com.capstone.skinifier.di.Injection
import com.capstone.skinifier.view.editProfile.EditProfileViewModel
import com.capstone.skinifier.view.main.MainViewModel
import com.capstone.skinifier.view.productDetail.ProductDetailViewModel
import com.capstone.skinifier.view.profile.ProfileViewModel
import com.capstone.skinifier.view.register.SignupViewModel
import com.capstone.skinifier.view.result.ResultViewModel
import com.capstone.skinifier.view.soldProduct.SoldProductViewModel
import com.capstone.skinifier.view.wishlist.WishlistViewModel

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(WishlistViewModel::class.java) -> {
                WishlistViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SoldProductViewModel::class.java) -> {
                SoldProductViewModel(repository) as T
            }
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                EditProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> {
                ProductDetailViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context) = ViewModelFactory(Injection.provideRepository(context))
    }
}