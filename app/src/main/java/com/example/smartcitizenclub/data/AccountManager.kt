package com.example.smartcitizenclub.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Global Account Manager
 * Manages the currently active account and caches it for persistence
 */
object AccountManager {
    private val _activeAccount = MutableStateFlow<SubAccount?>(null)
    val activeAccount: StateFlow<SubAccount?> = _activeAccount.asStateFlow()
    
    private lateinit var prefs: SharedPreferences
    private val gson = Gson()
    private const val PREFS_NAME = "account_manager_prefs"
    private const val KEY_ACTIVE_ACCOUNT = "active_account"
    private const val KEY_ACTIVE_ACCOUNT_ID = "active_account_id"
    
    /**
     * Initialize the AccountManager with context
     */
    fun initialize(context: Context) {
        if (!::prefs.isInitialized) {
            prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            loadCachedAccount()
        }
    }
    
    /**
     * Set the active account and cache it
     */
    fun setActiveAccount(account: SubAccount) {
        _activeAccount.value = account
        cacheActiveAccount(account)
    }
    
    /**
     * Get the currently active account
     */
    fun getActiveAccount(): SubAccount? = _activeAccount.value
    
    /**
     * Get the currently active account ID
     */
    fun getActiveAccountId(): String? = _activeAccount.value?.id
    
    /**
     * Check if a specific account is the active one
     */
    fun isActiveAccount(account: SubAccount): Boolean {
        return _activeAccount.value?.id == account.id
    }
    
    /**
     * Clear the active account
     */
    fun clearActiveAccount() {
        _activeAccount.value = null
        prefs.edit().remove(KEY_ACTIVE_ACCOUNT).remove(KEY_ACTIVE_ACCOUNT_ID).apply()
    }
    
    /**
     * Cache the active account to SharedPreferences
     */
    private fun cacheActiveAccount(account: SubAccount) {
        try {
            val accountJson = gson.toJson(account)
            prefs.edit()
                .putString(KEY_ACTIVE_ACCOUNT, accountJson)
                .putString(KEY_ACTIVE_ACCOUNT_ID, account.id)
                .apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Load cached active account from SharedPreferences
     */
    private fun loadCachedAccount() {
        try {
            val accountJson = prefs.getString(KEY_ACTIVE_ACCOUNT, null)
            if (accountJson != null) {
                val account = gson.fromJson(accountJson, SubAccount::class.java)
                _activeAccount.value = account
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Update active account from a list of accounts (useful when syncing from server)
     */
    fun updateActiveAccountFromList(accounts: List<SubAccount>) {
        val cachedId = prefs.getString(KEY_ACTIVE_ACCOUNT_ID, null)
        if (cachedId != null) {
            val account = accounts.find { it.id == cachedId }
            if (account != null) {
                setActiveAccount(account)
            }
        }
    }
    
    /**
     * Sync active account with user's current sub account
     */
    fun syncWithUser(user: User) {
        // First, try to get from user's currentSubAccount
        user.currentSubAccount?.let {
            setActiveAccount(it)
            return
        }
        
        // If not set, try to load from cache
        if (_activeAccount.value == null) {
            loadCachedAccount()
        }
        
        // If still null, set first available account
        if (_activeAccount.value == null && user.subAccounts.isNotEmpty()) {
            setActiveAccount(user.subAccounts.first())
        }
    }
}

