package com.example.smartcitizenclub.presentation.feature.messages.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.example.smartcitizenclub.presentation.feature.messages.screens.MessagesListScreen
import com.example.smartcitizenclub.presentation.feature.messages.models.Chat

/**
 * Main Messages Screen - now uses the restructured MessagesListScreen
 */
@Composable
fun MessagesScreen(
    user: User,
    onShowSelectContact: () -> Unit = {},
    onChatClick: (Chat) -> Unit = {},
    onNavigateToChat: (Chat) -> Unit = {}
) {
    MessagesListScreen(
        user = user,
        onShowSelectContact = onShowSelectContact,
        onChatClick = onChatClick,
        onNavigateToChat = onNavigateToChat
    )
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