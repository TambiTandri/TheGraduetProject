package com.example.projekakhir.ui.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekakhir.data.repository.DataRepository
import com.example.projekakhir.network.model.response.forgotpw.ForgotPasswordRequestBody
import com.example.projekakhir.network.model.response.forgotpw.ForgotPasswordResponse
import com.example.projekakhir.utils.Resource
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _forgotPasswordResponse = MutableLiveData<Resource<ForgotPasswordResponse>>()
    val forgotPasswordResponse : LiveData<Resource<ForgotPasswordResponse>> get() = _forgotPasswordResponse

    fun forgotPassword(forgotPasswordRequestBody: ForgotPasswordRequestBody){
        _forgotPasswordResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            _forgotPasswordResponse.postValue(dataRepository.postForgotPassword(forgotPasswordRequestBody))
        }
    }
}