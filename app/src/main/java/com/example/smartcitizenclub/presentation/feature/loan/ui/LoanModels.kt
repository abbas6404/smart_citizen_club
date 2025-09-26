package com.example.smartcitizenclub.presentation.feature.loan.ui

import androidx.compose.ui.graphics.Color

// Loan data class
data class Loan(
    val id: String,
    val name: String,
    val description: String,
    val minAmount: Double,
    val maxAmount: Double,
    val interestRate: Double, // Annual interest rate percentage
    val tenureOptions: List<Int>, // Tenure in months
    val processingFee: Double, // Processing fee percentage
    val isActive: Boolean = true
)

// User loan data class
data class UserLoan(
    val id: String,
    val userId: String,
    val loanId: String,
    val amount: Double,
    val tenure: Int, // In months
    val interestRate: Double,
    val processingFee: Double,
    val emiAmount: Double,
    val totalAmount: Double, // Principal + Interest + Processing Fee
    val startDate: Long,
    val endDate: Long,
    val status: LoanStatus,
    val remainingAmount: Double,
    val nextPaymentDate: Long? = null
)

// Loan application request data class
data class LoanApplicationRequest(
    val userId: String,
    val loanId: String,
    val amount: Double,
    val tenure: Int, // In months
    val pin: String
)

// Loan application response data class
data class LoanApplicationResponse(
    val success: Boolean,
    val userLoanId: String? = null,
    val message: String,
    val transactionId: String? = null
)

// Loan payment request data class
data class LoanPaymentRequest(
    val userLoanId: String,
    val amount: Double,
    val pin: String
)

// Loan payment response data class
data class LoanPaymentResponse(
    val success: Boolean,
    val message: String,
    val transactionId: String? = null,
    val remainingAmount: Double? = null
)

// Loan status enum
enum class LoanStatus {
    APPLIED,
    APPROVED,
    ACTIVE,
    PAID,
    REJECTED,
    DEFAULTED
}

// Payment method enum
enum class PaymentMethod {
    WALLET,
    CARD,
    BANK_TRANSFER
}

// Colors for loan screens
object LoanColors {
    val Red = Color(0xFFE53E3E)
    val LightGray = Color(0xFFF5F5F5)
    val Gray = Color(0xFF9E9E9E)
    val DarkGray = Color(0xFF424242)
    val Green = Color(0xFF4CAF50)
    val Orange = Color(0xFFFF9800)
    val Blue = Color(0xFF2196F3)
    val Purple = Color(0xFF9C27B0)
}
