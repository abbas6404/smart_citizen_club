package com.example.smartcitizenclub.presentation.feature.sendmoney.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.example.smartcitizenclub.presentation.theme.OrangeGradient
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient

// Function to read contacts from device
fun readContacts(context: Context): List<Contact> {
    val contacts = mutableListOf<Contact>()
    val cursor = context.contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,
        null,
        null,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
    )
    
    cursor?.use {
        val nameColumn = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val numberColumn = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
        
        while (it.moveToNext()) {
            val name = it.getString(nameColumn) ?: ""
            val number = it.getString(numberColumn) ?: ""
            if (number.isNotEmpty()) {
                contacts.add(Contact(
                    id = contacts.size.toString(),
                    name = name,
                    phoneNumber = number
                ))
            }
        }
    }
    
    return contacts
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoneyScreen(
    onBackClick: () -> Unit,
    onContactClick: (Contact) -> Unit = {},
    onNavigateToAmount: (String, String?, String?) -> Unit = { _, _, _ -> } // recipient, accountNumber, accountName
) {
    val context = LocalContext.current
    var recipientInput by remember { mutableStateOf("") }
    var showAccountSelection by remember { mutableStateOf(false) }
    var selectedMobileNumber by remember { mutableStateOf("") }
    var hasContactPermission by remember { 
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
        ) 
    }
    var deviceContacts by remember { mutableStateOf<List<Contact>>(emptyList()) }
    
    // Function to validate input and determine next action
    fun handleRecipientInput(input: String) {
        val cleanInput = input.replace("\\s+".toRegex(), "").replace("-", "")
        
        when {
            cleanInput.length == 11 && cleanInput.matches("\\d+".toRegex()) -> {
                // 11-digit mobile number - show account selection
                selectedMobileNumber = cleanInput
                showAccountSelection = true
            }
            cleanInput.length == 9 && cleanInput.matches("\\d+".toRegex()) -> {
                // 9-digit account number - go directly to amount
                onNavigateToAmount(cleanInput, null, null)
            }
            cleanInput.length > 0 && cleanInput.length < 9 -> {
                // Too short - show error or do nothing
                // You can add error handling here
            }
            cleanInput.length > 11 -> {
                // Too long - should not happen due to max length validation
                // You can add error handling here
            }
            else -> {
                // Invalid input - show error or do nothing
                // You can add error handling here
            }
        }
    }
    
    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasContactPermission = isGranted
        if (isGranted) {
            deviceContacts = readContacts(context)
        }
    }
    
    // Load contacts when permission is granted
    LaunchedEffect(hasContactPermission) {
        if (hasContactPermission) {
            deviceContacts = readContacts(context)
        }
    }
    
    // Recent contacts (empty for now - can be populated from transaction history)
    val recentContacts = remember { emptyList<Contact>() }
    
    // Use only device contacts - no fallback data
    val allContacts = deviceContacts.take(20) // Limit to first 20 contacts
    
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
                containerColor = PrimaryOrangeGradient
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
                    
                    OutlinedTextField(
                        value = recipientInput,
                        onValueChange = { newValue ->
                            // Limit input to 11 digits maximum
                            if (newValue.length <= 11) {
                                recipientInput = newValue
                            }
                        },
                        placeholder = {
                            Text(
                                text = "Phone or 9 Digit acc number",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryOrangeGradient,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Phone,
                                contentDescription = "Phone",
                                tint = PrimaryOrangeGradient,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { 
                                    handleRecipientInput(recipientInput)
                                },
                                enabled = recipientInput.isNotEmpty()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (recipientInput.isNotEmpty()) PrimaryOrangeGradient else Color.Gray.copy(alpha = 0.3f)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.ArrowForward,
                                        contentDescription = "Send",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    )
                }
            }
            
            // Recent Contacts Section
            item {
                Column {
                    Text(
                        text = "Recent",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryOrangeGradient
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
            
            // Saved Numbers Section
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Saved Numbers",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryOrangeGradient
                        )
                        
                        Row {
                            if (!hasContactPermission) {
                                IconButton(
                                    onClick = { 
                                        permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Contacts,
                                        contentDescription = "Access Contacts",
                                        tint = PrimaryOrangeGradient,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            
                            IconButton(
                                onClick = { /* Handle add saved number */ }
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "Add Saved Number",
                                    tint = PrimaryOrangeGradient,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Show permission request message if no permission
                    if (!hasContactPermission) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = PrimaryOrangeGradient.copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = "Info",
                                    tint = PrimaryOrangeGradient,
                                    modifier = Modifier.size(20.dp)
                                )
                                
                                Spacer(modifier = Modifier.width(12.dp))
                                
                                Text(
                                    text = "Tap the contacts icon to access your saved mobile numbers",
                                    fontSize = 12.sp,
                                    color = PrimaryOrangeGradient,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    
                    // Show all device contacts in one section
                    deviceContacts.forEach { contact ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { 
                                    // Directly navigate to amount screen when saved number is clicked
                                    onNavigateToAmount(contact.phoneNumber, null, contact.name)
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Phone,
                                    contentDescription = "Phone",
                                    tint = PrimaryOrangeGradient,
                                    modifier = Modifier.size(20.dp)
                                )
                                
                                Spacer(modifier = Modifier.width(12.dp))
                                
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = contact.phoneNumber,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black
                                    )
                                    if (contact.name.isNotEmpty()) {
                                        Text(
                                            text = contact.name,
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                }
                                
                                Icon(
                                    Icons.Default.ArrowForward,
                                    contentDescription = "Select",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        
                        if (contact != deviceContacts.lastOrNull()) {
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
    
    // Account Selection Screen
    if (showAccountSelection) {
        AccountSelectionScreen(
            mobileNumber = selectedMobileNumber,
            onAccountSelected = { accountNumber, accountName ->
                showAccountSelection = false
                onNavigateToAmount(selectedMobileNumber, accountNumber, accountName)
            },
            onBackClick = {
                showAccountSelection = false
            }
        )
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
            onContactClick = {},
            onNavigateToAmount = { _, _, _ -> }
        )
    }
}
