package com.fittrackapp.fittrack_mobile.domain.model

data class AuthUser(
    val id: Int,
    val username: String,
    val email: String,
    val token: String
) {
    override fun toString(): String {
        return "AuthUser(id=$id, username='$username', email='$email', token='$token')"
    }
}