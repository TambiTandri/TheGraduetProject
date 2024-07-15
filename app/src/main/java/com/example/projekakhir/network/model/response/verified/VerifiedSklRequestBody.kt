package com.example.projekakhir.network.model.response.verified

import com.google.gson.annotations.SerializedName

data class VerifiedSklRequestBody(

    @field:SerializedName("verification_skl")
    val verificationSKL: String,

    )
