package com.example.smartcitizenclub.presentation.feature.billpay.ui

import androidx.compose.ui.graphics.Color

// Bill payment data class
data class BillPayment(
    val id: String,
    val type: BillPaymentType,
    val title: String,
    val description: String,
    val amount: Double,
    val dueDate: Long? = null,
    val status: BillPaymentStatus = BillPaymentStatus.PENDING,
    val reference: String? = null,
    val additionalInfo: Map<String, String> = emptyMap()
)

// Bill payment type enum
enum class BillPaymentType {
    UTILITY,
    MOBILE_INTERNET,
    INSURANCE,
    CREDIT_CARD,
    OTHER
}

// Bill payment status enum
enum class BillPaymentStatus {
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

// Bill payment request
data class BillPaymentRequest(
    val userId: String,
    val billPaymentType: BillPaymentType,
    val amount: Double,
    val paymentMethod: PaymentMethod,
    val reference: String? = null,
    val additionalData: Map<String, String> = emptyMap(),
    val pin: String
)

// Bill payment response
data class BillPaymentResponse(
    val success: Boolean,
    val transactionId: String? = null,
    val message: String,
    val remainingBalance: Double? = null
)

// Colors for bill pay screens
object BillPayColors {
    val Red = Color(0xFFE53E3E)
    val LightGray = Color(0xFFF5F5F5)
    val Gray = Color(0xFF9E9E9E)
    val DarkGray = Color(0xFF424242)
    val Green = Color(0xFF4CAF50)
    val Orange = Color(0xFFFF9800)
    val Blue = Color(0xFF2196F3)
    val Purple = Color(0xFF9C27B0)
}
