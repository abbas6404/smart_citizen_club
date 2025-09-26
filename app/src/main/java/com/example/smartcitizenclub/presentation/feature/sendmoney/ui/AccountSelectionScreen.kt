package com.example.smartcitizenclub.presentation.feature.sendmoney.ui

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
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

// Account info data class
data class AccountInfo(
    val accountNumber: String,
    val accountName: String
)

// Account Selection Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSelectionScreen(
    mobileNumber: String,
    onAccountSelected: (String, String) -> Unit,
    onBackClick: () -> Unit
) {
    // Sample accounts for the mobile number
    val accounts = remember(mobileNumber) {
        listOf(
            AccountInfo("123456789", "Personal Account"),
            AccountInfo("987654321", "Business Account"),
            AccountInfo("456789123", "Savings Account"),
            AccountInfo("789123456", "Investment Account")
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Primary Orange Background
        TopAppBar(
            title = {
                Text(
                    text = "Select Account",
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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Mobile Number Info
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = PrimaryOrangeGradient.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Phone,
                            contentDescription = "Phone",
                            tint = PrimaryOrangeGradient,
                            modifier = Modifier.size(24.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Column {
                            Text(
                                text = "Mobile Number",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = mobileNumber,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = PrimaryOrangeGradient
                            )
                        }
                    }
                }
            }
            
            // Accounts List
            items(accounts) { account ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { 
                            onAccountSelected(account.accountNumber, account.accountName)
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.AccountBalance,
                            contentDescription = "Account",
                            tint = PrimaryOrangeGradient,
                            modifier = Modifier.size(24.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = account.accountName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            Text(
                                text = account.accountNumber,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                        
                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = "Select",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            
            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountSelectionScreenPreview() {
    SmartCitizenClubTheme {
        AccountSelectionScreen(
            mobileNumber = "01742184298",
            onAccountSelected = { _, _ -> },
            onBackClick = { }
        )
    }
}
