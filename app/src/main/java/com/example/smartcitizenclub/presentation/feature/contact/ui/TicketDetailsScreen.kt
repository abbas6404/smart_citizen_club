package com.example.smartcitizenclub.presentation.feature.contact.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
fun TicketDetailsScreen(
    ticket: SupportTicket,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Red Background
        TopAppBar(
            title = {
                Text(
                    text = "Ticket Details",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Ticket Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
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
                            text = "Ticket #${ticket.id}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        
                        Text(
                            text = ticket.status.name.replace("_", " "),
                            fontSize = 14.sp,
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
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = ticket.subject,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Category",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = ticket.category.name.replace("_", " "),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }
                        
                        Column {
                            Text(
                                text = "Priority",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = ticket.priority.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = when (ticket.priority) {
                                    TicketPriority.LOW -> Color(0xFF4CAF50)
                                    TicketPriority.MEDIUM -> Color(0xFFFF9800)
                                    TicketPriority.HIGH -> Color(0xFFF44336)
                                    TicketPriority.URGENT -> Color(0xFFE91E63)
                                }
                            )
                        }
                    }
                }
            }
            
            // Created Date
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = "Date",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = "Created on: ${java.text.SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", java.util.Locale.getDefault()).format(java.util.Date(ticket.createdAt))}",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }
            
            // Description
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Description",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = ticket.description,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }
            
            // User Info
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Submitted By",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Name",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = ticket.userName,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                    
                    if (ticket.userPhone != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Phone,
                                contentDescription = "Phone",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Text(
                                text = ticket.userPhone,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                    }
                    
                    if (ticket.userEmail != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = "Email",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Text(
                                text = ticket.userEmail,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
            
            // Response Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Response",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = "No response yet. Our support team will respond to your ticket shortly.",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TicketDetailsScreenPreview() {
    SmartCitizenClubTheme {
        TicketDetailsScreen(
            ticket = SupportTicket(
                id = "ticket_12345",
                subject = "Unable to send money to contact",
                description = "I'm trying to send money to my contact but the app is showing an error message. Please help me resolve this issue.",
                category = TicketCategory.TECHNICAL_SUPPORT,
                priority = TicketPriority.HIGH,
                status = TicketStatus.OPEN,
                createdAt = System.currentTimeMillis() - 3600000, // 1 hour ago
                updatedAt = System.currentTimeMillis() - 3600000, // 1 hour ago
                userId = "user_1",
                userName = "Abbas Uddin",
                userEmail = "abbas@example.com",
                userPhone = "01741736354"
            ),
            onBackClick = {}
        )
    }
}
