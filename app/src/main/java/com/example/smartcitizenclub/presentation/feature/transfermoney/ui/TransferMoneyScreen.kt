package com.example.smartcitizenclub.presentation.feature.transfermoney.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferMoneyScreen(
    onBackClick: () -> Unit,
    onSccToBinanceClick: () -> Unit = {},
    onSccToBkashClick: () -> Unit = {},
    onSccToNagadClick: () -> Unit = {},
    onSccToRocketClick: () -> Unit = {}
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
                    text = "Transfer Money",
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    text = "Transfer Money from SCC Wallet",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Text(
                    text = "Choose your preferred service to transfer money from your Smart Citizen Club wallet",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            // SCC to Binance Option (First Position)
            item {
                TransferMethodCard(
                    title = "SCC to Binance",
                    subtitle = "Transfer money to your Binance account",
                    iconText = "BN",
                    iconColor = Color(0xFFF3BA2F), // Binance yellow color
                    onClick = onSccToBinanceClick
                )
            }

            // SCC to Bkash Option
            item {
                TransferMethodCard(
                    title = "SCC to Bkash",
                    subtitle = "Transfer money to your Bkash account",
                    iconText = "BK",
                    iconColor = Color(0xFFE2136E), // Bkash pink color
                    onClick = onSccToBkashClick
                )
            }

            // SCC to Nagad Option
            item {
                TransferMethodCard(
                    title = "SCC to Nagad",
                    subtitle = "Transfer money to your Nagad account",
                    iconText = "NG",
                    iconColor = Color(0xFFE31E24), // Nagad red color
                    onClick = onSccToNagadClick
                )
            }

            // SCC to Rocket Option
            item {
                TransferMethodCard(
                    title = "SCC to Rocket",
                    subtitle = "Transfer money to your Rocket account",
                    iconText = "RK",
                    iconColor = Color(0xFF1E88E5), // Rocket blue color
                    onClick = onSccToRocketClick
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
private fun TransferMethodCard(
    title: String,
    subtitle: String,
    iconText: String,
    iconColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = iconText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Arrow
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Continue",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransferMoneyScreenPreview() {
    SmartCitizenClubTheme {
        TransferMoneyScreen(
            onBackClick = {},
            onSccToBinanceClick = {},
            onSccToBkashClick = {},
            onSccToNagadClick = {},
            onSccToRocketClick = {}
        )
    }
}
