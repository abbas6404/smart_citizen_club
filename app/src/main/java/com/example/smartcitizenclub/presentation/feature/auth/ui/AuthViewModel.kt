package com.example.smartcitizenclub.presentation.feature.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartcitizenclub.data.AuthRepositoryImpl
import com.example.smartcitizenclub.data.AuthResult
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.core.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepositoryImpl()
    
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    fun login(identifier: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            when (val result = authRepository.login(identifier, password)) {
                is Result.Success -> {
                    _authState.value = AuthState.Authenticated(result.data.user!!)
                }
                is Result.Error -> {
                    _errorMessage.value = result.exception.message
                }
                is Result.Loading -> {
                    // Handle loading state if needed
                }
            }
            _isLoading.value = false
        }
    }
    
    fun signup(identifier: String, password: String, name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val user = User(
                id = "new",
                name = name,
                phone = identifier,
                email = "$identifier@example.com"
            )
            
            when (val result = authRepository.signup(user, password)) {
                is Result.Success -> {
                    _authState.value = AuthState.Authenticated(result.data.user!!)
                }
                is Result.Error -> {
                    _errorMessage.value = result.exception.message
                }
                is Result.Loading -> {
                    // Handle loading state if needed
                }
            }
            _isLoading.value = false
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _authState.value = AuthState.Unauthenticated
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}

sealed class AuthState {
    object Initial : AuthState()
    object Unauthenticated : AuthState()
    data class Authenticated(val user: User) : AuthState()
}
