package com.example.smartcitizenclub.presentation.feature.messages.ui

import androidx.compose.ui.graphics.Color

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
