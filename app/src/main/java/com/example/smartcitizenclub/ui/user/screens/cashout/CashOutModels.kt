package com.example.smartcitizenclub.ui.user.screens.cashout

import androidx.compose.ui.graphics.Color

// Cash out provider data class
data class CashOutProvider(
    val id: String,
    val name: String,
    val icon: String? = null,
    val isFavorite: Boolean = false,
    val isRecent: Boolean = false
)

// Cash out account data class
data class CashOutAccount(
    val id: String,
    val provider: CashOutProvider,
    val accountNumber: String,
    val accountName: String? = null
)

// Transaction data class
data class CashOutTransaction(
    val id: String,
    val provider: CashOutProvider,
    val amount: Double,
    val timestamp: Long,
    val status: CashOutStatus,
    val reference: String? = null
)

// Cash out status enum
enum class CashOutStatus {
    PENDING,
    COMPLETED,
    FAILED,
    CANCELLED
}

// Cash out request data class
data class CashOutRequest(
    val providerId: String,
    val account: String, // Account number or mobile number
    val amount: Double,
    val pin: String,
    val reference: String? = null
)

// Cash out response data class
data class CashOutResponse(
    val success: Boolean,
    val transactionId: String? = null,
    val message: String,
    val balance: Double? = null
)

// Colors for cash out screen
object CashOutColors {
    val Red = Color(0xFFE53E3E)
    val LightGray = Color(0xFFF5F5F5)
    val Gray = Color(0xFF9E9E9E)
    val DarkGray = Color(0xFF424242)
    val Green = Color(0xFF4CAF50)
    val Orange = Color(0xFFFF9800)
}
