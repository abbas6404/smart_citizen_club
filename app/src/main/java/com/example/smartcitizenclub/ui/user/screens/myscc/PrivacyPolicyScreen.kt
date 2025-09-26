package com.example.smartcitizenclub.ui.user.screens.myscc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(
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
                        text = "Privacy Policy",
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
                // Last Updated
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF59E0B).copy(alpha = 0.1f) // Gold from logo
                    ),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Update,
                            contentDescription = "Last Updated",
                            tint = Color(0xFFF59E0B), // Gold from logo
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Last Updated: December 2024",
                            fontSize = 14.sp,
                            color = Color(0xFF374151),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                // Introduction
                PolicySection(
                    title = "Introduction",
                    content = "Smart Citizen Club (SCC) is committed to protecting your privacy. This Privacy Policy explains how we collect, use, and safeguard your personal information when you use our mobile application and services."
                )
                
                // Information We Collect
                PolicySection(
                    title = "Information We Collect",
                    content = "We collect information you provide directly to us, such as when you create an account, complete KYC verification, or contact us for support. This may include:\n\n• Personal identification information (Name, NID, Date of Birth)\n• Contact information (Email, Phone Number)\n• Address information\n• KYC documents (NID copies, selfie)\n• Usage data and preferences"
                )
                
                // How We Use Information
                PolicySection(
                    title = "How We Use Your Information",
                    content = "We use the information we collect to:\n\n• Provide and maintain our services\n• Process KYC verification\n• Send you important updates and notifications\n• Improve our app and user experience\n• Comply with legal and regulatory requirements\n• Prevent fraud and ensure security"
                )
                
                // Data Security
                PolicySection(
                    title = "Data Security",
                    content = "We implement appropriate security measures to protect your personal information against unauthorized access, alteration, disclosure, or destruction. Your data is encrypted and stored securely on our servers."
                )
                
                // Data Sharing
                PolicySection(
                    title = "Data Sharing",
                    content = "We do not sell, trade, or rent your personal information to third parties. We may share your information only in the following circumstances:\n\n• With your explicit consent\n• To comply with legal obligations\n• To protect our rights and prevent fraud\n• With trusted service providers who assist us in operating our app"
                )
                
                // Your Rights
                PolicySection(
                    title = "Your Rights",
                    content = "You have the right to:\n\n• Access your personal information\n• Correct inaccurate data\n• Request deletion of your data\n• Withdraw consent at any time\n• Data portability\n• Lodge a complaint with relevant authorities"
                )
                
                // Contact Us
                PolicySection(
                    title = "Contact Us",
                    content = "If you have any questions about this Privacy Policy or our data practices, please contact us at:\n\nEmail: privacy@smartcitizenclub.com\nPhone: +880-123-456-7890\nAddress: Dhaka, Bangladesh"
                )
                
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun PolicySection(
    title: String,
    content: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF9FAFB)
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
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

@Preview(showBackground = true)
@Composable
fun PrivacyPolicyScreenPreview() {
    SmartCitizenClubTheme {
        PrivacyPolicyScreen(
            onBackClick = {}
        )
    }
}
