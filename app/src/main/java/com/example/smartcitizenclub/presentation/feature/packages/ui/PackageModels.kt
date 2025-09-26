package com.example.smartcitizenclub.presentation.feature.packages.ui

import androidx.compose.ui.graphics.Color

// Package data class
data class Package(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val validity: String, // e.g., "7 days", "30 days"
    val dataLimit: String, // e.g., "1GB", "Unlimited"
    val smsLimit: String, // e.g., "100 SMS", "Unlimited"
    val voiceLimit: String, // e.g., "100 mins", "Unlimited"
    val operator: String, // e.g., "Grameenphone", "Robi"
    val category: PackageCategory,
    val isActive: Boolean = true
)

// Package category enum
enum class PackageCategory {
    INTERNET,
    VOICE,
    SMS,
    COMBO,
    PREMIUM
}

// User package data class
data class UserPackage(
    val id: String,
    val userId: String,
    val packageId: String,
    val purchaseDate: Long,
    val expiryDate: Long,
    val isActive: Boolean = true,
    val autoRenew: Boolean = false
)

// Package purchase request data class
data class PackagePurchaseRequest(
    val userId: String,
    val packageId: String,
    val paymentMethod: PaymentMethod,
    val pin: String
)

// Package purchase response data class
data class PackagePurchaseResponse(
    val success: Boolean,
    val userPackageId: String? = null,
    val message: String,
    val transactionId: String? = null
)

// Payment method enum
enum class PaymentMethod {
    WALLET,
    CARD,
    BANK_TRANSFER,
    MOBILE_RECHARGE
}

// Colors for package purchase screen
object PackageColors {
    val Red = Color(0xFFE53E3E)
    val LightGray = Color(0xFFF5F5F5)
    val Gray = Color(0xFF9E9E9E)
    val DarkGray = Color(0xFF424242)
    val Green = Color(0xFF4CAF50)
    val Orange = Color(0xFFFF9800)
    val Blue = Color(0xFF2196F3)
    val Purple = Color(0xFF9C27B0)
}
