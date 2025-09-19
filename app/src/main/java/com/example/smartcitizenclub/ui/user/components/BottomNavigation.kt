package com.example.smartcitizenclub.ui.user.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.ui.user.UserScreen
import com.example.smartcitizenclub.ui.user.screens.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(
    user: User,
    selectedScreen: UserScreen,
    onScreenSelected: (UserScreen) -> Unit,
    onLogout: () -> Unit,
    onSwitchToSidebar: () -> Unit
) {
    var showChangePassword by remember { mutableStateOf(false) }
    var showKYCSubmit by remember { mutableStateOf(false) }
    Scaffold(
        bottomBar = {
            if (!showChangePassword && !showKYCSubmit) {
                NavigationBar {
                    // Home
                    NavigationBarItem(
                        icon = { Icon(UserScreen.Home.icon, contentDescription = UserScreen.Home.title) },
                        label = { 
                            Text(
                                text = UserScreen.Home.title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp
                            ) 
                        },
                        selected = selectedScreen == UserScreen.Home,
                        onClick = { onScreenSelected(UserScreen.Home) }
                    )
                    
                    // Transactions
                    NavigationBarItem(
                        icon = { Icon(UserScreen.Transactions.icon, contentDescription = UserScreen.Transactions.title) },
                        label = { 
                            Text(
                                text = UserScreen.Transactions.title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp
                            ) 
                        },
                        selected = selectedScreen == UserScreen.Transactions,
                        onClick = { onScreenSelected(UserScreen.Transactions) }
                    )
                    
                    // Messages
                    NavigationBarItem(
                        icon = { Icon(UserScreen.Messages.icon, contentDescription = UserScreen.Messages.title) },
                        label = { 
                            Text(
                                text = UserScreen.Messages.title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp
                            ) 
                        },
                        selected = selectedScreen == UserScreen.Messages,
                        onClick = { onScreenSelected(UserScreen.Messages) }
                    )
                    
                    // Account
                    NavigationBarItem(
                        icon = { Icon(UserScreen.Account.icon, contentDescription = UserScreen.Account.title) },
                        label = { 
                            Text(
                                text = UserScreen.Account.title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp
                            ) 
                        },
                        selected = selectedScreen == UserScreen.Account,
                        onClick = { onScreenSelected(UserScreen.Account) }
                    )
                    
                    // My SCC
                    NavigationBarItem(
                        icon = { Icon(UserScreen.MySCC.icon, contentDescription = UserScreen.MySCC.title) },
                        label = { 
                            Text(
                                text = UserScreen.MySCC.title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp
                            ) 
                        },
                        selected = selectedScreen == UserScreen.MySCC,
                        onClick = { onScreenSelected(UserScreen.MySCC) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        ) {
            when {
                showChangePassword -> {
                    com.example.smartcitizenclub.ui.user.screens.myscc.ChangePasswordScreen(
                        onBackClick = { showChangePassword = false },
                        onPasswordChanged = { 
                            showChangePassword = false
                            // You can add success message or other actions here
                        }
                    )
                }
                showKYCSubmit -> {
                    com.example.smartcitizenclub.ui.user.screens.myscc.KYCSubmitScreen(
                        onBackClick = { showKYCSubmit = false },
                        onKYCSubmitted = { 
                            showKYCSubmit = false
                            // You can add success message or other actions here
                        }
                    )
                }
                else -> {
                when (selectedScreen) {
                    UserScreen.Home -> HomeScreen(user)
                    UserScreen.Transactions -> FinanceScreen(user)
                    UserScreen.Messages -> MessagesScreen(user)
                    UserScreen.Account -> AccountScreen(user, onLogout)
                    UserScreen.MySCC -> MySCCScreen(
                        user = user, 
                        onLogout = onLogout,
                        onNavigateToChangePassword = { showChangePassword = true },
                        onNavigateToKYCSubmit = { showKYCSubmit = true }
                    )
                }
                }
            }
        }
    }
}