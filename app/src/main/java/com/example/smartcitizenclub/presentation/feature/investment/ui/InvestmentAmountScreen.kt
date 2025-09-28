package com.example.smartcitizenclub.presentation.feature.investment.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestmentAmountScreen(
    option: InvestmentOption,
    onBackClick: () -> Unit,
    onAmountEntered: (Double) -> Unit = {}
) {
    var amount by remember { mutableStateOf("") }
    var isValidAmount by remember { mutableStateOf(false) }
    
    // Quick amount options
    val quickAmounts = remember {
        listOf(
            option.minAmount,
            option.minAmount * 2,
            option.minAmount * 5,
            option.minAmount * 10
        ).filter { it <= option.maxAmount }
    }
    
    LaunchedEffect(amount) {
        val amountValue = amount.toDoubleOrNull() ?: 0.0
        isValidAmount = amountValue >= option.minAmount && amountValue <= option.maxAmount
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Blue Background
        TopAppBar(
            title = {
                Text(
                    text = "Investment Amount",
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Investment Option Details
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = option.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = option.description,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Investment details
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Expected Return",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "${option.expectedReturn}%",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = InvestmentColors.Green
                            )
                        }
                        
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Duration",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = option.duration,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Risk Level",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = option.riskLevel.name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = getRiskColor(option.riskLevel)
                            )
                        }
                    }
                }
            }
            
            // Amount Input Section
            Column {
                Text(
                    text = "Investment Amount",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    placeholder = {
                        Text(
                            text = "Enter amount (৳${String.format("%.0f", option.minAmount)} - ৳${String.format("%.0f", option.maxAmount)})",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = InvestmentColors.Primary,
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    ),
                    leadingIcon = {
                        Text(
                            text = "৳",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                )
                
                if (amount.isNotEmpty() && !isValidAmount) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Amount must be between ৳${String.format("%.0f", option.minAmount)} and ৳${String.format("%.0f", option.maxAmount)}",
                        fontSize = 12.sp,
                        color = InvestmentColors.Red
                    )
                }
            }
            
            // Quick Amount Buttons
            Column {
                Text(
                    text = "Quick Amount",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    quickAmounts.forEach { quickAmount ->
                        OutlinedButton(
                            onClick = { amount = quickAmount.toString() },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = InvestmentColors.Primary
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                InvestmentColors.Primary
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "৳${String.format("%.0f", quickAmount)}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
            
            // Expected Return Calculation
            if (amount.isNotEmpty() && isValidAmount) {
                val amountValue = amount.toDoubleOrNull() ?: 0.0
                val expectedReturn = amountValue * (option.expectedReturn / 100)
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = InvestmentColors.LightGray),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Expected Return Calculation",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
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
                                text = "৳${String.format("%.2f", amountValue)}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Expected Return (${option.expectedReturn}%):",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "৳${String.format("%.2f", expectedReturn)}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = InvestmentColors.Green
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total Value:",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "৳${String.format("%.2f", amountValue + expectedReturn)}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = InvestmentColors.Green
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Continue Button
            Button(
                onClick = { onAmountEntered(amount.toDoubleOrNull() ?: 0.0) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = InvestmentColors.Primary
                ),
                shape = RoundedCornerShape(25.dp),
                enabled = isValidAmount
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

@Preview(showBackground = true)
@Composable
fun InvestmentAmountScreenPreview() {
    SmartCitizenClubTheme {
        InvestmentAmountScreen(
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
            onBackClick = {},
            onAmountEntered = {}
        )
    }
}
