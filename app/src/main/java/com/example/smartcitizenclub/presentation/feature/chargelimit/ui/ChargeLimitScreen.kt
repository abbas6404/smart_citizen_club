package com.example.smartcitizenclub.presentation.feature.chargelimit.ui

import androidx.compose.foundation.background
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
    // Sample service charges data
    val serviceCharges = remember {
        listOf(
            ServiceCharge("1", "Send Money", ChargeType.FIXED, 5.0, "Fixed charge for sending money"),
            ServiceCharge("2", "Cash Out", ChargeType.FIXED, 10.0, "Fixed charge for cash out"),
            ServiceCharge("3", "Mobile Recharge", ChargeType.FIXED, 0.0, "No charge for mobile recharge"),
            ServiceCharge("4", "Bill Payment", ChargeType.FIXED, 2.0, "Fixed charge for bill payment"),
            ServiceCharge("5", "Investment", ChargeType.PERCENTAGE, 1.0, "1% charge on investment amount"),
            ServiceCharge("6", "Loan Processing", ChargeType.PERCENTAGE, 2.5, "2.5% charge on loan amount")
        )
    }
    
    // Sample transaction limits data
    val transactionLimits = remember {
        listOf(
            TransactionLimit("1", "Send Money", LimitType.MINIMUM, 10.0, LimitPeriod.DAILY, "Minimum amount for sending money"),
            TransactionLimit("2", "Send Money", LimitType.MAXIMUM, 50000.0, LimitPeriod.DAILY, "Maximum amount for sending money per day"),
            TransactionLimit("3", "Cash Out", LimitType.MINIMUM, 50.0, LimitPeriod.DAILY, "Minimum amount for cash out"),
            TransactionLimit("4", "Cash Out", LimitType.MAXIMUM, 100000.0, LimitPeriod.DAILY, "Maximum amount for cash out per day"),
            TransactionLimit("5", "Mobile Recharge", LimitType.MINIMUM, 10.0, LimitPeriod.DAILY, "Minimum amount for mobile recharge"),
            TransactionLimit("6", "Mobile Recharge", LimitType.MAXIMUM, 1000.0, LimitPeriod.DAILY, "Maximum amount for mobile recharge per day"),
            TransactionLimit("7", "Investment", LimitType.MINIMUM, 1000.0, LimitPeriod.DAILY, "Minimum amount for investment"),
            TransactionLimit("8", "Investment", LimitType.MAXIMUM, 1000000.0, LimitPeriod.DAILY, "Maximum amount for investment per day")
        )
    }
    
    // Sample user limits data
    val userLimits = remember {
        listOf(
            UserLimit("user1", "Send Money", 25000.0, 50000.0, LimitPeriod.DAILY, System.currentTimeMillis() + 86400000),
            UserLimit("user1", "Cash Out", 50000.0, 100000.0, LimitPeriod.DAILY, System.currentTimeMillis() + 86400000),
            UserLimit("user1", "Mobile Recharge", 500.0, 1000.0, LimitPeriod.DAILY, System.currentTimeMillis() + 86400000),
            UserLimit("user1", "Investment", 25000.0, 100000.0, LimitPeriod.DAILY, System.currentTimeMillis() + 86400000)
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
                            Icons.Default.AccountBalance,
                            contentDescription = "Charge & Limit",
                            tint = ChargeLimitColors.Primary,
                            modifier = Modifier.size(48.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text(
                            text = "Service Charges & Transaction Limits",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "View your transaction limits and service charges",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            
            // User Limits Section
            item {
                Column {
                    Text(
                        text = "Your Current Limits",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        userLimits.forEach { limit ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
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
                                            text = limit.serviceName,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                        
                                        Text(
                                            text = "${String.format("%.0f", limit.currentAmount)}/${String.format("%.0f", limit.limitAmount)} ৳",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.Black
                                        )
                                    }
                                    
                                    Spacer(modifier = Modifier.height(8.dp))
                                    
                                    // Progress bar for limit usage
                                    LinearProgressIndicator(
                                        progress = (limit.currentAmount / limit.limitAmount).toFloat(),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(8.dp),
                                        color = when {
                                            (limit.currentAmount / limit.limitAmount) < 0.5 -> ChargeLimitColors.Green
                                            (limit.currentAmount / limit.limitAmount) < 0.8 -> ChargeLimitColors.Orange
                                            else -> ChargeLimitColors.Red
                                        },
                                        trackColor = Color(0xFFE0E0E0)
                                    )
                                    
                                    Spacer(modifier = Modifier.height(8.dp))
                                    
                                    Text(
                                        text = "Resets on: ${java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(java.util.Date(limit.resetDate))}",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            // Service Charges Section
            item {
                Column {
                    Text(
                        text = "Service Charges",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        serviceCharges.forEach { charge ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(
                                            text = charge.serviceName,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = charge.description,
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    
                                    Text(
                                        text = when (charge.chargeType) {
                                            ChargeType.FIXED -> if (charge.amount == 0.0) "Free" else "${String.format("%.0f", charge.amount)} ৳"
                                            ChargeType.PERCENTAGE -> "${String.format("%.1f", charge.amount)}%"
                                            ChargeType.SLAB -> "Slab Rate"
                                        },
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (charge.amount == 0.0) ChargeLimitColors.Green else ChargeLimitColors.Primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            // Transaction Limits Section
            item {
                Column {
                    Text(
                        text = "Transaction Limits",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        transactionLimits.forEach { limit ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(
                                            text = "${limit.serviceName} - ${limit.limitType.name}",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = limit.description,
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    
                                    Text(
                                        text = "${String.format("%.0f", limit.amount)} ৳",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ChargeLimitColors.Primary
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
