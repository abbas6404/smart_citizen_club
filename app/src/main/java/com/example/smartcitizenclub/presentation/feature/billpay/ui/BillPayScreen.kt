package com.example.smartcitizenclub.presentation.feature.billpay.ui

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
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillPayScreen(
    payments: List<BillPayment>,
    onBackClick: () -> Unit,
    onPaymentSelected: (BillPayment) -> Unit = {}
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
                    text = "Bill Pay",
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Coming Soon Icon
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        color = Color(0xFFE53E3E).copy(alpha = 0.1f),
                        shape = RoundedCornerShape(60.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Construction,
                    contentDescription = "Coming Soon",
                    tint = Color(0xFFE53E3E),
                    modifier = Modifier.size(60.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Coming Soon Text
            Text(
                text = "Coming Soon",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE53E3E),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Bill Payment Service",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "We're working hard to bring you a comprehensive bill payment service. Stay tuned for updates!",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 24.sp
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Features Preview Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "What to Expect",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Feature list
                    val features = listOf(
                        "Utility Bill Payments",
                        "Mobile & Internet Bills",
                        "Insurance Premiums",
                        "Credit Card Payments",
                        "Easy Payment Tracking"
                    )
                    
                    features.forEach { feature ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = "Feature",
                                tint = Color(0xFF4CAF50),
                                modifier = Modifier.size(20.dp)
                            )
                            
                            Spacer(modifier = Modifier.width(12.dp))
                            
                            Text(
                                text = feature,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                        
                        if (feature != features.last()) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillPaymentAmountScreen(
    payment: BillPayment,
    onBackClick: () -> Unit,
    onAmountEntered: (Double, PaymentMethod, String) -> Unit = { _, _, _ -> }
) {
    var amount by remember { mutableStateOf(payment.amount.toString()) }
    var selectedPaymentMethod by remember { mutableStateOf(PaymentMethod.WALLET) }
    var pin by remember { mutableStateOf("") }
    var showPin by remember { mutableStateOf(false) }
    var amountError by remember { mutableStateOf(false) }
    var pinError by remember { mutableStateOf(false) }
    
    val amountValue = amount.toDoubleOrNull() ?: 0.0
    val isValidAmount = amountValue > 0 && amountValue <= payment.amount
    
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
            
            // Payment Details Card
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
                        text = "Payment Details",
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
                                text = payment.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = payment.type.name,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                        
                        Text(
                            text = "৳${String.format("%.2f", payment.amount)}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE53E3E)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.3f))
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Reference:",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = payment.reference ?: "N/A",
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
                            text = "Due Date:",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = payment.dueDate?.let { formatDateTime(it) } ?: "N/A",
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
                            text = "Enter amount up to ৳${String.format("%.2f", payment.amount)}",
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
                        text = "Please enter a valid amount between ৳0.01 - ৳${String.format("%.2f", payment.amount)}",
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
                                    PaymentMethod.MOBILE_RECHARGE -> "Mobile Recharge"
                                },
                                fontSize = 14.sp,
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
                    if (amountDouble != null && amountDouble > 0 && amountDouble <= payment.amount &&
                        pin.length == 4 && pin.all { it.isDigit() }) {
                        onAmountEntered(amountDouble, selectedPaymentMethod, pin)
                    } else {
                        if (amountDouble == null || amountDouble <= 0 || amountDouble > payment.amount) {
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
private fun getBillPaymentTypeIcon(type: BillPaymentType): androidx.compose.ui.graphics.vector.ImageVector {
    return when (type) {
        BillPaymentType.UTILITY -> Icons.Default.Home
        BillPaymentType.MOBILE_INTERNET -> Icons.Default.Phone
        BillPaymentType.INSURANCE -> Icons.Default.Security
        BillPaymentType.CREDIT_CARD -> Icons.Default.CreditCard
        BillPaymentType.OTHER -> Icons.Default.Help
    }
}

private fun formatDateTime(timestamp: Long): String {
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
fun BillPayScreenPreview() {
    SmartCitizenClubTheme {
        BillPayScreen(
            payments = listOf(
                BillPayment(
                    id = "1",
                    type = BillPaymentType.UTILITY,
                    title = "Electricity Bill",
                    description = "Monthly electricity bill",
                    amount = 2500.0,
                    dueDate = System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000L),
                    status = BillPaymentStatus.PENDING
                ),
                BillPayment(
                    id = "2",
                    type = BillPaymentType.MOBILE_INTERNET,
                    title = "Internet Bill",
                    description = "Monthly internet subscription",
                    amount = 1200.0,
                    status = BillPaymentStatus.PENDING
                )
            ),
            onBackClick = {},
            onPaymentSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BillPaymentAmountScreenPreview() {
    SmartCitizenClubTheme {
        BillPaymentAmountScreen(
            payment = BillPayment(
                id = "1",
                type = BillPaymentType.UTILITY,
                title = "Electricity Bill",
                description = "Monthly electricity bill",
                amount = 2500.0,
                dueDate = System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000L),
                status = BillPaymentStatus.PENDING
            ),
            onBackClick = {},
            onAmountEntered = { _, _, _ -> }
        )
    }
}
