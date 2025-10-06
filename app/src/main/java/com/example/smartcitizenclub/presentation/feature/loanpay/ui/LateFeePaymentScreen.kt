package com.example.smartcitizenclub.presentation.feature.loanpay.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.smartcitizenclub.presentation.feature.loan.ui.LateFee
import com.example.smartcitizenclub.presentation.feature.loan.ui.LateFeeStatus
import com.example.smartcitizenclub.presentation.feature.loan.ui.PaymentMethod
import com.example.smartcitizenclub.presentation.feature.loan.ui.UserLoan
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LateFeePaymentScreen(
    userLoan: UserLoan,
    lateFees: List<LateFee>,
    onBackClick: () -> Unit,
    onPayLateFee: (LateFee, Double) -> Unit = { _, _ -> }
) {
    var selectedLateFee by remember { mutableStateOf<LateFee?>(null) }
    var amount by remember { mutableStateOf("") }
    var amountError by remember { mutableStateOf(false) }
    
    val pendingLateFees = lateFees.filter { it.status == LateFeeStatus.PENDING }
    val totalPendingAmount = pendingLateFees.sumOf { it.amount }
    
    val amountValue = amount.toDoubleOrNull() ?: 0.0
    val isValidAmount = amountValue > 0 && amountValue <= totalPendingAmount
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Red Background
        TopAppBar(
            title = {
                Text(
                    text = "Pay Late Fees",
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
                containerColor = Color.Red
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
                        text = "Loan Information",
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
                                text = "Loan #${userLoan.loanNumber}",
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
                }
            }
            
            // Late Fees Summary
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = "Late Fees",
                            tint = Color.Red,
                            modifier = Modifier.size(24.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = "Pending Late Fees",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total Late Fees:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Red
                        )
                        Text(
                            text = "৳${String.format("%.2f", totalPendingAmount)}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "${pendingLateFees.size} pending late fee(s)",
                        fontSize = 12.sp,
                        color = Color.Red
                    )
                }
            }
            
            // Individual Late Fees List
            if (pendingLateFees.isNotEmpty()) {
                Text(
                    text = "Select Late Fee to Pay",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                
                pendingLateFees.forEach { lateFee ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedLateFee = lateFee },
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedLateFee?.id == lateFee.id) 
                                Color(0xFFFFF3E0) else Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedLateFee?.id == lateFee.id,
                                onClick = { selectedLateFee = lateFee },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.Red
                                )
                            )
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Late Fee #${lateFee.id}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                
                                Text(
                                    text = formatLateFeeDate(lateFee.createdDate),
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                
                                if (lateFee.description != null) {
                                    Text(
                                        text = lateFee.description,
                                        fontSize = 11.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                            
                            Text(
                                text = "৳${String.format("%.2f", lateFee.amount)}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )
                        }
                    }
                }
            }
            
            // Amount Input Section
            if (selectedLateFee != null) {
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
                                text = "Enter amount up to ৳${String.format("%.2f", selectedLateFee!!.amount)}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (amountError) Color.Red else Color.Red,
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
                            text = "Please enter a valid amount between ৳0.01 - ৳${String.format("%.2f", selectedLateFee!!.amount)}",
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
                        if (amountDouble != null && amountDouble > 0 && amountDouble <= selectedLateFee!!.amount) {
                            onPayLateFee(selectedLateFee!!, amountDouble)
                        } else {
                            amountError = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    shape = RoundedCornerShape(25.dp),
                    enabled = amount.isNotEmpty() && isValidAmount
                ) {
                    Text(
                        text = "Pay Late Fee",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
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

private fun formatLateFeeDate(timestamp: Long): String {
    val date = java.util.Date(timestamp)
    val format = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())
    return format.format(date)
}

@Preview(showBackground = true)
@Composable
fun LateFeePaymentScreenPreview() {
    SmartCitizenClubTheme {
        LateFeePaymentScreen(
            userLoan = UserLoan(
                id = "1",
                userId = "1",
                loanId = "1",
                loanNumber = "LN001234",
                subAccountId = "SUB001",
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
                nextPaymentDate = System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000L),
                hasOverduePayments = true,
                totalLateFeeAmount = 500.0
            ),
            lateFees = listOf(
                LateFee(
                    id = "LF001",
                    loanIssuedId = "LI001",
                    subAccountId = "SUB001",
                    amount = 300.0,
                    balanceBefore = 38000.0,
                    balanceAfter = 38000.0,
                    description = "Late fee for overdue EMI payment",
                    status = LateFeeStatus.PENDING,
                    createdDate = System.currentTimeMillis() - (10 * 24 * 60 * 60 * 1000L)
                ),
                LateFee(
                    id = "LF002",
                    loanIssuedId = "LI001",
                    subAccountId = "SUB001",
                    amount = 200.0,
                    balanceBefore = 38000.0,
                    balanceAfter = 38000.0,
                    description = "Late fee for overdue EMI payment",
                    status = LateFeeStatus.PENDING,
                    createdDate = System.currentTimeMillis() - (5 * 24 * 60 * 60 * 1000L)
                )
            ),
            onBackClick = {},
            onPayLateFee = { _, _ -> }
        )
    }
}
