package com.fittrackapp.fittrack_mobile.data.mapper

import com.fittrackapp.fittrack_mobile.FitTrackMobile
import com.fittrackapp.fittrack_mobile.domain.model.ApiError
import com.fittrackapp.fittrack_mobile.domain.model.NetworkError
import com.fittrackapp.fittrack_mobile.navigation.NavRoute
import com.fittrackapp.fittrack_mobile.navigation.Navigator
import com.fittrackapp.fittrack_mobile.utils.Toast
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toGeneralError(): NetworkError {
    val gson = Gson()
    val error = when (this) {
        is IOException -> ApiError.Network
        is HttpException -> {
            val errorBody = this.response()?.errorBody()?.string()
            val message = try {
                gson.fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) {
                "Unknown error"
            }

            when (this.code()) {
                400 -> ApiError.BadRequest(message ?: "Bad Request")
                401 -> {
                    // Clear secure preferences on unauthorized access
                    val securePrefsManager = FitTrackMobile.instance.securePrefsManager
                    securePrefsManager.clear()

                    ApiError.Unauthorized(message ?: "Unauthorized")
                }

                403 -> ApiError.Forbidden(message ?: "Forbidden")
                404 -> ApiError.NotFound(message ?: "Not Found")
                409 -> ApiError.Conflict(message ?: "Data Conflict")
                500 -> ApiError.InternalServerError(message ?: "Internal Server Error")
                503 -> ApiError.ServiceUnavailable(message ?: "Service Unavailable")
                else -> ApiError.UnknownResponse
            }
        }

        else -> ApiError.Unknown()
    }
    return NetworkError(
        error = error,
        t = this
    )
}

internal data class ErrorResponse(
    val message: String
)
