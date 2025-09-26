package com.example.smartcitizenclub.ui.user.screens.cashout

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.journeyapps.barcodescanner.ScanOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashOutScreen(
    onBackClick: () -> Unit,
    onProviderClick: (CashOutProvider) -> Unit = {}
) {
    var accountInput by remember { mutableStateOf("") }
    val context = LocalContext.current
    
    // QR Code scanning launcher
    val qrScannerLauncher = rememberLauncherForActivityResult(
        contract = com.journeyapps.barcodescanner.ScanContract(),
        onResult = { result ->
            if (result.contents != null) {
                // Handle the scanned QR code content
                accountInput = result.contents
            }
        }
    )
    
    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                // Permission granted, start scanning
                val options = ScanOptions()
                options.setPrompt("Scan a QR code")
                options.setBeepEnabled(true)
                options.setBarcodeImageEnabled(true)
                qrScannerLauncher.launch(options)
            } else {
                // Permission denied, show a message or handle accordingly
                // TODO: Show permission denied message
            }
        }
    )
    
    // Sample cash out providers
    val recentProviders = remember {
        listOf(
            CashOutProvider("1", "bKash"),
            CashOutProvider("2", "Nagad")
        )
    }
    
    val favoriteProviders = remember {
        listOf(
            CashOutProvider("3", "Rocket"),
            CashOutProvider("4", "Upay")
        )
    }
    
    val allProviders = remember {
        listOf(
            CashOutProvider("5", "bKash"),
            CashOutProvider("6", "Nagad"),
            CashOutProvider("7", "Rocket"),
            CashOutProvider("8", "Upay"),
            CashOutProvider("9", "MCash"),
            CashOutProvider("10", "T-Cash")
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
                    text = "Cash Out",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
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
            
            // Account Input Section
            item {
                Column {
                    Text(
                        text = "Account Number",
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
                            Icons.Default.AccountBalance,
                            contentDescription = "Account",
                            tint = Color(0xFFE53E3E),
                            modifier = Modifier.size(20.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        OutlinedTextField(
                            value = accountInput,
                            onValueChange = { accountInput = it },
                            placeholder = {
                                Text(
                                    text = "Enter Account Number or Mobile Number",
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
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
                                Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Send",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
            
            // QR Code Scanner Button
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { 
                            // Check camera permission before scanning
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                val options = ScanOptions()
                                options.setPrompt("Scan a QR code")
                                options.setBeepEnabled(true)
                                options.setBarcodeImageEnabled(true)
                                qrScannerLauncher.launch(options)
                            } else {
                                // Request camera permission
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.QrCodeScanner,
                            contentDescription = "Scan QR Code",
                            tint = Color(0xFFE53E3E),
                            modifier = Modifier.size(24.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = "Tap to Scan QR Code",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }
            }
            
            // Recent Providers Section
            item {
                Column {
                    Text(
                        text = "Recent",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    recentProviders.forEach { provider ->
                        ProviderItem(
                            provider = provider,
                            onClick = { onProviderClick(provider) }
                        )
                        
                        if (provider != recentProviders.last()) {
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
                    
                    favoriteProviders.forEach { provider ->
                        ProviderItem(
                            provider = provider,
                            onClick = { onProviderClick(provider) }
                        )
                        
                        if (provider != favoriteProviders.last()) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            
            // All Providers Section
            item {
                Column {
                    Text(
                        text = "All Providers",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    allProviders.forEach { provider ->
                        ProviderItem(
                            provider = provider,
                            onClick = { onProviderClick(provider) }
                        )
                        
                        if (provider != allProviders.last()) {
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
private fun ProviderItem(
    provider: CashOutProvider,
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
                Icons.Default.AccountBalance,
                contentDescription = "Provider",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Provider Info
        Column {
            Text(
                text = provider.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CashOutScreenPreview() {
    SmartCitizenClubTheme {
        CashOutScreen(
            onBackClick = {},
            onProviderClick = {}
        )
    }
}
