package com.example.smartcitizenclub.presentation.feature.recharge.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MobileRechargeScreen(
    onBackClick: () -> Unit,
    onOperatorClick: (MobileOperator) -> Unit = {}
) {
    var phoneNumber by remember { mutableStateOf("") }
    
    // Sample mobile operators
    val recentOperators = remember {
        listOf(
            MobileOperator("1", "Grameenphone"),
            MobileOperator("2", "Robi")
        )
    }
    
    val favoriteOperators = remember {
        listOf(
            MobileOperator("3", "Banglalink"),
            MobileOperator("4", "Airtel")
        )
    }
    
    val allOperators = remember {
        listOf(
            MobileOperator("5", "Grameenphone"),
            MobileOperator("6", "Robi"),
            MobileOperator("7", "Banglalink"),
            MobileOperator("8", "Airtel"),
            MobileOperator("9", "Teletalk")
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
                containerColor = Color(0xFFE53E3E) // Red color
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
            
            // Phone Number Input Section
            item {
                Column {
                    Text(
                        text = "Phone Number",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Phone,
                            contentDescription = "Phone",
                            tint = Color(0xFFE53E3E),
                            modifier = Modifier.size(20.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { newValue ->
                                // Allow only digits and limit to 11 characters
                                if (newValue.all { it.isDigit() } && newValue.length <= 11) {
                                    phoneNumber = newValue
                                }
                            },
                            placeholder = {
                                Text(
                                    text = "Enter 11-digit Mobile Number",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFE53E3E),
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        // Send Button
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                                .clickable { /* Handle send action */ },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.ArrowForward,
                                contentDescription = "Send",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
            
            // Recent Operators Section
            item {
                Column {
                    Text(
                        text = "Recent",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    recentOperators.forEach { operator ->
                        OperatorItem(
                            operator = operator,
                            onClick = { onOperatorClick(operator) }
                        )
                        
                        if (operator != recentOperators.last()) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            
            // Favourites Section
            item {
                Column {
                    Text(
                        text = "Favourites",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    favoriteOperators.forEach { operator ->
                        OperatorItem(
                            operator = operator,
                            onClick = { onOperatorClick(operator) }
                        )
                        
                        if (operator != favoriteOperators.last()) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            
            // All Operators Section
            item {
                Column {
                    Text(
                        text = "All Operators",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    allOperators.forEach { operator ->
                        OperatorItem(
                            operator = operator,
                            onClick = { onOperatorClick(operator) }
                        )
                        
                        if (operator != allOperators.last()) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
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

@Composable
private fun OperatorItem(
    operator: MobileOperator,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Phone,
                contentDescription = "Operator",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Operator Info
        Column {
            Text(
                text = operator.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MobileRechargeScreenPreview() {
    SmartCitizenClubTheme {
        MobileRechargeScreen(
            onBackClick = {},
            onOperatorClick = {}
        )
    }
}
