package com.capstone.skinifier.view.viewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.skinifier.data.repository.UserRepository
import com.capstone.skinifier.di.MLInjection
import com.capstone.skinifier.view.scan.ScanFaceViewModel

class MLViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ScanFaceViewModel::class.java) -> {
                ScanFaceViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context) = MLViewModelFactory(MLInjection.provideRepository(context))
    }
}