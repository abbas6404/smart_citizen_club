package com.example.smartcitizenclub.ui.user.screens.loan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanPayScreen(
    userLoans: List<UserLoan>,
    onBackClick: () -> Unit,
    onLoanPayment: (UserLoan) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Red Background
        TopAppBar(
            title = {
                Text(
                    text = "Loan Payment",
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
            
            // Header
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
                            Icons.Default.Payment,
                            contentDescription = "Loan Payment",
                            tint = Color(0xFFE53E3E),
                            modifier = Modifier.size(48.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text(
                            text = "Make Loan Payment",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Select a loan to make payment",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
            
            // Active Loans List
            if (userLoans.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = "No Loans",
                                tint = Color.Gray,
                                modifier = Modifier.size(48.dp)
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Text(
                                text = "No Active Loans",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "You don't have any active loans at the moment",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                items(userLoans.filter { it.status == LoanStatus.ACTIVE }) { userLoan ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLoanPayment(userLoan) },
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
                                        text = getLoanName(userLoan.loanId),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "Loan ID: ${userLoan.id}",
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                }
                                
                                Icon(
                                    Icons.Default.ChevronRight,
                                    contentDescription = "Navigate",
                                    tint = Color.Gray
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
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
                                        contentDescription = "Outstanding Amount",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = "৳${String.format("%.0f", userLoan.remainingAmount)}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "Outstanding",
                                        fontSize = 10.sp,
                                        color = Color.Gray
                                    )
                                }
                                
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        Icons.Default.CalendarToday,
                                        contentDescription = "Next Payment Date",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = getNextPaymentDate(userLoan.nextPaymentDate),
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "Next Payment",
                                        fontSize = 10.sp,
                                        color = Color.Gray
                                    )
                                }
                                
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        Icons.Default.AccountBalance,
                                        contentDescription = "EMI Amount",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = "৳${String.format("%.0f", userLoan.emiAmount)}",
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "EMI",
                                        fontSize = 10.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            LinearProgressIndicator(
                                progress = {
                                    val progress = (userLoan.totalAmount - userLoan.remainingAmount) / userLoan.totalAmount
                                    progress.toFloat()
                                },
                                color = Color(0xFF4CAF50),
                                trackColor = Color(0xFFE0E0E0),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Progress",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "${String.format("%.1f", (userLoan.totalAmount - userLoan.remainingAmount) / userLoan.totalAmount * 100)}% Complete",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
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
        // Top Bar with Red Background
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
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Loan Details",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = getLoanName(userLoan.loanId),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "Loan ID: ${userLoan.id}",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                        
                        Text(
                            text = "৳${String.format("%.2f", userLoan.remainingAmount)}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE53E3E)
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
                            text = "EMI Amount:",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "৳${String.format("%.2f", userLoan.emiAmount)}",
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
                            text = "Next Payment Date:",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = getNextPaymentDate(userLoan.nextPaymentDate),
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }
            }
            
            // Amount Input Section
            Column {
                Text(
                    text = "Enter Payment Amount",
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
                            text = "Enter amount up to ৳${String.format("%.2f", userLoan.remainingAmount)}",
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
                        text = "Please enter a valid amount between ৳0.01 - ৳${String.format("%.2f", userLoan.remainingAmount)}",
                        fontSize = 12.sp,
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
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Payment Method",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Payment method options
                    val paymentMethods = PaymentMethod.values()
                    paymentMethods.forEach { method ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedPaymentMethod = method }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedPaymentMethod == method,
                                onClick = { selectedPaymentMethod = method },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFFE53E3E)
                                )
                            )
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Text(
                                text = when (method) {
                                    PaymentMethod.WALLET -> "SCC Wallet"
                                    PaymentMethod.CARD -> "Credit/Debit Card"
                                    PaymentMethod.BANK_TRANSFER -> "Bank Transfer"
                                },
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                        
                        if (method != paymentMethods.last()) {
                            Divider(color = Color.Gray.copy(alpha = 0.3f))
                        }
                    }
                }
            }
            
            // PIN Input Section
            Column {
                Text(
                    text = "Enter PIN to Confirm",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = pin,
                    onValueChange = { 
                        pin = it
                        pinError = false
                    },
                    placeholder = {
                        Text(
                            text = "Enter 4-digit PIN",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = if (showPin) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (pinError) Color.Red else Color(0xFFE53E3E),
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
                        fontSize = 12.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
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
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53E3E)
                ),
                shape = RoundedCornerShape(25.dp),
                enabled = amount.isNotEmpty() && isValidAmount && pin.length == 4
            ) {
                Text(
                    text = "Make Payment",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(80.dp))
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
fun LoanPayScreenPreview() {
    SmartCitizenClubTheme {
        LoanPayScreen(
            userLoans = listOf(
                UserLoan(
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
                    status = LoanStatus.ACTIVE,
                    remainingAmount = 38000.0,
                    nextPaymentDate = System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000L)
                )
            ),
            onBackClick = {},
            onLoanPayment = {}
        )
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
                status = LoanStatus.ACTIVE,
                remainingAmount = 38000.0,
                nextPaymentDate = System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000L)
            ),
            onBackClick = {},
            onAmountEntered = { _, _, _ -> }
        )
    }
}
