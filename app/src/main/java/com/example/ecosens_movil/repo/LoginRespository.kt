package com.example.ecosens_movil.repo

import com.tuempresa.ecosens_movil.network.ApiClient
import com.tuempresa.ecosens_movil.network.LoginRequest
import com.tuempresa.ecosens_movil.network.LoginResponse

class LoginRepository {
    private val api = ApiClient.retrofit

    suspend fun login(email: String, password: String): LoginResponse {
        val request = LoginRequest(email = email, password = password)
        return api.login(request)
    }
}