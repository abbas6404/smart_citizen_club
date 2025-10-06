package com.example.smartcitizenclub.presentation.feature.chargelimit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChargeLimitScreen(
    user: User,
    onBackClick: () -> Unit
) {
    // Sample package data with limits and charges
    val packages = remember {
        listOf(
            PackageInfo(
                id = "1",
                name = "Basic Package",
                description = "Standard user package",
                withdrawalLimit = 25000.0,
                loanLimit = 50000.0,
                sendMoneyCharge = 5.0,
                cashOutCharge = 10.0,
                isActive = true
            ),
            PackageInfo(
                id = "2",
                name = "Premium Package",
                description = "Enhanced limits for premium users",
                withdrawalLimit = 100000.0,
                loanLimit = 200000.0,
                sendMoneyCharge = 3.0,
                cashOutCharge = 8.0,
                isActive = true
            ),
            PackageInfo(
                id = "3",
                name = "VIP Package",
                description = "Maximum limits for VIP users",
                withdrawalLimit = 500000.0,
                loanLimit = 1000000.0,
                sendMoneyCharge = 1.0,
                cashOutCharge = 5.0,
                isActive = true
            ),
            PackageInfo(
                id = "4",
                name = "Business Package",
                description = "Special package for business users",
                withdrawalLimit = 1000000.0,
                loanLimit = 2000000.0,
                sendMoneyCharge = 2.0,
                cashOutCharge = 6.0,
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
                    text = "Charge & Limit",
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
                containerColor = ChargeLimitColors.Primary
            ),
            modifier = Modifier.statusBarsPadding()
        )
        
        // Main Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Header
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = ChargeLimitColors.LightGray),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.TableChart,
                            contentDescription = "Charge & Limit",
                            tint = ChargeLimitColors.Primary,
                            modifier = Modifier.size(48.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text(
                            text = "Package Limits & Charges",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Compare different packages and their limits and charges",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            
            // Package Limits & Charges Table
            item {
                Column {
                    Text(
                        text = "Package Comparison",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column {
                            // Table Header
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(ChargeLimitColors.Primary)
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Package",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.weight(2f)
                                )
                                Text(
                                    text = "Withdrawal Limit",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.weight(1.5f),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Loan Limit",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.weight(1.5f),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Send Money Charge",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.weight(1.5f),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Cash Out Charge",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.weight(1.5f),
                                    textAlign = TextAlign.Center
                                )
                            }
                            
                            // Table Rows
                            packages.forEachIndexed { index, packageInfo ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(if (index % 2 == 0) Color.White else Color(0xFFF8F9FA))
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier.weight(2f)
                                    ) {
                                        Text(
                                            text = packageInfo.name,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = packageInfo.description,
                                            fontSize = 11.sp,
                                            color = Color.Gray,
                                            maxLines = 2
                                        )
                                    }
                                    
                                    Text(
                                        text = "${String.format("%.0f", packageInfo.withdrawalLimit)} ৳",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = ChargeLimitColors.Blue,
                                        modifier = Modifier.weight(1.5f),
                                        textAlign = TextAlign.Center
                                    )
                                    
                                    Text(
                                        text = "${String.format("%.0f", packageInfo.loanLimit)} ৳",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = ChargeLimitColors.Green,
                                        modifier = Modifier.weight(1.5f),
                                        textAlign = TextAlign.Center
                                    )
                                    
                                    Text(
                                        text = "${String.format("%.0f", packageInfo.sendMoneyCharge)} ৳",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = ChargeLimitColors.Orange,
                                        modifier = Modifier.weight(1.5f),
                                        textAlign = TextAlign.Center
                                    )
                                    
                                    Text(
                                        text = "${String.format("%.0f", packageInfo.cashOutCharge)} ৳",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = ChargeLimitColors.Red,
                                        modifier = Modifier.weight(1.5f),
                                        textAlign = TextAlign.Center
                                    )
                                }
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
fun ChargeLimitScreenPreview() {
    SmartCitizenClubTheme {
        ChargeLimitScreen(
            user = User(
                id = "1",
                name = "Abbas Uddin",
                email = "abbas@example.com",
                phone = "01741736354",
                type = UserType.USER
            ),
            onBackClick = {}
        )
    }
}
