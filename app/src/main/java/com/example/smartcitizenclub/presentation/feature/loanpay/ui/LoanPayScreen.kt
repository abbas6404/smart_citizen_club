package com.example.smartcitizenclub.presentation.feature.loanpay.ui

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.presentation.feature.loan.ui.Loan
import com.example.smartcitizenclub.presentation.feature.loan.ui.LoanStatus
import com.example.smartcitizenclub.presentation.feature.loan.ui.UserLoan
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanPayScreen(
    userLoans: List<UserLoan>,
    onBackClick: () -> Unit,
    onLoanPayment: (UserLoan) -> Unit = {},
    onPaymentHistory: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Orange Background
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
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Payment,
                            contentDescription = "Loan Payment",
                            tint = PrimaryOrangeGradient,
                            modifier = Modifier.size(40.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Make Loan Payment",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = "Select a loan to make payment",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
            
            // Payment History Button
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPaymentHistory() },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
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
                            Icons.Default.History,
                            contentDescription = "Payment History",
                            tint = PrimaryOrangeGradient,
                            modifier = Modifier.size(24.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Payment History",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "View your loan payment history",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = "Navigate",
                            tint = Color.Gray
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
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = "No Loans",
                                tint = Color.Gray,
                                modifier = Modifier.size(40.dp)
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "No Active Loans",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
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
                            modifier = Modifier.padding(12.dp)
                        ) {
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
                                        fontSize = 12.sp,
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
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "৳${String.format("%.0f", userLoan.remainingAmount)}",
                                        fontSize = 12.sp,
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
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = getNextPaymentDate(userLoan.nextPaymentDate),
                                        fontSize = 12.sp,
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
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "৳${String.format("%.0f", userLoan.emiAmount)}",
                                        fontSize = 12.sp,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "EMI",
                                        fontSize = 10.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            LinearProgressIndicator(
                                progress = {
                                    val progress = (userLoan.totalAmount - userLoan.remainingAmount) / userLoan.totalAmount
                                    progress.toFloat()
                                },
                                color = Color(0xFF4CAF50),
                                trackColor = Color(0xFFE0E0E0),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Progress",
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "${String.format("%.1f", (userLoan.totalAmount - userLoan.remainingAmount) / userLoan.totalAmount * 100)}% Complete",
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
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
            onLoanPayment = {},
            onPaymentHistory = {}
        )
    }
}
