package com.example.smartcitizenclub.presentation.feature.messages.data

import androidx.compose.ui.graphics.Color
import com.example.smartcitizenclub.presentation.feature.messages.models.Chat
import com.example.smartcitizenclub.presentation.feature.messages.models.ChatMessage
import com.example.smartcitizenclub.presentation.feature.messages.models.Contact

/**
 * Sample data for messaging features
 */

object MessagingSampleData {
    
    // Sample chat list
    val sampleChats = listOf(
        Chat(
            id = "1",
            contactName = "Community Admin",
            lastMessage = "Welcome to Smart Citizen Club!",
            timestamp = System.currentTimeMillis() - 3600000,
            isRead = false,
            unreadCount = 2,
            isOnline = true,
            isPinned = true
        ),
        Chat(
            id = "2",
            contactName = "City Council",
            lastMessage = "Important update about park development",
            timestamp = System.currentTimeMillis() - 172800000,
            isRead = false,
            unreadCount = 1,
            isOnline = false,
            isPinned = true
        ),
        Chat(
            id = "3",
            contactName = "Ariyan Abbas",
            lastMessage = "Hey there! I am using Smart Citizen Club.",
            timestamp = System.currentTimeMillis() - 86400000,
            isRead = true,
            unreadCount = 0,
            isOnline = true,
            isPinned = false
        ),
        Chat(
            id = "4",
            contactName = "Akhi 222 CSE DIU",
            lastMessage = "Sleeping",
            timestamp = System.currentTimeMillis() - 259200000,
            isRead = true,
            unreadCount = 0,
            isOnline = false,
            isPinned = false
        )
    )
    
    // Sample contacts
    val sampleContacts = listOf(
        Contact(
            id = "1",
            name = "Ariyan Abbas",
            phoneNumber = "+8801234567890",
            isSCCMember = true,
            avatarColor = Color(0xFF4CAF50),
            status = "Message yourself"
        ),
        Contact(
            id = "2",
            name = "Aaa",
            phoneNumber = "+8801234567891",
            isSCCMember = true,
            avatarColor = Color(0xFF2196F3),
            status = "Hey there! I am using Smart Citizen Club."
        ),
        Contact(
            id = "3",
            name = "Akbor Computer",
            phoneNumber = "+8801234567892",
            isSCCMember = true,
            avatarColor = Color(0xFFFFC107),
            status = "Hey there! I am using Smart Citizen Club."
        ),
        Contact(
            id = "4",
            name = "Akhi 222 CSE DIU",
            phoneNumber = "+8801234567893",
            isSCCMember = true,
            avatarColor = Color(0xFF9C27B0),
            status = "Sleeping"
        ),
        Contact(
            id = "5",
            name = "Akhi 222 DIU",
            phoneNumber = "+8801234567894",
            isSCCMember = true,
            avatarColor = Color(0xFFF44336),
            status = "Sleeping"
        ),
        Contact(
            id = "6",
            name = "Al-amin fs pup",
            phoneNumber = "+8801234567895",
            isSCCMember = false,
            avatarColor = Color(0xFF00BCD4),
            status = "Available"
        ),
        Contact(
            id = "7",
            name = "John Smith",
            phoneNumber = "+8801234567896",
            isSCCMember = false,
            avatarColor = Color(0xFFE91E63),
            status = "Last seen recently"
        ),
        Contact(
            id = "8",
            name = "Sarah Johnson",
            phoneNumber = "+8801234567897",
            isSCCMember = true,
            avatarColor = Color(0xFF607D8B),
            status = "Online"
        )
    )
    
    // Sample chat messages
    val sampleChatMessages = listOf(
        ChatMessage(
            id = "1",
            text = "Hello! How are you?",
            timestamp = System.currentTimeMillis() - 3600000,
            isFromUser = false
        ),
        ChatMessage(
            id = "2",
            text = "Hi! I'm doing great, thanks for asking. How about you?",
            timestamp = System.currentTimeMillis() - 3540000,
            isFromUser = true,
            isRead = true
        ),
        ChatMessage(
            id = "3",
            text = "I'm good too! Are you free for a call later?",
            timestamp = System.currentTimeMillis() - 3480000,
            isFromUser = false
        ),
        ChatMessage(
            id = "4",
            text = "Sure! What time works for you?",
            timestamp = System.currentTimeMillis() - 3420000,
            isFromUser = true,
            isRead = true
        ),
        ChatMessage(
            id = "5",
            text = "How about 2 PM?",
            timestamp = System.currentTimeMillis() - 3360000,
            isFromUser = false
        ),
        ChatMessage(
            id = "6",
            text = "Perfect! I'll call you then. See you soon!",
            timestamp = System.currentTimeMillis() - 3300000,
            isFromUser = true,
            isRead = true
        )
    )
    
    // Country codes for contact creation
    val countryCodes = listOf(
        "BD +880",
        "US +1", 
        "IN +91",
        "UK +44",
        "CA +1",
        "AU +61",
        "DE +49",
        "FR +33"
    )
}
