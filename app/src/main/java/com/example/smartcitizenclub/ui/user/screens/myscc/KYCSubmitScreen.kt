package com.example.smartcitizenclub.ui.user.screens.myscc

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KYCSubmitScreen(
    onBackClick: () -> Unit,
    onKYCSubmitted: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var nidNumber by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }
    
    // Document upload states
    var nidFrontUploaded by remember { mutableStateOf(false) }
    var nidBackUploaded by remember { mutableStateOf(false) }
    var selfieUploaded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with Red Background (from logo)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE53E3E)) // Red from logo
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
                        text = "Submit KYC",
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
                    .verticalScroll(rememberScrollState())
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
                            Icons.Default.Info,
                            contentDescription = "Info",
                            tint = Color(0xFFF59E0B), // Gold from logo
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Please provide accurate information and clear documents for KYC verification",
                            fontSize = 14.sp,
                            color = Color(0xFF374151),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                // Personal Information Section
                Text(
                    text = "Personal Information",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF59E0B) // Gold from logo
                )
                
                // Full Name
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { 
                        fullName = it
                        errorMessage = ""
                    },
                    label = { Text("Full Name") },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = "Full Name")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF10B981), // Green from logo
                        unfocusedBorderColor = Color(0xFFD1D5DB)
                    )
                )
                
                // NID Number
                OutlinedTextField(
                    value = nidNumber,
                    onValueChange = { 
                        nidNumber = it
                        errorMessage = ""
                    },
                    label = { Text("NID Number") },
                    leadingIcon = {
                        Icon(Icons.Default.CreditCard, contentDescription = "NID Number")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF10B981), // Green from logo
                        unfocusedBorderColor = Color(0xFFD1D5DB)
                    )
                )
                
                // Date of Birth
                OutlinedTextField(
                    value = dateOfBirth,
                    onValueChange = { 
                        dateOfBirth = it
                        errorMessage = ""
                    },
                    label = { Text("Date of Birth (DD/MM/YYYY)") },
                    leadingIcon = {
                        Icon(Icons.Default.CalendarToday, contentDescription = "Date of Birth")
                    },
                    placeholder = { Text("DD/MM/YYYY") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF10B981), // Green from logo
                        unfocusedBorderColor = Color(0xFFD1D5DB)
                    )
                )
                
                // Address
                OutlinedTextField(
                    value = address,
                    onValueChange = { 
                        address = it
                        errorMessage = ""
                    },
                    label = { Text("Address") },
                    leadingIcon = {
                        Icon(Icons.Default.LocationOn, contentDescription = "Address")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    enabled = !isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF10B981), // Green from logo
                        unfocusedBorderColor = Color(0xFFD1D5DB)
                    )
                )
                
                
                // Document Upload Section
                Text(
                    text = "Document Upload",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF59E0B) // Gold from logo
                )
                
                // NID Front Upload
                DocumentUploadCard(
                    title = "NID Front Side",
                    isUploaded = nidFrontUploaded,
                    onUploadClick = { nidFrontUploaded = true }
                )
                
                // NID Back Upload
                DocumentUploadCard(
                    title = "NID Back Side",
                    isUploaded = nidBackUploaded,
                    onUploadClick = { nidBackUploaded = true }
                )
                
                // Selfie Upload
                DocumentUploadCard(
                    title = "Selfie with NID",
                    isUploaded = selfieUploaded,
                    onUploadClick = { selfieUploaded = true }
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
                                tint = Color(0xFF10B981), // Green from logo
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = successMessage,
                                fontSize = 14.sp,
                                color = Color(0xFF10B981)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Submit Button
                Button(
                    onClick = {
                        if (fullName.isEmpty()) {
                            errorMessage = "Please enter your full name"
                        } else if (nidNumber.isEmpty()) {
                            errorMessage = "Please enter your NID number"
                        } else if (dateOfBirth.isEmpty()) {
                            errorMessage = "Please enter your date of birth"
                        } else if (address.isEmpty()) {
                            errorMessage = "Please enter your address"
                        } else if (!nidFrontUploaded || !nidBackUploaded || !selfieUploaded) {
                            errorMessage = "Please upload all required documents"
                        } else {
                            // Simulate KYC submission
                            isLoading = true
                            errorMessage = ""
                            successMessage = "KYC submitted successfully! We will review your documents within 24-48 hours."
                            onKYCSubmitted()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !isLoading && fullName.isNotEmpty() && nidNumber.isNotEmpty() && 
                             dateOfBirth.isNotEmpty() && address.isNotEmpty() && 
                             nidFrontUploaded && nidBackUploaded && selfieUploaded,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE53E3E) // Red from logo
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
                            text = "Submit KYC",
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

@Composable
private fun DocumentUploadCard(
    title: String,
    isUploaded: Boolean,
    onUploadClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onUploadClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isUploaded) 
                Color(0xFFD1FAE5) // Light green when uploaded
            else 
                Color(0xFFF3F4F6) // Light gray when not uploaded
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                if (isUploaded) Icons.Default.CheckCircle else Icons.Default.CloudUpload,
                contentDescription = title,
                tint = if (isUploaded) 
                    Color(0xFF10B981) // Green from logo
                else 
                    Color(0xFF6B7280),
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isUploaded) 
                        Color(0xFF10B981) // Green from logo
                    else 
                        Color(0xFF374151)
                )
                Text(
                    text = if (isUploaded) "Uploaded successfully" else "Tap to upload",
                    fontSize = 14.sp,
                    color = if (isUploaded) 
                        Color(0xFF10B981) // Green from logo
                    else 
                        Color(0xFF6B7280)
                )
            }
            
            if (!isUploaded) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Upload",
                    tint = Color(0xFF6B7280),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KYCSubmitScreenPreview() {
    SmartCitizenClubTheme {
        KYCSubmitScreen(
            onBackClick = {},
            onKYCSubmitted = {}
        )
    }
}
