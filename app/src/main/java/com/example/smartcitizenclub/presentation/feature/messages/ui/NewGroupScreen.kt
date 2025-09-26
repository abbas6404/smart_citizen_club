package com.example.smartcitizenclub.presentation.feature.messages.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewGroupScreen(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedContacts by remember { mutableStateOf(listOf<Contact>()) }

    // Sample contacts data
    val allContacts = remember {
        listOf(
            Contact("1", "Al-amin fs", "01712345678", true, Color(0xFF4CAF50)),
            Contact("2", "Akhi 222 CSE DIU", "01812345678", true, Color(0xFF2196F3)),
            Contact("3", "Aaa", "01912345678", true, Color(0xFFFFC107)),
            Contact("4", "Alice Smith", "01612345678", true, Color(0xFF9C27B0)),
            Contact("5", "Bob Johnson", "01512345678", true, Color(0xFFF44336)),
            Contact("6", "Charlie Brown", "01723456789", true, Color(0xFF00BCD4)),
            Contact("7", "Diana Prince", "01823456789", true, Color(0xFFE91E63)),
            Contact("8", "Eve Adams", "01923456789", true, Color(0xFF607D8B)),
            Contact("9", "Frank White", "01623456789", true, Color(0xFF795548)),
            Contact("10", "Grace Lee", "01523456789", true, Color(0xFF8BC34A)),
            Contact("11", "Henry King", "01734567890", true, Color(0xFFCDDC39)),
            Contact("12", "Ivy Queen", "01834567890", true, Color(0xFF03A9F4)),
            Contact("13", "Jack Black", "01934567890", true, Color(0xFFFF5722)),
            Contact("14", "Karen Green", "01634567890", true, Color(0xFFFFEB3B)),
            Contact("15", "Liam Blue", "01534567890", true, Color(0xFF673AB7))
        )
    }

    val filteredContacts = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            allContacts
        } else {
            allContacts.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.phoneNumber.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "New group",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${selectedContacts.size} of ${allContacts.size} selected",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )
                    }
                },
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
                    IconButton(onClick = { /* TODO: Search functionality */ }) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SmartCitizenColors.PrimaryOrange
                )
            )
        },
        floatingActionButton = {
            if (selectedContacts.isNotEmpty()) {
                FloatingActionButton(
                    onClick = onNextClick,
                    containerColor = Color(0xFF25D366), // WhatsApp green
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = "Next",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Selected Contacts Row
            if (selectedContacts.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(selectedContacts) { contact ->
                        SelectedContactItem(
                            contact = contact,
                            onRemoveClick = {
                                selectedContacts = selectedContacts.filter { it.id != contact.id }
                            }
                        )
                    }
                }
            }

            // Section Header
            Text(
                text = "Contacts on Smart Citizen Club",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Contacts List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp) // Space for FAB
            ) {
                items(filteredContacts) { contact ->
                    ContactListItem(
                        contact = contact,
                        isSelected = selectedContacts.any { it.id == contact.id },
                        onClick = {
                            selectedContacts = if (selectedContacts.any { it.id == contact.id }) {
                                selectedContacts.filter { it.id != contact.id }
                            } else {
                                selectedContacts + contact
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectedContactItem(
    contact: Contact,
    onRemoveClick: () -> Unit
) {
    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(60.dp)
        ) {
            // Avatar with remove button
            Box {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(contact.avatarColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = contact.name.take(1).uppercase(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // Remove button
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .clickable { onRemoveClick() }
                        .align(Alignment.BottomEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Remove",
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            // Name
            Text(
                text = contact.name,
                fontSize = 10.sp,
                color = Color.Black,
                maxLines = 1,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun ContactListItem(
    contact: Contact,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar with selection indicator
        Box {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(contact.avatarColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = contact.name.take(1).uppercase(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Selection indicator
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color.Black)
                        .align(Alignment.BottomEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = contact.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = contact.phoneNumber,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewGroupScreenPreview() {
    SmartCitizenClubTheme {
        NewGroupScreen(
            onBackClick = {},
            onNextClick = {}
        )
    }
}
