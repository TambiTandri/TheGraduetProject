package com.example.projekakhir.data.repository

import android.content.Context
import com.example.projekakhir.network.model.response.biodatapendaftaran.BiodataPendaftaranRequestBody
import com.example.projekakhir.network.model.response.biodatapendaftaran.BiodataPendaftaranResponse
import com.example.projekakhir.network.model.response.biodatapendaftaran.update.UpdateBiodataPendaftaranRequestBody
import com.example.projekakhir.network.model.response.biodatapendaftaran.update.UpdateBiodataPendaftaranResponse
import com.example.projekakhir.network.model.response.documen.getDoc.GetDocumentResponse
import com.example.projekakhir.network.model.response.forgotpw.ForgotPasswordRequestBody
import com.example.projekakhir.network.model.response.forgotpw.ForgotPasswordResponse
import com.example.projekakhir.network.model.response.getAllStudent.GetAllStudentResponse
import com.example.projekakhir.network.model.response.getStudent.GetStudentByStudentIdResponse
import com.example.projekakhir.network.model.response.getgraduateform.GetGraduateFormResponse
import com.example.projekakhir.network.model.response.graduateform.GraduateFormRequestBody
import com.example.projekakhir.network.model.response.graduateform.GraduateFormResponse
import com.example.projekakhir.network.model.response.graduateform.upadategraduateform.UpdateGraduateForm
import com.example.projekakhir.network.model.response.graduateform.upadategraduateform.UpdateGraduateFormResponse
import com.example.projekakhir.network.model.response.lecturer.LecturerResponse
import com.example.projekakhir.network.model.response.login.LoginRequestBody
import com.example.projekakhir.network.model.response.login.LoginResponse
import com.example.projekakhir.network.model.response.register.RegisterRequestBody
import com.example.projekakhir.network.model.response.register.RegisterResponse
import com.example.projekakhir.network.model.response.semester.SemesterResponse
import com.example.projekakhir.network.model.response.upload.artikel.ArticleResponse
import com.example.projekakhir.network.model.response.upload.birth.BirthResponse
import com.example.projekakhir.network.model.response.upload.competency.CompetencyCertificateResponse
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
import com.example.projekakhir.network.model.response.verified.VerifiedSklRequestBody
import com.example.projekakhir.network.service.ApiService
import com.example.projekakhir.utils.Resource
import com.example.projekakhir.utils.proceed
import okhttp3.MultipartBody

class DataRepository(private val apiService: ApiService,private val context: Context){

    suspend fun postRegist(registerRequestBody: RegisterRequestBody): Resource<RegisterResponse> =
        proceed {
            apiService.postRegist(registerRequestBody)
        }
    suspend fun postLogin(loginRequestBody: LoginRequestBody) : Resource<LoginResponse> =
        proceed {
            apiService.postLogin(loginRequestBody)
        }
    suspend fun postPendaftaranBiodata(token:String,biodataPendaftaranRequestBody: BiodataPendaftaranRequestBody) : Resource<BiodataPendaftaranResponse> =
        proceed {
            apiService.postPendaftaranBiodata(token,biodataPendaftaranRequestBody)
        }
    suspend fun getStudentByStudentId(token: String,npm:String): Resource<GetStudentByStudentIdResponse> =
        proceed {
            apiService.getStudentsPendaftaran(token,npm)
        }
    //ijasah
    suspend fun uploadGradCert(token: String, file:MultipartBody.Part): Resource<GradCertResponse> =
        proceed {
            apiService.uploadGradCert(token,file)
        }
    //akta kelahiran
    suspend fun uploadBirthCert(token: String, file:MultipartBody.Part): Resource<BirthResponse> =
        proceed {
            apiService.uploadBirthCert(token,file)
        }
    //kartu keluarga
    suspend fun uploadFamilyCard(token: String, file:MultipartBody.Part): Resource<FamilyCardResponse> =
        proceed {
            apiService.uploadFamilyCard(token,file)
        }
    //ktp
    suspend fun uploadIdCard(token: String, file:MultipartBody.Part): Resource<IdCardResponse> =
        proceed {
            apiService.uploadIdCard(token,file)
        }
    //ktm
    suspend fun uploadStudentCard(token: String, file: MultipartBody.Part): Resource<StudentCardResponse> =
        proceed {
            apiService.uploadStudentCard(token,file)
        }
    //foto
    suspend fun uploadPhoto(token: String, file: MultipartBody.Part): Resource<PhotoResponse> =
        proceed {
            apiService.uploadPhoto(token,file)
        }
    //skl
    suspend fun uploadTempGrad(token: String, file: MultipartBody.Part): Resource<TempGradResponse> =
        proceed {
            apiService.uploadTempGrad(token,file)
        }
    //toeic
    suspend fun uploadToeic(token: String, file: MultipartBody.Part): Resource<ToeicResponse> =
        proceed {
            apiService.uploadToeic(token,file)
        }
    //skripsi
    suspend fun uploadThesis(token: String, file: MultipartBody.Part): Resource<ThesisResponse> =
        proceed {
            apiService.uploadThesis(token,file)
        }
    //lembar pengesahan
    suspend fun uploadValsheet(token: String, file: MultipartBody.Part): Resource<ValsheetResponse> =
        proceed {
            apiService.uploadValsheet(token,file)
        }
    //artikel
    suspend fun uploadArticle(token: String, file: MultipartBody.Part): Resource<ArticleResponse> =
        proceed {
            apiService.uploadArticle(token,file)
        }
    //sertifikat kompetensi
    suspend fun uploadCompCert(token: String, file: MultipartBody.Part): Resource<CompetencyCertificateResponse> =
        proceed {
            apiService.uploadCompCert(token,file)
        }
    //verif
    suspend fun patchVerif(token: String,verifiedRequestBody: VerifiedRequestBody,npm: String): Resource<VerifiedResponse> =
        proceed {
            apiService.patchVerif(token,verifiedRequestBody,npm)
        }
    //verif skl
    suspend fun patchVerifSkl(token: String,verifiedSklRequestBody: VerifiedSklRequestBody,npm: String): Resource<VerifiedResponse> =
        proceed {
            apiService.patchVerifSkl(token,verifiedSklRequestBody,npm)
        }
    //update biodata pendaftaran
    suspend fun updateBiodataPendaftaran(token: String,updateBiodataPendaftaranRequestBody: UpdateBiodataPendaftaranRequestBody,npm: String) : Resource<UpdateBiodataPendaftaranResponse> =
        proceed {
            apiService.updateBiodataPendaftaran(token,updateBiodataPendaftaranRequestBody,npm)
        }
    //get dokumen
    suspend fun getDoc(token: String,npm: String): Resource<GetDocumentResponse> =
        proceed {
            apiService.getDoc(token,npm)
        }
    //get allstudent
    suspend fun getAllStudent(token: String): Resource<GetAllStudentResponse> =
        proceed {
            apiService.getStudent(token)
        }

    //post graduateForm
    suspend fun postGraduateForm(token: String,graduateFormRequestBody: GraduateFormRequestBody) : Resource<GraduateFormResponse> =
        proceed {
            apiService.postGraduationForm(token,graduateFormRequestBody)
        }
    //update graduate form
    suspend fun updateGraduateForm(token: String,graduateForm: UpdateGraduateForm): Resource<UpdateGraduateFormResponse> =
        proceed {
            apiService.updateGraduateForm(token,graduateForm)
        }
    //get graduate form
    suspend fun getGraduateForm(token: String,npm: String): Resource<GetGraduateFormResponse> =
        proceed {
            apiService.getGraduateForm(token,npm)
        }
    //get list lecturer
    suspend fun getListLecturer():Resource<LecturerResponse> =
        proceed {
            apiService.getListLecturer()
        }
    //get list semester
    suspend fun getListSemester():Resource<SemesterResponse> =
        proceed {
            apiService.getListSemester()
        }
    //post forgot password
    suspend fun postForgotPassword(forgotPasswordRequestBody: ForgotPasswordRequestBody): Resource<ForgotPasswordResponse> =
        proceed {
            apiService.postForgotPassword(forgotPasswordRequestBody)
        }
}