package com.example.smartcitizenclub.presentation.feature.investment.ui

import androidx.compose.ui.graphics.Color

// Investment option data class
data class InvestmentOption(
    val id: String,
    val name: String,
    val description: String,
    val minAmount: Double,
    val maxAmount: Double,
    val expectedReturn: Double, // Annual percentage return
    val riskLevel: RiskLevel,
    val duration: String, // e.g., "6 months", "1 year", "2 years"
    val category: InvestmentCategory,
    val isActive: Boolean = true
)

// Investment category enum
enum class InvestmentCategory {
    SAVINGS,
    MUTUAL_FUNDS,
    STOCKS,
    BONDS,
    CRYPTO,
    REAL_ESTATE,
    GOLD
}

// Risk level enum
enum class RiskLevel {
    LOW,
    MEDIUM,
    HIGH
}

// User investment data class
data class UserInvestment(
    val id: String,
    val userId: String,
    val investmentOptionId: String,
    val amount: Double,
    val investmentDate: Long,
    val maturityDate: Long,
    val currentValue: Double,
    val isActive: Boolean = true
)

// Investment request data class
data class InvestmentRequest(
    val userId: String,
    val investmentOptionId: String,
    val amount: Double,
    val pin: String
)

// Investment response data class
data class InvestmentResponse(
    val success: Boolean,
    val userInvestmentId: String? = null,
    val message: String,
    val transactionId: String? = null
)

// Colors for investment screen
object InvestmentColors {
    val Primary = Color(0xFF2196F3)
    val LightGray = Color(0xFFF5F5F5)
    val Gray = Color(0xFF9E9E9E)
    val DarkGray = Color(0xFF424242)
    val Green = Color(0xFF4CAF50)
    val Orange = Color(0xFFFF9800)
    val Red = Color(0xFFF44336)
    val Purple = Color(0xFF9C27B0)
    val Gold = Color(0xFFFFD700)
}
