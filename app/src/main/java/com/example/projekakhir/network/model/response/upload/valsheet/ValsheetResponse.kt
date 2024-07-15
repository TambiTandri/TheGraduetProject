package com.example.projekakhir.network.model.response.upload.valsheet

import com.google.gson.annotations.SerializedName

data class ValsheetResponse(

	@field:SerializedName("data")
	val data: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
