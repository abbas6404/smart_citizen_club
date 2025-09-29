package com.example.smartcitizenclub.presentation.feature.messages.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility functions for messaging features
 */

/**
 * Formats timestamp to relative time string
 * @param timestamp The timestamp in milliseconds
 * @return Formatted time string (now, Xm, Xh, Xd, or MMM dd)
 */
fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    
    return when {
        diff < 60000 -> "now" // Less than 1 minute
        diff < 3600000 -> "${diff / 60000}m" // Less than 1 hour
        diff < 86400000 -> "${diff / 3600000}h" // Less than 1 day
        diff < 604800000 -> "${diff / 86400000}d" // Less than 1 week
        else -> {
            val formatter = SimpleDateFormat("MMM dd", Locale.getDefault())
            formatter.format(Date(timestamp))
        }
    }
}

/**
 * Formats message timestamp to time string
 * @param timestamp The timestamp in milliseconds
 * @return Formatted time string (h:mm a)
 */
fun formatMessageTime(timestamp: Long): String {
    val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
    return formatter.format(Date(timestamp))
}

/**
 * Generates avatar text from contact name
 * @param name The contact name
 * @return First character of the name in uppercase
 */
fun generateAvatarText(name: String): String {
    return name.take(1).uppercase()
}

/**
 * Validates phone number format
 * @param phoneNumber The phone number to validate
 * @return true if valid, false otherwise
 */
fun isValidPhoneNumber(phoneNumber: String): Boolean {
    val phoneRegex = Regex("^[+]?[0-9]{10,15}$")
    return phoneRegex.matches(phoneNumber)
}

/**
 * Formats phone number with country code
 * @param countryCode The country code (e.g., "BD +880")
 * @param phoneNumber The phone number
 * @return Formatted phone number
 */
fun formatPhoneNumber(countryCode: String, phoneNumber: String): String {
    val code = countryCode.substringAfter("+")
    return "+$code$phoneNumber"
}
