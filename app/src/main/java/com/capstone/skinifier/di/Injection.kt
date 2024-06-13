package com.capstone.skinifier.di

import android.content.Context
import com.capstone.skinifier.data.api.ApiConfig
import com.capstone.skinifier.data.pref.UserPreference
import com.capstone.skinifier.data.pref.dataStore
import com.capstone.skinifier.data.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(apiService, pref)
    }
}