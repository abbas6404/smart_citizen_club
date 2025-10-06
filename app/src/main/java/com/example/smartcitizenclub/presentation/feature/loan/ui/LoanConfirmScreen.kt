package com.example.smartcitizenclub.presentation.feature.loan.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanConfirmScreen(
    loanAmount: Double,
    interestRate: Double = 12.5,
    tenure: Int = 12,
    onBackClick: () -> Unit,
    onLoanComplete: () -> Unit = {}
) {
    var isHolding by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    
    // Calculate loan details
    val monthlyInterestRate = interestRate / 100 / 12
    val totalInterest = loanAmount * monthlyInterestRate * tenure
    val totalAmount = loanAmount + totalInterest
    val emiAmount = totalAmount / tenure
    
    // Simulate progress when holding
    LaunchedEffect(isHolding) {
        if (isHolding) {
            while (progress < 1f && isHolding) {
                delay(50)
                progress += 0.02f
            }
            if (progress >= 1f) {
                // Immediately navigate to success screen
                onLoanComplete()
            }
        } else {
            progress = 0f
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Orange Background
        TopAppBar(
            title = {
                Text(
                    text = "Confirm Loan Application",
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
                .padding(top = 16.dp)
        ) {
            // Loan Details Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
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
                    
                    // Loan Amount
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Loan Amount",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "৳${String.format("%.0f", loanAmount)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Interest Rate
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Interest Rate",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "$interestRate% per annum",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Tenure
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Tenure",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "$tenure months",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // EMI Amount
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Monthly EMI",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "৳${String.format("%.2f", emiAmount)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryOrangeGradient
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Total Interest
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total Interest",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "৳${String.format("%.2f", totalInterest)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Total Amount
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total Amount",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "৳${String.format("%.2f", totalAmount)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Tap and Hold Button with Progress Border
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 0.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isHolding = true
                                tryAwaitRelease()
                                isHolding = false
                            }
                        )
                    }
            ) {
                // Progress Border (when holding)
                if (isHolding) {
                    CircularProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .size(220.dp)
                            .align(Alignment.Center),
                        color = PrimaryOrangeGradient,
                        strokeWidth = 8.dp,
                        trackColor = Color.Transparent,
                        strokeCap = StrokeCap.Round
                    )
                }
                
                // Main Button
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    colors = CardDefaults.cardColors(
                        containerColor = PrimaryOrangeGradient
                    ),
                    shape = RoundedCornerShape(topStart = 150.dp, topEnd = 150.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        // Content positioned at bottom
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom,
                            modifier = Modifier.padding(bottom = 24.dp)
                        ) {
                            Icon(
                                Icons.Default.AccountBalance,
                                contentDescription = "Apply Loan",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            if (isHolding) {
                                // Show percentage in center when holding
                                Text(
                                    text = "${(progress * 100).toInt()}%",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            } else {
                                // Show normal text when not holding
                                Text(
                                    text = "Tap and hold to Apply",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoanConfirmScreenPreview() {
    SmartCitizenClubTheme {
        LoanConfirmScreen(
            loanAmount = 50000.0,
            interestRate = 12.5,
            tenure = 12,
            onBackClick = {},
            onLoanComplete = {}
        )
    }
}
