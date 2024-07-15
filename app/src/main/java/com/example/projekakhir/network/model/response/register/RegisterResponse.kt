package com.example.projekakhir.network.model.response.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Data(

	@field:SerializedName("firstName")
	val firstName: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("last_name")
	val lastName: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
