package com.example.smartcitizenclub.presentation.feature.messages.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import com.example.smartcitizenclub.presentation.theme.OrangeGradient
import com.example.smartcitizenclub.presentation.feature.account.ui.AccountColors
import com.example.smartcitizenclub.presentation.feature.messages.data.MessagingSampleData
import com.example.smartcitizenclub.presentation.feature.messages.utils.isValidPhoneNumber
import com.example.smartcitizenclub.presentation.feature.messages.utils.formatPhoneNumber

/**
 * Screen for adding new contacts
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewContactScreen(
    onBackClick: () -> Unit,
    onSaveContact: (String, String, String, String, Boolean) -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("BD +880") }
    var phoneNumber by remember { mutableStateOf("") }
    var showQRCode by remember { mutableStateOf(false) }
    var isValidForm by remember { mutableStateOf(false) }

    // Validate form whenever inputs change
    LaunchedEffect(firstName, lastName, phoneNumber) {
        isValidForm = firstName.isNotBlank() && 
                     lastName.isNotBlank() && 
                     phoneNumber.isNotBlank() && 
                     isValidPhoneNumber(phoneNumber)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "New contact",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
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
                    IconButton(onClick = { /* TODO: More options */ }) {
                        Icon(
                            Icons.Default.Apps,
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
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // First Name
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Person icon",
                    tint = AccountColors.TextSecondary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First name") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryOrangeGradient,
                        unfocusedBorderColor = AccountColors.TextTertiary,
                        focusedLabelColor = PrimaryOrangeGradient,
                        unfocusedLabelColor = AccountColors.TextSecondary,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Last Name
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(40.dp)) // Align with First Name field
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last name") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryOrangeGradient,
                        unfocusedBorderColor = AccountColors.TextTertiary,
                        focusedLabelColor = PrimaryOrangeGradient,
                        unfocusedLabelColor = AccountColors.TextSecondary,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Phone Number
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Phone,
                    contentDescription = "Phone icon",
                    tint = AccountColors.TextSecondary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                
                // Country Code Dropdown
                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .wrapContentSize(Alignment.TopStart)
                ) {
                    var expanded by remember { mutableStateOf(false) }
                    OutlinedTextField(
                        value = countryCode,
                        onValueChange = { /* Not directly editable, handled by dropdown */ },
                        label = { Text("Country") },
                        readOnly = true,
                        singleLine = true,
                        maxLines = 1,
                        trailingIcon = {
                            Icon(
                                Icons.Default.ArrowDropDown, 
                                contentDescription = "Dropdown arrow",
                                tint = AccountColors.TextSecondary
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryOrangeGradient,
                            unfocusedBorderColor = AccountColors.TextTertiary,
                            focusedLabelColor = PrimaryOrangeGradient,
                            unfocusedLabelColor = AccountColors.TextSecondary,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.width(IntrinsicSize.Max)
                    ) {
                        MessagingSampleData.countryCodes.forEach { code ->
                            DropdownMenuItem(
                                text = { Text(code) },
                                onClick = {
                                    countryCode = code
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryOrangeGradient,
                        unfocusedBorderColor = AccountColors.TextTertiary,
                        focusedLabelColor = PrimaryOrangeGradient,
                        unfocusedLabelColor = AccountColors.TextSecondary,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Save by QR Code button with Orange Gradient Border
            OutlinedButton(
                onClick = { showQRCode = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PrimaryOrangeGradient
                ),
                border = androidx.compose.foundation.BorderStroke(
                    2.dp, 
                    PrimaryOrangeGradient
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    Icons.Default.QrCode,
                    contentDescription = "QR Code",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Save by QR code",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // Pushes content to top and button to bottom

            // Save Button with Orange Gradient
            Button(
                onClick = { 
                    val formattedPhone = formatPhoneNumber(countryCode, phoneNumber)
                    onSaveContact(firstName, lastName, countryCode, formattedPhone, showQRCode) 
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        brush = if (isValidForm) OrangeGradient else androidx.compose.ui.graphics.Brush.linearGradient(
                            colors = listOf(AccountColors.TextTertiary, AccountColors.TextTertiary)
                        ),
                        shape = RoundedCornerShape(28.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(28.dp),
                enabled = isValidForm
            ) {
                Text(
                    text = "Save",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddNewContactScreenPreview() {
    SmartCitizenClubTheme {
        AddNewContactScreen(
            onBackClick = {},
            onSaveContact = { _, _, _, _, _ -> }
        )
    }
}
