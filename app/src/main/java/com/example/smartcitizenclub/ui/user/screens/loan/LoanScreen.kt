package com.example.smartcitizenclub.ui.user.screens.loan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanScreen(
    onBackClick: () -> Unit,
    onLoanClick: (Loan) -> Unit = {}
) {
    // Sample loans
    val loans = remember {
        listOf(
            Loan(
                id = "1",
                name = "Personal Loan",
                description = "Flexible personal loan for your needs",
                minAmount = 5000.0,
                maxAmount = 200000.0,
                interestRate = 12.5,
                tenureOptions = listOf(6, 12, 18, 24, 36),
                processingFee = 2.0
            ),
            Loan(
                id = "2",
                name = "Business Loan",
                description = "Grow your business with our loan",
                minAmount = 50000.0,
                maxAmount = 1000000.0,
                interestRate = 14.0,
                tenureOptions = listOf(12, 24, 36, 48, 60),
                processingFee = 2.5
            ),
            Loan(
                id = "3",
                name = "Education Loan",
                description = "Finance your education journey",
                minAmount = 10000.0,
                maxAmount = 500000.0,
                interestRate = 10.0,
                tenureOptions = listOf(12, 24, 36, 48, 60, 72),
                processingFee = 1.5
            ),
            Loan(
                id = "4",
                name = "Home Loan",
                description = "Make your dream home a reality",
                minAmount = 500000.0,
                maxAmount = 5000000.0,
                interestRate = 8.5,
                tenureOptions = listOf(60, 120, 180, 240, 300, 360),
                processingFee = 0.5
            )
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
                    text = "Apply for Loan",
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
                            Icons.Default.AccountBalance,
                            contentDescription = "Loan",
                            tint = Color(0xFFE53E3E),
                            modifier = Modifier.size(48.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text(
                            text = "Choose a Loan Type",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Select from a variety of loan options to suit your needs",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
            
            // Loans List
            items(loans) { loan ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onLoanClick(loan) },
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
                                    text = loan.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Interest: ${loan.interestRate}%",
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
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = loan.description,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                        
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
                                    contentDescription = "Min Amount",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "৳${String.format("%.0f", loan.minAmount)}",
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Min",
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }
                            
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.AttachMoney,
                                    contentDescription = "Max Amount",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "৳${String.format("%.0f", loan.maxAmount)}",
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Max",
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }
                            
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.CalendarToday,
                                    contentDescription = "Tenure",
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
                            
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.AccountBalance,
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
                                    text = "Fee",
                                    fontSize = 10.sp,
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

@Preview(showBackground = true)
@Composable
fun LoanScreenPreview() {
    SmartCitizenClubTheme {
        LoanScreen(
            onBackClick = {},
            onLoanClick = {}
        )
    }
}
