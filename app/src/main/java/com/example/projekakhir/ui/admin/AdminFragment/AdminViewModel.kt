package com.example.projekakhir.ui.admin.AdminFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekakhir.data.repository.DataRepository
import com.example.projekakhir.network.model.response.getAllStudent.GetAllStudentResponse
import com.example.projekakhir.network.model.response.verified.VerifiedResponse
import com.example.projekakhir.network.model.response.verified.VerifiedSklRequestBody
import com.example.projekakhir.utils.TokenPreferences
import com.example.projekakhir.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminViewModel(private val dataRepository: DataRepository, private val tokenPreferences: TokenPreferences) : ViewModel() {
    private val _listStudentResponse = MutableLiveData<Resource<GetAllStudentResponse>>(Resource.Loading())
    val listStudentResponse: LiveData<Resource<GetAllStudentResponse>> get() = _listStudentResponse
    //verif
    private val _patchVerifResponse = MutableLiveData<Resource<VerifiedResponse>>()
    val patchVerifResponse:LiveData<Resource<VerifiedResponse>> get() = _patchVerifResponse

    fun getAllStudent() {
        viewModelScope.launch {
            _listStudentResponse.postValue( dataRepository.getAllStudent(tokenPreferences.getToken().orEmpty()))
            Log.d("guru",dataRepository.getAllStudent(tokenPreferences.getToken().orEmpty()).payload?.data.toString())
        }
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
    //verif
    fun patchVerif(verifiedSklRequestBody: VerifiedSklRequestBody,studentId:String) {
        _patchVerifResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _patchVerifResponse.postValue(
                dataRepository.patchVerifSkl(
                    tokenPreferences.getToken().orEmpty(),
                    verifiedSklRequestBody,
                    studentId
                )
            )
        }
    }

}