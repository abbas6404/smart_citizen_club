package com.example.smartcitizenclub.presentation.feature.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.material3.*
import com.example.smartcitizenclub.presentation.theme.OrangeGradient
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
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
fun LoginScreen(
    authViewModel: AuthViewModel,
    onNavigateToSignup: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var mobileNumber by remember { mutableStateOf("01741736354") }
    var password by remember { mutableStateOf("user123") }
    var passwordVisible by remember { mutableStateOf(false) }
    
    val isLoading by authViewModel.isLoading.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    
    // Focus tracking for keyboard detection
    val mobileInteractionSource = remember { MutableInteractionSource() }
    val passwordInteractionSource = remember { MutableInteractionSource() }
    val isMobileFocused by mobileInteractionSource.collectIsFocusedAsState()
    val isPasswordFocused by passwordInteractionSource.collectIsFocusedAsState()
    
    // Determine if keyboard is open (when any field is focused)
    val isKeyboardOpen = isMobileFocused || isPasswordFocused
    
    // Set default credentials
    LaunchedEffect(Unit) {
        mobileNumber = "01741736354"
        password = "user123"
    }
    
    // Handle authentication state changes
    LaunchedEffect(authViewModel.authState) {
        authViewModel.authState.collect { authState ->
            if (authState is AuthState.Authenticated) {
                onLoginSuccess()
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
                .padding(20.dp)
                .imePadding(), // Add padding for keyboard
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (isKeyboardOpen) Arrangement.Top else Arrangement.Center
        ) {
            // App Header with Logo and Title
            AuthHeader(
                isKeyboardOpen = isKeyboardOpen,
                tagline = "Sign in to your account"
            )
            
            // Login Form Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(20.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    // Mobile Number Field
                    MobileNumberField(
                        value = mobileNumber,
                        onValueChange = { mobileNumber = it },
                        modifier = Modifier,
                        enabled = !isLoading,
                        interactionSource = mobileInteractionSource,
                        useLabel = false
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Password Field
                    PasswordField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier,
                        enabled = !isLoading,
                        interactionSource = passwordInteractionSource,
                        useLabel = false,
                        label = "Password",
                        placeholder = "Enter your password"
                    )
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Error Message
                    ErrorMessageCard(errorMessage = errorMessage)
                    
                    // Login Button with Gradient
                    GradientButton(
                        text = "Login",
                        onClick = {
                            keyboardController?.hide()
                            authViewModel.clearError()
                            authViewModel.login(mobileNumber, password)
                        },
                        enabled = !isLoading && mobileNumber.isNotBlank() && password.isNotBlank(),
                        isLoading = isLoading
                    )
                }
            }
            
            // Sign Up Link
            AuthNavigationLink(
                questionText = "Not registered yet?",
                linkText = "Open New Account",
                onClick = {
                    keyboardController?.hide()
                    onNavigateToSignup()
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
fun LoginScreenPreview() {
    var mobileNumber by remember { mutableStateOf("+880 1234567890") }
    var password by remember { mutableStateOf("user123") }
    
    SmartCitizenClubTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Welcome Header
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 30.dp)
                ) {
                    // App Logo placeholder
                    Text(
                        text = "🏛️",
                        fontSize = 80.sp
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Welcome",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Text(
                        text = "Sign in to continue",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                
                // Login Form Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(20.dp)
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        // Mobile Number Field
                        Text(
                            text = "Mobile Number",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        OutlinedTextField(
                            value = mobileNumber,
                            onValueChange = { },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Phone,
                                    contentDescription = "Mobile Number",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            placeholder = { Text("+880 XXXX XXXXXX") },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        // Password Field
                        Text(
                            text = "Password",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        OutlinedTextField(
                            value = "••••••••",
                            onValueChange = { },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock, 
                                    contentDescription = "Password",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { }) {
                                    Icon(
                                        imageVector = Icons.Default.VisibilityOff,
                                        contentDescription = "Show password",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            placeholder = { Text("Enter your password") },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Sign In Button
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "Login",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Sign Up Link
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Not registered yet?",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Open New Account",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
