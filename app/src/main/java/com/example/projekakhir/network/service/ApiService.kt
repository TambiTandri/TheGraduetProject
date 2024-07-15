package com.example.projekakhir.network.service

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
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @POST("users")
    suspend fun postRegist(@Body registerBody: RegisterRequestBody
    ): RegisterResponse

    @POST("auth/login")
    suspend fun postLogin(@Body loginRequestBody: LoginRequestBody
    ): LoginResponse

    @POST("students")
    suspend fun postPendaftaranBiodata(
        @Header("Authorization") authorization:String,
        @Body biodataPendaftaranRequestBody: BiodataPendaftaranRequestBody
    ): BiodataPendaftaranResponse

    @GET("students/{studentId}")
    suspend fun getStudentsPendaftaran(
        @Header("Authorization") authorization: String,
        @Path("studentId") npm : String
    ): GetStudentByStudentIdResponse

    //ijasah sma
    @Multipart
    @POST("students/doc/gradcert")
    suspend fun uploadGradCert(
        @Header("Authorization") authorization: String,
        @Part grad_certificate: MultipartBody.Part
    ):GradCertResponse

    //akta kelahiran
    @Multipart
    @POST("students/doc/birth")
    suspend fun uploadBirthCert(
        @Header("Authorization") authorization: String,
        @Part birth_certificate: MultipartBody.Part
    ):BirthResponse

    //kartu keluarga
    @Multipart
    @POST("students/doc/familycard")
    suspend fun uploadFamilyCard(
        @Header("Authorization") authorization: String,
        @Part family_card: MultipartBody.Part
    ):FamilyCardResponse

    //ktp
    @Multipart
    @POST("students/doc/idcard")
    suspend fun uploadIdCard(
        @Header("Authorization") authorization: String,
        @Part idcard: MultipartBody.Part
    ):IdCardResponse

    //ktm
    @Multipart
    @POST("students/doc/studentcard")
    suspend fun uploadStudentCard(
        @Header("Authorization") authorization: String,
        @Part student_card: MultipartBody.Part
    ):StudentCardResponse

    //pas foto
    @Multipart
    @POST("students/doc/photo")
    suspend fun uploadPhoto(
        @Header("Authorization") authorization: String,
        @Part photo: MultipartBody.Part
    ):PhotoResponse

    //skl
    @Multipart
    @POST("students/doc/tempgrad")
    suspend fun uploadTempGrad(
        @Header("Authorization") authorization: String,
        @Part temp_grad: MultipartBody.Part
    ):TempGradResponse

    //toeic
    @Multipart
    @POST("students/doc/toeic")
    suspend fun uploadToeic(
        @Header("Authorization") authorization: String,
        @Part toeic: MultipartBody.Part
    ):ToeicResponse

    //skripsi
    @Multipart
    @POST("students/doc/thesis")
    suspend fun uploadThesis(
        @Header("Authorization") authorization: String,
        @Part thesis: MultipartBody.Part
    ):ThesisResponse

    //lembar pengesahan
    @Multipart
    @POST("students/doc/valsheet")
    suspend fun uploadValsheet(
        @Header("Authorization") authorization: String,
        @Part validity_sheet: MultipartBody.Part
    ):ValsheetResponse

    //artikel
    @Multipart
    @POST("students/doc/article")
    suspend fun uploadArticle(
        @Header("Authorization") authorization: String,
        @Part article: MultipartBody.Part
    ):ArticleResponse

    //sertifikat kompetensi
    @Multipart
    @POST("students/doc/comp_cert")
    suspend fun uploadCompCert(
        @Header("Authorization") authorization: String,
        @Part competency_certificate: MultipartBody.Part
    ):CompetencyCertificateResponse

    @PATCH("/students/{id}")
    suspend fun patchVerif(
        @Header("Authorization") authorization: String,
        @Body verifiedRequestBody: VerifiedRequestBody,
        @Path("id") id: String
    ):VerifiedResponse

    @PATCH("/students/{id}")
    suspend fun patchVerifSkl(
        @Header("Authorization") authorization: String,
        @Body verifiedSklRequestBody: VerifiedSklRequestBody,
        @Path("id") id: String
    ):VerifiedResponse

    @PATCH("students/{studentId}")
    suspend fun updateBiodataPendaftaran(
        @Header("Authorization") authorization: String,
        @Body updateBiodataPendaftaranRequestBody: UpdateBiodataPendaftaranRequestBody,
        @Path("studentId") npm:String
    ):UpdateBiodataPendaftaranResponse

    @GET("students/docs/{studentId}")
    suspend fun getDoc(
        @Header("Authorization") authorization: String,
        @Path("studentId") npm:String
    ):GetDocumentResponse

    @GET("students")
    suspend fun getStudent(
        @Header("Authorization") authorization: String,
    ):GetAllStudentResponse

    @POST("graduateform/create")
    suspend fun postGraduationForm(
        @Header("Authorization") authorization: String,
        @Body graduationFormRequestBody: GraduateFormRequestBody
    ): GraduateFormResponse

    @PATCH("graduateform/update")
    suspend fun updateGraduateForm(
        @Header("Authorization") authorization: String,
        @Body updateGraduateForm: UpdateGraduateForm
    ): UpdateGraduateFormResponse


    @GET("graduateform/detail/{id}")
    suspend fun getGraduateForm(
        @Header("Authorization") authorization: String,
        @Path("id") npm:String
    ): GetGraduateFormResponse

    @GET("lecturer/list")
    suspend fun getListLecturer(
    ): LecturerResponse

    @GET("semester/list")
    suspend fun getListSemester(
    ): SemesterResponse

    @POST("users/sendemail")
    suspend fun postForgotPassword(@Body forgotPasswordBody: ForgotPasswordRequestBody):ForgotPasswordResponse
}