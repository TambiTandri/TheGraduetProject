package com.example.projekakhir.ui.berhasilsimpanpendaftaran

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekakhir.data.repository.DataRepository
import com.example.projekakhir.network.model.response.biodatapendaftaran.update.UpdateBiodataPendaftaranRequestBody
import com.example.projekakhir.network.model.response.biodatapendaftaran.update.UpdateBiodataPendaftaranResponse
import com.example.projekakhir.network.model.response.documen.getDoc.GetDocumentResponse
import com.example.projekakhir.network.model.response.getStudent.GetStudentByStudentIdResponse
import com.example.projekakhir.network.model.response.lecturer.DataItem
import com.example.projekakhir.network.model.response.lecturer.LecturerResponse
import com.example.projekakhir.network.model.response.semester.SemesterResponse
import com.example.projekakhir.network.model.response.verified.VerifiedRequestBody
import com.example.projekakhir.network.model.response.verified.VerifiedResponse
import com.example.projekakhir.utils.TokenPreferences
import com.example.projekakhir.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BerhasilSimpanPendaftaranViewModel(private val dataRepository: DataRepository,private val tokenPreferences: TokenPreferences) : ViewModel() {
    private val _getStudentByStudentIdResponse = MutableLiveData<Resource<GetStudentByStudentIdResponse>>()
    val getStudentByStudentIdResponse: LiveData<Resource<GetStudentByStudentIdResponse>> get() = _getStudentByStudentIdResponse

    private val _getDocResponse = MutableLiveData<Resource<GetDocumentResponse>>()
    val getDocResponse: LiveData<Resource<GetDocumentResponse>> get() = _getDocResponse
    //semester
    private val _listSemesterResponse = MutableLiveData<Resource<SemesterResponse>>()
    val listSemesterResponse: LiveData<Resource<SemesterResponse>> get() = _listSemesterResponse
    private val _semester = MutableLiveData<List<com.example.projekakhir.network.model.response.semester.DataItem>>()
    val semester: LiveData<List<com.example.projekakhir.network.model.response.semester.DataItem>> get() = _semester
    //academic year
    private val _listAcademicYearResponse = MutableLiveData<Resource<SemesterResponse>>()
    val listAcademicYearResponse: LiveData<Resource<SemesterResponse>> get() = _listAcademicYearResponse
    private val _academicYear = MutableLiveData<List<com.example.projekakhir.network.model.response.semester.DataItem>>()
    val academicYear:LiveData<List<com.example.projekakhir.network.model.response.semester.DataItem>> get() = _academicYear
    //lecturer
    private val _listLecturerResponse = MutableLiveData<Resource<LecturerResponse>>()
    val listLecturerResponse : LiveData<Resource<LecturerResponse>> get() = _listLecturerResponse
    private val _lecturers = MutableLiveData<List<DataItem>>()
    val lecturers: LiveData<List<DataItem>> get() = _lecturers
    //update biodata pendaftaran
    private val _updateBiodataPendaftaranResponse = MutableLiveData<Resource<UpdateBiodataPendaftaranResponse>>()
    val updateBiodataPendaftaranResponse:LiveData<Resource<UpdateBiodataPendaftaranResponse>> get() = _updateBiodataPendaftaranResponse

    //update biodata pendaftaran
    fun updateBiodataPendaftaran(updateBiodataPendaftaranRequestBody: UpdateBiodataPendaftaranRequestBody){
        _updateBiodataPendaftaranResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _updateBiodataPendaftaranResponse.postValue(dataRepository.updateBiodataPendaftaran(tokenPreferences.getToken().orEmpty(),updateBiodataPendaftaranRequestBody,tokenPreferences.getNpmUser().orEmpty()))
        }
    }
    fun getListAcademicYear(){
        viewModelScope.launch {
            _listAcademicYearResponse.postValue(Resource.Loading())
            val response = dataRepository.getListSemester()
            _listAcademicYearResponse.postValue(response)
            if (response is Resource.Success){
                _academicYear.postValue(response.payload?.data)
            }
            Log.d("listAcademicYear",response.payload?.data.toString())
        }
    }
    fun getListSemester(){
        viewModelScope.launch {
            _listSemesterResponse.postValue(Resource.Loading())
            val response = dataRepository.getListSemester()
            _listSemesterResponse.postValue(response)
            if (response is Resource.Success){
                _semester.postValue(response.payload?.data)
            }
            Log.d("listSemester",response.payload?.data.toString())
        }
    }
    fun getListLecturer() {
        viewModelScope.launch {
            _listLecturerResponse.postValue(Resource.Loading())
            val response = dataRepository.getListLecturer()
            _listLecturerResponse.postValue(response)
            if (response is Resource.Success) {
                _lecturers.postValue(response.payload?.data)
            }
            Log.d("listLecturer", response.payload?.data.toString())
        }
    }
    fun getStudent(){
        viewModelScope.launch(Dispatchers.IO) {
            _getStudentByStudentIdResponse.postValue(dataRepository.getStudentByStudentId(tokenPreferences.getToken().orEmpty(),tokenPreferences.getNpmUser().orEmpty()))
        }
    }
    fun getDoc(){
        viewModelScope.launch(Dispatchers.IO){
            _getDocResponse.postValue(dataRepository.getDoc(tokenPreferences.getToken().orEmpty(),tokenPreferences.getNpmUser().orEmpty()))
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