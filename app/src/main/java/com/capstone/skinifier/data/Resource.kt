package com.capstone.skinifier.data

sealed class Resource<out T> {
    data object Loading: Resource<Nothing>()
    data class Success<T>(val data: T): Resource<T>()
    data class Error<T>(val error: String): Resource<T>()
}