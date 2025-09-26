package com.example.smartcitizenclub.ui.user.screens.donation

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
import androidx.compose.ui.draw.clip
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
fun DonationHistoryScreen(
    user: User,
    onBackClick: () -> Unit,
    onDonateAgain: (DonationCampaign) -> Unit = {}
) {
    // Sample donation history
    val donationHistory = remember {
        listOf(
            UserDonation(
                id = "donation_1",
                userId = user.id,
                campaignId = "1",
                amount = 500.0,
                timestamp = System.currentTimeMillis() - 86400000, // 1 day ago
                paymentMethod = PaymentMethod.WALLET,
                status = DonationStatus.COMPLETED,
                anonymous = false
            ),
            UserDonation(
                id = "donation_2",
                userId = user.id,
                campaignId = "2",
                amount = 1000.0,
                timestamp = System.currentTimeMillis() - 172800000, // 2 days ago
                paymentMethod = PaymentMethod.CARD,
                status = DonationStatus.COMPLETED,
                anonymous = true
            ),
            UserDonation(
                id = "donation_3",
                userId = user.id,
                campaignId = "3",
                amount = 250.0,
                timestamp = System.currentTimeMillis() - 259200000, // 3 days ago
                paymentMethod = PaymentMethod.WALLET,
                status = DonationStatus.COMPLETED,
                anonymous = false
            )
        )
    }
    
    // Sample campaigns for donation history
    val campaigns = remember {
        listOf(
            DonationCampaign(
                id = "1",
                title = "Education for Underprivileged Children",
                description = "Help us provide education to children who cannot afford it.",
                organization = "Smart Citizen Education Foundation",
                targetAmount = 500000.0,
                raisedAmount = 325000.0,
                endDate = System.currentTimeMillis() + 2592000000,
                category = DonationCategory.EDUCATION,
                isActive = true
            ),
            DonationCampaign(
                id = "2",
                title = "Clean Water for Rural Communities",
                description = "Provide clean drinking water to rural communities.",
                organization = "Water for All Foundation",
                targetAmount = 300000.0,
                raisedAmount = 150000.0,
                endDate = System.currentTimeMillis() + 1296000000,
                category = DonationCategory.ENVIRONMENT,
                isActive = true
            ),
            DonationCampaign(
                id = "3",
                title = "Medical Aid for Flood Victims",
                description = "Provide immediate medical assistance to flood victims.",
                organization = "Disaster Relief Organization",
                targetAmount = 200000.0,
                raisedAmount = 180000.0,
                endDate = System.currentTimeMillis() + 604800000,
                category = DonationCategory.DISASTER_RELIEF,
                isActive = true
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
                    text = "Donation History",
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
            
            // Header with Stats
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Your Impact",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${donationHistory.size}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE53E3E)
                                )
                                Text(
                                    text = "Donations",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "৳${String.format("%.0f", donationHistory.sumOf { it.amount })}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE53E3E)
                                )
                                Text(
                                    text = "Total Given",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${campaigns.size}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE53E3E)
                                )
                                Text(
                                    text = "Causes",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
            
            // Donation History List
            if (donationHistory.isNotEmpty()) {
                item {
                    Text(
                        text = "Recent Donations",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                
                items(donationHistory) { donation ->
                    val campaign = campaigns.find { it.id == donation.campaignId }
                    
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                campaign?.let { onDonateAgain(it) }
                            },
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
                                Column {
                                    Text(
                                        text = campaign?.title ?: "Unknown Campaign",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    
                                    if (donation.anonymous) {
                                        Text(
                                            text = "Anonymous Donation",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                }
                                
                                Text(
                                    text = "৳${String.format("%.0f", donation.amount)}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE53E3E)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(java.util.Date(donation.timestamp)),
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                
                                Text(
                                    text = when (donation.paymentMethod) {
                                        PaymentMethod.WALLET -> "Wallet"
                                        PaymentMethod.CARD -> "Card"
                                        PaymentMethod.BANK_TRANSFER -> "Bank"
                                        PaymentMethod.MOBILE_RECHARGE -> "Mobile"
                                    },
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(0xFF4CAF50))
                                )
                                
                                Spacer(modifier = Modifier.width(8.dp))
                                
                                Text(
                                    text = donation.status.name,
                                    fontSize = 12.sp,
                                    color = Color(0xFF4CAF50)
                                )
                            }
                        }
                    }
                }
            } else {
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
                                Icons.Default.FavoriteBorder,
                                contentDescription = "No donations",
                                tint = Color.Gray,
                                modifier = Modifier.size(48.dp)
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = "No Donations Yet",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Your donation history will appear here once you make your first donation.",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
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
fun DonationHistoryScreenPreview() {
    SmartCitizenClubTheme {
        DonationHistoryScreen(
            user = User(
                id = "1",
                name = "Abbas Uddin",
                email = "abbas@example.com",
                phone = "01741736354",
                type = UserType.USER
            ),
            onBackClick = {},
            onDonateAgain = {}
        )
    }
}
