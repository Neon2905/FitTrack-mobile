package com.fittrackapp.fittrack_mobile.domain.model


data class NetworkError(
    val error: ApiError,
    val message: String = error.message,
    val t: Throwable? = null // I just run out of ideas on how to name this properly
)

sealed class ApiError(open val message: String) {
    object Network : ApiError("Network error")
    object UnknownResponse : ApiError("Unknown response")
    data class Unknown(val errorMessage: String = "An error occurred") : ApiError(errorMessage)
    data class Unauthorized(override val message: String) : ApiError(message)
    data class BadRequest(override val message: String) : ApiError(message)
    data class NotFound(override val message: String) : ApiError(message)
    data class InternalServerError(override val message: String) : ApiError(message)
    data class ServiceUnavailable(override val message: String) : ApiError(message)
    data class Forbidden(override val message: String) : ApiError(message)
}