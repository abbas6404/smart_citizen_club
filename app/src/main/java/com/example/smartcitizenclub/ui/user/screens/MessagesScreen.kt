package com.example.smartcitizenclub.ui.user.screens

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
import com.example.smartcitizenclub.ui.theme.SmartCitizenClubTheme
import java.text.SimpleDateFormat
import java.util.*

// WhatsApp-style colors
private object WhatsAppColors {
    val PrimaryGreen = Color(0xFF075E54)
    val LightGreen = Color(0xFF128C7E)
    val AccentGreen = Color(0xFF25D366)
    val Background = Color(0xFFECE5DD)
    val ChatBackground = Color(0xFFE5DDD5)
    val MessageBubble = Color(0xFFDCF8C6)
    val UnreadBackground = Color(0xFFF0F0F0)
    val TextPrimary = Color(0xFF303030)
    val TextSecondary = Color(0xFF757575)
    val TextTertiary = Color(0xFF9E9E9E)
}

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
fun MessagesScreen(user: User) {
    var searchQuery by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(false) }
    
    // Sample chat data
    val chats = remember {
        listOf(
            Chat(
                id = "1",
                contactName = "Community Admin",
                lastMessage = "Welcome to Smart Citizen Club! We're excited to have you join our community.",
                timestamp = System.currentTimeMillis() - 3600000, // 1 hour ago
                isRead = false,
                unreadCount = 2,
                isOnline = true,
                isPinned = true
            ),
            Chat(
                id = "2",
                contactName = "Sarah Johnson",
                lastMessage = "Thanks for the helpful information about local events!",
                timestamp = System.currentTimeMillis() - 7200000, // 2 hours ago
                isRead = true,
                unreadCount = 0,
                isOnline = false,
                isPinned = false
            ),
            Chat(
                id = "3",
                contactName = "Mike Chen",
                lastMessage = "Can we schedule a community meeting for next week?",
                timestamp = System.currentTimeMillis() - 86400000, // 1 day ago
                isRead = true,
                unreadCount = 0,
                isOnline = true,
                isPinned = false
            ),
            Chat(
                id = "4",
                contactName = "City Council",
                lastMessage = "Important update about the new park development project.",
                timestamp = System.currentTimeMillis() - 172800000, // 2 days ago
                isRead = false,
                unreadCount = 1,
                isOnline = false,
                isPinned = true
            ),
            Chat(
                id = "5",
                contactName = "Lisa Park",
                lastMessage = "The neighborhood cleanup was a great success!",
                timestamp = System.currentTimeMillis() - 259200000, // 3 days ago
                isRead = true,
                unreadCount = 0,
                isOnline = false,
                isPinned = false
            ),
            Chat(
                id = "6",
                contactName = "David Wilson",
                lastMessage = "Hey, how are you doing?",
                timestamp = System.currentTimeMillis() - 432000000, // 5 days ago
                isRead = true,
                unreadCount = 0,
                isOnline = false,
                isPinned = false
            ),
            Chat(
                id = "7",
                contactName = "Emma Thompson",
                lastMessage = "See you tomorrow at the meeting!",
                timestamp = System.currentTimeMillis() - 604800000, // 1 week ago
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
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WhatsAppColors.Background)
    ) {
        // Top Bar
        WhatsAppTopBar(
            showSearchBar = showSearchBar,
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            onSearchToggle = { showSearchBar = !showSearchBar },
            onMenuClick = { /* TODO: Show menu */ }
        )
        
        // Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp), // Account for top bar height
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            if (filteredChats.isEmpty()) {
                item {
                    EmptyState()
                }
            } else {
                items(filteredChats) { chat ->
                    ChatItem(
                        chat = chat,
                        onClick = { /* TODO: Open chat */ }
                    )
                }
            }
        }
        
        // Floating Action Button
        FloatingActionButton(
            onClick = { /* TODO: Start new chat */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = WhatsAppColors.AccentGreen,
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
                .background(WhatsAppColors.PrimaryGreen)
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
                    text = "WhatsApp",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = WhatsAppColors.PrimaryGreen
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
            .background(
                if (chat.isRead) Color.Transparent else WhatsAppColors.UnreadBackground
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar with online indicator
        Box {
            // Avatar
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(WhatsAppColors.LightGreen),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = chat.contactName.take(1).uppercase(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            // Online indicator
            if (chat.isOnline) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(WhatsAppColors.AccentGreen)
                        .align(Alignment.BottomEnd)
                )
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Chat content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chat.contactName,
                    fontSize = 16.sp,
                    fontWeight = if (chat.isRead) FontWeight.Normal else FontWeight.Bold,
                    color = WhatsAppColors.TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (chat.isPinned) {
                        Icon(
                            Icons.Default.PushPin,
                            contentDescription = "Pinned",
                            tint = WhatsAppColors.TextTertiary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    
                    Text(
                        text = formatTimestamp(chat.timestamp),
                        fontSize = 12.sp,
                        color = WhatsAppColors.TextTertiary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chat.lastMessage,
                    fontSize = 14.sp,
                    color = if (chat.isRead) WhatsAppColors.TextSecondary else WhatsAppColors.TextPrimary,
                    fontWeight = if (chat.isRead) FontWeight.Normal else FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                
                if (chat.unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(WhatsAppColors.AccentGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (chat.unreadCount > 99) "99+" else chat.unreadCount.toString(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.Chat,
            contentDescription = "No Chats",
            modifier = Modifier.size(80.dp),
            tint = WhatsAppColors.TextTertiary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "No chats yet",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = WhatsAppColors.TextSecondary
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Start a conversation by tapping the chat button",
            fontSize = 14.sp,
            color = WhatsAppColors.TextTertiary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
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
                phone = null,
                userType = UserType.USER
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