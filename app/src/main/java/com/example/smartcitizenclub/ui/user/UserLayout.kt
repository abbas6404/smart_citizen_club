package com.example.smartcitizenclub.ui.user

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.example.smartcitizenclub.ui.user.components.BottomNavigation
import com.example.smartcitizenclub.ui.user.screens.*

sealed class UserScreen(val title: String, val icon: ImageVector) {
    // Main Bottom Navigation
    object Home : UserScreen("Home", Icons.Default.Home)
    object Transactions : UserScreen("Finance", Icons.Default.AccountBalance)
    object Messages : UserScreen("Messages", Icons.Default.Message)
    object Account : UserScreen("Account", Icons.Default.AccountCircle)
    object MySCC : UserScreen("My SCC", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserLayout(
    user: User,
    onLogout: () -> Unit,
    useSidebar: Boolean = false // Not used anymore, keeping for compatibility
) {
    var selectedScreen by remember { mutableStateOf<UserScreen>(UserScreen.Home) }
    
    // Always use Bottom Navigation
    BottomNavigation(
        user = user,
        selectedScreen = selectedScreen,
        onScreenSelected = { selectedScreen = it },
        onLogout = onLogout,
        onSwitchToSidebar = { /* No sidebar functionality */ }
    )
}

@Preview(showBackground = true)
@Composable
fun UserLayoutPreview() {
    SmartCitizenClubTheme {
        UserLayout(
            user = User(
                id = "1",
                name = "John Doe",
                email = "john@example.com",
                phone = "01741736354",
                type = UserType.USER
            ),
            onLogout = {},
            useSidebar = false // Preview with bottom navigation
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserLayoutSidebarPreview() {
    SmartCitizenClubTheme {
        UserLayout(
            user = User(
                id = "1",
                name = "John Doe",
                email = "john@example.com",
                phone = "01741736354",
                type = UserType.USER
            ),
            onLogout = {},
            useSidebar = true // Preview with sidebar
        )
    }
}
