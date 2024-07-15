package com.example.projekakhir.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekakhir.data.repository.DataRepository
import com.example.projekakhir.network.model.response.login.LoginRequestBody
import com.example.projekakhir.network.model.response.login.LoginResponse
import com.example.projekakhir.utils.TokenPreferences
import com.example.projekakhir.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private val dataRepository: DataRepository,private val tokenPreferences: TokenPreferences): ViewModel() {
    private val _loginResponse = MutableLiveData<Resource<LoginResponse>>()
    val loginResponse: LiveData<Resource<LoginResponse>> get() = _loginResponse

    fun login(loginRequestBody: LoginRequestBody){
        _loginResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val response = dataRepository.postLogin(loginRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _loginResponse.postValue(response)
            }
        }
    }

    fun saveEmail(email:String){
        viewModelScope.launch(Dispatchers.IO) {
            tokenPreferences.saveEmail(email)
        }
    }
    fun saveIdNSaveToken(token: String){
        viewModelScope.launch(Dispatchers.IO) {
            tokenPreferences.saveToken(token)
        }
    }

    fun saveFirstNameNLastName(firstName:String,lastName:String){
        viewModelScope.launch(Dispatchers.IO) {
            tokenPreferences.saveFirstName(firstName)
            tokenPreferences.saveLastName(lastName)
        }
    }

    fun saveNpmUser(npm: String){
        viewModelScope.launch(Dispatchers.IO) {
            tokenPreferences.saveNpmUser(npm)
        }
    }

    fun saveRoleUser(role:String){
        viewModelScope.launch(Dispatchers.IO) {
            tokenPreferences.saveRole(role)
        }
    }

}