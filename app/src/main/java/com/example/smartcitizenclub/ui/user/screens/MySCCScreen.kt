package com.example.smartcitizenclub.ui.user.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.ui.theme.SmartCitizenClubTheme

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
    onLogout: () -> Unit
) {
    val settingsItems = remember {
        listOf(
            // General Section
            SettingsItem("Language", Icons.Default.Language, "English", true, "General"),
            SettingsItem("Account Type", Icons.Default.Person, "Regular", false, "General"),
            SettingsItem("I Want Profit", Icons.Default.TrendingUp, "YES", false, "General"),
            SettingsItem("Change PIN", Icons.Default.Lock, hasAction = true, category = "General"),
            SettingsItem("Change Mobile Operator", Icons.Default.PhoneAndroid, hasAction = true, category = "General"),
            SettingsItem("Re-submit KYC", Icons.Default.Description, hasAction = true, category = "General"),
            SettingsItem("Trusted Merchants", Icons.Default.Store, hasAction = true, category = "General"),
            
            // More Information Section
            SettingsItem("Privacy Policy", Icons.Default.PrivateConnectivity, hasAction = true, category = "More Information"),
            SettingsItem("FAQ", Icons.Default.Help, hasAction = true, category = "More Information"),
            SettingsItem("Store Locator", Icons.Default.LocationOn, hasAction = true, category = "More Information"),
            SettingsItem("About", Icons.Default.Info, hasAction = true, category = "More Information"),
            SettingsItem("SCC Page", Icons.Default.Web, hasAction = true, category = "More Information")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        // Header Section with Orange Background (like Nagad)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFF6B35)) // Orange color similar to Nagad
                .padding(16.dp)
        ) {
            Column {
                // Title
                Text(
                    text = "My SCC",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // User Profile Section
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Profile Avatar
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(32.dp),
                            tint = Color.White
                        )
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
            contentPadding = PaddingValues(16.dp)
        ) {
            // General Section
            item {
                SectionHeader("General")
            }
            
            val generalItems = settingsItems.filter { it.category == "General" }
            items(generalItems) { item ->
                SettingsItemCard(
                    item = item,
                    onClick = { /* Handle click */ }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader("More Information")
            }
            
            val moreInfoItems = settingsItems.filter { it.category == "More Information" }
            items(moreInfoItems) { item ->
                SettingsItemCard(
                    item = item,
                    onClick = { /* Handle click */ }
                )
            }
            
            // Logout Button
            item {
                Spacer(modifier = Modifier.height(32.dp))
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFF6B35) // Orange background for logout
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
                            Icons.Default.Logout,
                            contentDescription = "Logout",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Logout",
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF666666),
        modifier = Modifier.padding(vertical = 8.dp)
    )
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
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        onClick = if (item.hasAction) onClick else { {} }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with orange background circle
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFF6B35).copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    item.icon,
                    contentDescription = item.title,
                    tint = Color(0xFFFF6B35),
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Title
            Text(
                text = item.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            
            // Value (if present)
            item.value?.let { value ->
                Text(
                    text = value,
                    fontSize = 14.sp,
                    color = Color(0xFF666666)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            
            // Arrow icon (if has action)
            if (item.hasAction) {
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = "Navigate",
                    tint = Color(0xFF999999),
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
                userType = UserType.USER
            ),
            onLogout = {}
        )
    }
}