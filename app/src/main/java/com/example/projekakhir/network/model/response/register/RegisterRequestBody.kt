package com.example.projekakhir.network.model.response.register

data class RegisterRequestBody(
    val username: String,
    val password: String,
    val role: String,
    val id:String,
    val first_name: String,
    val last_name: String,
    val email: String
)