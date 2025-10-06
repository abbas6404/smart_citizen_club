package com.example.smartcitizenclub.presentation.feature.ticketsupport.ui

import androidx.compose.ui.graphics.Color
import java.util.*

data class SupportTicket(
    val id: String,
    val userId: String,
    val subject: String,
    val description: String,
    val category: TicketCategory,
    val priority: TicketPriority,
    val status: TicketStatus,
    val createdAt: Long,
    val updatedAt: Long,
    val assignedTo: String? = null,
    val resolution: String? = null,
    val resolvedAt: Long? = null
)

enum class TicketCategory {
    GENERAL_INQUIRY,
    TECHNICAL_SUPPORT,
    ACCOUNT_ISSUE,
    PAYMENT_ISSUE,
    LOAN_SUPPORT,
    COMPLAINT,
    FEATURE_REQUEST,
    OTHER
}

enum class TicketPriority {
    LOW,
    MEDIUM,
    HIGH,
    URGENT
}

enum class TicketStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED,
    CANCELLED
}

data class TicketRequest(
    val subject: String,
    val description: String,
    val category: TicketCategory,
    val priority: TicketPriority
)

data class TicketResponse(
    val success: Boolean,
    val message: String,
    val ticket: SupportTicket? = null
)

object TicketSupportColors {
    val categoryColors = mapOf(
        TicketCategory.GENERAL_INQUIRY to Color(0xFF2196F3),
        TicketCategory.TECHNICAL_SUPPORT to Color(0xFF4CAF50),
        TicketCategory.ACCOUNT_ISSUE to Color(0xFFFF9800),
        TicketCategory.PAYMENT_ISSUE to Color(0xFFE91E63),
        TicketCategory.LOAN_SUPPORT to Color(0xFF9C27B0),
        TicketCategory.COMPLAINT to Color(0xFFF44336),
        TicketCategory.FEATURE_REQUEST to Color(0xFF00BCD4),
        TicketCategory.OTHER to Color(0xFF607D8B)
    )

    val priorityColors = mapOf(
        TicketPriority.LOW to Color(0xFF4CAF50),
        TicketPriority.MEDIUM to Color(0xFFFF9800),
        TicketPriority.HIGH to Color(0xFFE91E63),
        TicketPriority.URGENT to Color(0xFFF44336)
    )

    val statusColors = mapOf(
        TicketStatus.OPEN to Color(0xFF2196F3),
        TicketStatus.IN_PROGRESS to Color(0xFFFF9800),
        TicketStatus.RESOLVED to Color(0xFF4CAF50),
        TicketStatus.CLOSED to Color(0xFF9E9E9E),
        TicketStatus.CANCELLED to Color(0xFFF44336)
    )
}
