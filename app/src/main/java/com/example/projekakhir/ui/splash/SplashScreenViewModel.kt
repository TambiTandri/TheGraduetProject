package com.example.projekakhir.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.projekakhir.utils.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreenViewModel(private val tokenPreferences: TokenPreferences) : ViewModel() {
}