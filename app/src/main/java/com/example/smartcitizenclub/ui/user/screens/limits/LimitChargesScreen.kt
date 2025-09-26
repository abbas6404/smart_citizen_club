package com.example.smartcitizenclub.ui.user.screens.limits

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LimitChargesScreen(
    user: User,
    onBackClick: () -> Unit
) {
    // Sample limits data
    val transactionLimits = remember {
        listOf(
            TransactionLimit("1", ServiceType.SEND_MONEY, LimitType.MINIMUM, 10.0, LimitPeriod.DAILY, "Minimum amount for sending money"),
            TransactionLimit("2", ServiceType.SEND_MONEY, LimitType.MAXIMUM, 50000.0, LimitPeriod.DAILY, "Maximum amount for sending money per day"),
            TransactionLimit("3", ServiceType.CASH_OUT, LimitType.MINIMUM, 50.0, LimitPeriod.DAILY, "Minimum amount for cash out"),
            TransactionLimit("4", ServiceType.CASH_OUT, LimitType.MAXIMUM, 100000.0, LimitPeriod.DAILY, "Maximum amount for cash out per day"),
            TransactionLimit("5", ServiceType.MOBILE_RECHARGE, LimitType.MINIMUM, 10.0, LimitPeriod.DAILY, "Minimum amount for mobile recharge"),
            TransactionLimit("6", ServiceType.MOBILE_RECHARGE, LimitType.MAXIMUM, 1000.0, LimitPeriod.DAILY, "Maximum amount for mobile recharge per day")
        )
    }
    
    // Sample charges data
    val serviceCharges = remember {
        listOf(
            ServiceCharge("1", ServiceType.SEND_MONEY, 5.0, ChargeType.FIXED, description = "Fixed charge for sending money"),
            ServiceCharge("2", ServiceType.CASH_OUT, 10.0, ChargeType.FIXED, description = "Fixed charge for cash out"),
            ServiceCharge("3", ServiceType.MOBILE_RECHARGE, 0.0, ChargeType.FIXED, description = "No charge for mobile recharge"),
            ServiceCharge("4", ServiceType.BILL_PAYMENT, 2.0, ChargeType.FIXED, description = "Fixed charge for bill payment")
        )
    }
    
    // Sample user limits data
    val userLimits = remember {
        listOf(
            UserLimit("user1", ServiceType.SEND_MONEY, 25000.0, 50000.0, LimitPeriod.DAILY, System.currentTimeMillis() + 86400000),
            UserLimit("user1", ServiceType.CASH_OUT, 50000.0, 100000.0, LimitPeriod.DAILY, System.currentTimeMillis() + 86400000)
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
                    text = "Limits & Charges",
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Limits Header
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.AccountBalance,
                            contentDescription = "Limits",
                            tint = Color(0xFFE53E3E),
                            modifier = Modifier.size(48.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text(
                            text = "Transaction Limits & Charges",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "View your transaction limits and service charges",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
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
                                            text = limit.serviceType.name.replace("_", " "),
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
                                            (limit.currentAmount / limit.limitAmount) < 0.5 -> Color(0xFF4CAF50) // Green
                                            (limit.currentAmount / limit.limitAmount) < 0.8 -> Color(0xFFFF9800) // Orange
                                            else -> Color(0xFFF44336) // Red
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
                                            text = limit.serviceType.name.replace("_", " "),
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
                                        color = Color(0xFFE53E3E)
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
                                            text = charge.serviceType.name.replace("_", " "),
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
                                        text = if (charge.amount == 0.0) "Free" else "${String.format("%.0f", charge.amount)} ৳",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (charge.amount == 0.0) Color(0xFF4CAF50) else Color(0xFFE53E3E)
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
fun LimitChargesScreenPreview() {
    SmartCitizenClubTheme {
        LimitChargesScreen(
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
