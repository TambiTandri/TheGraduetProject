package com.example.projekakhir.network.model.response.getAllStudent

import com.google.gson.annotations.SerializedName

data class GetAllStudentResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Data(

	@field:SerializedName("national_id")
	val nationalId: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("birth_date")
	val birthDate: String,

	@field:SerializedName("commencement_date")
	val commencementDate: String,

	@field:SerializedName("telephone_number")
	val telephoneNumber: String,

	@field:SerializedName("examiner")
	val examiner: String,

	@field:SerializedName("religion_advisor")
	val religionAdvisor: String,

	@field:SerializedName("advisor")
	val advisor: String,

	@field:SerializedName("major")
	val major: String,

	@field:SerializedName("academic_year")
	val academicYear: String,

	@field:SerializedName("gpa")
	val gpa: Float,

	@field:SerializedName("first_name")
	val firstName: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("verification")
	val verification: String,

	@field:SerializedName("credit_course")
	val creditCourse: Int,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("verification_skl")
	val verificationSkl: String,

	@field:SerializedName("message_skl")
	val messageSkl: String,

	@field:SerializedName("student_id")
	val studentId: String,

	@field:SerializedName("last_name")
	val lastName: String,

	@field:SerializedName("birth_place")
	val birthPlace: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("thesis_title")
	val thesisTitle: String,

	@field:SerializedName("phone_number")
	val phoneNumber: String,

	@field:SerializedName("semester")
	val semester: String,

	@field:SerializedName("graduate_date")
	val graduateDate: String
)

data class Document(

	@field:SerializedName("birth_certificate")
	val birthCertificate: String,

	@field:SerializedName("competency_certificate")
	val competencyCertificate: String,

	@field:SerializedName("id_card")
	val idCard: String,

	@field:SerializedName("graduation_certificate")
	val graduationCertificate: String,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("toeic_certificate")
	val toeicCertificate: String,

	@field:SerializedName("student_card")
	val studentCard: String,

	@field:SerializedName("article")
	val article: String,

	@field:SerializedName("validity_sheet")
	val validitySheet: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("thesis_file")
	val thesisFile: String,

	@field:SerializedName("family_card")
	val familyCard: String,

	@field:SerializedName("temp_graduation_certificate")
	val tempGraduationCertificate: String,

	@field:SerializedName("id_student")
	val idStudent: String
)

data class DataItem(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("document")
	val document: Document
)
