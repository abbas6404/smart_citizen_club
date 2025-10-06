package com.example.smartcitizenclub.presentation.feature.chargelimit.ui

import androidx.compose.ui.graphics.Color

// Service charge data class
data class ServiceCharge(
    val id: String,
    val serviceName: String,
    val chargeType: ChargeType,
    val amount: Double,
    val description: String,
    val isActive: Boolean = true
)

// Charge type enum
enum class ChargeType {
    FIXED,
    PERCENTAGE,
    SLAB
}

// Transaction limit data class
data class TransactionLimit(
    val id: String,
    val serviceName: String,
    val limitType: LimitType,
    val amount: Double,
    val period: LimitPeriod,
    val description: String,
    val isActive: Boolean = true
)

// Limit type enum
enum class LimitType {
    MINIMUM,
    MAXIMUM,
    DAILY,
    MONTHLY,
    YEARLY
}

// Limit period enum
enum class LimitPeriod {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}

// User limit data class
data class UserLimit(
    val userId: String,
    val serviceName: String,
    val currentAmount: Double,
    val limitAmount: Double,
    val period: LimitPeriod,
    val resetDate: Long,
    val isActive: Boolean = true
)

// Package information data class
data class PackageInfo(
    val id: String,
    val name: String,
    val description: String,
    val withdrawalLimit: Double,
    val loanLimit: Double,
    val sendMoneyCharge: Double,
    val cashOutCharge: Double,
    val isActive: Boolean = true
)

// Colors for charge and limit screen
object ChargeLimitColors {
    val Primary = Color(0xFFE53E3E)
    val LightGray = Color(0xFFF5F5F5)
    val Gray = Color(0xFF9E9E9E)
    val DarkGray = Color(0xFF424242)
    val Green = Color(0xFF4CAF50)
    val Orange = Color(0xFFFF9800)
    val Blue = Color(0xFF2196F3)
    val Red = Color(0xFFF44336)
}
