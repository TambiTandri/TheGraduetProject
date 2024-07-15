package com.example.projekakhir.network.model.response.upload.thesis

import com.google.gson.annotations.SerializedName

data class ThesisResponse(
    @field:SerializedName("data")
    val data: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)