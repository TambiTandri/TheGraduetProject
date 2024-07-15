package com.example.projekakhir.ui.biodatapendaftaran

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.projekakhir.data.repository.DataRepository
import com.example.projekakhir.network.model.response.biodatapendaftaran.BiodataPendaftaranRequestBody
import com.example.projekakhir.network.model.response.biodatapendaftaran.BiodataPendaftaranResponse
import com.example.projekakhir.network.model.response.lecturer.DataItem
import com.example.projekakhir.network.model.response.lecturer.LecturerResponse
import com.example.projekakhir.network.model.response.semester.SemesterResponse
import com.example.projekakhir.network.model.response.upload.birth.BirthResponse
import com.example.projekakhir.network.model.response.upload.familycard.FamilyCardResponse
import com.example.projekakhir.network.model.response.upload.gradcert.GradCertResponse
import com.example.projekakhir.network.model.response.upload.idcard.IdCardResponse
import com.example.projekakhir.network.model.response.upload.photo.PhotoResponse
import com.example.projekakhir.network.model.response.upload.studentcard.StudentCardResponse
import com.example.projekakhir.network.model.response.upload.tempgrad.TempGradResponse
import com.example.projekakhir.network.model.response.upload.thesis.ThesisResponse
import com.example.projekakhir.network.model.response.upload.toeic.ToeicResponse
import com.example.projekakhir.network.model.response.upload.valsheet.ValsheetResponse
import com.example.projekakhir.network.model.response.verified.VerifiedRequestBody
import com.example.projekakhir.network.model.response.verified.VerifiedResponse
import com.example.projekakhir.utils.TokenPreferences
import com.example.projekakhir.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class BiodataPendaftaranViewModel(private val dataRepository: DataRepository,private val tokenPreferences: TokenPreferences): ViewModel(){
    private val _biodataPendaftaranResponse = MutableLiveData<Resource<BiodataPendaftaranResponse>>()
    val biodataPendaftaranResponse : LiveData<Resource<BiodataPendaftaranResponse>> get() = _biodataPendaftaranResponse

    //ijasah sma
    private val _uploadGradCertResponse = MutableLiveData<Resource<GradCertResponse>>()
    val uploadGradCertResponse: LiveData<Resource<GradCertResponse>> get() = _uploadGradCertResponse
    //akta kelahiran
    private val _uploadBirthCertResponse = MutableLiveData<Resource<BirthResponse>>()
    val uploadBirthCertResponse: LiveData<Resource<BirthResponse>> get() = _uploadBirthCertResponse
    //kartu keluarga
    private val _uploadFamilyCardResponse = MutableLiveData<Resource<FamilyCardResponse>>()
    val uploadFamilyCardResponse: LiveData<Resource<FamilyCardResponse>> get() = _uploadFamilyCardResponse
    //ktp
    private val _uploadIdCardResponse = MutableLiveData<Resource<IdCardResponse>>()
    val uploadIdCardResponse: LiveData<Resource<IdCardResponse>> get() = _uploadIdCardResponse
    //ktm
    private val _uploadStudentCardResponse = MutableLiveData<Resource<StudentCardResponse>>()
    val uploadStudentCardResponse: LiveData<Resource<StudentCardResponse>> get() = _uploadStudentCardResponse
    //photo
    private val _uploadPhotoResponse = MutableLiveData<Resource<PhotoResponse>>()
    val uploadPhotoResponse: LiveData<Resource<PhotoResponse>> get() = _uploadPhotoResponse
    //skl
    private val _uploadTempGradResponse = MutableLiveData<Resource<TempGradResponse>>()
    val uploadTempGradResponse: LiveData<Resource<TempGradResponse>> get() = _uploadTempGradResponse
    //toeic
    private val _uploadToeicResponse = MutableLiveData<Resource<ToeicResponse>>()
    val uploadToeicResponse: LiveData<Resource<ToeicResponse>> get() = _uploadToeicResponse
    //skripsi
    private val _uploadThesisResponse = MutableLiveData<Resource<ThesisResponse>>()
    val uploadThesisResponse: LiveData<Resource<ThesisResponse>> get() = _uploadThesisResponse
    //lembar pengesahan
    private val _uploadValsheetResponse = MutableLiveData<Resource<ValsheetResponse>>()
    val uploadValsheetResponse: LiveData<Resource<ValsheetResponse>> get() = _uploadValsheetResponse

    //verif
    private val _patchVerifResponse = MutableLiveData<Resource<VerifiedResponse>>()
    val patchVerifResponse:LiveData<Resource<VerifiedResponse>> get() = _patchVerifResponse

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
    //ijasah sma
    fun uploadGradCert(file: MultipartBody.Part){
        _uploadGradCertResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _uploadGradCertResponse.postValue(dataRepository.uploadGradCert(tokenPreferences.getToken().orEmpty(),file))
        }
    }
    //akta kelahiran
    fun uploadBirthCert(file: MultipartBody.Part){
        _uploadBirthCertResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _uploadBirthCertResponse.postValue(dataRepository.uploadBirthCert(tokenPreferences.getToken().orEmpty(),file))
        }
    }
    //kartu keluarga
    fun uploadFamilyCard(file: MultipartBody.Part){
        _uploadFamilyCardResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _uploadFamilyCardResponse.postValue(dataRepository.uploadFamilyCard(tokenPreferences.getToken().orEmpty(),file))
        }
    }
    //ktp
    fun uploadIdCard(file: MultipartBody.Part){
        _uploadIdCardResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _uploadIdCardResponse.postValue(dataRepository.uploadIdCard(tokenPreferences.getToken().orEmpty(),file))
        }
    }
    //ktm
    fun uploadStudentCard(file: MultipartBody.Part){
        _uploadStudentCardResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _uploadStudentCardResponse.postValue(dataRepository.uploadStudentCard(tokenPreferences.getToken().orEmpty(),file))
        }
    }
    //photo
    fun uploadPhoto(file: MultipartBody.Part){
        _uploadPhotoResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _uploadPhotoResponse.postValue(dataRepository.uploadPhoto(tokenPreferences.getToken().orEmpty(),file))
        }
    }
    //skl
    fun uploadSkl(file: MultipartBody.Part){
        _uploadTempGradResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _uploadTempGradResponse.postValue(dataRepository.uploadTempGrad(tokenPreferences.getToken().orEmpty(),file))
        }
    }
    //toeic
    fun uploadToeic(file: MultipartBody.Part){
        _uploadToeicResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _uploadToeicResponse.postValue(dataRepository.uploadToeic(tokenPreferences.getToken().orEmpty(),file))
        }
    }
    //skripsi
    fun uploadThesis(file: MultipartBody.Part){
        _uploadThesisResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _uploadThesisResponse.postValue(dataRepository.uploadThesis(tokenPreferences.getToken().orEmpty(),file))
        }
    }
    //lembar pengesahan
    fun uploadValsheet(file: MultipartBody.Part){
        _uploadValsheetResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _uploadValsheetResponse.postValue(dataRepository.uploadValsheet(tokenPreferences.getToken().orEmpty(),file))
        }
    }
    fun saveBiodataPendaftaran(biodataPendaftaranRequestBody: BiodataPendaftaranRequestBody){
        _biodataPendaftaranResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _biodataPendaftaranResponse.postValue(dataRepository.postPendaftaranBiodata(tokenPreferences.getToken().orEmpty(),biodataPendaftaranRequestBody))
        }
    }
    //verif
    fun patchVerif(verifiedRequestBody: VerifiedRequestBody){
        _patchVerifResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _patchVerifResponse.postValue(dataRepository.patchVerif(tokenPreferences.getToken().orEmpty(),verifiedRequestBody,tokenPreferences.getNpmUser().orEmpty()))
        }
    }

    val npm: LiveData<String?> = liveData {
        val npm = tokenPreferences.getNpmUser().orEmpty()
        emit("$npm")
    }

    val email : LiveData<String?> = liveData {
        val email = tokenPreferences.getEmail().orEmpty()
        emit("$email")
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