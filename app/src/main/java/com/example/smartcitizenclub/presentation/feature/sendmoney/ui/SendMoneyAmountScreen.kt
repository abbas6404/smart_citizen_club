package com.example.smartcitizenclub.presentation.feature.sendmoney.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.smartcitizenclub.presentation.theme.OrangeGradient
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoneyAmountScreen(
    contact: Contact,
    onBackClick: () -> Unit,
    onAmountEntered: (Double) -> Unit = {}
) {
    var amount by remember { mutableStateOf("") }
    
    // Quick amount buttons
    val quickAmounts = listOf("500", "1000", "2000", "5000")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Red Background
        TopAppBar(
            title = {
                Text(
                    text = "Enter Amount",
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Recipient Info
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PrimaryOrangeGradient.copy(alpha = 0.05f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(PrimaryOrangeGradient.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Avatar",
                            tint = PrimaryOrangeGradient,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(10.dp))
                    
                    Column {
                        // Name - Always show
                        Text(
                            text = if (contact.name.isNotEmpty()) contact.name else "Unknown Contact",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (contact.name.isNotEmpty()) Color.Black else Color.Gray
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        // Phone Number - Always show
                        Text(
                            text = if (contact.phoneNumber.isNotEmpty()) "Phone: ${contact.phoneNumber}" else "Phone: Not available",
                            fontSize = 14.sp,
                            color = if (contact.phoneNumber.isNotEmpty()) Color.Gray else Color.Gray.copy(alpha = 0.6f)
                        )
                        
                        Spacer(modifier = Modifier.height(2.dp))
                        
                        // Account Number - Always show
                        Text(
                            text = if (!contact.accountNumber.isNullOrEmpty()) "Account: ${contact.accountNumber}" else "Account: Not available",
                            fontSize = 14.sp,
                            color = if (!contact.accountNumber.isNullOrEmpty()) PrimaryOrangeGradient else PrimaryOrangeGradient.copy(alpha = 0.6f),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            // Amount Input Section
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
                    color = PrimaryOrangeGradient
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
                        focusedBorderColor = PrimaryOrangeGradient,
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
                    color = PrimaryOrangeGradient
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
                                containerColor = PrimaryOrangeGradient.copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "৳$quickAmount",
                                fontSize = 14.sp,
                                color = PrimaryOrangeGradient,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Continue Button
            Button(
                onClick = { 
                    val amountValue = amount.toDoubleOrNull() ?: 0.0
                    onAmountEntered(amountValue)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryOrangeGradient
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

@Preview(showBackground = true)
@Composable
fun SendMoneyAmountScreenPreview() {
    SmartCitizenClubTheme {
        SendMoneyAmountScreen(
            contact = Contact(
                id = "1",
                name = "Abdul Malek Koyal Dim",
                phoneNumber = "01777-127775",
                accountNumber = "123456789"
            ),
            onBackClick = {},
            onAmountEntered = {}
        )
    }
}
