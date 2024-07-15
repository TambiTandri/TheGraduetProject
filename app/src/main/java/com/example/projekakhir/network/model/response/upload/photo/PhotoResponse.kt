package com.example.projekakhir.network.model.response.upload.photo

import com.google.gson.annotations.SerializedName

data class PhotoResponse(

	@field:SerializedName("data")
	val data: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
