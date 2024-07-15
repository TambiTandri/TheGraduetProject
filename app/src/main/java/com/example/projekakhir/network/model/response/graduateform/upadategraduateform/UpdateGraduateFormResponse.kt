package com.example.projekakhir.network.model.response.graduateform.upadategraduateform

import com.google.gson.annotations.SerializedName

data class UpdateGraduateFormResponse(

	@field:SerializedName("data")
	val data: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
