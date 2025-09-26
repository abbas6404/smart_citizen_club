package com.example.smartcitizenclub.data

data class User(
    val id: String,
    val name: String,
    val phone: String,
    val email: String,
    val type: UserType = UserType.REGULAR
)

enum class UserType {
    REGULAR,
    PREMIUM,
    BUSINESS,
    USER  // Add the missing USER type
}
