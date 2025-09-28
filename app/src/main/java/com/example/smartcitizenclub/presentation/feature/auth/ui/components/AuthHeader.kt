package com.example.smartcitizenclub.presentation.feature.auth.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.R

@Composable
fun AuthHeader(
    isKeyboardOpen: Boolean,
    tagline: String,
    modifier: Modifier = Modifier
) {
    // Standard spacing for all devices
    Spacer(modifier = Modifier.height(40.dp))
    
    // App Logo - Always visible
    Image(
        painter = painterResource(id = R.drawable.smart_citizen_logo),
        contentDescription = "Smart Citizen Club Logo",
        modifier = Modifier
            .size(120.dp)
            .padding(vertical = 8.dp)
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    
    // App Title
    Text(
        text = "Smart Citizen Club",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
        letterSpacing = 0.5.sp
    )
    
    Spacer(modifier = Modifier.height(8.dp))
    
    // Subtitle
    Text(
        text = tagline,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        letterSpacing = 0.3.sp
    )
    
    Spacer(modifier = Modifier.height(24.dp))
}
