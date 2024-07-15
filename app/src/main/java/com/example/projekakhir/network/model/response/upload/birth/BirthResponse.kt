package com.example.projekakhir.network.model.response.upload.birth

import com.google.gson.annotations.SerializedName

data class BirthResponse(

	@field:SerializedName("data")
	val data: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
