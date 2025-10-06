package com.example.smartcitizenclub.presentation.feature.myscc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.example.smartcitizenclub.presentation.theme.OrangeGradient
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePinScreen(
    onBackClick: () -> Unit,
    onPinChanged: () -> Unit
) {
    var currentPin by remember { mutableStateOf("") }
    var newPin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showCurrentPin by remember { mutableStateOf(false) }
    var showNewPin by remember { mutableStateOf(false) }
    var showConfirmPin by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with Primary Gradient Background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(OrangeGradient)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = "Change PIN",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            
            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Info Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF59E0B).copy(alpha = 0.1f) // Gold from logo
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Security,
                            contentDescription = "Security Info",
                            tint = Color(0xFFF59E0B), // Gold from logo
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Your PIN is used for secure transactions. Choose a 4-digit PIN that's easy to remember but hard to guess.",
                            fontSize = 14.sp,
                            color = Color(0xFF374151),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                // Current PIN Field
                OutlinedTextField(
                    value = currentPin,
                    onValueChange = { 
                        if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                            currentPin = it
                            errorMessage = ""
                        }
                    },
                    label = { Text("Current PIN") },
                    placeholder = { Text("Enter 4-digit PIN") },
                    leadingIcon = {
                        Icon(Icons.Default.Pin, contentDescription = "Current PIN")
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { showCurrentPin = !showCurrentPin }
                        ) {
                            Icon(
                                if (showCurrentPin) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (showCurrentPin) "Hide PIN" else "Show PIN"
                            )
                        }
                    },
                    visualTransformation = if (showCurrentPin) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryOrangeGradient,
                        unfocusedBorderColor = Color(0xFFD1D5DB)
                    )
                )
                
                // New PIN Field
                OutlinedTextField(
                    value = newPin,
                    onValueChange = { 
                        if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                            newPin = it
                            errorMessage = ""
                        }
                    },
                    label = { Text("New PIN") },
                    placeholder = { Text("Enter 4-digit PIN") },
                    leadingIcon = {
                        Icon(Icons.Default.Pin, contentDescription = "New PIN")
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { showNewPin = !showNewPin }
                        ) {
                            Icon(
                                if (showNewPin) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (showNewPin) "Hide PIN" else "Show PIN"
                            )
                        }
                    },
                    visualTransformation = if (showNewPin) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryOrangeGradient,
                        unfocusedBorderColor = Color(0xFFD1D5DB)
                    )
                )
                
                // Confirm PIN Field
                OutlinedTextField(
                    value = confirmPin,
                    onValueChange = { 
                        if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                            confirmPin = it
                            errorMessage = ""
                        }
                    },
                    label = { Text("Confirm New PIN") },
                    placeholder = { Text("Re-enter 4-digit PIN") },
                    leadingIcon = {
                        Icon(Icons.Default.Pin, contentDescription = "Confirm PIN")
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { showConfirmPin = !showConfirmPin }
                        ) {
                            Icon(
                                if (showConfirmPin) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (showConfirmPin) "Hide PIN" else "Show PIN"
                            )
                        }
                    },
                    visualTransformation = if (showConfirmPin) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryOrangeGradient,
                        unfocusedBorderColor = Color(0xFFD1D5DB)
                    )
                )
                
                // Error Message
                if (errorMessage.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFEE2E2) // Light red background
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Error,
                                contentDescription = "Error",
                                tint = Color(0xFFDC2626),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = errorMessage,
                                fontSize = 14.sp,
                                color = Color(0xFFDC2626)
                            )
                        }
                    }
                }
                
                // Success Message
                if (successMessage.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFD1FAE5) // Light green background
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = "Success",
                                tint = Color(0xFF059669),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = successMessage,
                                fontSize = 14.sp,
                                color = Color(0xFF059669)
                            )
                        }
                    }
                }
                
                // PIN Security Tips
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFEFF6FF) // Light blue background
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Lightbulb,
                                contentDescription = "Tips",
                                tint = Color(0xFF3B82F6),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "PIN Security Tips",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF3B82F6)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "• Use a unique 4-digit PIN\n• Avoid obvious patterns like 1234\n• Don't share your PIN with anyone\n• Change your PIN regularly",
                            fontSize = 12.sp,
                            color = Color(0xFF374151),
                            lineHeight = 16.sp
                        )
                    }
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Change PIN Button
                Button(
                    onClick = {
                        when {
                            currentPin.isEmpty() -> {
                                errorMessage = "Please enter your current PIN"
                            }
                            currentPin.length != 4 -> {
                                errorMessage = "Current PIN must be 4 digits"
                            }
                            newPin.isEmpty() -> {
                                errorMessage = "Please enter a new PIN"
                            }
                            newPin.length != 4 -> {
                                errorMessage = "New PIN must be 4 digits"
                            }
                            newPin == currentPin -> {
                                errorMessage = "New PIN must be different from current PIN"
                            }
                            confirmPin.isEmpty() -> {
                                errorMessage = "Please confirm your new PIN"
                            }
                            newPin != confirmPin -> {
                                errorMessage = "New PIN and confirmation do not match"
                            }
                            else -> {
                                // Simulate PIN change
                                isLoading = true
                                errorMessage = ""
                                
                                // Simulate API call delay
                                kotlinx.coroutines.GlobalScope.launch {
                                    kotlinx.coroutines.delay(2000)
                                    isLoading = false
                                    successMessage = "PIN changed successfully!"
                                    
                                    // Auto navigate back after success
                                    kotlinx.coroutines.delay(2000)
                                    onPinChanged()
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !isLoading && currentPin.length == 4 && newPin.length == 4 && confirmPin.length == 4,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryOrangeGradient
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text(
                            text = "Change PIN",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChangePinScreenPreview() {
    SmartCitizenClubTheme {
        ChangePinScreen(
            onBackClick = {},
            onPinChanged = {}
        )
    }
}
