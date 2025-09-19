package com.example.smartcitizenclub.ui.user.screens.myscc

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
import com.example.smartcitizenclub.ui.theme.SmartCitizenClubTheme

data class FAQItem(
    val question: String,
    val answer: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAQScreen(
    onBackClick: () -> Unit
) {
    val faqItems = remember {
        listOf(
            FAQItem(
                question = "What is Smart Citizen Club (SCC)?",
                answer = "Smart Citizen Club is a digital platform that provides various services to citizens including KYC verification, financial services, and community features."
            ),
            FAQItem(
                question = "How do I create an account?",
                answer = "You can create an account by downloading our app, clicking 'Sign Up', and providing your basic information including name, email, and phone number."
            ),
            FAQItem(
                question = "What is KYC verification?",
                answer = "KYC (Know Your Customer) verification is a process to verify your identity using government-issued documents like NID. This helps us provide secure services."
            ),
            FAQItem(
                question = "How long does KYC verification take?",
                answer = "KYC verification typically takes 24-48 hours after you submit all required documents. You will receive a notification once your verification is complete."
            ),
            FAQItem(
                question = "What documents do I need for KYC?",
                answer = "You need to upload:\n• NID Front Side\n• NID Back Side\n• Selfie with NID\n• Personal information (Name, NID Number, Date of Birth, Address)"
            ),
            FAQItem(
                question = "Is my personal information safe?",
                answer = "Yes, we use industry-standard encryption and security measures to protect your personal information. We never share your data with third parties without your consent."
            ),
            FAQItem(
                question = "How do I change my password?",
                answer = "Go to MySCC > Change Password, enter your current password, then enter and confirm your new password. Make sure your new password is at least 6 characters long."
            ),
            FAQItem(
                question = "What if I forget my password?",
                answer = "If you forget your password, you can reset it by clicking 'Forgot Password' on the login screen and following the instructions sent to your registered email or phone."
            ),
            FAQItem(
                question = "How do I contact customer support?",
                answer = "You can contact our customer support through:\n• Email: support@smartcitizenclub.com\n• Phone: +880-123-456-7890\n• In-app support chat"
            ),
            FAQItem(
                question = "Can I use the app without internet?",
                answer = "Some features require internet connection, but you can view previously loaded content offline. For full functionality, please ensure you have a stable internet connection."
            )
        )
    }
    
    var expandedItems by remember { mutableStateOf(setOf<Int>()) }
    
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
                        text = "Frequently Asked Questions",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            
            // Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(faqItems.size) { index ->
                    val item = faqItems[index]
                    val isExpanded = expandedItems.contains(index)
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isExpanded) 
                                Color(0xFF10B981).copy(alpha = 0.1f) // Light green when expanded
                            else 
                                Color(0xFFF9FAFB)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column {
                            // Question
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { 
                                        expandedItems = if (isExpanded) {
                                            expandedItems - index
                                        } else {
                                            expandedItems + index
                                        }
                                    }
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.question,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF374151),
                                    modifier = Modifier.weight(1f)
                                )
                                
                                Icon(
                                    if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                                    tint = Color(0xFF10B981), // Green from logo
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            
                            // Answer (expandable)
                            if (isExpanded) {
                                Divider(
                                    color = Color(0xFFE5E7EB),
                                    thickness = 1.dp
                                )
                                Text(
                                    text = item.answer,
                                    fontSize = 14.sp,
                                    color = Color(0xFF6B7280),
                                    lineHeight = 20.sp,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FAQScreenPreview() {
    SmartCitizenClubTheme {
        FAQScreen(
            onBackClick = {}
        )
    }
}
