package com.example.fittrack.data.remote

import com.example.fittrack.data.model.ApiResponse
import com.example.fittrack.data.model.Workout
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("register.php")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse

    @POST("login.php")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse

    @POST("add_workout.php")
    @FormUrlEncoded
    suspend fun addWorkout(
        @Field("user_id") userId: Int,
        @Field("type") type: String,
        @Field("duration") duration: Int, // in minutes
        @Field("calories") calories: Int
    ): ApiResponse

    @GET("get_workouts.php")
    suspend fun getWorkouts(
        @Query("user_id") userId: Int
    ): List<Workout>
}
