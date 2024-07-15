package com.example.projekakhir.network.model.response.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
