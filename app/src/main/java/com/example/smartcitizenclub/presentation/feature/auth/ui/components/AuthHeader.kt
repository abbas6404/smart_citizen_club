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
    // Dynamic spacing based on keyboard state
    Spacer(modifier = Modifier.height(if (isKeyboardOpen) 20.dp else 60.dp))
    
    // App Logo and Title - Only show when keyboard is closed
    if (!isKeyboardOpen) {
        // App Logo - Clean display with perfect spacing
        Image(
            painter = painterResource(id = R.drawable.smart_citizen_logo),
            contentDescription = "Smart Citizen Club Logo",
            modifier = Modifier
                .size(180.dp)
                .padding(vertical = 8.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Smart Citizen Club",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            letterSpacing = 0.5.sp
        )
        
        Spacer(modifier = Modifier.height(6.dp))
        
        Text(
            text = tagline,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            letterSpacing = 0.3.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
    } else {
        // When keyboard is open, show a smaller title
        Text(
            text = "Smart Citizen Club",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            letterSpacing = 0.5.sp
        )
        
        Spacer(modifier = Modifier.height(12.dp))
    }
}
