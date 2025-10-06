package com.example.smartcitizenclub.presentation.feature.ticketsupport.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.verticalScroll
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import java.text.SimpleDateFormat
import java.util.*

// Sample tickets data
private fun getSampleTickets(): List<SupportTicket> {
    val currentTime = System.currentTimeMillis()
    return listOf(
        SupportTicket(
            id = "1",
            userId = "user123",
            subject = "Account Balance Issue",
            description = "My account balance is showing incorrect amount. Please help me resolve this issue.",
            category = TicketCategory.ACCOUNT_ISSUE,
            priority = TicketPriority.HIGH,
            status = TicketStatus.OPEN,
            createdAt = currentTime - (2 * 24 * 60 * 60 * 1000L), // 2 days ago
            updatedAt = currentTime - (2 * 24 * 60 * 60 * 1000L)
        ),
        SupportTicket(
            id = "2",
            userId = "user123",
            subject = "Loan Application Status",
            description = "I submitted a loan application 3 days ago but haven't received any update. Can you check the status?",
            category = TicketCategory.LOAN_SUPPORT,
            priority = TicketPriority.MEDIUM,
            status = TicketStatus.IN_PROGRESS,
            createdAt = currentTime - (5 * 24 * 60 * 60 * 1000L), // 5 days ago
            updatedAt = currentTime - (1 * 24 * 60 * 60 * 1000L), // 1 day ago
            assignedTo = "Support Agent"
        ),
        SupportTicket(
            id = "3",
            userId = "user123",
            subject = "Mobile Recharge Failed",
            description = "I tried to recharge my mobile number but the transaction failed. Money was deducted from my account.",
            category = TicketCategory.PAYMENT_ISSUE,
            priority = TicketPriority.URGENT,
            status = TicketStatus.RESOLVED,
            createdAt = currentTime - (7 * 24 * 60 * 60 * 1000L), // 7 days ago
            updatedAt = currentTime - (3 * 24 * 60 * 60 * 1000L), // 3 days ago
            assignedTo = "Payment Team",
            resolution = "Refund processed successfully. Amount credited back to your account.",
            resolvedAt = currentTime - (3 * 24 * 60 * 60 * 1000L)
        ),
        SupportTicket(
            id = "4",
            userId = "user123",
            subject = "App Login Problem",
            description = "I'm unable to login to the app. Getting error message 'Invalid credentials' even with correct password.",
            category = TicketCategory.TECHNICAL_SUPPORT,
            priority = TicketPriority.MEDIUM,
            status = TicketStatus.OPEN,
            createdAt = currentTime - (1 * 24 * 60 * 60 * 1000L), // 1 day ago
            updatedAt = currentTime - (1 * 24 * 60 * 60 * 1000L)
        ),
        SupportTicket(
            id = "5",
            userId = "user123",
            subject = "Feature Request - Dark Mode",
            description = "It would be great if you could add a dark mode option to the app. Many users would appreciate this feature.",
            category = TicketCategory.FEATURE_REQUEST,
            priority = TicketPriority.LOW,
            status = TicketStatus.CLOSED,
            createdAt = currentTime - (14 * 24 * 60 * 60 * 1000L), // 14 days ago
            updatedAt = currentTime - (10 * 24 * 60 * 60 * 1000L), // 10 days ago
            assignedTo = "Product Team",
            resolution = "Thank you for your suggestion. We'll consider this for future updates.",
            resolvedAt = currentTime - (10 * 24 * 60 * 60 * 1000L)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketSupportScreen(
    user: User,
    onBackClick: () -> Unit,
    onTicketSubmitted: (SupportTicket) -> Unit = {}
) {
    var showCreateTicket by remember { mutableStateOf(false) }
    var showTicketDetails by remember { mutableStateOf(false) }
    var selectedTicket by remember { mutableStateOf<SupportTicket?>(null) }
    var tickets by remember { mutableStateOf<List<SupportTicket>>(getSampleTickets()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = when {
                        showTicketDetails -> "Ticket Details"
                        showCreateTicket -> "Create New Ticket"
                        else -> "Ticket Support"
                    },
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    when {
                        showTicketDetails -> {
                            showTicketDetails = false
                            selectedTicket = null
                        }
                        showCreateTicket -> showCreateTicket = false
                        else -> onBackClick()
                    }
                }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFE53E3E)
            ),
            modifier = Modifier.statusBarsPadding()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when {
                showTicketDetails && selectedTicket != null -> {
                    TicketDetailsScreen(
                        ticket = selectedTicket!!,
                        onBackClick = { 
                            showTicketDetails = false
                            selectedTicket = null
                        }
                    )
                }
                showCreateTicket -> {
                    CreateTicketForm(
                        user = user,
                        onSubmitTicket = { ticket ->
                            tickets = tickets + ticket
                            onTicketSubmitted(ticket)
                            showCreateTicket = false
                        }
                    )
                }
                else -> {
                    TicketSupportMainScreen(
                        onCreateTicketClick = { showCreateTicket = true },
                        tickets = tickets,
                        onTicketClick = { ticket ->
                            selectedTicket = ticket
                            showTicketDetails = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TicketSupportMainScreen(
    onCreateTicketClick: () -> Unit,
    tickets: List<SupportTicket>,
    onTicketClick: (SupportTicket) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Create New Ticket Button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCreateTicketClick() },
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE53E3E)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Create Ticket",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = "Create New Ticket",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // Ticket List Section
        Text(
            text = "Your Tickets",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        if (tickets.isEmpty()) {
            // Empty State
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.SupportAgent,
                        contentDescription = "No Tickets",
                        tint = Color.Gray,
                        modifier = Modifier.size(48.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "No tickets yet",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Create your first support ticket to get help",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tickets) { ticket ->
                    TicketCard(
                        ticket = ticket,
                        onClick = { onTicketClick(ticket) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateTicketForm(
    user: User,
    onSubmitTicket: (SupportTicket) -> Unit
) {
    var subject by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var hasAttachment by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Subject Field
        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text("Subject") },
            placeholder = { Text("Brief description of your issue") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFE53E3E),
                focusedLabelColor = Color(0xFFE53E3E)
            )
        )

        // Description Field
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            placeholder = { Text("Detailed description of your issue") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            maxLines = 5,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFE53E3E),
                focusedLabelColor = Color(0xFFE53E3E)
            )
        )

        // Image Attachment Option
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { 
                    // TODO: Implement actual image picker
                    hasAttachment = !hasAttachment 
                },
            colors = CardDefaults.cardColors(
                containerColor = if (hasAttachment) Color(0xFFE53E3E).copy(alpha = 0.1f) else Color(0xFFF8F9FA)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Image,
                    contentDescription = "Image Attachment",
                    tint = if (hasAttachment) Color(0xFFE53E3E) else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Add Image",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (hasAttachment) Color(0xFFE53E3E) else Color.Black
                    )
                    Text(
                        text = if (hasAttachment) "Image will be attached" else "Tap to select image from gallery",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                if (hasAttachment) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Selected",
                        tint = Color(0xFFE53E3E),
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add Image",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = {
                if (subject.isNotBlank() && description.isNotBlank()) {
                    val ticket = SupportTicket(
                        id = UUID.randomUUID().toString(),
                        userId = user.id,
                        subject = subject,
                        description = description,
                        category = TicketCategory.GENERAL_INQUIRY, // Default category
                        priority = TicketPriority.MEDIUM, // Default priority
                        status = TicketStatus.OPEN,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )
                    onSubmitTicket(ticket)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53E3E)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = subject.isNotBlank() && description.isNotBlank()
        ) {
            Text(
                text = "Submit Ticket",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun TicketCard(
    ticket: SupportTicket,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Subject
            Text(
                text = ticket.subject,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Description
            Text(
                text = ticket.description.take(100) + if (ticket.description.length > 100) "..." else "",
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp
            )

            // Status, Opening Time, and Closing Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status (simple text, no colored badge)
                Text(
                    text = "Status: ${ticket.status.name.replace("_", " ")}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                // Dates
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Opened: ${SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(ticket.createdAt))}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    
                    if (ticket.resolvedAt != null) {
                        Text(
                            text = "Closed: ${SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(ticket.resolvedAt))}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    } else {
                        Text(
                            text = "Status: Open",
                            fontSize = 12.sp,
                            color = Color(0xFF4CAF50)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TicketDetailsScreen(
    ticket: SupportTicket,
    onBackClick: () -> Unit
) {
    val replies = remember { getSampleReplies(ticket.id) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Ticket Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = ticket.subject,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Text(
                        text = ticket.description,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        lineHeight = 20.sp
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Status: ${ticket.status.name.replace("_", " ")}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        
                        Text(
                            text = "Created: ${SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(ticket.createdAt))}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Support Replies Section
            Text(
                text = "Support Replies",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Replies List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(replies) { reply ->
                    SupportReplyCard(reply = reply)
                }
            }
        }
    }
}

@Composable
private fun SupportReplyCard(reply: SupportReply) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = reply.senderName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE53E3E)
                )
                
                Text(
                    text = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(Date(reply.timestamp)),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            Text(
                text = reply.message,
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 20.sp
            )
        }
    }
}

// Sample support replies
private fun getSampleReplies(ticketId: String): List<SupportReply> {
    val currentTime = System.currentTimeMillis()
    return listOf(
        SupportReply(
            id = "1",
            ticketId = ticketId,
            senderId = "support1",
            senderName = "Sarah Johnson - Support Agent",
            message = "Hello! I've received your ticket and I'm looking into this issue. Let me check your account balance and get back to you shortly. I'll resolve this as soon as possible.",
            timestamp = currentTime - (2 * 60 * 60 * 1000L) // 2 hours ago
        )
    )
}

data class SupportReply(
    val id: String,
    val ticketId: String,
    val senderId: String,
    val senderName: String,
    val message: String,
    val timestamp: Long
)

@Preview(showBackground = true)
@Composable
fun TicketSupportScreenPreview() {
    SmartCitizenClubTheme {
        TicketSupportScreen(
            user = User(
                id = "1",
                name = "John Doe",
                email = "john@example.com",
                phone = "+1234567890"
            ),
            onBackClick = {},
            onTicketSubmitted = {}
        )
    }
}
