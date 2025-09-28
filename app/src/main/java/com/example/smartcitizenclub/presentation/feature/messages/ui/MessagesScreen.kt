package com.example.smartcitizenclub.presentation.feature.messages.ui

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import com.example.smartcitizenclub.presentation.feature.account.ui.AccountColors
import java.text.SimpleDateFormat
import java.util.*

data class Chat(
    val id: String,
    val contactName: String,
    val lastMessage: String,
    val timestamp: Long,
    val isRead: Boolean = false,
    val unreadCount: Int = 0,
    val isOnline: Boolean = false,
    val isPinned: Boolean = false,
    val avatar: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
    user: User,
    onShowSelectContact: () -> Unit = {},
    onChatClick: (Chat) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(false) }
    
    // Sample chat data
    val chats = remember {
        listOf(
            Chat(
                id = "1",
                contactName = "Community Admin",
                lastMessage = "Welcome to Smart Citizen Club!",
                timestamp = System.currentTimeMillis() - 3600000, // 1 hour ago
                isRead = false,
                unreadCount = 2,
                isOnline = true,
                isPinned = true
            ),
            Chat(
                id = "2",
                contactName = "Sarah Johnson",
                lastMessage = "Thanks for the helpful information!",
                timestamp = System.currentTimeMillis() - 7200000, // 2 hours ago
                isRead = true,
                unreadCount = 0,
                isOnline = false,
                isPinned = false
            ),
            Chat(
                id = "3",
                contactName = "Mike Chen",
                lastMessage = "Can we schedule a community meeting?",
                timestamp = System.currentTimeMillis() - 86400000, // 1 day ago
                isRead = true,
                unreadCount = 0,
                isOnline = true,
                isPinned = false
            ),
            Chat(
                id = "4",
                contactName = "City Council",
                lastMessage = "Important update about park development",
                timestamp = System.currentTimeMillis() - 172800000, // 2 days ago
                isRead = false,
                unreadCount = 1,
                isOnline = false,
                isPinned = true
            ),
            Chat(
                id = "5",
                contactName = "Lisa Park",
                lastMessage = "Neighborhood cleanup was successful!",
                timestamp = System.currentTimeMillis() - 259200000, // 3 days ago
                isRead = true,
                unreadCount = 0,
                isOnline = false,
                isPinned = false
            ),
            Chat(
                id = "6",
                contactName = "David Wilson",
                lastMessage = "Looking forward to community garden",
                timestamp = System.currentTimeMillis() - 345600000, // 4 days ago
                isRead = true,
                unreadCount = 0,
                isOnline = true,
                isPinned = false
            ),
            Chat(
                id = "7",
                contactName = "Emma Thompson",
                lastMessage = "Recycling program is working well",
                timestamp = System.currentTimeMillis() - 432000000, // 5 days ago
                isRead = true,
                unreadCount = 0,
                isOnline = false,
                isPinned = false
            )
        )
    }
    
    val filteredChats = if (searchQuery.isEmpty()) {
        chats.sortedWith(compareBy<Chat> { !it.isPinned }.thenByDescending { it.timestamp })
    } else {
        chats.filter { 
            it.contactName.contains(searchQuery, ignoreCase = true) ||
            it.lastMessage.contains(searchQuery, ignoreCase = true)
        }.sortedWith(compareBy<Chat> { !it.isPinned }.thenByDescending { it.timestamp })
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
            containerColor = Color.White
        ) { paddingValues ->
            // Content
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                if (filteredChats.isEmpty()) {
                    item {
                        EmptyState()
                    }
                } else {
                    items(filteredChats) { chat ->
                        ChatItem(
                            chat = chat,
                            onClick = { onChatClick(chat) }
                        )
                    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WhatsAppTopBar(
    showSearchBar: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchToggle: () -> Unit,
    onMenuClick: () -> Unit
) {
    if (showSearchBar) {
        // Search Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryOrangeGradient)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onSearchToggle) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = { 
                    Text(
                        "Search or start new chat",
                        color = Color.White.copy(alpha = 0.7f)
                    ) 
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White.copy(alpha = 0.5f),
                    unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
            )
        }
    } else {
        // Main Top Bar
        TopAppBar(
            title = {
                Text(
                    text = "Messages",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = PrimaryOrangeGradient
            ),
            actions = {
                IconButton(onClick = onSearchToggle) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
                IconButton(onClick = onMenuClick) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
            }
        )
    }
}

@Composable
private fun ChatItem(
    chat: Chat,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar with online indicator
        Box {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            chat.isPinned -> PrimaryOrangeGradient
                            chat.unreadCount > 0 -> PrimaryOrangeGradient
                            else -> AccountColors.Blue
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = chat.contactName.take(1).uppercase(),
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Online indicator
            if (chat.isOnline) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(PrimaryOrangeGradient)
                        .align(Alignment.BottomEnd)
                )
            }
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        // Chat info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = chat.contactName,
                        fontSize = 16.sp,
                        fontWeight = if (chat.isRead) FontWeight.Medium else FontWeight.Bold,
                        color = AccountColors.TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    if (chat.isPinned) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            Icons.Default.PushPin,
                            contentDescription = "Pinned",
                            tint = PrimaryOrangeGradient,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                
                Text(
                    text = formatTimestamp(chat.timestamp),
                    fontSize = 12.sp,
                    color = AccountColors.TextSecondary,
                    fontWeight = FontWeight.Normal
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chat.lastMessage,
                    fontSize = 14.sp,
                    color = if (chat.isRead) AccountColors.TextSecondary else AccountColors.TextPrimary,
                    fontWeight = if (chat.isRead) FontWeight.Normal else FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                
                if (chat.unreadCount > 0) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(PrimaryOrangeGradient),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (chat.unreadCount > 99) "99+" else chat.unreadCount.toString(),
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(PrimaryOrangeGradient.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Chat,
                contentDescription = "No Chats",
                modifier = Modifier.size(60.dp),
                tint = PrimaryOrangeGradient
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "No messages yet",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = AccountColors.TextPrimary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = "Start a conversation by tapping the chat button below",
            fontSize = 16.sp,
            color = AccountColors.TextSecondary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            lineHeight = 24.sp
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { /* TODO: Handle new chat */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryOrangeGradient
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Icon(
                Icons.Default.Chat,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Start New Chat",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    
    return when {
        diff < 60000 -> "now" // Less than 1 minute
        diff < 3600000 -> "${diff / 60000}m" // Less than 1 hour
        diff < 86400000 -> "${diff / 3600000}h" // Less than 1 day
        diff < 604800000 -> "${diff / 86400000}d" // Less than 1 week
        else -> {
            val formatter = SimpleDateFormat("MMM dd", Locale.getDefault())
            formatter.format(Date(timestamp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessagesScreenPreview() {
    SmartCitizenClubTheme {
        MessagesScreen(
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

@Preview(showBackground = true)
@Composable
fun ChatItemPreview() {
    SmartCitizenClubTheme {
        ChatItem(
            chat = Chat(
                id = "1",
                contactName = "Community Admin",
                lastMessage = "Welcome to Smart Citizen Club! We're excited to have you join our community.",
                timestamp = System.currentTimeMillis() - 3600000,
                isRead = false,
                unreadCount = 2,
                isOnline = true,
                isPinned = true
            ),
            onClick = {}
        )
    }
}
