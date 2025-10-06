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
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactUsScreen(
    user: User,
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
                    text = "Contact Us",
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
                containerColor = Color(0xFFE53E3E)
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
            
            // Contact Information Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Get in Touch",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Text(
                        text = "We're here to help! Reach out to us through any of the following channels:",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        lineHeight = 20.sp
                    )
                    
                    // Contact Methods
                    ContactMethod(
                        icon = Icons.Default.Phone,
                        title = "Phone Support",
                        description = "Call us for immediate assistance",
                        contact = "+1 (555) 123-4567",
                        color = Color(0xFF4CAF50)
                    )
                    
                    ContactMethod(
                        icon = Icons.Default.Email,
                        title = "Email Support",
                        description = "Send us an email and we'll respond within 24 hours",
                        contact = "support@smartcitizenclub.com",
                        color = Color(0xFF2196F3)
                    )
                    
                    ContactMethod(
                        icon = Icons.Default.Chat,
                        title = "Live Chat",
                        description = "Chat with our support team in real-time",
                        contact = "Available 9 AM - 6 PM (Mon-Fri)",
                        color = Color(0xFF9C27B0)
                    )
                    
                    ContactMethod(
                        icon = Icons.Default.LocationOn,
                        title = "Office Address",
                        description = "Visit our office for in-person support",
                        contact = "123 Main Street, City, State 12345",
                        color = Color(0xFFE91E63)
                    )
                }
            }
            
            // Business Hours Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Business Hours",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    BusinessHourRow("Monday - Friday", "9:00 AM - 6:00 PM")
                    BusinessHourRow("Saturday", "10:00 AM - 4:00 PM")
                    BusinessHourRow("Sunday", "Closed")
                }
            }
            
            // FAQ Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Frequently Asked Questions",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    FAQItem(
                        question = "How do I reset my password?",
                        answer = "Go to Settings > Security > Change Password and follow the instructions."
                    )
                    
                    FAQItem(
                        question = "How long does it take to process a loan?",
                        answer = "Loan applications are typically processed within 1-3 business days."
                    )
                    
                    FAQItem(
                        question = "What are the transaction fees?",
                        answer = "Transaction fees vary by service. Check the specific service page for detailed fee information."
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun ContactMethod(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    contact: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier
                    .padding(12.dp)
                    .size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color.Gray,
                lineHeight = 16.sp
            )
            
            Text(
                text = contact,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = color
            )
        }
    }
}

@Composable
private fun BusinessHourRow(day: String, hours: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = day,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        
        Text(
            text = hours,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Composable
private fun FAQItem(question: String, answer: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = question,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        
        Text(
            text = answer,
            fontSize = 12.sp,
            color = Color.Gray,
            lineHeight = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContactUsScreenPreview() {
    SmartCitizenClubTheme {
        ContactUsScreen(
            user = User(
                id = "1",
                name = "John Doe",
                email = "john@example.com",
                phone = "+1234567890"
            ),
            onBackClick = {}
        )
    }
}