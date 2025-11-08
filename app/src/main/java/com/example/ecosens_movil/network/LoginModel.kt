package com.example.ecosens_movil.network

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val correo: String,
    val contrasena: String
)

data class LoginResponse(
    val token: String,
    @SerializedName("id")
    val userId: Int,
    @SerializedName("tipo_id")
    val tipoId: Int
)