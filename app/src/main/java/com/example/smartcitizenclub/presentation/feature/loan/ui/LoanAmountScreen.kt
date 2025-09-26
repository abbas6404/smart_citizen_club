package com.example.smartcitizenclub.presentation.feature.loan.ui

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
import kotlin.math.pow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanAmountScreen(
    loan: Loan,
    onBackClick: () -> Unit,
    onAmountEntered: (Double, Int) -> Unit = { _, _ -> }
) {
    var amount by remember { mutableStateOf("") }
    var selectedTenure by remember { mutableStateOf(loan.tenureOptions.first()) }
    var amountError by remember { mutableStateOf(false) }
    
    val amountValue = amount.toDoubleOrNull() ?: 0.0
    val isValidAmount = amountValue >= loan.minAmount && amountValue <= loan.maxAmount
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Red Background
        TopAppBar(
            title = {
                Text(
                    text = "Loan Amount",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Loan Details Card
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
                    // Loan Info
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.AccountBalance,
                                contentDescription = "Loan",
                                tint = Color.Gray,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Column {
                            Text(
                                text = loan.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "${loan.interestRate}% Interest Rate",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Amount Range
                    Text(
                        text = "Loan Amount Range",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "৳${String.format("%.0f", loan.minAmount)} - ৳${String.format("%.0f", loan.maxAmount)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE53E3E)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Loan details
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.AttachMoney,
                                contentDescription = "Processing Fee",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "${loan.processingFee}%",
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                            Text(
                                text = "Processing Fee",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                        }
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.CalendarToday,
                                contentDescription = "Tenure Options",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "${loan.tenureOptions.first()}-${loan.tenureOptions.last()} months",
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                            Text(
                                text = "Tenure",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = loan.description,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
            
            // Amount Input Section
            Column {
                Text(
                    text = "Enter Loan Amount",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = amount,
                    onValueChange = { 
                        amount = it
                        amountError = false
                    },
                    placeholder = {
                        Text(
                            text = "Enter amount between ৳${String.format("%.0f", loan.minAmount)} - ৳${String.format("%.0f", loan.maxAmount)}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (amountError) Color.Red else Color(0xFFE53E3E),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    ),
                    leadingIcon = {
                        Text(
                            text = "৳",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    },
                    isError = amountError
                )
                
                if (amountError) {
                    Text(
                        text = "Please enter a valid amount between ৳${String.format("%.0f", loan.minAmount)} - ৳${String.format("%.0f", loan.maxAmount)}",
                        fontSize = 12.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            
            // Tenure Selection
            Column {
                Text(
                    text = "Select Tenure (Months)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Tenure options as chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    loan.tenureOptions.forEach { tenure ->
                        FilterChip(
                            selected = selectedTenure == tenure,
                            onClick = { selectedTenure = tenure },
                            label = {
                                Text(
                                    text = "$tenure months",
                                    fontSize = 14.sp
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFE53E3E),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Calculate and display loan details
            if (amount.isNotEmpty() && isValidAmount) {
                val processingFee = amountValue * loan.processingFee / 100
                val totalAmount = amountValue + processingFee
                val monthlyInterestRate = loan.interestRate / 12 / 100
                val tenureDouble = selectedTenure.toDouble()
                val emi = (amountValue * monthlyInterestRate * (1 + monthlyInterestRate).pow(tenureDouble)) / 
                         ((1 + monthlyInterestRate).pow(tenureDouble) - 1)
                
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
                            text = "Loan Summary",
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
                                text = "Principal Amount:",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "৳${String.format("%.2f", amountValue)}",
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Processing Fee (${loan.processingFee}%):",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "৳${String.format("%.2f", processingFee)}",
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "EMI Amount:",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "৳${String.format("%.2f", emi)}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total Payable:",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "৳${String.format("%.2f", emi * selectedTenure)}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE53E3E)
                            )
                        }
                    }
                }
            }
            
            // Confirm Button
            Button(
                onClick = {
                    val amountDouble = amount.toDoubleOrNull()
                    if (amountDouble != null && amountDouble >= loan.minAmount && amountDouble <= loan.maxAmount) {
                        onAmountEntered(amountDouble, selectedTenure)
                    } else {
                        amountError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53E3E)
                ),
                shape = RoundedCornerShape(25.dp),
                enabled = amount.isNotEmpty() && isValidAmount
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
fun LoanAmountScreenPreview() {
    SmartCitizenClubTheme {
        LoanAmountScreen(
            loan = Loan(
                id = "1",
                name = "Personal Loan",
                description = "Flexible personal loan for your needs",
                minAmount = 5000.0,
                maxAmount = 200000.0,
                interestRate = 12.5,
                tenureOptions = listOf(6, 12, 18, 24, 36),
                processingFee = 2.0
            ),
            onBackClick = {},
            onAmountEntered = { _, _ -> }
        )
    }
}
