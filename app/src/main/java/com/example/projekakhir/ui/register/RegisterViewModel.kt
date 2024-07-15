package com.example.projekakhir.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekakhir.data.repository.DataRepository
import com.example.projekakhir.network.model.response.register.RegisterRequestBody
import com.example.projekakhir.network.model.response.register.RegisterResponse
import com.example.projekakhir.utils.Resource
import kotlinx.coroutines.launch

class RegisterViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _registerResponse = MutableLiveData<Resource<RegisterResponse>>()
    val registerResponse : LiveData<Resource<RegisterResponse>> get() = _registerResponse

    fun register(registerRequestBody: RegisterRequestBody){
        _registerResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            _registerResponse.postValue(dataRepository.postRegist(registerRequestBody))
        }
    }
}