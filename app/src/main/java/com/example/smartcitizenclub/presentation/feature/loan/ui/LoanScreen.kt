package com.example.smartcitizenclub.presentation.feature.loan.ui

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import com.example.smartcitizenclub.presentation.theme.OrangeGradient
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanScreen(
    onBackClick: () -> Unit,
    onLoanConfirm: (Double) -> Unit = { _ -> }
) {
    var loanAmount by remember { mutableStateOf(50000.0) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Top Bar with Orange Background
        TopAppBar(
            title = {
                Text(
                    text = "Apply for Loan",
                    color = Color.White,
                    fontSize = 20.sp,
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // Header
            item {
                Text(
                    text = "Choose Your Loan Amount",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Text(
                    text = "Select your desired loan amount and choose from available loan options",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            // Loan Amount Input Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Loan Amount",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Amount Input Field
                        OutlinedTextField(
                            value = String.format("%.0f", loanAmount),
                            onValueChange = { value ->
                                value.toDoubleOrNull()?.let { amount ->
                                    if (amount >= 1000 && amount <= 10000000) {
                                        loanAmount = amount
                                    }
                                }
                            },
                            label = { Text("Enter Amount") },
                            prefix = { Text("৳") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryOrangeGradient,
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Amount Slider
                        Text(
                            text = "Use slider to adjust amount",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Slider(
                            value = loanAmount.toFloat(),
                            onValueChange = { value ->
                                loanAmount = value.toDouble()
                            },
                            valueRange = 1000f..1000000f,
                            modifier = Modifier.fillMaxWidth(),
                            colors = SliderDefaults.colors(
                                thumbColor = PrimaryOrangeGradient,
                                activeTrackColor = PrimaryOrangeGradient,
                                inactiveTrackColor = Color.Gray.copy(alpha = 0.3f)
                            )
                        )
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "৳1,000",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "৳1,000,000",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Interest Rate Display
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Interest Rate",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                                
                                Text(
                                    text = "12.5% per annum",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryOrangeGradient
                                )
                            }
                        }
                    }
                }
            }
            
            // Loan Limit Info
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = "Info",
                            tint = Color(0xFF2196F3),
                            modifier = Modifier.size(20.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = "Your maximum loan limit: ৳500,000",
                            fontSize = 14.sp,
                            color = Color(0xFF2196F3),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            // Confirm Button
            item {
                Button(
                    onClick = { 
                        onLoanConfirm(loanAmount)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = OrangeGradient,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Confirm Loan Amount",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Icon(
                                Icons.Default.ArrowForward,
                                contentDescription = "Confirm",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoanScreenPreview() {
    SmartCitizenClubTheme {
        LoanScreen(
            onBackClick = {},
            onLoanConfirm = { _ -> }
        )
    }
}
