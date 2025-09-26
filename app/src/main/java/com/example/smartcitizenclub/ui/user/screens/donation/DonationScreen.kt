package com.example.smartcitizenclub.ui.user.screens.donation

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
import androidx.compose.ui.draw.clip
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
fun DonationScreen(
    user: User,
    onBackClick: () -> Unit,
    onDonationSuccess: (UserDonation) -> Unit = {}
) {
    var showCampaignList by remember { mutableStateOf(true) }
    var showDonationForm by remember { mutableStateOf(false) }
    var showDonationConfirm by remember { mutableStateOf(false) }
    
    var selectedCampaign by remember { mutableStateOf<DonationCampaign?>(null) }
    var donationAmount by remember { mutableStateOf("") }
    var selectedPaymentMethod by remember { mutableStateOf(PaymentMethod.WALLET) }
    var isAnonymous by remember { mutableStateOf(false) }
    var pin by remember { mutableStateOf("") }
    var showPin by remember { mutableStateOf(false) }
    
    // Sample donation campaigns
    val donationCampaigns = remember {
        listOf(
            DonationCampaign(
                id = "1",
                title = "Education for Underprivileged Children",
                description = "Help us provide education to children who cannot afford it. Your donation will help buy books, uniforms, and pay teacher salaries.",
                organization = "Smart Citizen Education Foundation",
                targetAmount = 500000.0,
                raisedAmount = 325000.0,
                endDate = System.currentTimeMillis() + 2592000000, // 30 days from now
                category = DonationCategory.EDUCATION,
                isActive = true
            ),
            DonationCampaign(
                id = "2",
                title = "Clean Water for Rural Communities",
                description = "Provide clean drinking water to rural communities by building wells and water purification systems.",
                organization = "Water for All Foundation",
                targetAmount = 300000.0,
                raisedAmount = 150000.0,
                endDate = System.currentTimeMillis() + 1296000000, // 15 days from now
                category = DonationCategory.ENVIRONMENT,
                isActive = true
            ),
            DonationCampaign(
                id = "3",
                title = "Medical Aid for Flood Victims",
                description = "Provide immediate medical assistance to flood victims in affected areas.",
                organization = "Disaster Relief Organization",
                targetAmount = 200000.0,
                raisedAmount = 180000.0,
                endDate = System.currentTimeMillis() + 604800000, // 7 days from now
                category = DonationCategory.DISASTER_RELIEF,
                isActive = true
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
                    text = if (showCampaignList) "Donate" else if (showDonationForm) "Make Donation" else "Confirm Donation",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    when {
                        showDonationConfirm -> {
                            showDonationConfirm = false
                            showDonationForm = true
                        }
                        showDonationForm -> {
                            showDonationForm = false
                            showCampaignList = true
                        }
                        else -> onBackClick()
                    }
                }) {
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
        if (showCampaignList) {
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
                                Icons.Default.Favorite,
                                contentDescription = "Donation",
                                tint = Color(0xFFE53E3E),
                                modifier = Modifier.size(48.dp)
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Text(
                                text = "Make a Difference",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Your donation can help change lives. Choose a cause below to contribute.",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
                
                // Campaigns List
                items(donationCampaigns) { campaign ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedCampaign = campaign
                                showCampaignList = false
                                showDonationForm = true
                            },
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
                                Text(
                                    text = campaign.title,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    modifier = Modifier.weight(1f)
                                )
                                
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = "Favorite",
                                    tint = Color.Red,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = campaign.organization,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = campaign.description,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            // Progress bar for funds raised
                            LinearProgressIndicator(
                                progress = (campaign.raisedAmount / campaign.targetAmount).toFloat(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp),
                                color = Color(0xFF4CAF50),
                                trackColor = Color(0xFFE0E0E0)
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Raised: ${String.format("%.0f", campaign.raisedAmount)} ৳",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                
                                Text(
                                    text = "Target: ${String.format("%.0f", campaign.targetAmount)} ৳",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Ends on: ${java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(java.util.Date(campaign.endDate))}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        } else if (showDonationForm) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                // Campaign Info
                selectedCampaign?.let { campaign ->
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = campaign.title,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Text(
                                    text = campaign.organization,
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                                
                                Spacer(modifier = Modifier.height(12.dp))
                                
                                Text(
                                    text = campaign.description,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
                
                // Donation Amount
                item {
                    Column {
                        Text(
                            text = "Donation Amount",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Amount Display
                        Text(
                            text = if (donationAmount.isEmpty()) "৳0.00" else "৳${donationAmount}.00",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE53E3E),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Amount Input Field
                        OutlinedTextField(
                            value = donationAmount,
                            onValueChange = { newAmount ->
                                // Only allow numeric input
                                if (newAmount.all { it.isDigit() } && newAmount.length <= 6) {
                                    donationAmount = newAmount
                                }
                            },
                            placeholder = {
                                Text(
                                    text = "Enter donation amount",
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFE53E3E),
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                            ),
                            leadingIcon = {
                                Text(
                                    text = "৳",
                                    fontSize = 18.sp,
                                    color = Color.Gray
                                )
                            }
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Quick Amount Buttons
                        Text(
                            text = "Quick Amount",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        val quickAmounts = listOf("100", "500", "1000", "2000", "5000")
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            quickAmounts.forEach { quickAmount ->
                                Button(
                                    onClick = { donationAmount = quickAmount },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Gray.copy(alpha = 0.1f)
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "৳$quickAmount",
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
                
                // Payment Method
                item {
                    Column {
                        Text(
                            text = "Payment Method",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Payment method options
                        val paymentMethods = PaymentMethod.values()
                        paymentMethods.forEach { method ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedPaymentMethod = method }
                                    .padding(vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedPaymentMethod == method,
                                    onClick = { selectedPaymentMethod = method },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color(0xFFE53E3E)
                                    )
                                )
                                
                                Spacer(modifier = Modifier.width(8.dp))
                                
                                Text(
                                    text = when (method) {
                                        PaymentMethod.WALLET -> "SCC Wallet"
                                        PaymentMethod.CARD -> "Credit/Debit Card"
                                        PaymentMethod.BANK_TRANSFER -> "Bank Transfer"
                                        PaymentMethod.MOBILE_RECHARGE -> "Mobile Recharge"
                                    },
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }
                            
                            if (method != paymentMethods.last()) {
                                Divider(color = Color.Gray.copy(alpha = 0.3f))
                            }
                        }
                    }
                }
                
                // Anonymous Donation
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isAnonymous = !isAnonymous }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isAnonymous,
                            onCheckedChange = { isAnonymous = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFFE53E3E)
                            )
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = "Donate anonymously",
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }
                
                // Donate Button
                item {
                    Button(
                        onClick = {
                            if (donationAmount.isNotEmpty() && donationAmount.toDoubleOrNull() != null && donationAmount.toDoubleOrNull()!! > 0) {
                                showDonationForm = false
                                showDonationConfirm = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE53E3E)
                        ),
                        shape = RoundedCornerShape(25.dp),
                        enabled = donationAmount.isNotEmpty() && donationAmount.toDoubleOrNull() != null && donationAmount.toDoubleOrNull()!! > 0
                    ) {
                        Text(
                            text = "Continue",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        } else if (showDonationConfirm) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                // Confirmation Header
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
                                Icons.Default.CheckCircle,
                                contentDescription = "Confirm",
                                tint = Color(0xFF4CAF50),
                                modifier = Modifier.size(48.dp)
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Text(
                                text = "Confirm Donation",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Please review your donation details before confirming",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
                
                // Donation Details
                selectedCampaign?.let { campaign ->
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Campaign Info
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color.Gray.copy(alpha = 0.3f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Default.Favorite,
                                            contentDescription = "Campaign",
                                            tint = Color.Red,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    
                                    Spacer(modifier = Modifier.width(16.dp))
                                    
                                    Column {
                                        Text(
                                            text = campaign.title,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = campaign.organization,
                                            fontSize = 14.sp,
                                            color = Color.Gray
                                        )
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(24.dp))
                                
                                // Amount
                                Text(
                                    text = "Donation Amount",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Text(
                                    text = "৳${String.format("%.2f", donationAmount.toDoubleOrNull() ?: 0.0)}",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE53E3E)
                                )
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                // Payment Method
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Payment Method",
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = when (selectedPaymentMethod) {
                                            PaymentMethod.WALLET -> "SCC Wallet"
                                            PaymentMethod.CARD -> "Credit/Debit Card"
                                            PaymentMethod.BANK_TRANSFER -> "Bank Transfer"
                                            PaymentMethod.MOBILE_RECHARGE -> "Mobile Recharge"
                                        },
                                        fontSize = 14.sp,
                                        color = Color.Black
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
                                        text = "৳${String.format("%.2f", donationAmount.toDoubleOrNull() ?: 0.0)}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
                
                // PIN Input Section
                item {
                    Column {
                        Text(
                            text = "Enter PIN to Confirm",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        OutlinedTextField(
                            value = pin,
                            onValueChange = { pin = it },
                            placeholder = {
                                Text(
                                    text = "Enter 4-digit PIN",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            visualTransformation = if (showPin) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFE53E3E),
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                            ),
                            trailingIcon = {
                                IconButton(onClick = { showPin = !showPin }) {
                                    Icon(
                                        if (showPin) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = if (showPin) "Hide PIN" else "Show PIN",
                                        tint = Color.Gray
                                    )
                                }
                            }
                        )
                    }
                }
                
                // Confirm Button
                item {
                    Button(
                        onClick = {
                            // Create mock donation
                            val mockDonation = UserDonation(
                                id = "donation_${System.currentTimeMillis()}",
                                userId = user.id,
                                campaignId = selectedCampaign?.id ?: "",
                                amount = donationAmount.toDoubleOrNull() ?: 0.0,
                                timestamp = System.currentTimeMillis(),
                                paymentMethod = selectedPaymentMethod,
                                status = DonationStatus.COMPLETED,
                                anonymous = isAnonymous
                            )
                            onDonationSuccess(mockDonation)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE53E3E)
                        ),
                        shape = RoundedCornerShape(25.dp),
                        enabled = pin.length == 4
                    ) {
                        Text(
                            text = "Confirm Donation",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DonationScreenPreview() {
    SmartCitizenClubTheme {
        DonationScreen(
            user = User(
                id = "1",
                name = "Abbas Uddin",
                email = "abbas@example.com",
                phone = "01741736354",
                type = UserType.USER
            ),
            onBackClick = {},
            onDonationSuccess = {}
        )
    }
}
