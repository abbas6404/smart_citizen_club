package com.example.smartcitizenclub.data

import android.util.Patterns
import kotlinx.coroutines.delay

class AuthRepository {
    suspend fun login(identifier: String, password: String): AuthResult {
        // Simulate network delay
        delay(1000)
        
        // Simple validation for demo purposes
        return when {
            identifier.isEmpty() || password.isEmpty() -> {
                AuthResult.Error("Login credentials cannot be empty")
            }
            validateUserLogin(identifier, password) -> {
                AuthResult.Success(createDemoUser(identifier))
            }
            else -> {
                AuthResult.Error("Invalid credentials")
            }
        }
    }
    
    private fun validateUserLogin(identifier: String, password: String): Boolean {
        // Demo user credentials - mobile numbers only
        return (identifier == "+0987654321" || 
                identifier == "01741736354") && 
               password == "user123"
    }
    
    private fun createDemoUser(identifier: String): User {
        // Always treat as phone number since email is removed
        return User(
            id = "user_1",
            email = null,
            phone = identifier,
            name = "Demo User",
            userType = UserType.USER
        )
    }
    
    suspend fun signup(identifier: String, password: String, name: String): AuthResult {
        // Simulate network delay
        delay(1000)
        
        return when {
            identifier.isEmpty() || password.isEmpty() || name.isEmpty() -> {
                AuthResult.Error("All fields are required")
            }
            password.length < 6 -> {
                AuthResult.Error("Password must be at least 6 characters")
            }
            !isValidIdentifier(identifier) -> {
                AuthResult.Error("Please enter a valid phone number")
            }
            else -> {
                // Always treat as phone number since email is removed
                AuthResult.Success(User(
                    id = System.currentTimeMillis().toString(),
                    email = null,
                    phone = identifier,
                    name = name,
                    userType = UserType.USER
                ))
            }
        }
    }
    
    private fun isValidIdentifier(identifier: String): Boolean {
        // Only validate phone numbers since email is removed
        return Patterns.PHONE.matcher(identifier).matches() || 
               identifier.matches(Regex("^[0-9]{11}$")) // Bangladesh mobile format
    }
    
    suspend fun logout() {
        delay(500) // Simulate logout delay
    }
}

data class User(
    val id: String,
    val email: String?,
    val phone: String?,
    val name: String,
    val userType: UserType
)

enum class UserType {
    USER
}

sealed class AuthResult {
    data class Success(val user: User) : AuthResult()
    data class Error(val message: String) : AuthResult()
}
