package com.example.projekakhir.network.model.response.verified

import com.google.gson.annotations.SerializedName

data class VerifiedRequestBody(

	@field:SerializedName("verification")
	val verification: String,

)
