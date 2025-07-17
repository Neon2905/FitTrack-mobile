package com.fittrackapp.fittrack_mobile.data.remote

import com.fittrackapp.fittrack_mobile.data.model.ActivityBulkAddRequest
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/* WARNING: Adding even '/' at the start of api route might fail connection */
interface ActivityApi {
    @GET("list")
    suspend fun getAll(): List<Activity>

    @GET("list")
    suspend fun sync(@retrofit2.http.Query("exceptions") exceptions: String): List<Activity>

    @POST("log")
    suspend fun register(@Body request: Activity): Boolean

    @POST("batch-log")
    suspend fun registerBulk(@Body request: ActivityBulkAddRequest): Int
}