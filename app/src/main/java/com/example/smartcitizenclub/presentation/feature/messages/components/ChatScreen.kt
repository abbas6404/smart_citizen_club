package com.example.smartcitizenclub.presentation.feature.messages.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import com.example.smartcitizenclub.presentation.feature.messages.models.ChatMessage
import com.example.smartcitizenclub.presentation.feature.messages.utils.formatMessageTime
import com.example.smartcitizenclub.presentation.feature.messages.utils.generateAvatarText

/**
 * Individual message bubble component
 */
@Composable
fun MessageBubble(
    message: ChatMessage
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start
        ) {
            if (message.isFromUser) {
                Spacer(modifier = Modifier.weight(1f))
            }
            
            Card(
                modifier = Modifier.widthIn(max = 280.dp),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (message.isFromUser) 16.dp else 4.dp,
                    bottomEnd = if (message.isFromUser) 4.dp else 16.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = if (message.isFromUser) PrimaryOrangeGradient else Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = message.text,
                    modifier = Modifier.padding(12.dp),
                    color = if (message.isFromUser) Color.White else Color.Black,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
            
            if (!message.isFromUser) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        
        Spacer(modifier = Modifier.height(2.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start
        ) {
            Text(
                text = formatMessageTime(message.timestamp),
                fontSize = 11.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            
            if (message.isFromUser && message.isRead) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    Icons.Default.DoneAll,
                    contentDescription = "Read",
                    tint = PrimaryOrangeGradient,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

/**
 * Chat conversation screen component
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chat: com.example.smartcitizenclub.presentation.feature.messages.models.Chat,
    user: User,
    onBackClick: () -> Unit
) {
    var messageText by remember { mutableStateOf("") }
    
    // Sample chat messages for the conversation
    val chatMessages = remember {
        com.example.smartcitizenclub.presentation.feature.messages.data.MessagingSampleData.sampleChatMessages
    }
    
    val listState = rememberLazyListState()
    
    // Auto-scroll to bottom when messages change
    LaunchedEffect(chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(chatMessages.size - 1)
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
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
                                    .background(PrimaryOrangeGradient),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = generateAvatarText(chat.contactName),
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            
                            Spacer(modifier = Modifier.width(12.dp))
                            
                            Column {
                                Text(
                                    text = chat.contactName,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Last seen recently",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = PrimaryOrangeGradient
                    ),
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Video call */ }) {
                            Icon(
                                Icons.Default.Videocam,
                                contentDescription = "Video call",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { /* Voice call */ }) {
                            Icon(
                                Icons.Default.Call,
                                contentDescription = "Voice call",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { /* More options */ }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = "More options",
                                tint = Color.White
                            )
                        }
                    }
                )
            },
            containerColor = Color(0xFFF0F0F0),
            bottomBar = { } // No bottom navigation
        ) { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Messages List
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(chatMessages) { message ->
                        MessageBubble(message = message)
                    }
                }
                
                // Message Input Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* Attach file */ }) {
                        Icon(
                            Icons.Default.AttachFile,
                            contentDescription = "Attach",
                            tint = Color.Gray
                        )
                    }
                    
                    OutlinedTextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        placeholder = { Text("Message") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        singleLine = true,
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5)
                        )
                    )
                    
                    IconButton(onClick = { /* Send emoji */ }) {
                        Icon(
                            Icons.Default.EmojiEmotions,
                            contentDescription = "Emoji",
                            tint = Color.Gray
                        )
                    }
                    
                    IconButton(
                        onClick = { 
                            if (messageText.isNotBlank()) {
                                // TODO: Send message
                                messageText = ""
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Send,
                            contentDescription = "Send",
                            tint = PrimaryOrangeGradient
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageBubblePreview() {
    SmartCitizenClubTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MessageBubble(
                message = ChatMessage(
                    id = "1",
                    text = "Hello! How are you?",
                    timestamp = System.currentTimeMillis(),
                    isFromUser = false
                )
            )
            MessageBubble(
                message = ChatMessage(
                    id = "2",
                    text = "Hi! I'm doing great, thanks for asking. How about you?",
                    timestamp = System.currentTimeMillis(),
                    isFromUser = true,
                    isRead = true
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    SmartCitizenClubTheme {
        ChatScreen(
            chat = com.example.smartcitizenclub.presentation.feature.messages.models.Chat(
                id = "1",
                contactName = "City Council",
                lastMessage = "Important update about park development",
                timestamp = System.currentTimeMillis() - 172800000,
                isRead = false,
                unreadCount = 1,
                isOnline = false,
                isPinned = true
            ),
            user = User(
                id = "1",
                name = "John Doe",
                email = "john@example.com",
                phone = "01741736354",
                type = UserType.USER
            ),
            onBackClick = {}
        )
    }
}
