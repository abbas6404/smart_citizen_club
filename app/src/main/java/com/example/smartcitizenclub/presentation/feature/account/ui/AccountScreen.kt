package com.example.smartcitizenclub.presentation.feature.account.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.AccountManager
import com.example.smartcitizenclub.data.SubAccount
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

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
    var showFloatingMenu by remember { mutableStateOf(false) }
    var showShareDialog by remember { mutableStateOf(false) }
    var selectedAccountForShare by remember { mutableStateOf<SubAccount?>(null) }
    
    // Sample data
    val subAccounts = remember {
        listOf(
            SubAccount("1", "Main Account", "ACC-001", 15000.0, true, null, "REF001"),
            SubAccount("2", "Personal Savings", "ACC-002", 25000.0, true, "1", "REF002"),
            SubAccount("3", "Business Account", "ACC-003", 50000.0, true, "2", "REF003"),
            SubAccount("4", "Emergency Fund", "ACC-004", 30000.0, true, "5", "REF004"),
            SubAccount("5", "Investment Portfolio", "ACC-005", 75000.0, true, "4", "REF005"),
            SubAccount("6", "Personal Checking", "ACC-006", 15000.0, true, "1", "REF006"),
            SubAccount("7", "Business Savings", "ACC-007", 7000.0, true, "2", "REF007"),
            SubAccount("8", "Ungrouped Account", "ACC-008", 3000.0, true, null, null)
        )
    }

    // Get active account from AccountManager
    val activeAccount = AccountManager.activeAccount.collectAsState().value 
        ?: subAccounts.firstOrNull { it.id == "1" }
    
    val groupedAccounts = subAccounts.groupBy { it.groupId ?: "" }
    val ungroupedAccounts = subAccounts.filter { it.groupId == null && it.id != "1" }
    
    // Initialize AccountManager with subaccounts on first load
    LaunchedEffect(subAccounts) {
        if (activeAccount == null) {
            AccountManager.setActiveAccount(subAccounts.first())
        } else {
            AccountManager.updateActiveAccountFromList(subAccounts)
        }
    }

    Scaffold(
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Add Group FAB with text
                if (showFloatingMenu) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.clickable { 
                            showAddGroupDialog = true
                            showFloatingMenu = false
                        }
                    ) {
                        Text(
                            text = "Add Group",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .background(
                                    PrimaryOrangeGradient,
                                    RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                        FloatingActionButton(
                            onClick = { },
                            containerColor = Color(0xFF10B981),
                            contentColor = Color.White,
                            modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                Icons.Default.GroupAdd,
                                contentDescription = "Add Group",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
                
                // Add Account FAB with text
                if (showFloatingMenu) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.clickable { 
                            showAddAccountDialog = true
                            showFloatingMenu = false
                        }
                    ) {
                        Text(
                            text = "Add Account",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .background(
                                    PrimaryOrangeGradient,
                                    RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                        FloatingActionButton(
                            onClick = { },
                            containerColor = Color(0xFF3B82F6),
                            contentColor = Color.White,
                            modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                Icons.Default.AccountBalance,
                                contentDescription = "Add Account",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
                
                // Main FAB
                FloatingActionButton(
                    onClick = { showFloatingMenu = !showFloatingMenu },
                    containerColor = PrimaryOrangeGradient,
                    contentColor = Color.White,
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        if (showFloatingMenu) Icons.Default.Close else Icons.Default.Add,
                        contentDescription = if (showFloatingMenu) "Close Menu" else "Add",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Active Account Card (Mastercard Style)
            activeAccount?.let { account ->
                ActiveAccountCard(
                    account = account,
                    onLongPress = { selectedAccount ->
                        selectedAccountForShare = selectedAccount
                        showShareDialog = true
                    }
                )
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
                    onAccountClick = { account ->
                        // Switch to this account as active
                        AccountManager.setActiveAccount(account)
                    },
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
                        onClick = { 
                            // Switch to this account as active
                            AccountManager.setActiveAccount(account)
                        },
                        onLongPress = {
                            selectedAccount = account
                            showContextMenu = true
                        }
                    )
                }
            }
            
            // Extra bottom spacing
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
    
    // Share Account Dialog
    ShareAccountDialog(
        showDialog = showShareDialog,
        account = selectedAccountForShare,
        onDismiss = {
            showShareDialog = false
            selectedAccountForShare = null
        }
    )

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
                println("Changed group for ${selectedAccount?.accountName} to: $newGroupId")
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