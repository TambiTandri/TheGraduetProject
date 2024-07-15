package com.example.projekakhir.ui.dashboardfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.projekakhir.data.repository.DataRepository
import com.example.projekakhir.network.model.response.documen.getDoc.GetDocumentResponse
import com.example.projekakhir.utils.TokenPreferences
import com.example.projekakhir.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel (private val tokenPreferences: TokenPreferences,private val dataRepository: DataRepository): ViewModel(){

    private val _getDocResponse = MutableLiveData<Resource<GetDocumentResponse>>()
    val getDocResponse: LiveData<Resource<GetDocumentResponse>> get() = _getDocResponse

    fun getDoc(){
        viewModelScope.launch(Dispatchers.IO){
            _getDocResponse.postValue(dataRepository.getDoc(tokenPreferences.getToken().orEmpty(),tokenPreferences.getNpmUser().orEmpty()))
        }
    }

    val fullName: LiveData<String?> = liveData {
        val firstName = tokenPreferences.getFirstName().orEmpty()
        val lastName = tokenPreferences.getLastName().orEmpty()
        emit("$firstName $lastName")
    }

    fun deleteSession(){
        viewModelScope.launch(Dispatchers.IO) {
            tokenPreferences.deleteToken()
            tokenPreferences.deleteFirstName()
            tokenPreferences.deleteLastName()
            tokenPreferences.deleteNpmUser()
            tokenPreferences.deleteEmail()
            tokenPreferences.deleteRole()
        }
    }
}