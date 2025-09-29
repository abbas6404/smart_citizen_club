package com.example.smartcitizenclub.presentation.feature.messages.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
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
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import com.example.smartcitizenclub.presentation.feature.account.ui.AccountColors
import com.example.smartcitizenclub.presentation.feature.messages.models.Chat
import com.example.smartcitizenclub.presentation.feature.messages.utils.formatTimestamp
import com.example.smartcitizenclub.presentation.feature.messages.utils.generateAvatarText

/**
 * Individual chat item component for the chat list
 */
@Composable
fun ChatListItem(
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
                    text = generateAvatarText(chat.contactName),
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chat.lastMessage,
                    fontSize = 14.sp,
                    color = if (chat.isRead) AccountColors.TextSecondary else AccountColors.TextPrimary,
                    fontWeight = if (chat.isRead) FontWeight.Normal else FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
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

@Preview(showBackground = true)
@Composable
fun ChatListItemPreview() {
    SmartCitizenClubTheme {
        ChatListItem(
            chat = Chat(
                id = "1",
                contactName = "Community Admin",
                lastMessage = "Welcome to Smart Citizen Club!",
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
