package com.example.smartcitizenclub.presentation.feature.recharge.ui

import android.Manifest
import android.content.pm.PackageManager
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.feature.sendmoney.ui.Contact
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MobileRechargeScreen(
    onBackClick: () -> Unit,
    onNumberEntered: (String) -> Unit = {}
) {
    var numberInput by remember { mutableStateOf("") }
    var savedNumbers by remember { mutableStateOf<List<Contact>>(emptyList()) }
    
    val context = LocalContext.current
    
    // Sample recent numbers
    val recentNumbers = remember {
        listOf(
            Contact("1", "01741736354", "01741736354", isRecent = true),
            Contact("2", "01912345678", "01912345678", isRecent = true)
        )
    }
    
    // Function to load saved numbers from SharedPreferences
    fun loadSavedNumbers(): List<Contact> {
        val prefs: SharedPreferences = context.getSharedPreferences("mobile_recharge_saved", android.content.Context.MODE_PRIVATE)
        val savedNumbersJson = prefs.getString("saved_numbers", "[]")
        val savedNumbersList = mutableListOf<Contact>()
        
        try {
            val jsonArray = JSONArray(savedNumbersJson)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val contact = Contact(
                    id = jsonObject.getString("id"),
                    name = jsonObject.getString("name"),
                    phoneNumber = jsonObject.getString("phoneNumber"),
                    isRecent = false,
                    isFavorite = true
                )
                savedNumbersList.add(contact)
            }
        } catch (e: Exception) {
            // If there's an error, return empty list
        }
        
        return savedNumbersList
    }
    
    // Function to save a number to SharedPreferences
    fun saveNumber(phoneNumber: String, name: String = phoneNumber) {
        val prefs: SharedPreferences = context.getSharedPreferences("mobile_recharge_saved", android.content.Context.MODE_PRIVATE)
        val currentSaved = loadSavedNumbers().toMutableList()
        
        // Check if number already exists
        val existingIndex = currentSaved.indexOfFirst { it.phoneNumber == phoneNumber }
        if (existingIndex != -1) {
            // Update existing
            currentSaved[existingIndex] = Contact(
                id = currentSaved[existingIndex].id,
                name = name,
                phoneNumber = phoneNumber,
                isRecent = false,
                isFavorite = true
            )
        } else {
            // Add new
            val newContact = Contact(
                id = System.currentTimeMillis().toString(),
                name = name,
                phoneNumber = phoneNumber,
                isRecent = false,
                isFavorite = true
            )
            currentSaved.add(newContact)
        }
        
        // Save to SharedPreferences
        val jsonArray = JSONArray()
        currentSaved.forEach { contact ->
            val jsonObject = JSONObject()
            jsonObject.put("id", contact.id)
            jsonObject.put("name", contact.name)
            jsonObject.put("phoneNumber", contact.phoneNumber)
            jsonArray.put(jsonObject)
        }
        
        prefs.edit().putString("saved_numbers", jsonArray.toString()).apply()
        
        // Update the state
        savedNumbers = currentSaved
    }
    
    // Load saved numbers from device storage
    LaunchedEffect(Unit) {
        savedNumbers = loadSavedNumbers()
    }
    
    fun handleNumberInput(number: String) {
        if (number.length == 11) {
            // Save the number to saved numbers when used
            saveNumber(number)
            onNumberEntered(number)
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Orange Background
        TopAppBar(
            title = {
                Text(
                    text = "Mobile Recharge",
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
            
            // Phone Number Input Section
            item {
                Column {
                    Text(
                        text = "Mobile Number",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    OutlinedTextField(
                        value = numberInput,
                        onValueChange = { newValue ->
                            if (newValue.length <= 11 && newValue.all { it.isDigit() }) {
                                numberInput = newValue
                            }
                        },
                        placeholder = {
                            Text(
                                text = "01XXXXXXXXX",
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
                                    handleNumberInput(numberInput)
                                },
                                enabled = numberInput.length == 11
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (numberInput.length == 11) PrimaryOrangeGradient else Color.Gray.copy(alpha = 0.3f)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.ArrowForward,
                                        contentDescription = "Continue",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    )
                }
            }
            
            // Recent Numbers Section
            item {
                Column {
                    Text(
                        text = "Recent",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    recentNumbers.forEach { contact ->
                        ContactItem(
                            contact = contact,
                            onClick = { handleNumberInput(contact.phoneNumber) }
                        )
                        
                        if (contact != recentNumbers.last()) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            
            // Saved Numbers Section
            item {
                Column {
                    Text(
                        text = "Saved Numbers",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    savedNumbers.forEach { contact ->
                        ContactItem(
                            contact = contact,
                            onClick = { handleNumberInput(contact.phoneNumber) }
                        )
                        
                        if (contact != savedNumbers.last()) {
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
                .background(PrimaryOrangeGradient.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Phone,
                contentDescription = "Contact",
                tint = PrimaryOrangeGradient,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Contact Info
        Column {
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
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Arrow icon
        Icon(
            Icons.Default.ArrowForward,
            contentDescription = "Select",
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MobileRechargeScreenPreview() {
    SmartCitizenClubTheme {
        MobileRechargeScreen(
            onBackClick = {},
            onNumberEntered = {}
        )
    }
}
