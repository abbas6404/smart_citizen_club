package com.example.smartcitizenclub.presentation.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import android.graphics.Bitmap
import android.graphics.Color as AndroidColor
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import android.widget.ImageView
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.data.SubAccount
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.example.smartcitizenclub.presentation.theme.OrangeGradient

// Home services data class
data class HomeService(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val color: Color = Color.Unspecified // Will use theme colors
)

// Payment data class
data class PaymentService(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val color: Color = Color.Unspecified // Will use theme colors
)

// Other services data class
data class OtherService(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val color: Color = Color.Unspecified // Will use theme colors
)

// Promotional banner data class
data class PromoBanner(
    val id: String,
    val title: String,
    val subtitle: String,
    val amount: String,
    val buttonText: String,
    val backgroundColor: Color = Color.Unspecified // Will use theme colors
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    user: User,
    onSendMoneyClick: () -> Unit = {},
    onCashOutClick: () -> Unit = {},
    onMobileRechargeClick: () -> Unit = {},
    onAddMoneyClick: () -> Unit = {},
    onTransferMoneyClick: () -> Unit = {},
    onLimitUpgradeClick: () -> Unit = {},
    onInvestmentClick: () -> Unit = {},
    onLoanClick: () -> Unit = {},
    onContactUsClick: () -> Unit = {},
    onLimitChargesClick: () -> Unit = {},
    onDonationClick: () -> Unit = {}
) {
    // Home services data
    val homeServices = remember {
        listOf(
            HomeService("1", "Send Money", Icons.Default.Send),
            HomeService("2", "Cash Out", Icons.Default.AttachMoney),
            HomeService("3", "Mobile Recharge", Icons.Default.Phone),
            HomeService("4", "Add Money", Icons.Default.Add),
            HomeService("5", "Transfer Money", Icons.Default.ArrowForward),
            HomeService("6", "Limit Upgrade", Icons.Default.Upgrade),
            HomeService("7", "Investment", Icons.Default.TrendingUp),
            HomeService("8", "Loan", Icons.Default.AccountBalance)
        )
    }
    
    // Payment services data
    val paymentServices = remember {
        listOf(
            PaymentService("1", "Loan Pay", Icons.Default.Payment),
            PaymentService("2", "Bill Pay", Icons.Default.Receipt)
        )
    }
    
    // Other services data
    val otherServices = remember {
        listOf(
            OtherService("1", "Ticket Support", Icons.Default.Support),
            OtherService("2", "Limit and Charge", Icons.Default.AccountBalance),
            OtherService("3", "Donation", Icons.Default.Favorite)
        )
    }
    
    // Sample promotional banners
    val promoBanners = remember {
        listOf(
            PromoBanner("1", "Airtel Recharge", "Cashback", "৳180", "Tap Here"),
            PromoBanner("2", "Special Offer", "Discount", "৳50", "Get Now"),
            PromoBanner("3", "New User", "Bonus", "৳100", "Claim")
        )
    }
    
    // State for current banner index
    var currentBannerIndex by remember { mutableStateOf(0) }
    
    // Auto-cycle through banners
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000) // Change banner every 3 seconds
            currentBannerIndex = (currentBannerIndex + 1) % promoBanners.size
        }
    }
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header Section with Theme Background
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = OrangeGradient
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // User Name - Highlighted and centered
                    Text(
                        text = user.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        letterSpacing = 1.sp
                    )
                    
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    // QR Code display with rounded corners - Centered
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        RealQRCode(
                            data = "SmartCitizenClub:${user.id}:${user.phone}:${user.currentSubAccount?.accountNumber ?: "MAIN"}",
                            size = 200.dp,
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(25.dp))
                                .clickable { /* TODO: Show QR code options */ }
                        )
                    }
                }
            }
        }
        
        //Services Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Services",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Home Services Grid (2 rows, 4 items each)
                Column {
                    // First row (4 items)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        homeServices.take(4).forEach { service ->
                            ServiceItem(
                                service = service,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    when (service.id) {
                                        "1" -> onSendMoneyClick()
                                        "2" -> onCashOutClick()
                                        "3" -> onMobileRechargeClick()
                                        "4" -> onAddMoneyClick()
                                    }
                                }
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Second row (4 items)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        homeServices.drop(4).forEach { service ->
                            ServiceItem(
                                service = service,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    when (service.id) {
                                        "5" -> onTransferMoneyClick()
                                        "6" -> onLimitUpgradeClick()
                                        "7" -> onInvestmentClick()
                                        "8" -> onLoanClick()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
        
        // Payments Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "Payment",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Payment Services Grid (2 items)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    paymentServices.forEach { payment ->
                        PaymentItem(
                            payment = payment,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
        
        // Promotional Banners
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(promoBanners) { banner ->
                        PromoBannerItem(banner = banner)
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Page indicators
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(promoBanners.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (index == currentBannerIndex) 
                                        MaterialTheme.colorScheme.primary 
                                    else 
                                        MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                )
                        )
                        if (index < promoBanners.size - 1) {
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }
        }
        
        // Other Services Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "Other",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Other Services Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                        otherServices.forEach { service ->
                            OtherServiceItem(
                                service = service,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    // Handle service click
                                    when (service.title) {
                                        "Ticket Support" -> onContactUsClick()
                                        "Limit and Charge" -> onLimitChargesClick()
                                        "Donation" -> onDonationClick()
                                        // Add other service handlers as needed
                                    }
                                }
                            )
                        }
                }
            }
        }
        
        // Bottom spacing for navigation bar
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun RealQRCode(
    data: String,
    size: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    
    LaunchedEffect(data) {
        try {
            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(
                data,
                BarcodeFormat.QR_CODE,
                512,
                512,
                mapOf(EncodeHintType.CHARACTER_SET to "UTF-8")
            )
            
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(
                        x, y,
                        if (bitMatrix[x, y]) AndroidColor.BLACK else AndroidColor.WHITE
                    )
                }
            }
            
            bitmap = bmp
        } catch (e: Exception) {
            // Handle error - create a simple placeholder
            val placeholder = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565)
            placeholder.eraseColor(AndroidColor.GRAY)
            bitmap = placeholder
        }
    }
    
    AndroidView(
        factory = { ctx ->
            ImageView(ctx).apply {
                scaleType = android.widget.ImageView.ScaleType.CENTER_CROP
            }
        },
        update = { imageView ->
            bitmap?.let { bmp ->
                imageView.setImageBitmap(bmp)
            }
        },
        modifier = modifier
    )
}

@Composable
private fun ServiceItem(
    service: HomeService,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.size(70.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (service.color != Color.Unspecified) 
                    service.color 
                else 
                    MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    service.icon,
                    contentDescription = service.title,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = service.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PaymentItem(
    payment: PaymentService,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .clickable { /* TODO: Handle payment click */ },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.size(70.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (payment.color != Color.Unspecified) 
                    payment.color 
                else 
                    MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    payment.icon,
                    contentDescription = payment.title,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = payment.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun OtherServiceItem(
    service: OtherService,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.size(70.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (service.color != Color.Unspecified) 
                    service.color 
                else 
                    MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    service.icon,
                    contentDescription = service.title,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = service.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PromoBannerItem(
    banner: PromoBanner,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(280.dp)
            .height(120.dp)
            .clickable { /* TODO: Handle banner click */ },
        colors = CardDefaults.cardColors(
            containerColor = if (banner.backgroundColor != Color.Unspecified) 
                banner.backgroundColor 
            else 
                MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = banner.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = banner.subtitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
                
                Text(
                    text = banner.amount,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    onClick = { /* TODO: Handle button click */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = banner.buttonText,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onError
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Banner image placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Image,
                    contentDescription = "Banner Image",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SmartCitizenClubTheme {
        HomeScreen(
            user = User(
                id = "1",
                name = "Abbas Uddin",
                email = "abbas@example.com",
                phone = "+8801234567890",
                type = UserType.USER,
                subAccounts = listOf(
                    SubAccount("SUB001", "Personal Account", 1500.0),
                    SubAccount("SUB002", "Business Account", 2500.0),
                    SubAccount("SUB003", "Savings Account", 5000.0)
                ),
                currentSubAccount = SubAccount("SUB001", "Personal Account", 1500.0)
            )
        )
    }
}