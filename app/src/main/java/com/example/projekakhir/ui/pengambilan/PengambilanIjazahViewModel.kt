package com.example.projekakhir.ui.pengambilan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.projekakhir.data.repository.DataRepository
import com.example.projekakhir.network.model.response.getStudent.GetStudentByStudentIdResponse
import com.example.projekakhir.network.model.response.graduateform.GraduateFormRequestBody
import com.example.projekakhir.network.model.response.graduateform.GraduateFormResponse
import com.example.projekakhir.utils.TokenPreferences
import com.example.projekakhir.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PengambilanIjazahViewModel(private val dataRepository: DataRepository, private val tokenPreferences: TokenPreferences): ViewModel(){
    private val _graduateForm = MutableLiveData<Resource<GraduateFormResponse>>()
    val graduateForm : LiveData<Resource<GraduateFormResponse>> get() = _graduateForm

    private val _getStudentByStudentIdResponse = MutableLiveData<Resource<GetStudentByStudentIdResponse>>()
    val getStudentByStudentIdResponse: LiveData<Resource<GetStudentByStudentIdResponse>> get() = _getStudentByStudentIdResponse

    //graduateForm
    fun saveGraduateForm(graduateFormRequestBody: GraduateFormRequestBody){
        _graduateForm.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _graduateForm.postValue(dataRepository.postGraduateForm(tokenPreferences.getToken().orEmpty(),graduateFormRequestBody))
        }
    }

    val npm: LiveData<String?> = liveData {
        val npm = tokenPreferences.getNpmUser().orEmpty()
        emit("$npm")
    }
    val fullName: LiveData<String?> = liveData {
        val firstName = tokenPreferences.getFirstName().orEmpty()
        val lastName = tokenPreferences.getLastName().orEmpty()
        emit("$firstName $lastName")
    }

    fun getStudent(){
        viewModelScope.launch(Dispatchers.IO) {
            _getStudentByStudentIdResponse.postValue(dataRepository.getStudentByStudentId(tokenPreferences.getToken().orEmpty(),tokenPreferences.getNpmUser().orEmpty()))
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
}