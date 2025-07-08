package com.fittrackapp.fittrack_mobile.data.remote

import arrow.core.Either
import com.fittrackapp.fittrack_mobile.data.model.LoginRequest
import com.fittrackapp.fittrack_mobile.data.model.RegisterRequest
import com.fittrackapp.fittrack_mobile.domain.model.ApiError
import com.fittrackapp.fittrack_mobile.domain.model.AuthUser
import com.fittrackapp.fittrack_mobile.domain.model.NetworkError
import retrofit2.http.Body
import retrofit2.http.POST

/* WARNING: Adding even '/' at the start of api route might fail connection */
interface AuthApi {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): AuthUser

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): AuthUser

    suspend fun logout(): Boolean
}