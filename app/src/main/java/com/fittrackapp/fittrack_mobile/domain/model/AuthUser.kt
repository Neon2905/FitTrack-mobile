package com.fittrackapp.fittrack_mobile.domain.model

data class AuthUser(
    val username: String,
    val email: String,
) {
    override fun toString(): String {
        return "AuthUser(username='$username', email='$email')"
    }
}