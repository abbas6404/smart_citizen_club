package com.example.smartcitizenclub.data

data class User(
    val id: String,
    val name: String,
    val phone: String,
    val email: String,
    val type: UserType = UserType.REGULAR,
    val subAccounts: List<SubAccount> = emptyList(),
    val currentSubAccount: SubAccount? = null
)

data class SubAccount(
    val accountNumber: String,
    val accountName: String,
    val balance: Double = 0.0,
    val isActive: Boolean = true
)

enum class UserType {
    REGULAR,
    PREMIUM,
    BUSINESS,
    USER  // Add the missing USER type
}
