package com.example.smartcitizenclub.presentation.feature.limits.ui

import androidx.compose.ui.graphics.Color

// Transaction limit data class
data class TransactionLimit(
    val id: String,
    val serviceType: ServiceType,
    val limitType: LimitType,
    val amount: Double,
    val period: LimitPeriod,
    val description: String
)

// Service type enum
enum class ServiceType {
    SEND_MONEY,
    CASH_OUT,
    MOBILE_RECHARGE,
    BILL_PAYMENT,
    DONATION
}

// Limit type enum
enum class LimitType {
    MINIMUM,
    MAXIMUM,
    DAILY,
    MONTHLY
}

// Limit period enum
enum class LimitPeriod {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}

// Service charge data class
data class ServiceCharge(
    val id: String,
    val serviceType: ServiceType,
    val amount: Double,
    val chargeType: ChargeType,
    val minAmount: Double? = null,
    val maxAmount: Double? = null,
    val description: String
)

// Charge type enum
enum class ChargeType {
    FIXED,
    PERCENTAGE,
    SLAB
}

// User limit data class
data class UserLimit(
    val userId: String,
    val serviceType: ServiceType,
    val currentAmount: Double,
    val limitAmount: Double,
    val period: LimitPeriod,
    val resetDate: Long
)

// Colors for limits and charges screen
object LimitColors {
    val Red = Color(0xFFE53E3E)
    val LightGray = Color(0xFFF5F5F5)
    val Gray = Color(0xFF9E9E9E)
    val DarkGray = Color(0xFF424242)
    val Green = Color(0xFF4CAF50)
    val Orange = Color(0xFFFF9800)
    val Blue = Color(0xFF2196F3)
}
