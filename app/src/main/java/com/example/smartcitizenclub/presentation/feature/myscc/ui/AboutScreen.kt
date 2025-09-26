package com.example.smartcitizenclub.presentation.feature.myscc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with Red Background (from logo)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE53E3E)) // Red from logo
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = "About SCC",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            
            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // App Logo and Title
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF59E0B).copy(alpha = 0.1f) // Gold from logo
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // App Logo Placeholder
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE53E3E)), // Red from logo
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "SCC Logo",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Smart Citizen Club",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE53E3E), // Red from logo
                            textAlign = TextAlign.Center
                        )
                        
                        Text(
                            text = "Version 1.0.0",
                            fontSize = 14.sp,
                            color = Color(0xFF6B7280),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                // App Description
                AboutSection(
                    title = "About Our App",
                    content = "Smart Citizen Club (SCC) is a comprehensive digital platform designed to empower citizens with easy access to essential services. Our mission is to bridge the gap between citizens and government services through technology, making everyday tasks simpler and more accessible."
                )
                
                // Features
                AboutSection(
                    title = "Key Features",
                    content = "• KYC Verification: Secure identity verification process\n• Digital Services: Access to various government services\n• Community Features: Connect with fellow citizens\n• Secure Messaging: Safe communication platform\n• Multi-language Support: English and বাংলা\n• Real-time Updates: Stay informed with notifications"
                )
                
                // Mission
                AboutSection(
                    title = "Our Mission",
                    content = "To create a digital ecosystem that makes government services accessible, transparent, and efficient for every citizen, fostering a more connected and empowered society."
                )
                
                // Vision
                AboutSection(
                    title = "Our Vision",
                    content = "To be the leading digital platform that transforms how citizens interact with government services, making Bangladesh a truly smart nation."
                )
                
                // Team
                AboutSection(
                    title = "Our Team",
                    content = "We are a dedicated team of developers, designers, and policy experts working together to create innovative solutions that serve the needs of our citizens. Our diverse team brings together technical expertise and deep understanding of citizen needs."
                )
                
                // Contact Information
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF10B981).copy(alpha = 0.1f) // Green from logo
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Contact Information",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981), // Green from logo
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        
                        ContactItem(
                            icon = Icons.Default.Email,
                            title = "Email",
                            value = "info@smartcitizenclub.com"
                        )
                        
                        ContactItem(
                            icon = Icons.Default.Phone,
                            title = "Phone",
                            value = "+880-123-456-7890"
                        )
                        
                        ContactItem(
                            icon = Icons.Default.LocationOn,
                            title = "Address",
                            value = "Dhaka, Bangladesh"
                        )
                    }
                }
                
                // Copyright
                Text(
                    text = "© 2024 Smart Citizen Club. All rights reserved.",
                    fontSize = 12.sp,
                    color = Color(0xFF9CA3AF),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun AboutSection(
    title: String,
    content: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF9FAFB)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE53E3E), // Red from logo
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = content,
                fontSize = 14.sp,
                color = Color(0xFF374151),
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun ContactItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = title,
            tint = Color(0xFF10B981), // Green from logo
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "$title: $value",
            fontSize = 14.sp,
            color = Color(0xFF374151)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    SmartCitizenClubTheme {
        AboutScreen(
            onBackClick = {}
        )
    }
}
