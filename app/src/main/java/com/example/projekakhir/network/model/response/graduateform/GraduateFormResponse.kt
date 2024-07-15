package com.example.projekakhir.network.model.response.graduateform

import com.google.gson.annotations.SerializedName

data class GraduateFormResponse(

	@field:SerializedName("data")
	val data: Any,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
