package com.example.projekakhir.network.model.response.verified

import com.google.gson.annotations.SerializedName

data class VerifiedResponse(

	@field:SerializedName("data")
	val data: Data,

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
	val gpa: Int,

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
