package com.example.smartcitizenclub.presentation.feature.recharge.ui

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
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MobileRechargeConfirmScreen(
    phoneNumber: String,
    amount: Double,
    onBackClick: () -> Unit,
    onRechargeComplete: () -> Unit = {}
) {
    var isHolding by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    
    // Simulate progress when holding
    LaunchedEffect(isHolding) {
        if (isHolding) {
            while (progress < 1f && isHolding) {
                delay(50)
                progress += 0.02f
            }
            if (progress >= 1f) {
                // Immediately navigate to success screen
                onRechargeComplete()
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
                    text = "Mobile Recharge",
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
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Transaction Details Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Amount
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Amount",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "৳${String.format("%.2f", amount)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryOrangeGradient
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Mobile Number
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Mobile Number",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = phoneNumber,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Transaction Fee
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Transaction Fee",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "৳0.00",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Divider(color = Color.Gray.copy(alpha = 0.3f))
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Total Amount
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total Amount",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "৳${String.format("%.2f", amount)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Tap and Hold Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
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
                        contentAlignment = Alignment.Center
                    ) {
                        // Content positioned at center
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.Phone,
                                contentDescription = "Mobile Recharge",
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
                                    text = "Tap and hold for Mobile Recharge",
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MobileRechargeConfirmScreenPreview() {
    SmartCitizenClubTheme {
        MobileRechargeConfirmScreen(
            phoneNumber = "01741736354",
            amount = 100.0,
            onBackClick = {},
            onRechargeComplete = {}
        )
    }
}
