package com.example.projekakhir.network.model.response.semester

import com.google.gson.annotations.SerializedName

data class SemesterResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataItem(

	@field:SerializedName("academic_year")
	val academicYear: String,

	@field:SerializedName("semester")
	val semester: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("status")
	val status: String
)
