package com.fittrackapp.fittrack_mobile.data.remote

import com.fittrackapp.fittrack_mobile.data.model.LoginRequest
import com.fittrackapp.fittrack_mobile.domain.model.AuthUser
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthUser

    suspend fun register(username: String, password: String): AuthUser

    suspend fun logout(): Boolean
}