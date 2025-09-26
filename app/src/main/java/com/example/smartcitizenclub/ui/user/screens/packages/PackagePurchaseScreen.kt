package com.example.smartcitizenclub.ui.user.screens.packages

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackagePurchaseScreen(
    onBackClick: () -> Unit,
    onPackageSelected: (Package) -> Unit = {}
) {
    // Sample packages
    val packages = remember {
        listOf(
            Package(
                id = "1",
                name = "Internet 1GB",
                description = "1GB high-speed data for 7 days",
                price = 99.0,
                validity = "7 days",
                dataLimit = "1GB",
                smsLimit = "0 SMS",
                voiceLimit = "0 mins",
                operator = "Grameenphone",
                category = PackageCategory.INTERNET
            ),
            Package(
                id = "2",
                name = "Combo Pack 500MB",
                description = "500MB data + 100 SMS + 100 minutes voice for 30 days",
                price = 199.0,
                validity = "30 days",
                dataLimit = "500MB",
                smsLimit = "100 SMS",
                voiceLimit = "100 mins",
                operator = "Robi",
                category = PackageCategory.COMBO
            ),
            Package(
                id = "3",
                name = "Voice 200 Minutes",
                description = "200 minutes voice calls for 30 days",
                price = 149.0,
                validity = "30 days",
                dataLimit = "0GB",
                smsLimit = "0 SMS",
                voiceLimit = "200 mins",
                operator = "Banglalink",
                category = PackageCategory.VOICE
            ),
            Package(
                id = "4",
                name = "SMS 500 Pack",
                description = "500 SMS for 30 days",
                price = 79.0,
                validity = "30 days",
                dataLimit = "0GB",
                smsLimit = "500 SMS",
                voiceLimit = "0 mins",
                operator = "Airtel",
                category = PackageCategory.SMS
            ),
            Package(
                id = "5",
                name = "Premium Unlimited",
                description = "Unlimited data + 1000 SMS + 500 minutes voice for 30 days",
                price = 599.0,
                validity = "30 days",
                dataLimit = "Unlimited",
                smsLimit = "1000 SMS",
                voiceLimit = "500 mins",
                operator = "Grameenphone",
                category = PackageCategory.PREMIUM
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
                    text = "Package Purchase",
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
                            Icons.Default.ShoppingBag,
                            contentDescription = "Package",
                            tint = Color(0xFFE53E3E),
                            modifier = Modifier.size(48.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text(
                            text = "Choose a Package",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Select from a variety of packages to suit your needs",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
            
            // Packages List
            items(packages) { pkg ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPackageSelected(pkg) },
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
                                    text = pkg.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = pkg.operator,
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                            
                            Text(
                                text = "৳${String.format("%.0f", pkg.price)}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE53E3E)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = pkg.description,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Package details
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.DataUsage,
                                    contentDescription = "Data",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = pkg.dataLimit,
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Data",
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }
                            
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.Message,
                                    contentDescription = "SMS",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = pkg.smsLimit,
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "SMS",
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }
                            
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.Phone,
                                    contentDescription = "Voice",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = pkg.voiceLimit,
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Voice",
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }
                            
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.CalendarToday,
                                    contentDescription = "Validity",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = pkg.validity,
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Validity",
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
fun PackagePurchaseScreenPreview() {
    SmartCitizenClubTheme {
        PackagePurchaseScreen(
            onBackClick = {},
            onPackageSelected = {}
        )
    }
}
