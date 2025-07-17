package com.fittrackapp.fittrack_mobile.data.repository

import android.util.Log
import arrow.core.Either
import arrow.core.flatMap
import com.fittrackapp.fittrack_mobile.data.mapper.toGeneralError
import com.fittrackapp.fittrack_mobile.data.model.LoginRequest
import com.fittrackapp.fittrack_mobile.data.model.RegisterRequest
import com.fittrackapp.fittrack_mobile.data.remote.AuthApi
import com.fittrackapp.fittrack_mobile.domain.model.AuthUser
import com.fittrackapp.fittrack_mobile.domain.model.NetworkError
import com.fittrackapp.fittrack_mobile.domain.repository.AuthRepository
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun checkAuth(): Either<NetworkError, AuthUser> {
        return Either.catch {
            authApi.checkAuth()
        }.mapLeft {
            Log.e("AuthRepositoryImpl", "Login failed: " + it.message, it)
            it.toGeneralError()
        }
    }

    override suspend fun login(
        username: String,
        password: String
    ): Either<NetworkError, AuthUser> {
        return Either.catch {
            authApi.login(LoginRequest(username, password))
        }.mapLeft {
            Log.e("AuthRepositoryImpl", "Login failed: " + it.message, it)
            it.toGeneralError()
        }
    }

    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): Either<NetworkError, AuthUser> {
        return Either.catch {
            val request = RegisterRequest(email, username, password)
            authApi.register(request)
        }.mapLeft {
            Log.e("AuthRepositoryImpl", "Registration failed: " + it.message, it)
            it.toGeneralError()
        }
    }

    override suspend fun logout(): Boolean {
        TODO("Not yet implemented")
    }
}