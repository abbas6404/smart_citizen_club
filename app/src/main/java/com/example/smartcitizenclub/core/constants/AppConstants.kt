package com.example.smartcitizenclub.core.constants

/**
 * Application-wide constants
 */
object AppConstants {
    
    // Brand Colors
    const val PRIMARY_COLOR = "#FF4500"        // Vibrant Orange-Red
    const val PRIMARY_RED = "#EB001B"          // Mastercard Red
    const val PRIMARY_ORANGE = "#F79E1B"       // Mastercard Orange
    const val ACCENT_GREEN = "#10B981"         // Success Green
    const val ACCENT_GOLD = "#F59E0B"         // Warning Gold
    
    // API Configuration
    const val BASE_URL = "https://api.smartcitizenclub.com/"
    const val API_TIMEOUT = 30L
    
    // Database Configuration
    const val DATABASE_NAME = "smart_citizen_club.db"
    const val DATABASE_VERSION = 1
    
    // SharedPreferences Keys
    const val PREFS_NAME = "smart_citizen_club_prefs"
    const val KEY_USER_TOKEN = "user_token"
    const val KEY_USER_ID = "user_id"
    const val KEY_LANGUAGE = "language"
    const val KEY_THEME = "theme"
    
    // Validation Constants
    const val MIN_PASSWORD_LENGTH = 6
    const val MAX_PASSWORD_LENGTH = 20
    const val PHONE_NUMBER_LENGTH = 11
    
    // Transaction Constants
    const val MIN_TRANSACTION_AMOUNT = 1.0
    const val MAX_TRANSACTION_AMOUNT = 100000.0
    const val TRANSACTION_FEE_PERCENTAGE = 1.5
    
    // QR Code Configuration
    const val QR_CODE_SIZE = 512
    const val QR_CODE_FORMAT = "UTF-8"
    
    // Pagination
    const val DEFAULT_PAGE_SIZE = 20
    const val MAX_PAGE_SIZE = 100
}
