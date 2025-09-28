package com.example.smartcitizenclub.presentation.feature.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.shadow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import com.example.smartcitizenclub.presentation.theme.OrangeGradient
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import com.example.smartcitizenclub.R
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.example.smartcitizenclub.presentation.feature.auth.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    authViewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit,
    onSignupSuccess: () -> Unit
) {
    var name by remember { mutableStateOf("Demo User") }
    var mobileNumber by remember { mutableStateOf("01741736354") }
    var password by remember { mutableStateOf("user123") }
    var confirmPassword by remember { mutableStateOf("user123") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    
    val isLoading by authViewModel.isLoading.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    
    // Focus tracking for keyboard detection
    val nameInteractionSource = remember { MutableInteractionSource() }
    val mobileInteractionSource = remember { MutableInteractionSource() }
    val passwordInteractionSource = remember { MutableInteractionSource() }
    val confirmPasswordInteractionSource = remember { MutableInteractionSource() }
    
    val isNameFocused by nameInteractionSource.collectIsFocusedAsState()
    val isMobileFocused by mobileInteractionSource.collectIsFocusedAsState()
    val isPasswordFocused by passwordInteractionSource.collectIsFocusedAsState()
    val isConfirmPasswordFocused by confirmPasswordInteractionSource.collectIsFocusedAsState()
    
    // Determine if keyboard is open (when any field is focused)
    val isKeyboardOpen = isNameFocused || isMobileFocused || isPasswordFocused || isConfirmPasswordFocused
    
    // Handle authentication state changes
    LaunchedEffect(authViewModel.authState) {
        authViewModel.authState.collect { authState ->
            if (authState is AuthState.Authenticated) {
                onSignupSuccess()
            }
        }
    }
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .imePadding(), // Add padding for keyboard
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Header with Logo and Title
            AuthHeader(
                isKeyboardOpen = isKeyboardOpen,
                tagline = "Join our community"
            )
            
            // Signup Form Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Name Field
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Full Name") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = "Name")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.fillMaxWidth(),
                        interactionSource = nameInteractionSource,
                        singleLine = true,
                        enabled = !isLoading,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Mobile Number Field
                    MobileNumberField(
                        value = mobileNumber,
                        onValueChange = { mobileNumber = it },
                        modifier = Modifier,
                        enabled = !isLoading,
                        interactionSource = mobileInteractionSource,
                        useLabel = true
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Password Field
                    PasswordField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier,
                        enabled = !isLoading,
                        interactionSource = passwordInteractionSource,
                        useLabel = true,
                        label = "Password",
                        placeholder = "Enter your password"
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Confirm Password Field
                    PasswordField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        modifier = Modifier,
                        enabled = !isLoading,
                        interactionSource = confirmPasswordInteractionSource,
                        useLabel = true,
                        label = "Confirm Password",
                        placeholder = "Confirm your password"
                    )
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Error Message
                    ErrorMessageCard(errorMessage = errorMessage)
                    
                    // Sign Up Button with Gradient
                    GradientButton(
                        text = "Create Account",
                        onClick = {
                            keyboardController?.hide()
                            authViewModel.clearError()
                            if (password == confirmPassword) {
                                authViewModel.signup(mobileNumber, password, name)
                            }
                        },
                        enabled = !isLoading && 
                                 name.isNotBlank() && 
                                 mobileNumber.isNotBlank() && 
                                 password.isNotBlank() && 
                                 confirmPassword.isNotBlank() &&
                                 password == confirmPassword,
                        isLoading = isLoading
                    )
                }
            }
            
            // Sign In Link
            AuthNavigationRow(
                questionText = "Already have an account? ",
                linkText = "Sign In",
                onClick = {
                    keyboardController?.hide()
                    onNavigateToLogin()
                },
                enabled = !isLoading
            )
            
            // Dynamic bottom spacing - reduced for better visibility
            Spacer(modifier = Modifier.height(if (isKeyboardOpen) 30.dp else 10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    SmartCitizenClubTheme {
        // Preview with mock values - AuthViewModel requires context
        // This preview shows the UI layout only
    }
}
