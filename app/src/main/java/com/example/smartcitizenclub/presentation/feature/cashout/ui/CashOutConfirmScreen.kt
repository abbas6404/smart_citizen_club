package com.example.smartcitizenclub.presentation.feature.cashout.ui

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
fun CashOutConfirmScreen(
    mobileNumber: String,
    amount: Double,
    reference: String? = null,
    onBackClick: () -> Unit,
    onCashOutComplete: () -> Unit = {}
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
                onCashOutComplete()
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
                    text = "Cash Out",
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
            // Transaction Details
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left Column - Transaction Info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Total",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "৳${String.format("%.2f", amount)}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "to ${mobileNumber}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    
                    // Reference display (if provided)
                    if (!reference.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Reference:",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = reference,
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                // Right Column - Balance Info
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "New Balance",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "৳${String.format("%.2f", 0.67)}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
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
                                Icons.Default.AttachMoney,
                                contentDescription = "Cash Out",
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
                                    text = "Tap and hold for Cash Out",
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
fun CashOutConfirmScreenPreview() {
    SmartCitizenClubTheme {
        CashOutConfirmScreen(
            mobileNumber = "01533619640",
            amount = 1000.0,
            reference = "Cash out for expenses",
            onBackClick = {},
            onCashOutComplete = {}
        )
    }
}