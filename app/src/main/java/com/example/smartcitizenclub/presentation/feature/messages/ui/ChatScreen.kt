package com.example.smartcitizenclub.presentation.feature.messages.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.example.smartcitizenclub.presentation.feature.account.ui.AccountColors
import kotlinx.coroutines.launch

// Message data class
data class Message(
    val id: String,
    val text: String,
    val timestamp: String,
    val isFromMe: Boolean,
    val isRead: Boolean = true
)

// Import Chat from MessagesScreen instead of defining our own
// The Chat data class is defined in MessagesScreen.kt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chat: com.example.smartcitizenclub.presentation.feature.messages.ui.Chat,
    currentUser: User,
    onBackClick: () -> Unit
) {
    var messageText by remember { mutableStateOf("") }
    var showEmojiPicker by remember { mutableStateOf(false) }
    var showAttachmentMenu by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Sample messages
    val messages = remember {
        listOf(
            Message("1", "Hello! How are you?", "10:30 AM", false),
            Message(
                "2",
                "Hi! I'm doing great, thanks for asking. How about you?",
                "10:32 AM",
                true
            ),
            Message("3", "I'm good too! Are you free for a call later?", "10:35 AM", false),
            Message("4", "Sure! What time works for you?", "10:36 AM", true),
            Message("5", "How about 2 PM?", "10:38 AM", false),
            Message("6", "Perfect! I'll call you then. See you soon!", "10:40 AM", true),
            Message("7", "Great! Talk to you later.", "10:41 AM", false)
        )
    }

    // Auto scroll to bottom when new message is added
    LaunchedEffect(messages.size) {
        scope.launch {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Contact Avatar
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF4CAF50)), // Default green color
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = chat.contactName.take(1).uppercase(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = chat.contactName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = if (chat.isOnline) "Online" else "Last seen recently",
                                fontSize = 14.sp,
                                color = if (chat.isOnline) AccountColors.SecondaryGreen else AccountColors.TextSecondary
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Video call */ }) {
                        Icon(
                            Icons.Default.Videocam,
                            contentDescription = "Video call",
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = { /* TODO: Voice call */ }) {
                        Icon(
                            Icons.Default.Call,
                            contentDescription = "Voice call",
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = { /* TODO: More options */ }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "More options",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            // Message input area
            Column {
                // Attachment menu (shown when attachment icon is clicked)
                if (showAttachmentMenu) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        AttachmentOption(
                            icon = Icons.Default.CameraAlt,
                            text = "Camera",
                            onClick = { showAttachmentMenu = false }
                        )
                        AttachmentOption(
                            icon = Icons.Default.Photo,
                            text = "Gallery",
                            onClick = { showAttachmentMenu = false }
                        )
                        AttachmentOption(
                            icon = Icons.Default.Description,
                            text = "Document",
                            onClick = { showAttachmentMenu = false }
                        )
                        AttachmentOption(
                            icon = Icons.Default.LocationOn,
                            text = "Location",
                            onClick = { showAttachmentMenu = false }
                        )
                    }
                }

                // Message input
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    // Attachment button
                    IconButton(onClick = { showAttachmentMenu = !showAttachmentMenu }) {
                        Icon(
                            Icons.Default.AttachFile,
                            contentDescription = "Attach",
                            tint = AccountColors.TextSecondary
                        )
                    }

                    // Message input field
                    OutlinedTextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        placeholder = { Text("Message") },
                        modifier = Modifier.weight(1f),
                        maxLines = 4,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccountColors.SecondaryGreen,
                            unfocusedBorderColor = AccountColors.TextTertiary,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )

                    // Emoji button
                    IconButton(onClick = { showEmojiPicker = !showEmojiPicker }) {
                        Icon(
                            Icons.Default.EmojiEmotions,
                            contentDescription = "Emoji",
                            tint = AccountColors.TextSecondary
                        )
                    }

                    // Send button
                    IconButton(
                        onClick = {
                            if (messageText.isNotBlank()) {
                                // TODO: Send message
                                messageText = ""
                            }
                        },
                        enabled = messageText.isNotBlank()
                    ) {
                        Icon(
                            Icons.Default.Send,
                            contentDescription = "Send",
                            tint = if (messageText.isNotBlank()) AccountColors.SecondaryGreen else AccountColors.TextTertiary
                        )
                    }
                }
            }
        },
        containerColor = Color(0xFFF0F0F0)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = listState,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                MessageBubble(
                    message = message,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun AttachmentOption(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(AccountColors.SecondaryGreen.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = text,
                tint = AccountColors.SecondaryGreen,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = AccountColors.TextPrimary
        )
    }
}

@Composable
private fun MessageBubble(
    message: Message,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = if (message.isFromMe) Arrangement.End else Arrangement.Start
    ) {
        if (!message.isFromMe) {
            Spacer(modifier = Modifier.width(40.dp))
        }

        Column(
            horizontalAlignment = if (message.isFromMe) Alignment.End else Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp,
                            bottomStart = if (message.isFromMe) 12.dp else 4.dp,
                            bottomEnd = if (message.isFromMe) 4.dp else 12.dp
                        )
                    )
                    .background(
                        if (message.isFromMe) AccountColors.SecondaryGreen else Color.White
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = message.text,
                    fontSize = 16.sp,
                    color = if (message.isFromMe) Color.White else Color.Black,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message.timestamp,
                    fontSize = 12.sp,
                    color = AccountColors.TextSecondary
                )

                if (message.isFromMe) {
                    Icon(
                        if (message.isRead) Icons.Default.DoneAll else Icons.Default.Done,
                        contentDescription = "Message status",
                        tint = if (message.isRead) AccountColors.SecondaryGreen else AccountColors.TextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        if (message.isFromMe) {
            Spacer(modifier = Modifier.width(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    SmartCitizenClubTheme {
        ChatScreen(
            chat = com.example.smartcitizenclub.presentation.feature.messages.ui.Chat(
                id = "1",
                contactName = "John Doe",
                lastMessage = "Hello! How are you?",
                timestamp = System.currentTimeMillis(),
                isRead = true,
                unreadCount = 0,
                isOnline = true,
                isPinned = false,
                avatar = null
            ),
            currentUser = User(
                id = "1",
                name = "Current User",
                email = "user@example.com",
                phone = "+8801234567890",
                type = com.example.smartcitizenclub.data.UserType.USER
            ),
            onBackClick = {}
        )
    }
}
