package com.example.smartcitizenclub.ui.user.screens

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

// Home services data class
data class HomeService(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val color: Color = Color(0xFFFF4500) // Orange color
)

// Payment data class
data class PaymentService(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val color: Color = Color(0xFFFF4500) // Orange color
)

// Other services data class
data class OtherService(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val color: Color = Color(0xFFFF4500) // Orange color
)

// Promotional banner data class
data class PromoBanner(
    val id: String,
    val title: String,
    val subtitle: String,
    val amount: String,
    val buttonText: String,
    val backgroundColor: Color = Color(0xFFE8F5E8) // Light green
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    user: User,
    onSendMoneyClick: () -> Unit = {},
    onCashOutClick: () -> Unit = {},
    onMobileRechargeClick: () -> Unit = {},
    onPackagePurchaseClick: () -> Unit = {},
    onLoanClick: () -> Unit = {},
    onContactUsClick: () -> Unit = {},
    onLimitChargesClick: () -> Unit = {},
    onDonationClick: () -> Unit = {}
) {
    // Home services data
    val homeServices = remember {
        listOf(
            HomeService("1", "Send Money", Icons.Default.Send, Color(0xFFFF4500)),
            HomeService("2", "Cash Out", Icons.Default.AttachMoney, Color(0xFFFF4500)),
            HomeService("3", "Mobile Recharge", Icons.Default.Phone, Color(0xFFFF4500)),
            HomeService("4", "Package Purchase", Icons.Default.ShoppingBag, Color(0xFFFF4500)),
            HomeService("5", "Loan", Icons.Default.AccountBalance, Color(0xFFFF4500))
        )
    }
    
    // Payment services data
    val paymentServices = remember {
        listOf(
            PaymentService("1", "Loan Pay", Icons.Default.Payment, Color(0xFFFF4500))
        )
    }
    
    // Other services data
    val otherServices = remember {
        listOf(
            OtherService("1", "Contact Us", Icons.Default.ContactSupport, Color(0xFFFF4500)),
            OtherService("2", "Limit and Charges", Icons.Default.AccountBalance, Color(0xFFFF4500)),
            OtherService("3", "Donation", Icons.Default.Favorite, Color(0xFFFF4500))
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
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header Section with Orange Background
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFF4500)) // Orange background
                    .padding(20.dp)
            ) {
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // QR Code display
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* TODO: Show QR code options */ },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 0.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // User Name
                            Text(
                                text = user.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            
                            // Real QR Code
                            RealQRCode(
                                data = "SmartCitizenClub:${user.id}:${user.phone}",
                                size = 200.dp,
                                modifier = Modifier
                                    .size(200.dp)
                                    .background(Color.White)
                            )
                        }
                    }
                }
            }
        }
        
        // Home Services Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Home Services",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Home Services Grid (2 rows)
                Column {
                    // First row (3 items)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        homeServices.take(3).forEach { service ->
                            ServiceItem(
                                service = service,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    when (service.id) {
                                        "1" -> onSendMoneyClick()
                                        "2" -> onCashOutClick()
                                        "3" -> onMobileRechargeClick()
                                    }
                                }
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Second row (2 items)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        homeServices.drop(3).forEach { service ->
                            ServiceItem(
                                service = service,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    when (service.id) {
                                        "4" -> onPackagePurchaseClick()
                                        "5" -> onLoanClick()
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
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Payment Services Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
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
                                    if (index == 0) Color.Gray else Color.Gray.copy(alpha = 0.3f)
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
                    color = Color.Black
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
                                    "Contact Us" -> onContactUsClick()
                                    "Limit and Charges" -> onLimitChargesClick()
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
            colors = CardDefaults.cardColors(containerColor = service.color),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    service.icon,
                    contentDescription = service.title,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = service.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
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
            colors = CardDefaults.cardColors(containerColor = payment.color),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    payment.icon,
                    contentDescription = payment.title,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = payment.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
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
            colors = CardDefaults.cardColors(containerColor = service.color),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    service.icon,
                    contentDescription = service.title,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = service.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
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
        colors = CardDefaults.cardColors(containerColor = banner.backgroundColor),
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
                    color = Color.Black
                )
                
                Text(
                    text = banner.subtitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                
                Text(
                    text = banner.amount,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    onClick = { /* TODO: Handle button click */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = banner.buttonText,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Banner image placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Image,
                    contentDescription = "Banner Image",
                    tint = Color.Gray,
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
                type = UserType.USER
            )
        )
    }
}