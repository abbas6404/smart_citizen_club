package com.example.smartcitizenclub.presentation.feature.account.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

// Color constants for account screen
private object AccountScreenColors {
    val DarkGray = Color(0xFF374151)
    val Gray = Color(0xFF6B7280)
    val Green = Color(0xFF10B981)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    user: User, 
    onLogout: () -> Unit
) {
    var showAddAccountDialog by remember { mutableStateOf(false) }
    var showAddGroupDialog by remember { mutableStateOf(false) }
    var expandedGroups by remember { mutableStateOf(setOf<String>()) }
    var showContextMenu by remember { mutableStateOf(false) }
    var selectedAccount by remember { mutableStateOf<SubAccount?>(null) }
    var showChangeGroupDialog by remember { mutableStateOf(false) }
    
    // Sample data
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
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "My Accounts",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E3A8A)
                ),
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = "Logout",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Active Account Card (Mastercard Style)
            activeAccount?.let { account ->
                ActiveAccountCard(account = account)
            }

            // Groups Section
            Text(
                text = "Account Groups",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937)
            )

            // Groups List
            sampleAccountGroups.forEach { group ->
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
                    }
                )
            }

            // Ungrouped Accounts
            if (ungroupedAccounts.isNotEmpty()) {
                Text(
                    text = "Other Accounts",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )

                ungroupedAccounts.forEach { account ->
                    SubAccountCard(
                        account = account,
                        onClick = { /* Handle account click */ },
                        onLongPress = {
                            selectedAccount = account
                            showContextMenu = true
                        }
                    )
                }
            }
        }
    }

    // Context Menu for Account Actions
    AccountContextMenu(
        showMenu = showContextMenu,
        selectedAccount = selectedAccount,
        onDismiss = {
            showContextMenu = false
            selectedAccount = null
        },
        onChangeGroup = {
            showChangeGroupDialog = true
        }
    )

    // Change Group Dialog
    if (showChangeGroupDialog && selectedAccount != null) {
        ChangeGroupDialog(
            account = selectedAccount!!,
            accountGroups = sampleAccountGroups,
            onDismiss = {
                showChangeGroupDialog = false
                selectedAccount = null
            },
            onGroupChanged = { newGroupId ->
                // Handle group change - in real app, this would update the database
                println("Changed group for ${selectedAccount?.name} to: $newGroupId")
                showChangeGroupDialog = false
                selectedAccount = null
            }
        )
    }

    // Add Account Dialog
    AddAccountDialog(
        showDialog = showAddAccountDialog,
        onDismiss = { showAddAccountDialog = false },
        onAdd = { name, number, balance, groupId -> 
            // Handle add account - in real app, this would save to database
            println("Adding account: $name, $number, $balance, $groupId")
            showAddAccountDialog = false
        }
    )
    
    // Add Group Dialog
    AddGroupDialog(
        showDialog = showAddGroupDialog,
        onDismiss = { showAddGroupDialog = false },
        onAdd = { groupName -> 
            // Handle add group - in real app, this would save to database
            println("Adding group: $groupName")
            showAddGroupDialog = false
        }
    )
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
                type = UserType.USER
            ),
            onLogout = {}
        )
    }
}