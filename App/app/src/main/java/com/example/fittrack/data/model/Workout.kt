package com.example.fittrack.data.model

data class Workout(
    val id: Int,
    val user_id: Int,
    val type: String,
    val duration: Int,
    val calories: Int,
    val timestamp: String
)