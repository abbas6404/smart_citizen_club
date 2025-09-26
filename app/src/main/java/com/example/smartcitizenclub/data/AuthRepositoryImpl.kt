package com.example.smartcitizenclub.data

import com.example.smartcitizenclub.core.utils.Result

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(phone: String, password: String): Result<AuthResult> {
        // Mock implementation for now
        return Result.Success(AuthResult(
            success = true,
            user = User(
                id = "1",
                name = "Test User",
                phone = phone,
                email = "test@example.com"
            )
        ))
    }

    override suspend fun signup(user: User, password: String): Result<AuthResult> {
        // Mock implementation for now
        return Result.Success(AuthResult(
            success = true,
            user = user
        ))
    }

    override suspend fun logout() {
        // Mock implementation for now
    }
}
