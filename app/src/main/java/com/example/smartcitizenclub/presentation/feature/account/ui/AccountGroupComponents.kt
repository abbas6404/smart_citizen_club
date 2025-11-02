package com.example.smartcitizenclub.presentation.feature.account.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.SubAccount
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

// Data classes for account groups
data class AccountGroup(
    val id: String, 
    val name: String, 
    val color: Color, 
    val icon: ImageVector
)

// Sample account groups data
val sampleAccountGroups = listOf(
    AccountGroup("1", "Personal", AccountColors.Blue, Icons.Default.Person),
    AccountGroup("2", "Business", AccountColors.Green, Icons.Default.Business),
    AccountGroup("3", "Savings", AccountColors.Orange, Icons.Default.Savings),
    AccountGroup("4", "Investment", AccountColors.Purple, Icons.Default.TrendingUp),
    AccountGroup("5", "Emergency", AccountColors.Red, Icons.Default.Warning)
)

// Reusable Group Selection Component
@Composable
fun GroupSelection(
    selectedGroupId: String?, 
    onGroupSelected: (String?) -> Unit
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
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            if (selectedGroupId == null) AccountColors.Green.copy(alpha = 0.15f) else Color.Transparent,
                            RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Group,
                        contentDescription = "No Group",
                        tint = if (selectedGroupId == null) AccountColors.Green else AccountColors.DarkGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
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
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                if (selectedGroupId == group.id) group.color.copy(alpha = 0.15f) else Color.Transparent,
                                RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            group.icon,
                            contentDescription = group.name,
                            tint = if (selectedGroupId == group.id) group.color else AccountColors.DarkGray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
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
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = group.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = AccountColors.TextPrimary
                        )
                        Text(
                            text = "${accounts.size} accounts • ৳${
                                String.format(
                                    "%.0f", accounts.sumOf { it.balance })
                            }", 
                            fontSize = 14.sp, 
                            color = AccountColors.TextSecondary
                        )
                    }
                }

                Icon(
                    if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = AccountColors.TextSecondary,
                    modifier = Modifier.size(24.dp)
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(16.dp))
                
                Divider(
                    color = AccountColors.Divider,
                    thickness = 1.dp
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                accounts.forEach { account ->
                    SubAccountCard(
                        account = account,
                        onClick = { onAccountClick(account) },
                        onLongPress = { onAccountLongPress(account) })

                    if (account != accounts.last()) {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

// Add Group Dialog Composable
@Composable
fun AddGroupDialog(
    showDialog: Boolean, 
    onDismiss: () -> Unit, 
    onAdd: (String) -> Unit
) {
    if (showDialog) {
        var groupName by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = onDismiss, 
            title = {
                Text(
                    text = "Add New Group", 
                    fontSize = 18.sp, 
                    fontWeight = FontWeight.Bold
                )
            }, 
            text = {
                OutlinedTextField(
                    value = groupName,
                    onValueChange = { groupName = it },
                    label = { Text("Group Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }, 
            confirmButton = {
                TextButton(
                    onClick = {
                        onAdd(groupName)
                        onDismiss()
                    }, 
                    enabled = groupName.isNotBlank()
                ) {
                    Text("Add Group")
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

    AlertDialog(
        onDismissRequest = onDismiss, 
        title = {
            Text(
                text = "Change Group", 
                fontSize = 18.sp, 
                fontWeight = FontWeight.Bold
            )
        }, 
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Select a new group for \"${account.accountName}\"",
                    fontSize = 14.sp,
                    color = AccountColors.TextSecondary
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
                    onGroupChanged(selectedGroupId)
                }
            ) {
                Text("Change Group")
            }
        }, 
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GroupSelectionPreview() {
    SmartCitizenClubTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GroupSelection(
                selectedGroupId = "1",
                onGroupSelected = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupCardPreview() {
    SmartCitizenClubTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GroupCard(
                group = sampleAccountGroups[0],
                accounts = listOf(
                    SubAccount("1", "Personal Savings", "ACC-002", 25000.0, true, "1", "REF002"),
                    SubAccount("2", "Personal Checking", "ACC-006", 15000.0, true, "1", "REF006")
                ),
                isExpanded = true,
                onToggleExpanded = {},
                onAccountClick = {},
                onAccountLongPress = {}
            )
        }
    }
}
