package com.example.ecosens_movil.network

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/login") // ajusta la ruta a la de tu API si hace falta
    suspend fun login(@Body request: LoginRequest): LoginResponse
}