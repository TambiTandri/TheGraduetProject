package com.example.projekakhir.ui.berhasilsimpanijasah

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.projekakhir.data.repository.DataRepository
import com.example.projekakhir.network.model.response.getgraduateform.GetGraduateFormResponse
import com.example.projekakhir.network.model.response.graduateform.upadategraduateform.UpdateGraduateForm
import com.example.projekakhir.network.model.response.graduateform.upadategraduateform.UpdateGraduateFormResponse
import com.example.projekakhir.utils.TokenPreferences
import com.example.projekakhir.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BerhasilSimpanIjazahViewModel(private val dataRepository: DataRepository, private val tokenPreferences: TokenPreferences) : ViewModel() {

    private val _updateGraduateFormResponse = MutableLiveData<Resource<UpdateGraduateFormResponse>>()
    val updateGraduateFormResponse: LiveData<Resource<UpdateGraduateFormResponse>> get() = _updateGraduateFormResponse

    private val _getGraduateFormByNpm = MutableLiveData<Resource<GetGraduateFormResponse>>()
    val getGraduateFormByNpm: LiveData<Resource<GetGraduateFormResponse>> get() = _getGraduateFormByNpm

    fun updateGraduateForm(updateGraduateForm: UpdateGraduateForm) {
        _updateGraduateFormResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _updateGraduateFormResponse.postValue(
                dataRepository.updateGraduateForm(tokenPreferences.getToken().orEmpty(),updateGraduateForm)
            )
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
            _getGraduateFormByNpm.postValue(dataRepository.getGraduateForm(tokenPreferences.getToken().orEmpty(),tokenPreferences.getNpmUser().orEmpty()))
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