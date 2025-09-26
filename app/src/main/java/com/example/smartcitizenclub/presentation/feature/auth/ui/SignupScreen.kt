package com.example.smartcitizenclub.presentation.feature.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .let { modifier ->
                if (isKeyboardOpen) {
                    modifier.verticalScroll(scrollState)
                } else {
                    modifier
                }
            }
            .padding(24.dp)
            .imePadding(), // Add padding for keyboard
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (isKeyboardOpen) Arrangement.Top else Arrangement.Center
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
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // App Title
            Text(
                text = "Smart Citizen Club",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                letterSpacing = 0.5.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Join our community",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                letterSpacing = 0.3.sp
            )
            
            Spacer(modifier = Modifier.height(20.dp))
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
            
            Spacer(modifier = Modifier.height(16.dp))
        }
        
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
            enabled = !isLoading
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Mobile Number Field
        OutlinedTextField(
            value = mobileNumber,
            onValueChange = { newValue ->
                // Allow only digits and limit to 11 characters
                if (newValue.all { it.isDigit() } && newValue.length <= 11) {
                    mobileNumber = newValue
                }
            },
            label = { Text("Mobile Number") },
            leadingIcon = {
                Icon(Icons.Default.Phone, contentDescription = "Mobile Number")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            interactionSource = mobileInteractionSource,
            singleLine = true,
            enabled = !isLoading,
            placeholder = { Text("01XXXXXXXXX (11 digits)") }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Password")
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            interactionSource = passwordInteractionSource,
            singleLine = true,
            enabled = !isLoading
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Confirm Password Field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Confirm Password")
            },
            trailingIcon = {
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password"
                    )
                }
            },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            interactionSource = confirmPasswordInteractionSource,
            singleLine = true,
            enabled = !isLoading,
            isError = confirmPassword.isNotEmpty() && password != confirmPassword,
            supportingText = if (confirmPassword.isNotEmpty() && password != confirmPassword) {
                { Text("Passwords do not match") }
            } else null
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Error Message
        if (errorMessage != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = errorMessage!!,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Sign Up Button with Gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    brush = OrangeGradient,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Button(
                onClick = {
                    keyboardController?.hide() // Hide keyboard when signup is clicked
                    authViewModel.clearError()
                    if (password == confirmPassword) {
                        authViewModel.signup(mobileNumber, password, name)
                    }
                },
                modifier = Modifier.fillMaxSize(),
                enabled = !isLoading && 
                         name.isNotBlank() && 
                         mobileNumber.isNotBlank() && 
                         password.isNotBlank() && 
                         confirmPassword.isNotBlank() &&
                         password == confirmPassword,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color.Transparent
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = androidx.compose.ui.graphics.Color.White
                    )
                } else {
                    Text(
                        text = "Create Account",
                        fontSize = 18.sp,
                        color = androidx.compose.ui.graphics.Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Sign In Link
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already have an account? ",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(
                onClick = {
                    keyboardController?.hide() // Hide keyboard when navigating
                    onNavigateToLogin()
                },
                enabled = !isLoading
            ) {
                Text(
                    text = "Sign In",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
                )
            }
        }
        
        // Dynamic bottom spacing based on keyboard state
        Spacer(modifier = Modifier.height(if (isKeyboardOpen) 10.dp else 0.dp))
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
