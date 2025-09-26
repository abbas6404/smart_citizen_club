package com.example.smartcitizenclub.presentation.feature.recharge.ui

import androidx.compose.ui.graphics.Color

// Mobile operator data class
data class MobileOperator(
    val id: String,
    val name: String,
    val icon: String? = null,
    val isFavorite: Boolean = false,
    val isRecent: Boolean = false
)

// Recharge package data class
data class RechargePackage(
    val id: String,
    val operator: MobileOperator,
    val amount: Double,
    val validity: String, // e.g., "7 days", "30 days"
    val description: String // e.g., "1GB data + 100 SMS"
)

// Transaction data class
data class RechargeTransaction(
    val id: String,
    val operator: MobileOperator,
    val phoneNumber: String,
    val amount: Double,
    val timestamp: Long,
    val status: RechargeStatus,
    val reference: String? = null
)

// Recharge status enum
enum class RechargeStatus {
    PENDING,
    COMPLETED,
    FAILED,
    CANCELLED
}

// Mobile recharge request data class
data class MobileRechargeRequest(
    val operatorId: String,
    val phoneNumber: String,
    val amount: Double,
    val pin: String,
    val reference: String? = null
)

// Mobile recharge response data class
data class MobileRechargeResponse(
    val success: Boolean,
    val transactionId: String? = null,
    val message: String,
    val balance: Double? = null
)

// Colors for mobile recharge screen
object RechargeColors {
    val Red = Color(0xFFE53E3E)
    val LightGray = Color(0xFFF5F5F5)
    val Gray = Color(0xFF9E9E9E)
    val DarkGray = Color(0xFF424242)
    val Green = Color(0xFF4CAF50)
    val Orange = Color(0xFFFF9800)
    val Blue = Color(0xFF2196F3)
}
