package com.example.projekakhir.network.model.response.graduateform

import com.google.gson.annotations.SerializedName

data class GraduateFormRequestBody(

	@field:SerializedName("student_id")
	val studentId: String,

	@field:SerializedName("full_name")
	val fullName: String,

	@field:SerializedName("birth_date")
	val birthDate: String,

	@field:SerializedName("birth_place")
	val birthPlace: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("phone_number")
	val phoneNumber: String,

	@field:SerializedName("major")
	val major: String,

	@field:SerializedName("gpa")
	val gpa: Any,

	@field:SerializedName("religion")
	val religion: String,

	@field:SerializedName("level")
	val level: String,

	@field:SerializedName("dad")
	val dad: String,

	@field:SerializedName("mother")
	val mother: String,

	@field:SerializedName("parent_telp")
	val parentTelp: String,

	@field:SerializedName("parent_address")
	val parentAddress: String,

	@field:SerializedName("commencement_date")
	val commencementDate: String,
)
