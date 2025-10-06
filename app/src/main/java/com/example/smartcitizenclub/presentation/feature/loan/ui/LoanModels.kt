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
    val loanNumber: String, // Unique loan reference from backend
    val subAccountId: String, // Sub account ID from backend
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
    val nextPaymentDate: Long? = null,
    val lateFees: List<LateFee> = emptyList(),
    val totalLateFeeAmount: Double = 0.0,
    val hasOverduePayments: Boolean = false,
    val completedAt: Long? = null // When loan was completed
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

// Late fee data class
data class LateFee(
    val id: String,
    val loanIssuedId: String, // Links to loan_issueds table
    val subAccountId: String,
    val amount: Double,
    val balanceBefore: Double,
    val balanceAfter: Double,
    val description: String? = null,
    val status: LateFeeStatus,
    val createdDate: Long,
    val paidDate: Long? = null,
    val waivedBy: String? = null, // Admin who waived the fee
    val waivedAt: Long? = null,
    val waivedRemark: String? = null
)

// Late fee status enum
enum class LateFeeStatus {
    PENDING,
    PAID,
    WAIVED,
    CANCELLED
}

// Loan repayment data class
data class LoanRepayment(
    val id: String,
    val loanIssuedId: String, // Links to loan_issueds table
    val subAccountId: String,
    val paymentReference: String, // Unique payment reference
    val balanceBefore: Double,
    val amount: Double,
    val balanceAfter: Double,
    val description: String? = null,
    val status: RepaymentStatus,
    val checkedBy: String? = null, // Admin who processed
    val checkedAt: Long? = null,
    val checkedRemark: String? = null,
    val createdAt: Long
)

// Repayment status enum
enum class RepaymentStatus {
    PENDING,
    APPROVED,
    REJECTED
}

// Payment type enum for loan payment history
enum class PaymentType {
    LOAN_PAYMENT,
    LATE_FEE_PAYMENT,
    WAIVED_FEE
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
