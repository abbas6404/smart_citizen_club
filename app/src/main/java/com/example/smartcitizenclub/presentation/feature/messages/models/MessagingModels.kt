package com.example.smartcitizenclub.presentation.feature.messages.models

import androidx.compose.ui.graphics.Color

/**
 * Data models for the messaging system
 */

// Chat conversation data model
data class Chat(
    val id: String,
    val contactName: String,
    val lastMessage: String,
    val timestamp: Long,
    val isRead: Boolean = false,
    val unreadCount: Int = 0,
    val isOnline: Boolean = false,
    val isPinned: Boolean = false,
    val avatar: String? = null
)

// Individual message data model
data class ChatMessage(
    val id: String,
    val text: String,
    val timestamp: Long,
    val isFromUser: Boolean = false,
    val isRead: Boolean = false
)

// Contact data model
data class Contact(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val isSCCMember: Boolean,
    val avatarColor: Color = Color.LightGray,
    val profileImage: String? = null,
    val status: String? = null
)

// Group data model
data class ChatGroup(
    val id: String,
    val name: String,
    val members: List<Contact>,
    val lastMessage: String,
    val timestamp: Long,
    val isRead: Boolean = false,
    val unreadCount: Int = 0,
    val avatar: String? = null
)
