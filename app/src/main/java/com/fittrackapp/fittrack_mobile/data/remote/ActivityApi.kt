package com.fittrackapp.fittrack_mobile.data.remote

import com.fittrackapp.fittrack_mobile.data.model.LoginRequest
import com.fittrackapp.fittrack_mobile.data.model.RegisterRequest
import com.fittrackapp.fittrack_mobile.domain.model.AuthUser
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ActivityApi {
    @GET("activity/get/all")
    suspend fun getAll(): List<Activity>

    @POST("activity/register")
    suspend fun register(@Body request: Activity): Boolean
}