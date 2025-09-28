package com.example.smartcitizenclub.presentation.feature.investment.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestmentSuccessScreen(
    option: InvestmentOption,
    amount: Double,
    onBackClick: () -> Unit,
    onViewPortfolioClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Blue Background
        TopAppBar(
            title = {
                Text(
                    text = "Investment Success",
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
                containerColor = InvestmentColors.Primary
            ),
            modifier = Modifier.statusBarsPadding()
        )
        
        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            // Success Icon
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(InvestmentColors.Green.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Success",
                    tint = InvestmentColors.Green,
                    modifier = Modifier.size(80.dp)
                )
            }
            
            // Success Message
            Text(
                text = "Investment Successful!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "Your investment has been processed successfully",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            
            // Investment Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Investment Details",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Investment details rows
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Investment Plan:",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = option.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Investment Amount:",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "৳${String.format("%.2f", amount)}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = InvestmentColors.Primary
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Expected Return:",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "${option.expectedReturn}%",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = InvestmentColors.Green
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Duration:",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = option.duration,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Risk Level:",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = option.riskLevel.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = getRiskColor(option.riskLevel)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Divider(color = Color.Gray.copy(alpha = 0.3f))
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Expected Return Amount:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        Text(
                            text = "৳${String.format("%.2f", amount * (option.expectedReturn / 100))}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = InvestmentColors.Green
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total Expected Value:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "৳${String.format("%.2f", amount + (amount * (option.expectedReturn / 100)))}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = InvestmentColors.Green
                        )
                    }
                }
            }
            
            // Transaction ID (simulated)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Transaction ID",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "INV${System.currentTimeMillis()}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Action Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onViewPortfolioClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = InvestmentColors.Primary
                    ),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text(
                        text = "View Portfolio",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                
                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = InvestmentColors.Primary
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        InvestmentColors.Primary
                    ),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text(
                        text = "Back to Home",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InvestmentSuccessScreenPreview() {
    SmartCitizenClubTheme {
        InvestmentSuccessScreen(
            option = InvestmentOption(
                id = "1",
                name = "SCC Savings Plan",
                description = "Low-risk savings with guaranteed returns",
                minAmount = 1000.0,
                maxAmount = 100000.0,
                expectedReturn = 8.5,
                riskLevel = RiskLevel.LOW,
                duration = "1 year",
                category = InvestmentCategory.SAVINGS
            ),
            amount = 10000.0,
            onBackClick = {},
            onViewPortfolioClick = {}
        )
    }
}
