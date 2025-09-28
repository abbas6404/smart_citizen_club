package com.example.smartcitizenclub.presentation.feature.limitupgrade.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
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
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LimitUpgradeScreen(
    onBackClick: () -> Unit,
    onBasicUpgradeClick: () -> Unit = {},
    onPremiumUpgradeClick: () -> Unit = {},
    onGoldUpgradeClick: () -> Unit = {},
    onPlatinumUpgradeClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Orange Background
        TopAppBar(
            title = {
                Text(
                    text = "Limit Upgrade",
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
                containerColor = PrimaryOrangeGradient
            ),
            modifier = Modifier.statusBarsPadding()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    text = "Upgrade Your Limits",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Text(
                    text = "Choose a plan to increase your transaction limits and unlock premium features",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Current Plan Info
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Current Plan",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Basic",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryOrangeGradient
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Daily Limit: ৳10,000",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        
                        Text(
                            text = "Monthly Limit: ৳50,000",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Premium Upgrade Option
            item {
                UpgradePlanCard(
                    title = "Premium",
                    price = "৳299/month",
                    dailyLimit = "৳25,000",
                    monthlyLimit = "৳150,000",
                    features = listOf(
                        "Higher transaction limits",
                        "Priority customer support",
                        "Advanced security features"
                    ),
                    iconColor = Color(0xFF4CAF50), // Green
                    onClick = onPremiumUpgradeClick
                )
            }

            // Gold Upgrade Option
            item {
                UpgradePlanCard(
                    title = "Gold",
                    price = "৳599/month",
                    dailyLimit = "৳50,000",
                    monthlyLimit = "৳300,000",
                    features = listOf(
                        "Maximum transaction limits",
                        "24/7 premium support",
                        "Exclusive features",
                        "Cashback rewards"
                    ),
                    iconColor = Color(0xFFFFD700), // Gold
                    onClick = onGoldUpgradeClick
                )
            }

            // Platinum Upgrade Option
            item {
                UpgradePlanCard(
                    title = "Platinum",
                    price = "৳999/month",
                    dailyLimit = "৳100,000",
                    monthlyLimit = "৳500,000",
                    features = listOf(
                        "Unlimited transactions",
                        "Personal account manager",
                        "VIP features",
                        "Highest cashback rates",
                        "Priority processing"
                    ),
                    iconColor = Color(0xFF9C27B0), // Purple
                    onClick = onPlatinumUpgradeClick
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
private fun UpgradePlanCard(
    title: String,
    price: String,
    dailyLimit: String,
    monthlyLimit: String,
    features: List<String>,
    iconColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(iconColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Premium",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Title and Price
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Text(
                        text = price,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = PrimaryOrangeGradient
                    )
                }

                // Arrow
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = "Upgrade",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Limits
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Daily Limit",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = dailyLimit,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
                
                Column {
                    Text(
                        text = "Monthly Limit",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = monthlyLimit,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Features
            Text(
                text = "Features:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            features.forEach { feature ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Feature",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(16.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = feature,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                
                if (feature != features.last()) {
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LimitUpgradeScreenPreview() {
    SmartCitizenClubTheme {
        LimitUpgradeScreen(
            onBackClick = {},
            onBasicUpgradeClick = {},
            onPremiumUpgradeClick = {},
            onGoldUpgradeClick = {},
            onPlatinumUpgradeClick = {}
        )
    }
}
