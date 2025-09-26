package com.example.smartcitizenclub.presentation.feature.myscc.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.example.smartcitizenclub.presentation.theme.OrangeGradient
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import kotlinx.coroutines.delay

// Color constants from logo
private object Colors {
    val Red = Color(0xFFE53E3E)
    val Gold = Color(0xFFF59E0B)
    val Green = Color(0xFF10B981)
    val Gray = Color(0xFF666666)
    val LightGray = Color(0xFF999999)
    val DarkGray = Color(0xFF374151)
    val LightGreen = Color(0xFF10B981).copy(alpha = 0.1f)
    val LightGold = Color(0xFFF59E0B).copy(alpha = 0.2f)
    val LightRed = Color(0xFFE53E3E).copy(alpha = 0.1f)
}

// Helper function to open external URLs
private fun openExternalUrl(context: Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (e: Exception) {
        // Handle error if no app can handle the intent
        e.printStackTrace()
    }
}

data class SettingsItem(
    val title: String,
    val icon: ImageVector,
    val value: String? = null,
    val hasAction: Boolean = true,
    val category: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySCCScreen(
    user: User,
    onLogout: () -> Unit,
    onNavigateToChangePassword: () -> Unit = {},
    onNavigateToKYCSubmit: () -> Unit = {}
) {
    val context = LocalContext.current
    var showLanguageDialog by remember { mutableStateOf(false) }
    var currentLanguage by remember { mutableStateOf("English") }
    var showLanguageChangeNotification by remember { mutableStateOf(false) }
    var showPrivacyPolicy by remember { mutableStateOf(false) }
    var showFAQ by remember { mutableStateOf(false) }
    var showAbout by remember { mutableStateOf(false) }
    val settingsItems = remember(currentLanguage) {
        listOf(
            // General Section
            SettingsItem("Language", Icons.Default.Language, currentLanguage, true, "General"),
            SettingsItem("Change Password", Icons.Default.Lock, hasAction = true, category = "General"),
            SettingsItem("Submit KYC", Icons.Default.Description, hasAction = true, category = "General"),
            
            // More Information Section
            SettingsItem("Privacy Policy", Icons.Default.PrivateConnectivity, hasAction = true, category = "More Information"),
            SettingsItem("FAQ", Icons.Default.Help, hasAction = true, category = "More Information"),
            SettingsItem("About", Icons.Default.Info, hasAction = true, category = "More Information"),
            SettingsItem("SCC Page", Icons.Default.Web, hasAction = true, category = "More Information"),
            SettingsItem("Website", Icons.Default.Language, hasAction = true, category = "More Information")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        // Header Section with Primary Gradient Background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(OrangeGradient)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Column {
                // Title
                Text(
                    text = "My SCC",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // User Profile Section
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Profile Avatar with Primary Color Border
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.9f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Profile",
                                modifier = Modifier.size(28.dp),
                                tint = PrimaryOrangeGradient
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column {
                        Text(
                            text = user.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                        Text(
                            text = user.phone ?: user.email ?: "01742-184298",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    // Edit Button
                    IconButton(
                        onClick = { /* Handle edit */ }
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit Profile",
                            tint = Color.White
                        )
                    }
                }
            }
        }
        
        // Settings Content
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // General Section
            item {
                SectionHeader("General")
            }
            
            val generalItems = settingsItems.filter { it.category == "General" }
            items(generalItems) { item ->
                SettingsItemCard(
                    item = item,
                    onClick = { 
                        when (item.title) {
                            "Language" -> showLanguageDialog = true
                            "Change Password" -> onNavigateToChangePassword()
                            "Submit KYC" -> onNavigateToKYCSubmit()
                            // Add other click handlers here as needed
                        }
                    }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(8.dp))
                SectionHeader("More Information")
            }
            
            val moreInfoItems = settingsItems.filter { it.category == "More Information" }
            items(moreInfoItems) { item ->
                SettingsItemCard(
                    item = item,
                    onClick = { 
                        when (item.title) {
                            "Privacy Policy" -> showPrivacyPolicy = true
                            "FAQ" -> showFAQ = true
                            "About" -> showAbout = true
                            "SCC Page" -> {
                                // Open Facebook page
                                openExternalUrl(context, "https://facebook.com/smartcitizenclub")
                            }
                            "Website" -> {
                                // Open website
                                openExternalUrl(context, "https://smartcitizenclub.com")
                            }
                        }
                    }
                )
            }
            
            // Logout Button
            item {
                Spacer(modifier = Modifier.height(20.dp))
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = PrimaryOrangeGradient
                    ),
                    onClick = onLogout
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Sign out",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Sign out",
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
    
    // Language Selection Dialog
    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = {
                Text(
                    text = "Select Language",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryOrangeGradient
                )
            },
            text = {
                Column {
                    LanguageOption(
                        language = "English",
                        isSelected = currentLanguage == "English",
                        onClick = {
                            if (currentLanguage != "English") {
                                currentLanguage = "English"
                                showLanguageChangeNotification = true
                            }
                            showLanguageDialog = false
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LanguageOption(
                        language = "বাংলা",
                        isSelected = currentLanguage == "বাংলা",
                        onClick = {
                            if (currentLanguage != "বাংলা") {
                                currentLanguage = "বাংলা"
                                showLanguageChangeNotification = true
                            }
                            showLanguageDialog = false
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showLanguageDialog = false }
                ) {
                    Text(
                        text = "Cancel",
                        color = Color(0xFF666666)
                    )
                }
            }
        )
    }
    
    // Language Change Notification
    if (showLanguageChangeNotification) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(3000) // Show for 3 seconds
            showLanguageChangeNotification = false
        }
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = PrimaryOrangeGradient
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Language changed to $currentLanguage successfully!",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
    
    // Privacy Policy Screen
    if (showPrivacyPolicy) {
        com.example.smartcitizenclub.presentation.feature.myscc.ui.PrivacyPolicyScreen(
            onBackClick = { showPrivacyPolicy = false }
        )
    }
    
    // FAQ Screen
    if (showFAQ) {
        com.example.smartcitizenclub.presentation.feature.myscc.ui.FAQScreen(
            onBackClick = { showFAQ = false }
        )
    }
    
    // About Screen
    if (showAbout) {
        com.example.smartcitizenclub.presentation.feature.myscc.ui.AboutScreen(
            onBackClick = { showAbout = false }
        )
    }
    
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = PrimaryOrangeGradient,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun LanguageOption(
    language: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                PrimaryOrangeGradient.copy(alpha = 0.1f)
            else 
                Color(0xFFF3F4F6)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = language,
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) 
                    PrimaryOrangeGradient
                else 
                    Color(0xFF374151)
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            if (isSelected) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = PrimaryOrangeGradient,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsItemCard(
    item: SettingsItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        onClick = if (item.hasAction) onClick else { {} }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with primary color background circle
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(PrimaryOrangeGradient.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    item.icon,
                    contentDescription = item.title,
                    tint = PrimaryOrangeGradient,
                    modifier = Modifier.size(16.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Title
            Text(
                text = item.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            
            // Value (if present)
            item.value?.let { value ->
                Text(
                    text = value,
                    fontSize = 12.sp,
                    color = Colors.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            
            // Arrow icon (if has action)
            if (item.hasAction) {
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = "Navigate",
                    tint = Colors.LightGray,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MySCCScreenPreview() {
    SmartCitizenClubTheme {
        MySCCScreen(
            user = User(
                id = "1",
                name = "John Doe",
                email = "john@example.com",
                phone = "01742-184298",
                type = UserType.USER
            ),
            onLogout = {}
        )
    }
}
