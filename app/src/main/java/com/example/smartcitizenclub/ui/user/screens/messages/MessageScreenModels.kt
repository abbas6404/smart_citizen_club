package com.example.smartcitizenclub.ui.user.screens.messages

import androidx.compose.ui.graphics.Color

// SmartCitizenClub app colors for message screens
object SmartCitizenColors {
    val PrimaryOrange = Color(0xFFFF4500)   // Vibrant orange-red from account card
    val SecondaryGreen = Color(0xFF10B981)  // Accent color
    val Background = Color(0xFFF8F9FA)     // Light background
    val TextPrimary = Color(0xFF374151)     // Dark gray text
    val TextSecondary = Color(0xFF6B7280)   // Medium gray text
    val TextTertiary = Color(0xFF9CA3AF)    // Light gray text
    val Red = Color(0xFFE53E3E)             // Error/notification color
    val Gold = Color(0xFFF59E0B)            // Warning color
}

// Data class for a contact
data class Contact(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val isSCCMember: Boolean,
    val avatarColor: Color = Color.LightGray, // Default color
    val profileImage: String? = null,
    val status: String? = null
)
