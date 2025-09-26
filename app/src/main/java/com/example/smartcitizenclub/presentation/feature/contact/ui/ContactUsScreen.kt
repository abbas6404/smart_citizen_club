package com.example.smartcitizenclub.presentation.feature.contact.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun ContactUsScreen(
    user: User,
    onBackClick: () -> Unit,
    onTicketSubmitted: (SupportTicket) -> Unit = {}
) {
    var subject by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(TicketCategory.GENERAL_INQUIRY) }
    var selectedPriority by remember { mutableStateOf(TicketPriority.MEDIUM) }
    
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Red Background
        TopAppBar(
            title = {
                Text(
                    text = "Contact Us",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Contact Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.ContactSupport,
                        contentDescription = "Contact Support",
                        tint = Color(0xFFE53E3E),
                        modifier = Modifier.size(48.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = "How can we help you?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Submit a support ticket and our team will get back to you as soon as possible.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
            
            // User Info
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Your Information",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Name",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = user.name,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    if (user.phone != null) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Phone,
                                contentDescription = "Phone",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Text(
                                text = user.phone,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                    }
                    
                    if (user.email != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = "Email",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Text(
                                text = user.email,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
            
            // Ticket Form
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Submit a Ticket",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Subject Field
                    Text(
                        text = "Subject",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    OutlinedTextField(
                        value = subject,
                        onValueChange = { subject = it },
                        placeholder = {
                            Text(
                                text = "Enter subject",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFE53E3E),
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Description Field
                    Text(
                        text = "Description",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = {
                            Text(
                                text = "Describe your issue or question in detail",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFE53E3E),
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Category Dropdown
                    Text(
                        text = "Category",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    ExposedDropdownMenuBox(
                        expanded = false,
                        onExpandedChange = { /* TODO: Handle dropdown */ }
                    ) {
                        OutlinedTextField(
                            value = selectedCategory.name.replace("_", " "),
                            onValueChange = { },
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = "Expand",
                                    tint = Color.Gray
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFE53E3E),
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = false,
                            onDismissRequest = { /* TODO: Handle dismiss */ }
                        ) {
                            // TODO: Add category options
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Priority Dropdown
                    Text(
                        text = "Priority",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    ExposedDropdownMenuBox(
                        expanded = false,
                        onExpandedChange = { /* TODO: Handle dropdown */ }
                    ) {
                        OutlinedTextField(
                            value = selectedPriority.name,
                            onValueChange = { },
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = "Expand",
                                    tint = Color.Gray
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFE53E3E),
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = false,
                            onDismissRequest = { /* TODO: Handle dismiss */ }
                        ) {
                            // TODO: Add priority options
                        }
                    }
                }
            }
            
            // Submit Button
            Button(
                onClick = {
                    // TODO: Submit ticket
                    // For now, we'll just call the callback with a mock ticket
                    val mockTicket = SupportTicket(
                        id = "ticket_1",
                        subject = subject,
                        description = description,
                        category = selectedCategory,
                        priority = selectedPriority,
                        status = TicketStatus.OPEN,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis(),
                        userId = user.id,
                        userName = user.name,
                        userEmail = user.email,
                        userPhone = user.phone
                    )
                    onTicketSubmitted(mockTicket)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53E3E)
                ),
                shape = RoundedCornerShape(25.dp),
                enabled = subject.isNotEmpty() && description.isNotEmpty()
            ) {
                Text(
                    text = "Submit Ticket",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactUsScreenPreview() {
    SmartCitizenClubTheme {
        ContactUsScreen(
            user = User(
                id = "1",
                name = "Abbas Uddin",
                email = "abbas@example.com",
                phone = "01741736354",
                type = UserType.USER
            ),
            onBackClick = {},
            onTicketSubmitted = {}
        )
    }
}
