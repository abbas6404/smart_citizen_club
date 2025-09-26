package com.example.smartcitizenclub.ui.user.screens.contact

import androidx.compose.ui.graphics.Color

// Support ticket data class
data class SupportTicket(
    val id: String,
    val subject: String,
    val description: String,
    val category: TicketCategory,
    val priority: TicketPriority,
    val status: TicketStatus,
    val createdAt: Long,
    val updatedAt: Long,
    val userId: String,
    val userName: String,
    val userEmail: String?,
    val userPhone: String?
)

// Ticket category enum
enum class TicketCategory {
    GENERAL_INQUIRY,
    TECHNICAL_SUPPORT,
    ACCOUNT_ISSUE,
    PAYMENT_ISSUE,
    FEATURE_REQUEST,
    COMPLAINT,
    OTHER
}

// Ticket priority enum
enum class TicketPriority {
    LOW,
    MEDIUM,
    HIGH,
    URGENT
}

// Ticket status enum
enum class TicketStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED,
    CANCELLED
}

// Support ticket request data class
data class CreateTicketRequest(
    val subject: String,
    val description: String,
    val category: TicketCategory,
    val priority: TicketPriority,
    val userId: String,
    val userName: String,
    val userEmail: String?,
    val userPhone: String?
)

// Support ticket response data class
data class CreateTicketResponse(
    val success: Boolean,
    val ticketId: String? = null,
    val message: String
)

// Colors for contact us screen
object ContactColors {
    val Red = Color(0xFFE53E3E)
    val LightGray = Color(0xFFF5F5F5)
    val Gray = Color(0xFF9E9E9E)
    val DarkGray = Color(0xFF424242)
    val Green = Color(0xFF4CAF50)
    val Orange = Color(0xFFFF9800)
    val Blue = Color(0xFF2196F3)
}
