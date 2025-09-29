package com.example.smartcitizenclub.presentation.feature.messages.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.smartcitizenclub.presentation.feature.account.ui.AccountColors
import com.example.smartcitizenclub.presentation.feature.messages.models.Contact
import com.example.smartcitizenclub.presentation.feature.messages.data.MessagingSampleData
import com.example.smartcitizenclub.presentation.feature.messages.utils.generateAvatarText

/**
 * Contact selection screen for starting new chats
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactSelectionScreen(
    onBackClick: () -> Unit,
    onNewGroup: () -> Unit,
    onNewContact: () -> Unit,
    onContactSelected: (Contact) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(false) }
    
    // Get contacts from sample data
    val contacts = remember {
        MessagingSampleData.sampleContacts
    }
    
    val filteredContacts = remember(searchQuery, contacts) {
        if (searchQuery.isEmpty()) {
            contacts
        } else {
            contacts.filter { 
                it.name.contains(searchQuery, ignoreCase = true) ||
                it.phoneNumber.contains(searchQuery, ignoreCase = true)
            }
        }
    }
    
    val sccMembers = filteredContacts.filter { it.isSCCMember }
    val nonSCCMembers = filteredContacts.filter { !it.isSCCMember }
    
    Scaffold(
        topBar = {
            if (showSearchBar) {
                // Search Bar
                TopAppBar(
                    title = {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search name or number...") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            ),
                            leadingIcon = {
                                IconButton(onClick = { showSearchBar = false }) {
                                    Icon(
                                        Icons.Default.ArrowBack,
                                        contentDescription = "Close search",
                                        tint = Color.Black
                                    )
                                }
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(
                                            Icons.Default.Close,
                                            contentDescription = "Clear search",
                                            tint = Color.Black
                                        )
                                    }
                                }
                            }
                        )
                    },
                    actions = {
                        IconButton(onClick = { /* TODO: Menu */ }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = "Menu",
                                tint = Color.Black
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = AccountColors.Background
                    )
                )
            } else {
                // Normal TopAppBar
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Select contact",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "${contacts.size} contacts",
                                fontSize = 14.sp,
                                color = AccountColors.TextSecondary
                            )
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
                        IconButton(onClick = { showSearchBar = true }) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.Black
                            )
                        }
                        IconButton(onClick = { /* TODO: Menu */ }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = "Menu",
                                tint = Color.Black
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    )
                )
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            // Action Options Section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    // New Group
                    ActionOption(
                        title = "New group",
                        icon = Icons.Default.Group,
                        iconBackground = AccountColors.SecondaryGreen,
                        onClick = onNewGroup
                    )
                    
                    // New Contact
                    ActionOption(
                        title = "New contact",
                        icon = Icons.Default.PersonAdd,
                        iconBackground = AccountColors.SecondaryGreen,
                        onClick = onNewContact
                    )
                }
            }
            
            // SCC Members Section
            if (sccMembers.isNotEmpty()) {
                item {
                    Text(
                        text = "Contacts on Smart Citizen Club",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                
                items(sccMembers) { contact ->
                    ContactItem(
                        contact = contact,
                        onClick = { onContactSelected(contact) }
                    )
                }
            }
            
            // Non-SCC Members Section
            if (nonSCCMembers.isNotEmpty()) {
                item {
                    Text(
                        text = "Invite to Smart Citizen Club",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                
                items(nonSCCMembers) { contact ->
                    ContactItem(
                        contact = contact,
                        onClick = { onContactSelected(contact) },
                        showInviteOption = true
                    )
                }
            }
        }
    }
}

@Composable
private fun ActionOption(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBackground: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon with background
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(iconBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        if (title == "New contact") {
            Icon(
                Icons.Default.GridView,
                contentDescription = "Grid",
                tint = AccountColors.TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun ContactItem(
    contact: Contact,
    onClick: () -> Unit,
    showInviteOption: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Avatar
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    if (contact.isSCCMember) 
                        AccountColors.SecondaryGreen 
                    else 
                        AccountColors.PrimaryOrange
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = generateAvatarText(contact.name),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = contact.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
            Text(
                text = contact.status ?: if (showInviteOption) "Invite to SCC" else "SCC Member",
                fontSize = 14.sp,
                color = AccountColors.TextSecondary
            )
        }
        
        if (showInviteOption) {
            TextButton(
                onClick = { /* TODO: Invite to SCC */ },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = AccountColors.PrimaryOrange
                )
            ) {
                Text(
                    text = "Invite",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactSelectionScreenPreview() {
    SmartCitizenClubTheme {
        ContactSelectionScreen(
            onBackClick = {},
            onNewGroup = {},
            onNewContact = {},
            onContactSelected = {}
        )
    }
}
