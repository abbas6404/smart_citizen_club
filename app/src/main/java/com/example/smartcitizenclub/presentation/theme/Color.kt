package com.example.smartcitizenclub.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush

// Smart Citizen Club Brand Colors (Orange Gradient - Matching Account Card)
val PrimaryOrangeStart = Color(0xFFEB001B)    // Red side: #EB001B
val PrimaryOrangeEnd = Color(0xFFFF5E00)      // Orange side: rgb(255, 94, 0)
val PrimaryOrangeGradient = Color(0xFFEF3A1B) // Solid color when not using gradient: #EF3A1B


// Gradient Colors
val OrangeGradient = Brush.linearGradient(
    colors = listOf(PrimaryOrangeStart, PrimaryOrangeEnd)
)

// Light theme colors
val Primary80 = Color(0xFFFFB366)       // Lighter orange
val PrimaryGrey80 = Color(0xFFE0E0E0)   // Light grey
val Accent80 = Color(0xFF4CAF50)        // Green accent

// Dark theme colors  
val Primary40 = Color(0xFFE67E22)       // Darker orange
val PrimaryGrey40 = Color(0xFF424242)   // Dark grey
val Accent40 = Color(0xFF2E7D32)        // Dark green accent

// Legacy colors for compatibility
val Purple80 = Primary80
val PurpleGrey80 = PrimaryGrey80
val Pink80 = Accent80

val Purple40 = Primary40
val PurpleGrey40 = PrimaryGrey40
val Pink40 = Accent40