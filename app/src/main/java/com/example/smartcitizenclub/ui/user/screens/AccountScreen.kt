package com.example.smartcitizenclub.ui.user.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.ui.theme.SmartCitizenClubTheme

// Data classes for sub-accounts and groups
data class SubAccount(
    val id: String,
    val name: String,
    val number: String,
    val balance: Double,
    val groupId: String? = null,
    val referralCode: String? = null
)

data class AccountGroup(
    val id: String, val name: String, val color: Color, val icon: ImageVector
)

// Color constants from logo
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

// Sample account groups - shared data
private val sampleAccountGroups = listOf(
    AccountGroup("1", "Personal", AccountColors.Blue, Icons.Default.Person),
    AccountGroup("2", "Business", AccountColors.Green, Icons.Default.Business),
    AccountGroup("3", "Savings", AccountColors.Orange, Icons.Default.Savings),
    AccountGroup("4", "Investment", AccountColors.Purple, Icons.Default.TrendingUp),
    AccountGroup("5", "Emergency", AccountColors.Red, Icons.Default.Warning)
)

// Reusable Group Selection Component
@Composable
private fun GroupSelection(
    selectedGroupId: String?, onGroupSelected: (String?) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // No Group option
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onGroupSelected(null) },
            colors = CardDefaults.cardColors(
                containerColor = if (selectedGroupId == null) AccountColors.Green.copy(alpha = 0.1f) else Color.White
            ),
            border = if (selectedGroupId == null) BorderStroke(1.dp, AccountColors.Green) else null
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Group,
                    contentDescription = "No Group",
                    tint = if (selectedGroupId == null) AccountColors.Green else AccountColors.DarkGray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "No Group",
                    fontSize = 16.sp,
                    color = if (selectedGroupId == null) AccountColors.Green else AccountColors.DarkGray,
                    fontWeight = if (selectedGroupId == null) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        // Group options
        sampleAccountGroups.forEach { group ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onGroupSelected(group.id) },
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedGroupId == group.id) group.color.copy(alpha = 0.1f) else Color.White
                ),
                border = if (selectedGroupId == group.id) BorderStroke(1.dp, group.color) else null
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        group.icon,
                        contentDescription = group.name,
                        tint = if (selectedGroupId == group.id) group.color else AccountColors.DarkGray,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = group.name,
                        fontSize = 16.sp,
                        color = if (selectedGroupId == group.id) group.color else AccountColors.DarkGray,
                        fontWeight = if (selectedGroupId == group.id) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

// Group Card Composable
@Composable
fun GroupCard(
    group: AccountGroup,
    accounts: List<SubAccount>,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit,
    onAccountClick: (SubAccount) -> Unit,
    onAccountLongPress: (SubAccount) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleExpanded() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = group.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccountColors.DarkGray
                    )
                    Text(
                        text = "${accounts.size} accounts • ৳${
                            String.format(
                                "%.2f", accounts.sumOf { it.balance })
                        }", fontSize = 12.sp, color = AccountColors.Gray
                    )
                }

                Icon(
                    if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = AccountColors.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(4.dp))

                accounts.forEach { account ->
                    SubAccountCard(
                        account = account,
                        onClick = { onAccountClick(account) },
                        onLongPress = { onAccountLongPress(account) })

                    if (account != accounts.last()) {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

// Sub Account Card Composable
@Composable
fun SubAccountCard(
    account: SubAccount, onClick: () -> Unit, onLongPress: (SubAccount) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .pointerInput(account) {
                detectTapGestures(
                    onLongPress = { onLongPress(account) })
            }, colors = CardDefaults.cardColors(
        containerColor = Color.White
    ), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        AccountColors.Blue.copy(alpha = 0.1f), CircleShape
                    ), contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.AccountBalance,
                    contentDescription = "Account",
                    tint = AccountColors.Blue,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = account.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccountColors.DarkGray
                )
                Text(
                    text = account.number, fontSize = 14.sp, color = AccountColors.Gray
                )
                if (account.referralCode != null) {
                    Text(
                        text = "Ref: ${account.referralCode}",
                        fontSize = 10.sp,
                        color = AccountColors.Blue,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Text(
                text = "৳${String.format("%.2f", account.balance)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = AccountColors.Green
            )
        }
    }
}

// Add Account Dialog Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountDialog(
    showDialog: Boolean, onDismiss: () -> Unit, onAdd: (String, String, String?, String?) -> Unit
) {
    if (showDialog) {
        var accountName by remember { mutableStateOf("") }
        var accountNumber by remember { mutableStateOf("") }
        var selectedGroupId by remember { mutableStateOf<String?>(null) }

        // Use shared account groups data

        AlertDialog(onDismissRequest = onDismiss, title = {
            Text(
                text = "Add New Account", fontSize = 18.sp, fontWeight = FontWeight.Bold
            )
        }, text = {
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
                    onGroupSelected = { selectedGroupId = it })
            }
        }, confirmButton = {
                TextButton(
                    onClick = {
                    onAdd(accountName, accountNumber.ifBlank { "" }, null, selectedGroupId)
                    onDismiss()
                }, enabled = accountName.isNotBlank()
                ) {
                Text("Add Account")
                }
        }, dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
            }
        })
    }
}

// Add Group Dialog Composable
@Composable
fun AddGroupDialog(
    showDialog: Boolean, onDismiss: () -> Unit, onAdd: (String) -> Unit
) {
    if (showDialog) {
        var groupName by remember { mutableStateOf("") }

        AlertDialog(onDismissRequest = onDismiss, title = {
            Text(
                text = "Add New Group", fontSize = 18.sp, fontWeight = FontWeight.Bold
            )
        }, text = {
                OutlinedTextField(
                    value = groupName,
                    onValueChange = { groupName = it },
                    label = { Text("Group Name") },
                    modifier = Modifier.fillMaxWidth()
                )
        }, confirmButton = {
                TextButton(
                    onClick = {
                        onAdd(groupName)
                    onDismiss()
                }, enabled = groupName.isNotBlank()
                ) {
                Text("Add Group")
                }
        }, dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
            }
        })
    }
}

// Change Group Dialog Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeGroupDialog(
    account: SubAccount,
    accountGroups: List<AccountGroup>,
    onDismiss: () -> Unit,
    onGroupChanged: (String?) -> Unit
) {
    var selectedGroupId by remember { mutableStateOf(account.groupId) }

    AlertDialog(onDismissRequest = onDismiss, title = {
        Text(
            text = "Change Group", fontSize = 18.sp, fontWeight = FontWeight.Bold
        )
    }, text = {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Select a new group for \"${account.name}\"",
                fontSize = 14.sp,
                color = AccountColors.DarkGray
            )

            // Group Selection - Reusable component
            GroupSelection(
                selectedGroupId = selectedGroupId, onGroupSelected = { selectedGroupId = it })
        }
    }, confirmButton = {
        TextButton(
            onClick = {
                onGroupChanged(selectedGroupId)
            }) {
            Text("Change Group")
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text("Cancel")
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    user: User, onLogout: () -> Unit
) {
    var showAddAccountDialog by remember { mutableStateOf(false) }
    var showAddGroupDialog by remember { mutableStateOf(false) }
    var selectedGroupId by remember { mutableStateOf<String?>(null) }
    var expandedGroups by remember { mutableStateOf(setOf<String>()) }
    var showFloatingMenu by remember { mutableStateOf(false) }
    var showContextMenu by remember { mutableStateOf(false) }
    var selectedAccount by remember { mutableStateOf<SubAccount?>(null) }
    var showChangeGroupDialog by remember { mutableStateOf(false) }
    
    // Use shared account groups data
    val accountGroups = remember { sampleAccountGroups }
    
    val subAccounts = remember {
        listOf(
            SubAccount("1", "Main Account", "ACC-001", 15000.0, null, "REF001"),
            SubAccount("2", "Personal Savings", "ACC-002", 25000.0, "1", "REF002"),
            SubAccount("3", "Business Account", "ACC-003", 50000.0, "2", "REF003"),
            SubAccount("4", "Emergency Fund", "ACC-004", 30000.0, "5", "REF004"),
            SubAccount("5", "Investment Portfolio", "ACC-005", 75000.0, "4", "REF005"),
            SubAccount("6", "Personal Checking", "ACC-006", 15000.0, "1", "REF006"),
            SubAccount("7", "Business Savings", "ACC-007", 7000.0, "2", "REF007"),
            SubAccount("8", "Ungrouped Account", "ACC-008", 3000.0, null, null)
        )
    }

    val activeAccount = subAccounts.firstOrNull { it.id == "1" } // Main Account as active
    val groupedAccounts = subAccounts.groupBy { it.groupId ?: "" }
    val ungroupedAccounts = subAccounts.filter { it.groupId == null && it.id != "1" }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showFloatingMenu = !showFloatingMenu },
                containerColor = AccountColors.Green,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp, pressedElevation = 12.dp
                )
            ) {
                Icon(
                    if (showFloatingMenu) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = if (showFloatingMenu) "Close Menu" else "Add",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp) // Minimal spacing
        ) {
            // Active Account Card (Mastercard Style)
            activeAccount?.let { account ->
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
                                    ), start = Offset(0f, 0f), end = Offset(1000f, 1000f)
                                ), shape = RoundedCornerShape(16.dp)
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp)
                        ) {
                            // Top section with chip and Mastercard logo
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

                // Groups Section
                Text(
                    text = "Account Groups",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccountColors.DarkGray
                )

                // Groups List
                accountGroups.forEach { group ->
                    val groupAccounts = groupedAccounts[group.id] ?: emptyList()
                    GroupCard(
                            group = group,
                        accounts = groupAccounts,
                        isExpanded = expandedGroups.contains(group.id),
                        onToggleExpanded = {
                            expandedGroups = if (expandedGroups.contains(group.id)) {
                                expandedGroups - group.id
                            } else {
                                expandedGroups + group.id
                            }
                        },
                        onAccountClick = { /* Handle account click */ },
                        onAccountLongPress = { account ->
                            selectedAccount = account
                            showContextMenu = true
                        })
                }

                // Ungrouped Accounts
                if (ungroupedAccounts.isNotEmpty()) {
                    Text(
                        text = "Other Accounts",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccountColors.DarkGray
                    )

                    ungroupedAccounts.forEach { account ->
                        SubAccountCard(
                            account = account,
                            onClick = { /* Handle account click */ },
                            onLongPress = {
                                selectedAccount = account
                                showContextMenu = true
                            })
                    }
                }
            }

            // Floating Menu - Appears above the FAB
            if (showFloatingMenu) {
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
                                    showFloatingMenu = false
                                    showAddAccountDialog = true
                                }, colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                                    showFloatingMenu = false
                                    showAddGroupDialog = true
                                }, colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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

            // Context Menu for Account Actions
            if (showContextMenu && selectedAccount != null) {
                AlertDialog(onDismissRequest = {
                    showContextMenu = false
                    selectedAccount = null
                }, title = {
                    Text(
                        text = selectedAccount?.name ?: "",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }, text = {
                    Text(
                        text = "Choose an action for this account",
                        fontSize = 14.sp,
                        color = AccountColors.DarkGray
                    )
                }, confirmButton = {
                    TextButton(
                        onClick = {
                            showContextMenu = false
                            showChangeGroupDialog = true
                        }) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Change Group",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Change Group")
                    }
                }, dismissButton = {
                    TextButton(
                        onClick = {
                            showContextMenu = false
                            selectedAccount = null
                        }) {
                        Text("Cancel")
                    }
                })
            }

            // Change Group Dialog
            if (showChangeGroupDialog && selectedAccount != null) {
                ChangeGroupDialog(
                    account = selectedAccount!!,
                    accountGroups = accountGroups,
                    onDismiss = {
                        showChangeGroupDialog = false
                        selectedAccount = null
                    },
                    onGroupChanged = { newGroupId ->
                        // Handle group change - in real app, this would update the database
                        println("Changed group for ${selectedAccount?.name} to: $newGroupId")
                        showChangeGroupDialog = false
                        selectedAccount = null
                    })
    }

    // Add Account Dialog
    if (showAddAccountDialog) {
        AddAccountDialog(
            showDialog = true,
            onDismiss = { showAddAccountDialog = false },
            onAdd = { name, number, balance, groupId -> 
                // Handle add account - in real app, this would save to database
                println("Adding account: $name, $number, $balance, $groupId")
                showAddAccountDialog = false
                    })
    }
    
    // Add Group Dialog
    if (showAddGroupDialog) {
        AddGroupDialog(
            showDialog = true,
            onDismiss = { showAddGroupDialog = false },
            onAdd = { groupName -> 
                // Handle add group - in real app, this would save to database
                println("Adding group: $groupName")
                showAddGroupDialog = false
                    })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    SmartCitizenClubTheme {
        AccountScreen(
            user = User(
                id = "1",
                name = "John Doe",
                email = "john@example.com",
                phone = "01742-184298",
                userType = UserType.USER
            ),
            onLogout = {}
        )
    }
}
}
