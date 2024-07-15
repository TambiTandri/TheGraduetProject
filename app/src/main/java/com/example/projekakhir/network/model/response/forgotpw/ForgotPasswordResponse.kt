package com.example.projekakhir.network.model.response.forgotpw

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(

	@field:SerializedName("data")
	val data: Any,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
