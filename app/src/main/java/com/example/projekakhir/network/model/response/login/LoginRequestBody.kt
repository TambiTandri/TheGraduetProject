package com.example.projekakhir.network.model.response.login

import com.google.gson.annotations.SerializedName

data class LoginRequestBody(
	@field:SerializedName("username")
	val username: String,
	@field:SerializedName("password")
	val password: String


)
