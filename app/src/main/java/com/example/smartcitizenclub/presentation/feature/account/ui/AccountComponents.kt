package com.example.smartcitizenclub.presentation.feature.account.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

// Data class for sub-accounts
data class SubAccount(
    val id: String,
    val name: String,
    val number: String,
    val balance: Double,
    val groupId: String? = null,
    val referralCode: String? = null
)

// Color constants for account components
private object AccountColors {
    val Red = Color(0xFFE53E3E)
    val Gold = Color(0xFFF59E0B)
    val Green = Color(0xFF10B981)
    val Blue = Color(0xFF3B82F6)
    val Purple = Color(0xFF8B5CF6)
    val Orange = Color(0xFFF97316)
    val Gray = Color(0xFF6B7280)
    val LightGray = Color(0xFFF3F4F6)
    val DarkGray = Color(0xFF374151)
}

// Sub Account Card Composable
@Composable
fun SubAccountCard(
    account: SubAccount, 
    onClick: () -> Unit, 
    onLongPress: (SubAccount) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .pointerInput(account) {
                detectTapGestures(
                    onLongPress = { onLongPress(account) }
                )
            }, 
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ), 
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        AccountColors.Blue.copy(alpha = 0.1f), 
                        CircleShape
                    ), 
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.AccountBalance,
                    contentDescription = "Account",
                    tint = AccountColors.Blue,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = account.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )
                Text(
                    text = account.number, 
                    fontSize = 14.sp, 
                    color = Color(0xFF6B7280)
                )
                if (account.referralCode != null) {
                    Text(
                        text = "Ref: ${account.referralCode}",
                        fontSize = 12.sp,
                        color = AccountColors.Blue,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Text(
                text = "৳${String.format("%.0f", account.balance)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF059669)
            )
        }
    }
}

// Active Account Card (Mastercard Style)
@Composable
fun ActiveAccountCard(
    account: SubAccount
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFEB001B), // Mastercard Red
                            Color(0xFFF79E1B)  // Mastercard Orange
                        ), 
                        start = Offset(0f, 0f), 
                        end = Offset(1000f, 1000f)
                    ), 
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Top section with chip
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Chip
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                Color.White.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                                .background(
                                    Color.White.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(2.dp)
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Account number (like card number)
                Text(
                    text = account.number.replace(" ", "").chunked(4).joinToString(" "),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Account name
                Text(
                    text = account.name.uppercase(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Bottom section with balance
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "BALANCE",
                            fontSize = 10.sp,
                            color = Color.White.copy(alpha = 0.7f),
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "৳${String.format("%.2f", account.balance)}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    // Active indicator
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Active",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "ACTIVE",
                            fontSize = 10.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }
        }
    }
}

// Add Account Dialog Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountDialog(
    showDialog: Boolean, 
    onDismiss: () -> Unit, 
    onAdd: (String, String, String?, String?) -> Unit
) {
    if (showDialog) {
        var accountName by remember { mutableStateOf("") }
        var accountNumber by remember { mutableStateOf("") }
        var selectedGroupId by remember { mutableStateOf<String?>(null) }

        AlertDialog(
            onDismissRequest = onDismiss, 
            title = {
                Text(
                    text = "Add New Account", 
                    fontSize = 18.sp, 
                    fontWeight = FontWeight.Bold
                )
            }, 
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = accountName,
                        onValueChange = { accountName = it },
                        label = { Text("Account Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = accountNumber,
                        onValueChange = { accountNumber = it },
                        label = { Text("Account / Referral No") },
                        placeholder = { Text("Account/Referral No") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Select Group",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = AccountColors.DarkGray
                    )

                    // Group Selection - Reusable component
                    GroupSelection(
                        selectedGroupId = selectedGroupId,
                        onGroupSelected = { selectedGroupId = it }
                    )
                }
            }, 
            confirmButton = {
                TextButton(
                    onClick = {
                        onAdd(accountName, accountNumber.ifBlank { "" }, null, selectedGroupId)
                        onDismiss()
                    }, 
                    enabled = accountName.isNotBlank()
                ) {
                    Text("Add Account")
                }
            }, 
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}

// Floating Action Menu Composable
@Composable
fun FloatingActionMenu(
    showMenu: Boolean,
    onAddAccount: () -> Unit,
    onAddGroup: () -> Unit,
    onCloseMenu: () -> Unit
) {
    if (showMenu) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.BottomEnd),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                modifier = Modifier.padding(end = 20.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Add Account Option
                Card(
                    modifier = Modifier.clickable {
                        onCloseMenu()
                        onAddAccount()
                    }, 
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ), 
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.PersonAdd,
                            contentDescription = "Add Account",
                            tint = AccountColors.Blue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Add Account",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = AccountColors.DarkGray
                        )
                    }
                }

                // Add Group Option
                Card(
                    modifier = Modifier.clickable {
                        onCloseMenu()
                        onAddGroup()
                    }, 
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ), 
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.GroupAdd,
                            contentDescription = "Add Group",
                            tint = AccountColors.Green,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Add Group",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = AccountColors.DarkGray
                        )
                    }
                }
            }
        }
    }
}

// Context Menu for Account Actions
@Composable
fun AccountContextMenu(
    showMenu: Boolean,
    selectedAccount: SubAccount?,
    onDismiss: () -> Unit,
    onChangeGroup: () -> Unit
) {
    if (showMenu && selectedAccount != null) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            }, 
            title = {
                Text(
                    text = selectedAccount.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }, 
            text = {
                Text(
                    text = "Choose an action for this account",
                    fontSize = 14.sp,
                    color = AccountColors.DarkGray
                )
            }, 
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                        onChangeGroup()
                    }
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Change Group",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Change Group")
                }
            }, 
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SubAccountCardPreview() {
    SmartCitizenClubTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SubAccountCard(
                account = SubAccount("1", "Personal Savings", "ACC-002", 25000.0, "1", "REF002"),
                onClick = {},
                onLongPress = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActiveAccountCardPreview() {
    SmartCitizenClubTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ActiveAccountCard(
                account = SubAccount("1", "Main Account", "ACC-001", 15000.0, null, "REF001")
            )
        }
    }
}
