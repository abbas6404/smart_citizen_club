package com.example.smartcitizenclub.ui.user.screens.payments

import androidx.compose.ui.graphics.Color

// Generic payment data class
data class Payment(
    val id: String,
    val type: PaymentType,
    val title: String,
    val description: String,
    val amount: Double,
    val dueDate: Long? = null,
    val status: PaymentStatus = PaymentStatus.PENDING,
    val reference: String? = null,
    val additionalInfo: Map<String, String> = emptyMap()
)

// Payment type enum
enum class PaymentType {
    LOAN,
    MOBILE_RECHARGE,
    BILL,
    DONATION,
    TRANSFER,
    OTHER
}

// Payment status enum
enum class PaymentStatus {
    PENDING,
    COMPLETED,
    OVERDUE,
    CANCELLED
}

// Payment method enum
enum class PaymentMethod {
    WALLET,
    CARD,
    BANK_TRANSFER,
    MOBILE_RECHARGE
}

// Generic payment request
data class PaymentRequest(
    val userId: String,
    val paymentType: PaymentType,
    val amount: Double,
    val paymentMethod: PaymentMethod,
    val reference: String? = null,
    val additionalData: Map<String, String> = emptyMap(),
    val pin: String
)

// Generic payment response
data class PaymentResponse(
    val success: Boolean,
    val transactionId: String? = null,
    val message: String,
    val remainingBalance: Double? = null
)

// Colors for payment screens
object PaymentColors {
    val Red = Color(0xFFE53E3E)
    val LightGray = Color(0xFFF5F5F5)
    val Gray = Color(0xFF9E9E9E)
    val DarkGray = Color(0xFF424242)
    val Green = Color(0xFF4CAF50)
    val Orange = Color(0xFFFF9800)
    val Blue = Color(0xFF2196F3)
    val Purple = Color(0xFF9C27B0)
}
