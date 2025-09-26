package com.example.smartcitizenclub.presentation.feature.recharge.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MobileRechargeAmountScreen(
    operator: MobileOperator,
    phoneNumber: String,
    onBackClick: () -> Unit,
    onAmountEntered: (Double) -> Unit = {},
    onPackageSelected: (RechargePackage) -> Unit = {}
) {
    var amount by remember { mutableStateOf("") }
    
    // Sample quick amounts
    val quickAmounts = listOf("10", "20", "50", "100", "200", "500")
    
    // Sample recharge packages
    val rechargePackages = remember {
        listOf(
            RechargePackage("1", operator, 10.0, "7 days", "100 MB Data"),
            RechargePackage("2", operator, 25.0, "30 days", "500 MB Data"),
            RechargePackage("3", operator, 50.0, "30 days", "1 GB Data"),
            RechargePackage("4", operator, 100.0, "30 days", "2 GB Data"),
            RechargePackage("5", operator, 200.0, "30 days", "5 GB Data"),
            RechargePackage("6", operator, 500.0, "30 days", "15 GB Data")
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
                    text = "Recharge Amount",
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
            
            // Operator and Phone Info
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.Gray.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Phone,
                                contentDescription = "Operator",
                                tint = Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Column {
                            Text(
                                text = operator.name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = phoneNumber,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
            
            // Amount Input Section
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Enter Amount",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Amount Display
                    Text(
                        text = if (amount.isEmpty()) "৳0.00" else "৳${amount}.00",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE53E3E)
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Amount Input Field
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { newAmount ->
                            // Only allow numeric input
                            if (newAmount.all { it.isDigit() } && newAmount.length <= 6) {
                                amount = newAmount
                            }
                        },
                        placeholder = {
                            Text(
                                text = "Enter amount",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFE53E3E),
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                        ),
                        leadingIcon = {
                            Text(
                                text = "৳",
                                fontSize = 18.sp,
                                color = Color.Gray
                            )
                        }
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Quick Amount Buttons
                    Text(
                        text = "Quick Amount",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        quickAmounts.forEach { quickAmount ->
                            Button(
                                onClick = { amount = quickAmount },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Gray.copy(alpha = 0.1f)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "৳$quickAmount",
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
            
            // Recharge Packages Section
            item {
                Column {
                    Text(
                        text = "Recharge Packages",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rechargePackages.forEach { pkg ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onPackageSelected(pkg) },
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
                                            text = "৳${pkg.amount.toInt()}",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = pkg.description,
                                            fontSize = 14.sp,
                                            color = Color.Gray
                                        )
                                        Text(
                                            text = pkg.validity,
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    
                                    Icon(
                                        Icons.Default.ArrowForward,
                                        contentDescription = "Select",
                                        tint = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            // Continue Button
            item {
                Button(
                    onClick = { 
                        val amountValue = amount.toDoubleOrNull() ?: 0.0
                        onAmountEntered(amountValue)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE53E3E)
                    ),
                    shape = RoundedCornerShape(25.dp),
                    enabled = amount.isNotEmpty() && amount.toDoubleOrNull() != null && amount.toDoubleOrNull()!! > 0
                ) {
                    Text(
                        text = "Continue",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MobileRechargeAmountScreenPreview() {
    SmartCitizenClubTheme {
        MobileRechargeAmountScreen(
            operator = MobileOperator(
                id = "1",
                name = "Grameenphone"
            ),
            phoneNumber = "01741736354",
            onBackClick = {},
            onAmountEntered = {},
            onPackageSelected = {}
        )
    }
}
