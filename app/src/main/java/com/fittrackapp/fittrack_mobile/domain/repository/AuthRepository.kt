package com.fittrackapp.fittrack_mobile.domain.repository

import arrow.core.Either
import com.fittrackapp.fittrack_mobile.domain.model.AuthUser
import com.fittrackapp.fittrack_mobile.domain.model.NetworkError

interface AuthRepository {
    suspend fun checkAuth(): Either<NetworkError, AuthUser>
    suspend fun login(
        username: String,
        password: String
    ): Either<NetworkError, AuthUser>

    suspend fun register(
        email: String,
        username: String,
        password: String
    ): Either<NetworkError, AuthUser>

    suspend fun logout(): Boolean
}