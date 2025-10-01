package com.example.smartcitizenclub.presentation.feature.loanpay.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.presentation.feature.loan.ui.PaymentMethod
import com.example.smartcitizenclub.presentation.feature.loan.ui.UserLoan
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanPaymentAmountScreen(
    userLoan: UserLoan,
    onBackClick: () -> Unit,
    onAmountEntered: (Double, PaymentMethod, String) -> Unit = { _, _, _ -> }
) {
    var amount by remember { mutableStateOf("") }
    var selectedPaymentMethod by remember { mutableStateOf(PaymentMethod.WALLET) }
    var pin by remember { mutableStateOf("") }
    var showPin by remember { mutableStateOf(false) }
    var amountError by remember { mutableStateOf(false) }
    var pinError by remember { mutableStateOf(false) }
    
    val amountValue = amount.toDoubleOrNull() ?: 0.0
    val isValidAmount = amountValue > 0 && amountValue <= userLoan.remainingAmount
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Orange Background
        TopAppBar(
            title = {
                Text(
                    text = "Payment Amount",
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
        
        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            // Loan Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = "Loan Details",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = getLoanName(userLoan.loanId),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "Loan ID: ${userLoan.id}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        
                        Text(
                            text = "৳${String.format("%.2f", userLoan.remainingAmount)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryOrangeGradient
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.3f))
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "EMI Amount:",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "৳${String.format("%.2f", userLoan.emiAmount)}",
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Next Payment Date:",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = getNextPaymentDate(userLoan.nextPaymentDate),
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }
            }
            
            // Amount Input Section
            Column {
                Text(
                    text = "Enter Payment Amount",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                OutlinedTextField(
                    value = amount,
                    onValueChange = { 
                        amount = it
                        amountError = false
                    },
                    placeholder = {
                        Text(
                            text = "Enter amount up to ৳${String.format("%.2f", userLoan.remainingAmount)}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (amountError) Color.Red else PrimaryOrangeGradient,
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    ),
                    leadingIcon = {
                        Text(
                            text = "৳",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    },
                    isError = amountError
                )
                
                if (amountError) {
                    Text(
                        text = "Please enter a valid amount between ৳0.01 - ৳${String.format("%.2f", userLoan.remainingAmount)}",
                        fontSize = 10.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            
            // Payment Method
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = "Payment Method",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Payment method options
                    val paymentMethods = PaymentMethod.values()
                    paymentMethods.forEach { method ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedPaymentMethod == method,
                                onClick = { selectedPaymentMethod = method },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = PrimaryOrangeGradient
                                )
                            )
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Text(
                                text = when (method) {
                                    PaymentMethod.WALLET -> "SCC Wallet"
                                    PaymentMethod.CARD -> "Credit/Debit Card"
                                    PaymentMethod.BANK_TRANSFER -> "Bank Transfer"
                                },
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                        }
                        
                        if (method != paymentMethods.last()) {
                            HorizontalDivider(color = Color.Gray.copy(alpha = 0.3f))
                        }
                    }
                }
            }
            
            // PIN Input Section
            Column {
                Text(
                    text = "Enter PIN to Confirm",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                OutlinedTextField(
                    value = pin,
                    onValueChange = { 
                        pin = it
                        pinError = false
                    },
                    placeholder = {
                        Text(
                            text = "Enter 4-digit PIN",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = if (showPin) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (pinError) Color.Red else PrimaryOrangeGradient,
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    ),
                    trailingIcon = {
                        IconButton(onClick = { showPin = !showPin }) {
                            Icon(
                                if (showPin) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (showPin) "Hide PIN" else "Show PIN",
                                tint = Color.Gray
                            )
                        }
                    },
                    isError = pinError
                )
                
                if (pinError) {
                    Text(
                        text = "Please enter a valid 4-digit PIN",
                        fontSize = 10.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            
            // Confirm Button
            Button(
                onClick = {
                    val amountDouble = amount.toDoubleOrNull()
                    if (amountDouble != null && amountDouble > 0 && amountDouble <= userLoan.remainingAmount &&
                        pin.length == 4 && pin.all { it.isDigit() }) {
                        onAmountEntered(amountDouble, selectedPaymentMethod, pin)
                    } else {
                        if (amountDouble == null || amountDouble <= 0 || amountDouble > userLoan.remainingAmount) {
                            amountError = true
                        }
                        if (pin.length != 4 || !pin.all { it.isDigit() }) {
                            pinError = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryOrangeGradient
                ),
                shape = RoundedCornerShape(25.dp),
                enabled = amount.isNotEmpty() && isValidAmount && pin.length == 4
            ) {
                Text(
                    text = "Make Payment",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// Helper functions
private fun getLoanName(loanId: String): String {
    return when (loanId) {
        "1" -> "Personal Loan"
        "2" -> "Business Loan"
        "3" -> "Education Loan"
        "4" -> "Home Loan"
        else -> "Loan"
    }
}

private fun getNextPaymentDate(timestamp: Long?): String {
    if (timestamp == null) return "N/A"
    
    val now = System.currentTimeMillis()
    val diff = timestamp - now
    val days = diff / (24 * 60 * 60 * 1000)
    
    return when {
        days <= 0 -> "Due"
        days == 1L -> "Tomorrow"
        days < 30L -> "${days} days"
        else -> {
            val months = days / 30
            "${months} months"
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoanPaymentAmountScreenPreview() {
    SmartCitizenClubTheme {
        LoanPaymentAmountScreen(
            userLoan = UserLoan(
                id = "1",
                userId = "1",
                loanId = "1",
                amount = 50000.0,
                tenure = 12,
                interestRate = 12.5,
                processingFee = 2.0,
                emiAmount = 4467.89,
                totalAmount = 53614.68,
                startDate = System.currentTimeMillis() - (3 * 30 * 24 * 60 * 60 * 1000L),
                endDate = System.currentTimeMillis() + (9 * 30 * 24 * 60 * 60 * 1000L),
                status = com.example.smartcitizenclub.presentation.feature.loan.ui.LoanStatus.ACTIVE,
                remainingAmount = 38000.0,
                nextPaymentDate = System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000L)
            ),
            onBackClick = {},
            onAmountEntered = { _, _, _ -> }
        )
    }
}
