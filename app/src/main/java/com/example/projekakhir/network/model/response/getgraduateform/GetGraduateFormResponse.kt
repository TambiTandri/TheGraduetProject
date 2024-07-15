package com.example.projekakhir.network.model.response.getgraduateform

import com.google.gson.annotations.SerializedName

data class GetGraduateFormResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Data(

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("parent_address")
	val parentAddress: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("level")
	val level: String,

	@field:SerializedName("dad")
	val dad: String,

	@field:SerializedName("birth_date")
	val birthDate: String,

	@field:SerializedName("commencement_date")
	val commencementDate: String,

	@field:SerializedName("parent_telp")
	val parentTelp: String,

	@field:SerializedName("student_id")
	val studentId: String,

	@field:SerializedName("birth_place")
	val birthPlace: String,

	@field:SerializedName("religion")
	val religion: String,

	@field:SerializedName("mother")
	val mother: String,

	@field:SerializedName("full_name")
	val fullName: String,

	@field:SerializedName("major")
	val major: String,

	@field:SerializedName("gpa")
	val gpa: Any,

	@field:SerializedName("phone_number")
	val phoneNumber: String,

	@field:SerializedName("id")
	val id: String
)
