package com.example.smartcitizenclub.presentation.feature.contact.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketListScreen(
    user: User,
    onBackClick: () -> Unit,
    onTicketSelected: (SupportTicket) -> Unit = {},
    onCreateNewTicket: () -> Unit = {}
) {
    // Sample tickets data
    val tickets = remember {
        listOf(
            SupportTicket(
                id = "ticket_12345",
                subject = "Unable to send money to contact",
                description = "I'm trying to send money to my contact but the app is showing an error message.",
                category = TicketCategory.TECHNICAL_SUPPORT,
                priority = TicketPriority.HIGH,
                status = TicketStatus.OPEN,
                createdAt = System.currentTimeMillis() - 3600000, // 1 hour ago
                updatedAt = System.currentTimeMillis() - 3600000, // 1 hour ago
                userId = user.id,
                userName = user.name,
                userEmail = user.email,
                userPhone = user.phone
            ),
            SupportTicket(
                id = "ticket_12344",
                subject = "Mobile recharge not working",
                description = "I tried to recharge my mobile but the transaction failed.",
                category = TicketCategory.PAYMENT_ISSUE,
                priority = TicketPriority.MEDIUM,
                status = TicketStatus.IN_PROGRESS,
                createdAt = System.currentTimeMillis() - 86400000, // 1 day ago
                updatedAt = System.currentTimeMillis() - 43200000, // 12 hours ago
                userId = user.id,
                userName = user.name,
                userEmail = user.email,
                userPhone = user.phone
            ),
            SupportTicket(
                id = "ticket_12343",
                subject = "Account verification issue",
                description = "I'm having trouble verifying my account with the provided documents.",
                category = TicketCategory.ACCOUNT_ISSUE,
                priority = TicketPriority.LOW,
                status = TicketStatus.RESOLVED,
                createdAt = System.currentTimeMillis() - 172800000, // 2 days ago
                updatedAt = System.currentTimeMillis() - 86400000, // 1 day ago
                userId = user.id,
                userName = user.name,
                userEmail = user.email,
                userPhone = user.phone
            )
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Red Background
        TopAppBar(
            title = {
                Text(
                    text = "My Tickets",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFE53E3E) // Red color
            ),
            modifier = Modifier.statusBarsPadding()
        )
        
        // Main Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Header with Create Ticket Button
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Support Tickets",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Button(
                        onClick = onCreateNewTicket,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE53E3E)
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Create",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "New Ticket",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }
            
            // Tickets List
            items(tickets) { ticket ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTicketSelected(ticket) },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = ticket.subject,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.weight(1f)
                            )
                            
                            Text(
                                text = ticket.status.name.replace("_", " "),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = when (ticket.status) {
                                    TicketStatus.OPEN -> Color(0xFF2196F3)
                                    TicketStatus.IN_PROGRESS -> Color(0xFFFF9800)
                                    TicketStatus.RESOLVED -> Color(0xFF4CAF50)
                                    TicketStatus.CLOSED -> Color(0xFF9E9E9E)
                                    TicketStatus.CANCELLED -> Color(0xFFF44336)
                                }
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = ticket.description,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            maxLines = 2
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(java.util.Date(ticket.createdAt)),
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Label,
                                    contentDescription = "Category",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = ticket.category.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() },
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
            
            // Empty State
            if (tickets.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Inbox,
                                contentDescription = "No tickets",
                                tint = Color.Gray,
                                modifier = Modifier.size(48.dp)
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = "No tickets yet",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Submit a support ticket and our team will get back to you as soon as possible.",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Button(
                                onClick = onCreateNewTicket,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFE53E3E)
                                ),
                                shape = RoundedCornerShape(25.dp)
                            ) {
                                Text(
                                    text = "Create Ticket",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TicketListScreenPreview() {
    SmartCitizenClubTheme {
        TicketListScreen(
            user = User(
                id = "1",
                name = "Abbas Uddin",
                email = "abbas@example.com",
                phone = "01741736354",
                type = UserType.USER
            ),
            onBackClick = {},
            onTicketSelected = {},
            onCreateNewTicket = {}
        )
    }
}
