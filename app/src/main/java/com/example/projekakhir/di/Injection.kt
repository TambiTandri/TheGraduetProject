package com.example.projekakhir.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.projekakhir.data.repository.DataRepository
import com.example.projekakhir.network.service.ApiConfig
import com.example.projekakhir.utils.TokenPreferences
import com.example.projekakhir.utils.dataStore

object Injection{
    fun provideRepository(context:Context):DataRepository{
        val apiService = ApiConfig.getApiService(context)
        return DataRepository(apiService,context)
    }

    fun provideDataStore(context: Context): TokenPreferences {
        val dataStore: DataStore<Preferences> = context.dataStore
        val tokenPreferences = TokenPreferences.getInstance(dataStore)
        return tokenPreferences
    }
}