package com.example.smartcitizenclub.ui.user.screens.packages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageConfirmScreen(
    pkg: Package,
    onBackClick: () -> Unit,
    onConfirmClick: (PaymentMethod, String) -> Unit = { _, _ -> }
) {
    var selectedPaymentMethod by remember { mutableStateOf(PaymentMethod.WALLET) }
    var pin by remember { mutableStateOf("") }
    var showPin by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Red Background
        TopAppBar(
            title = {
                Text(
                    text = "Confirm Purchase",
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
            
            // Package Details Card
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
                    // Package Info
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
                                Icons.Default.ShoppingBag,
                                contentDescription = "Package",
                                tint = Color.Gray,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Column {
                            Text(
                                text = pkg.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = pkg.operator,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Price
                    Text(
                        text = "Package Price",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "৳${String.format("%.2f", pkg.price)}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE53E3E)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Package details
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.DataUsage,
                                contentDescription = "Data",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = pkg.dataLimit,
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                            Text(
                                text = "Data",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                        }
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Message,
                                contentDescription = "SMS",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = pkg.smsLimit,
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                            Text(
                                text = "SMS",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                        }
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Phone,
                                contentDescription = "Voice",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = pkg.voiceLimit,
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                            Text(
                                text = "Voice",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                        }
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.CalendarToday,
                                contentDescription = "Validity",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = pkg.validity,
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                            Text(
                                text = "Validity",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = pkg.description,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
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
                    onValueChange = { pin = it },
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
                        focusedBorderColor = Color(0xFFE53E3E),
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
                    }
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Confirm Button
            Button(
                onClick = { onConfirmClick(selectedPaymentMethod, pin) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53E3E)
                ),
                shape = RoundedCornerShape(25.dp),
                enabled = pin.length == 4
            ) {
                Text(
                    text = "Confirm Purchase",
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
fun PackageConfirmScreenPreview() {
    SmartCitizenClubTheme {
        PackageConfirmScreen(
            pkg = Package(
                id = "1",
                name = "Internet 1GB",
                description = "1GB high-speed data for 7 days",
                price = 99.0,
                validity = "7 days",
                dataLimit = "1GB",
                smsLimit = "0 SMS",
                voiceLimit = "0 mins",
                operator = "Grameenphone",
                category = PackageCategory.INTERNET
            ),
            onBackClick = {},
            onConfirmClick = { _, _ -> }
        )
    }
}
