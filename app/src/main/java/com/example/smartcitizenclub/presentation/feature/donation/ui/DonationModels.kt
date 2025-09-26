package com.example.smartcitizenclub.presentation.feature.donation.ui

import androidx.compose.ui.graphics.Color

// Donation campaign data class
data class DonationCampaign(
    val id: String,
    val title: String,
    val description: String,
    val organization: String,
    val targetAmount: Double,
    val raisedAmount: Double,
    val endDate: Long,
    val imageUrl: String? = null,
    val category: DonationCategory,
    val isActive: Boolean = true
)

// Donation category enum
enum class DonationCategory {
    EDUCATION,
    HEALTH,
    ENVIRONMENT,
    DISASTER_RELIEF,
    POVERTY_ALLEVIATION,
    ANIMAL_WELFARE,
    COMMUNITY_DEVELOPMENT,
    OTHER
}

// User donation data class
data class UserDonation(
    val id: String,
    val userId: String,
    val campaignId: String,
    val amount: Double,
    val timestamp: Long,
    val paymentMethod: PaymentMethod,
    val status: DonationStatus,
    val anonymous: Boolean = false
)

// Payment method enum
enum class PaymentMethod {
    WALLET,
    CARD,
    BANK_TRANSFER,
    MOBILE_RECHARGE
}

// Donation status enum
enum class DonationStatus {
    PENDING,
    COMPLETED,
    FAILED,
    REFUNDED
}

// Donation request data class
data class DonationRequest(
    val userId: String,
    val campaignId: String,
    val amount: Double,
    val paymentMethod: PaymentMethod,
    val anonymous: Boolean = false,
    val userName: String,
    val userEmail: String?,
    val userPhone: String?
)

// Donation response data class
data class DonationResponse(
    val success: Boolean,
    val donationId: String? = null,
    val message: String,
    val transactionId: String? = null
)

// Colors for donation screen
object DonationColors {
    val Red = Color(0xFFE53E3E)
    val LightGray = Color(0xFFF5F5F5)
    val Gray = Color(0xFF9E9E9E)
    val DarkGray = Color(0xFF424242)
    val Green = Color(0xFF4CAF50)
    val Orange = Color(0xFFFF9800)
    val Blue = Color(0xFF2196F3)
    val Purple = Color(0xFF9C27B0)
}
