package com.example.maquetacion.model.login

data class LoginResponse(
    val token: String,
    val refresh_token: String
)