package com.example.smartcitizenclub.ui.user.screens.sendmoney

import androidx.compose.ui.graphics.Color

// Contact data class
data class Contact(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val avatar: String? = null,
    val isFavorite: Boolean = false,
    val isRecent: Boolean = false
)

// Transaction data class
data class Transaction(
    val id: String,
    val recipient: Contact,
    val amount: Double,
    val timestamp: Long,
    val status: TransactionStatus,
    val reference: String? = null
)

// Transaction status enum
enum class TransactionStatus {
    PENDING,
    COMPLETED,
    FAILED,
    CANCELLED
}

// Send money request data class
data class SendMoneyRequest(
    val recipient: String, // Phone number or virtual card
    val amount: Double,
    val pin: String,
    val reference: String? = null
)

// Send money response data class
data class SendMoneyResponse(
    val success: Boolean,
    val transactionId: String?,
    val message: String,
    val balance: Double? = null
)

// Colors for send money screen
object SendMoneyColors {
    val Red = Color(0xFFE53E3E)
    val LightGray = Color(0xFFF5F5F5)
    val Gray = Color(0xFF9E9E9E)
    val DarkGray = Color(0xFF424242)
    val Green = Color(0xFF4CAF50)
    val Orange = Color(0xFFFF9800)
}
