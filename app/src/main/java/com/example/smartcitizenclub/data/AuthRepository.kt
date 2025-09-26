package com.example.smartcitizenclub.data

import com.example.smartcitizenclub.core.utils.Result

interface AuthRepository {
    suspend fun login(phone: String, password: String): Result<AuthResult>
    suspend fun signup(user: User, password: String): Result<AuthResult>
    suspend fun logout()
}

data class AuthResult(
    val success: Boolean,
    val user: User? = null,
    val message: String = ""
)
