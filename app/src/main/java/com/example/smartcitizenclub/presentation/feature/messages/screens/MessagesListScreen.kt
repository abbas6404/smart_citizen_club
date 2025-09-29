package com.example.smartcitizenclub.presentation.feature.messages.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import com.example.smartcitizenclub.presentation.feature.messages.components.ChatListItem
import com.example.smartcitizenclub.presentation.feature.messages.models.Chat
import com.example.smartcitizenclub.presentation.feature.messages.data.MessagingSampleData

/**
 * Main messages list screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesListScreen(
    user: User,
    onShowSelectContact: () -> Unit = {},
    onChatClick: (Chat) -> Unit = {},
    onNavigateToChat: (Chat) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(false) }
    
    // Get chat list from sample data
    val chatList = remember {
        MessagingSampleData.sampleChats
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Messages",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = PrimaryOrangeGradient
                    ),
                    actions = {
                        IconButton(onClick = { showSearchBar = !showSearchBar }) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { /* TODO: Show menu */ }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = "More options",
                                tint = Color.White
                            )
                        }
                    }
                )
            },
            containerColor = Color.White,
            bottomBar = { } // No bottom navigation
        ) { paddingValues ->
            // Show chat list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = paddingValues.calculateTopPadding(),
                    bottom = 80.dp
                )
            ) {
                items(chatList) { chat ->
                    ChatListItem(
                        chat = chat,
                        onClick = { onNavigateToChat(chat) }
                    )
                }
            }
        }
        
        // Floating Action Button
        FloatingActionButton(
            onClick = { onShowSelectContact() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = PrimaryOrangeGradient,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp
            )
        ) {
            Icon(
                Icons.Default.Chat,
                contentDescription = "New Chat",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessagesListScreenPreview() {
    SmartCitizenClubTheme {
        MessagesListScreen(
            user = User(
                id = "1",
                name = "John Doe",
                email = "john@example.com",
                phone = "01741736354",
                type = UserType.USER
            )
        )
    }
}
