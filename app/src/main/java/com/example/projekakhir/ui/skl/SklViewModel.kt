package com.example.projekakhir.ui.skl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekakhir.data.repository.DataRepository
import com.example.projekakhir.network.model.response.getStudent.GetStudentByStudentIdResponse
import com.example.projekakhir.network.model.response.upload.artikel.ArticleResponse
import com.example.projekakhir.network.model.response.upload.competency.CompetencyCertificateResponse
import com.example.projekakhir.network.model.response.upload.valsheet.ValsheetResponse
import com.example.projekakhir.network.model.response.verified.VerifiedResponse
import com.example.projekakhir.network.model.response.verified.VerifiedSklRequestBody
import com.example.projekakhir.utils.TokenPreferences
import com.example.projekakhir.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class SklViewModel(private val dataRepository: DataRepository,private val tokenPreferences: TokenPreferences): ViewModel(){
    //artikel
    private val _uploadArticleResponse = MutableLiveData<Resource<ArticleResponse>>()
    val uploadArticleResponse: LiveData<Resource<ArticleResponse>> get() = _uploadArticleResponse

    //valsheet
    private val _uploadValsheetResponse = MutableLiveData<Resource<ValsheetResponse>>()
    val uploadValsheetResponse: LiveData<Resource<ValsheetResponse>> get() = _uploadValsheetResponse

    //sertifikat kompetensi
    private val _uploadCompCertResponse = MutableLiveData<Resource<CompetencyCertificateResponse>>()
    val uploadCompCertResponse: LiveData<Resource<CompetencyCertificateResponse>> get() = _uploadCompCertResponse

    //verif
    private val _patchVerifResponse = MutableLiveData<Resource<VerifiedResponse>>()
    val patchVerifResponse:LiveData<Resource<VerifiedResponse>> get() = _patchVerifResponse

    //student
    private val _getStudentByStudentIdResponse = MutableLiveData<Resource<GetStudentByStudentIdResponse>>()
    val getStudentByStudentIdResponse: LiveData<Resource<GetStudentByStudentIdResponse>> get() = _getStudentByStudentIdResponse


    //artikel
    fun uploadArticle(file: MultipartBody.Part){
        _uploadArticleResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _uploadArticleResponse.postValue(dataRepository.uploadArticle(tokenPreferences.getToken().orEmpty(),file))
        }
    }
    //valsheet
    fun uploadValsheet(file: MultipartBody.Part){
        _uploadValsheetResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _uploadValsheetResponse.postValue(dataRepository.uploadValsheet(tokenPreferences.getToken().orEmpty(),file))
        }
    }
    //sertifikat kompetensi
    fun uploadCompCert(file: MultipartBody.Part){
        _uploadCompCertResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _uploadCompCertResponse.postValue(dataRepository.uploadCompCert(tokenPreferences.getToken().orEmpty(),file))
        }
    }
    //verif
    fun patchVerif(verifiedSklRequestBody: VerifiedSklRequestBody){
        _patchVerifResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _patchVerifResponse.postValue(dataRepository.patchVerifSkl(tokenPreferences.getToken().orEmpty(),verifiedSklRequestBody,tokenPreferences.getNpmUser().orEmpty()))
        }
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