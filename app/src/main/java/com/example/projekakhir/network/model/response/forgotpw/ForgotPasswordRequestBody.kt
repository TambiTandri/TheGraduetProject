package com.example.projekakhir.network.model.response.forgotpw

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequestBody(

	@field:SerializedName("email")
	val email: String
)
