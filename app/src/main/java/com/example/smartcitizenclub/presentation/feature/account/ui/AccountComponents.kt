package com.example.smartcitizenclub.presentation.feature.account.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient

// Data class for sub-accounts
data class SubAccount(
    val id: String,
    val name: String,
    val number: String,
    val balance: Double,
    val groupId: String? = null,
    val referralCode: String? = null
)

// Color constants for account components
object AccountColors {
    // Group colors
    val Red = Color(0xFFE53E3E)
    val Gold = Color(0xFFF59E0B)
    val Green = Color(0xFF10B981)
    val Blue = Color(0xFF3B82F6)
    val Purple = Color(0xFF8B5CF6)
    val Orange = Color(0xFFF97316)
    
    // UI colors
    val Gray = Color(0xFF6B7280)
    val LightGray = Color(0xFFF3F4F6)
    val DarkGray = Color(0xFF374151)
    val TextPrimary = Color(0xFF1F2937)
    val TextSecondary = Color(0xFF6B7280)
    val TextTertiary = Color(0xFF9CA3AF)
    val Divider = Color(0xFFE5E7EB)
    
    // Additional colors for messages
    val SecondaryGreen = Green
    val PrimaryOrange = PrimaryOrangeGradient
    val Background = Color(0xFFF8F9FA)
}

// Sub Account Card Composable
@Composable
fun SubAccountCard(
    account: SubAccount, 
    onClick: () -> Unit, 
    onLongPress: (SubAccount) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .pointerInput(account) {
                detectTapGestures(
                    onLongPress = { onLongPress(account) }
                )
            }, 
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ), 
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        AccountColors.Blue.copy(alpha = 0.1f), 
                        CircleShape
                    ), 
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.AccountBalance,
                    contentDescription = "Account",
                    tint = AccountColors.Blue,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = account.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )
                Text(
                    text = account.number, 
                    fontSize = 14.sp, 
                    color = Color(0xFF6B7280)
                )
                if (account.referralCode != null) {
                    Text(
                        text = "Ref: ${account.referralCode}",
                        fontSize = 12.sp,
                        color = AccountColors.Blue,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Text(
                text = "৳${String.format("%.0f", account.balance)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF059669)
            )
        }
    }
}

// Active Account Card (Mastercard Style)
@Composable
fun ActiveAccountCard(
    account: SubAccount,
    onLongPress: (SubAccount) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(account.id) {
                detectTapGestures(
                    onLongPress = {
                        onLongPress(account)
                    }
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFEB001B), // Mastercard Red
                            Color(0xFFF79E1B)  // Mastercard Orange
                        ), 
                        start = Offset(0f, 0f), 
                        end = Offset(1000f, 1000f)
                    ), 
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Top section with chip
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Chip
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                Color.White.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                                .background(
                                    Color.White.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(2.dp)
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Account number (like card number)
                Text(
                    text = account.number.replace(" ", "").chunked(4).joinToString(" "),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Account name
                Text(
                    text = account.name.uppercase(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Bottom section with balance
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "BALANCE",
                            fontSize = 10.sp,
                            color = Color.White.copy(alpha = 0.7f),
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "৳${String.format("%.2f", account.balance)}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    // Right side with reference code and active indicator
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        // Reference code
                        if (!account.referralCode.isNullOrBlank()) {
                            Text(
                                text = "REF: ${account.referralCode}",
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.8f),
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                        
                        // Active indicator
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Active",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "ACTIVE",
                                fontSize = 10.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

// Add Account Dialog Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountDialog(
    showDialog: Boolean, 
    onDismiss: () -> Unit, 
    onAdd: (String, String, String?, String?) -> Unit
) {
    if (showDialog) {
        var accountName by remember { mutableStateOf("") }
        var accountNumber by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = onDismiss, 
            title = {
                Text(
                    text = "Add New Account", 
                    fontSize = 18.sp, 
                    fontWeight = FontWeight.Bold
                )
            }, 
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = accountName,
                        onValueChange = { accountName = it },
                        label = { Text("Account Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = accountNumber,
                        onValueChange = { accountNumber = it },
                        label = { Text("Account / Referral No") },
                        placeholder = { Text("Account/Referral No") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }, 
            confirmButton = {
                TextButton(
                    onClick = {
                        onAdd(accountName, accountNumber.ifBlank { "" }, null, null)
                        onDismiss()
                    }, 
                    enabled = accountName.isNotBlank()
                ) {
                    Text("Create Account")
                }
            }, 
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}

// Floating Action Menu Composable
@Composable
fun FloatingActionMenu(
    showMenu: Boolean,
    onAddAccount: () -> Unit,
    onAddGroup: () -> Unit,
    onCloseMenu: () -> Unit
) {
    if (showMenu) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.BottomEnd),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                modifier = Modifier.padding(end = 20.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Add Account Option
                Card(
                    modifier = Modifier.clickable {
                        onCloseMenu()
                        onAddAccount()
                    }, 
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ), 
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.PersonAdd,
                            contentDescription = "Add Account",
                            tint = AccountColors.Blue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Add Account",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = AccountColors.DarkGray
                        )
                    }
                }

                // Add Group Option
                Card(
                    modifier = Modifier.clickable {
                        onCloseMenu()
                        onAddGroup()
                    }, 
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ), 
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.GroupAdd,
                            contentDescription = "Add Group",
                            tint = AccountColors.Green,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Add Group",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = AccountColors.DarkGray
                        )
                    }
                }
            }
        }
    }
}

// Context Menu for Account Actions
@Composable
fun AccountContextMenu(
    showMenu: Boolean,
    selectedAccount: SubAccount?,
    onDismiss: () -> Unit,
    onChangeGroup: () -> Unit
) {
    if (showMenu && selectedAccount != null) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            }, 
            title = {
                Text(
                    text = selectedAccount.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }, 
            text = {
                Text(
                    text = "Choose an action for this account",
                    fontSize = 14.sp,
                    color = AccountColors.DarkGray
                )
            }, 
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                        onChangeGroup()
                    }
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Change Group",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Change Group")
                }
            }, 
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SubAccountCardPreview() {
    SmartCitizenClubTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SubAccountCard(
                account = SubAccount("1", "Personal Savings", "ACC-002", 25000.0, "1", "REF002"),
                onClick = {},
                onLongPress = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActiveAccountCardPreview() {
    SmartCitizenClubTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ActiveAccountCard(
                account = SubAccount("1", "Main Account", "ACC-001", 15000.0, null, "REF001")
            )
        }
    }
}

// Share Account Dialog - Opens system share directly
@Composable
fun ShareAccountDialog(
    showDialog: Boolean,
    account: SubAccount?,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    
    if (showDialog && account != null) {
        // Open system share dialog directly
        LaunchedEffect(account) {
            createCardImageAndShare(context, account)
            onDismiss()
        }
    }
}

// Shared function to create card image bitmap
private fun createCardImageBitmap(account: SubAccount): Bitmap {
    val width = 480
    val height = 300
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)
    
    // Draw card background gradient - exact same colors as original
    val paint = android.graphics.Paint()
    val gradient = android.graphics.LinearGradient(
        0f, 0f, width.toFloat(), height.toFloat(),
        android.graphics.Color.parseColor("#EB001B"), // PrimaryOrangeStart
        android.graphics.Color.parseColor("#FF5E00"), // PrimaryOrangeEnd
        android.graphics.Shader.TileMode.CLAMP
    )
    paint.shader = gradient
    canvas.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), 48f, 48f, paint)
    
    // Draw chip icon (matches the actual card design)
    paint.shader = null
    paint.color = android.graphics.Color.parseColor("#FFFFFF") // White with alpha
    paint.alpha = 51 // 0.2f * 255
    canvas.drawRoundRect(24f, 24f, 56f, 56f, 12f, 12f, paint)
    
    // Inner chip
    paint.alpha = 77 // 0.3f * 255
    canvas.drawRoundRect(28f, 28f, 52f, 52f, 6f, 6f, paint)
    
    // Draw text with exact same styling as original
    paint.color = android.graphics.Color.WHITE
    paint.isAntiAlias = true
    paint.typeface = android.graphics.Typeface.DEFAULT_BOLD
    
    // Account number - large and bold (formatted like credit card)
    paint.textSize = 56f
    paint.isFakeBoldText = true
    val formattedNumber = account.number.replace(" ", "").chunked(4).joinToString(" ")
    canvas.drawText(formattedNumber, 48f, 100f, paint)
    
    // Account name - medium size
    paint.textSize = 40f
    paint.isFakeBoldText = true
    canvas.drawText(account.name.uppercase(), 48f, 140f, paint)
    
    // Balance label - smaller
    paint.textSize = 28f
    paint.isFakeBoldText = false
    canvas.drawText("BALANCE", 48f, 200f, paint)
    
    // Balance amount - large and bold
    paint.textSize = 44f
    paint.isFakeBoldText = true
    canvas.drawText("৳${String.format("%.2f", account.balance)}", 48f, 240f, paint)
    
    // Reference code - top right, smaller
    paint.textSize = 24f
    paint.isFakeBoldText = false
    val refText = "REF: ${account.referralCode ?: ""}"
    val refTextWidth = paint.measureText(refText)
    canvas.drawText(refText, width - refTextWidth - 48f, 60f, paint)
    
    // Active status - bottom right
    paint.textSize = 22f
    paint.isFakeBoldText = false
    val activeText = "★ ACTIVE"
    val activeTextWidth = paint.measureText(activeText)
    canvas.drawText(activeText, width - activeTextWidth - 48f, 280f, paint)
    
    return bitmap
}

// Card image capture and sharing functions
fun createCardImageAndShare(context: Context, account: SubAccount) {
    try {
        val bitmap = createCardImageBitmap(account)
        
        // Save bitmap to file
        val file = File(context.getExternalFilesDir(null), "account_card_${account.id}.png")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        
        // Create URI for sharing
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        
        // Share the image
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, "Check out my Smart Citizen Club account card!")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        
        context.startActivity(Intent.createChooser(shareIntent, "Share Account Card"))
        
    } catch (e: Exception) {
        // Fallback to text sharing if image creation fails
        shareAccountText(context, account)
    }
}

fun shareCardImageToWhatsApp(context: Context, account: SubAccount) {
    try {
        val bitmap = createCardImageBitmap(account)
        
        // Save bitmap to file
        val file = File(context.getExternalFilesDir(null), "whatsapp_card_${account.id}.png")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, "🏦 *Smart Citizen Club Account Card*")
            setPackage("com.whatsapp")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        
        context.startActivity(shareIntent)
        
    } catch (e: Exception) {
        shareToWhatsApp(context, account)
    }
}

// Sharing utility functions
fun shareToWhatsApp(context: Context, account: SubAccount) {
    val shareText = buildString {
        appendLine("🏦 *Smart Citizen Club Account*")
        appendLine("Account: ${account.name}")
        appendLine("Number: ${account.number}")
        if (!account.referralCode.isNullOrBlank()) {
            appendLine("Ref Code: ${account.referralCode}")
        }
        appendLine("Balance: ৳${String.format("%.2f", account.balance)}")
        appendLine()
        appendLine("Download Smart Citizen Club app to manage your accounts!")
    }
    
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
        setPackage("com.whatsapp")
    }
    
    try {
        context.startActivity(shareIntent)
    } catch (e: Exception) {
        // WhatsApp not installed, fallback to regular share
        shareAccountText(context, account)
    }
}

fun shareToFacebook(context: Context, account: SubAccount) {
    val shareText = buildString {
        appendLine("🏦 Smart Citizen Club Account")
        appendLine("Account: ${account.name}")
        appendLine("Number: ${account.number}")
        if (!account.referralCode.isNullOrBlank()) {
            appendLine("Ref Code: ${account.referralCode}")
        }
        appendLine("Balance: ৳${String.format("%.2f", account.balance)}")
    }
    
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
        setPackage("com.facebook.katana")
    }
    
    try {
        context.startActivity(shareIntent)
    } catch (e: Exception) {
        // Facebook not installed, fallback to regular share
        shareAccountText(context, account)
    }
}

fun shareToTelegram(context: Context, account: SubAccount) {
    val shareText = buildString {
        appendLine("🏦 Smart Citizen Club Account")
        appendLine("Account: ${account.name}")
        appendLine("Number: ${account.number}")
        if (!account.referralCode.isNullOrBlank()) {
            appendLine("Ref Code: ${account.referralCode}")
        }
        appendLine("Balance: ৳${String.format("%.2f", account.balance)}")
    }
    
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
        setPackage("org.telegram.messenger")
    }
    
    try {
        context.startActivity(shareIntent)
    } catch (e: Exception) {
        // Telegram not installed, fallback to regular share
        shareAccountText(context, account)
    }
}

fun shareAccountText(context: Context, account: SubAccount) {
    val shareText = buildString {
        appendLine("🏦 Smart Citizen Club Account")
        appendLine("Account Name: ${account.name}")
        appendLine("Account Number: ${account.number}")
        if (!account.referralCode.isNullOrBlank()) {
            appendLine("Referral Code: ${account.referralCode}")
        }
        appendLine("Balance: ৳${String.format("%.2f", account.balance)}")
        appendLine()
        appendLine("Download Smart Citizen Club app to manage your accounts!")
    }
    
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
        putExtra(Intent.EXTRA_SUBJECT, "My Smart Citizen Club Account")
    }
    
    context.startActivity(Intent.createChooser(shareIntent, "Share Account"))
}
