package com.fittrackapp.fittrack_mobile.data.remote

import com.fittrackapp.fittrack_mobile.data.model.ActivitySyncRequest
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/* WARNING: Adding even '/' at the start of api route might fail connection */
interface ActivityApi {
    @GET("get/all")
    suspend fun getAll(): List<Activity>

    @POST("register")
    suspend fun register(@Body request: Activity): Boolean

    @GET("sync")
    suspend fun sync(@Body request: ActivitySyncRequest): List<Activity>
}