package com.example.ecosens_movil.network

// Ajusta los campos al JSON real que devuelve tu API
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String? = null,
    val success: Boolean? = null,
    val message: String? = null
)