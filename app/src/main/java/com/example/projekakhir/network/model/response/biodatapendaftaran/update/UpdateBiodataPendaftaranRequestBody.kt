package com.example.projekakhir.network.model.response.biodatapendaftaran.update

import com.google.gson.annotations.SerializedName

data class UpdateBiodataPendaftaranRequestBody(
	@field:SerializedName("major")
	val major: String,
	@field:SerializedName("national_id")
	val nationalId: String,
	@field:SerializedName("address")
	val address: String,
	@field:SerializedName("birth_date")
	val birthDate: String,
	@field:SerializedName("nidn_advisor_one")
	val nidnAdvisorOne: String,
	@field:SerializedName("nidn_advisor_two")
	val nidnAdvisorTwo: String,
	@field:SerializedName("nidn_religion")
	val nidnReligion: String,
	@field:SerializedName("birth_place")
	val birthPlace: String,
	@field:SerializedName("phone_number")
	val phoneNumber: String,
	@field:SerializedName("telephone_number")
	val telephoneNumber: String,
	@field:SerializedName("credit_course")
	val creditCourse: Int,
	@field:SerializedName("gpa")
	val gpa: Any,
	@field:SerializedName("academic_year")
	val academicYear: String,
	@field:SerializedName("semester")
	val semester: String,
	@field:SerializedName("examiner")
	val examiner: String,
	@field:SerializedName("gender")
	val gender: String,
	@field:SerializedName("thesis_title")
	val thesisTitle: String,
	@field:SerializedName("advisor")
	val advisor: String,
	@field:SerializedName("religion_advisor")
	val religionAdvisor: String,
	@field:SerializedName("graduate_date")
	val graduateDate: String,
	@field:SerializedName("commencement_date")
	val commencementDate: String,
)
