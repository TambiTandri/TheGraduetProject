package com.example.projekakhir.network.model.response.lecturer

import com.google.gson.annotations.SerializedName

data class LecturerResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataItem(

	@field:SerializedName("nidn")
	val nidn: String,

	@field:SerializedName("lecturer_name")
	val lecturerName: String
)
