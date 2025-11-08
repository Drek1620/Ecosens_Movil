package com.example.ecosens_movil.repo

import com.example.ecosens_movil.network.ApiClient
import com.example.ecosens_movil.network.LoginRequest
import com.example.ecosens_movil.network.LoginResponse

class LoginRepository {
    private val api = ApiClient.retrofit

    suspend fun login(email: String, password: String): LoginResponse {
        val request = LoginRequest(correo = email, contrasena = password)
        return api.login(request)
    }
}