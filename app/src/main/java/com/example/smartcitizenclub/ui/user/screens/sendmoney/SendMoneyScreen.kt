package com.example.smartcitizenclub.ui.user.screens.sendmoney

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoneyScreen(
    onBackClick: () -> Unit,
    onContactClick: (Contact) -> Unit = {}
) {
    var recipientInput by remember { mutableStateOf("") }
    
    // Sample contacts data
    val recentContacts = remember {
        listOf(
            Contact("1", "", "01533-619640"),
            Contact("2", "", "01533-619640")
        )
    }
    
    val favoriteContacts = remember {
        listOf(
            Contact("3", "", "01533-619640"),
            Contact("4", "", "01533-619640")
        )
    }
    
    val allContacts = remember {
        listOf(
            Contact("5", "", "+8801731247370"),
            Contact("6", "Aaa", "01754-673339"),
            Contact("7", "Abdul Malek Koyal Dim", "01777-127775"),
            Contact("8", "Ahod C", "01788-123456"),
            Contact("9", "Bakar Ali", "01799-987654"),
            Contact("10", "Chandra Kumar", "01800-555555")
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Red Background
        TopAppBar(
            title = {
                Text(
                    text = "Send Money",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
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
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFE53E3E) // Red color
            ),
            modifier = Modifier.statusBarsPadding()
        )
        
        // Main Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Recipient Input Section
            item {
                Column {
                    Text(
                        text = "Recipient",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Phone,
                            contentDescription = "Phone",
                            tint = Color(0xFFE53E3E),
                            modifier = Modifier.size(20.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        OutlinedTextField(
                            value = recipientInput,
                            onValueChange = { recipientInput = it },
                            placeholder = {
                                Text(
                                    text = "Enter Name, 11-digit Mobile Number or 16-digit Virtual Card Number",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFE53E3E),
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        // Send Button
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                                .clickable { /* Handle send action */ },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.ArrowForward,
                                contentDescription = "Send",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
            
            // Recent Contacts Section
            item {
                Column {
                    Text(
                        text = "Recent",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    recentContacts.forEach { contact ->
                        ContactItem(
                            contact = contact,
                            onClick = { onContactClick(contact) }
                        )
                        
                        if (contact != recentContacts.last()) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            
            // Favourites Section
            item {
                Column {
                    Text(
                        text = "Favourites",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    favoriteContacts.forEach { contact ->
                        ContactItem(
                            contact = contact,
                            onClick = { onContactClick(contact) }
                        )
                        
                        if (contact != favoriteContacts.last()) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            
            // All Contacts Section
            item {
                Column {
                    Text(
                        text = "All Contacts",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    allContacts.forEach { contact ->
                        ContactItem(
                            contact = contact,
                            onClick = { onContactClick(contact) }
                        )
                        
                        if (contact != allContacts.last()) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            
            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
private fun ContactItem(
    contact: Contact,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Avatar",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Contact Info
        Column {
            if (contact.name.isNotEmpty()) {
                Text(
                    text = contact.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
            
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
fun SendMoneyScreenPreview() {
    SmartCitizenClubTheme {
        SendMoneyScreen(
            onBackClick = {},
            onContactClick = {}
        )
    }
}
