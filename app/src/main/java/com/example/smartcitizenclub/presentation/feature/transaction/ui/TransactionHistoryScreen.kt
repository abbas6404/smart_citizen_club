package com.example.smartcitizenclub.presentation.feature.transaction.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import com.example.smartcitizenclub.presentation.theme.OrangeGradient
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import java.text.SimpleDateFormat
import java.util.*

// Transaction data model
data class Transaction(
    val id: String,
    val type: TransactionType,
    val amount: Double,
    val description: String,
    val timestamp: Long,
    val status: TransactionStatus,
    val reference: String? = null,
    val recipient: String? = null
)

enum class TransactionType(val displayName: String, val icon: ImageVector) {
    SEND_MONEY("Send Money", Icons.Default.Send),
    RECEIVE_MONEY("Receive Money", Icons.Default.CallReceived),
    CASH_OUT("Cash Out", Icons.Default.AttachMoney),
    MOBILE_RECHARGE("Mobile Recharge", Icons.Default.Phone),
    ADD_MONEY("Add Money", Icons.Default.Add),
    TRANSFER_MONEY("Transfer Money", Icons.Default.ArrowForward),
    INVESTMENT("Investment", Icons.Default.TrendingUp),
    LOAN("Loan", Icons.Default.AccountBalance),
    PAYMENT("Payment", Icons.Default.Payment),
    DONATION("Donation", Icons.Default.Favorite)
}

enum class TransactionStatus(val displayName: String, val color: Color) {
    COMPLETED("Completed", Color(0xFF4CAF50)),
    PENDING("Pending", Color(0xFFFF9800)),
    FAILED("Failed", Color(0xFFF44336)),
    CANCELLED("Cancelled", Color(0xFF9E9E9E))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen(
    user: User,
    onBackClick: () -> Unit,
    onTransactionClick: (Transaction) -> Unit = {}
) {
    // Sample transaction data
    val transactions = remember {
        listOf(
            Transaction(
                id = "1",
                type = TransactionType.SEND_MONEY,
                amount = 500.0,
                description = "Sent to John Doe",
                timestamp = System.currentTimeMillis() - 3600000, // 1 hour ago
                status = TransactionStatus.COMPLETED,
                reference = "TXN001",
                recipient = "John Doe"
            ),
            Transaction(
                id = "2",
                type = TransactionType.CASH_OUT,
                amount = 1000.0,
                description = "Cash out to Bank Account",
                timestamp = System.currentTimeMillis() - 7200000, // 2 hours ago
                status = TransactionStatus.COMPLETED,
                reference = "TXN002"
            ),
            Transaction(
                id = "3",
                type = TransactionType.MOBILE_RECHARGE,
                amount = 200.0,
                description = "Airtel Recharge - 01741736354",
                timestamp = System.currentTimeMillis() - 10800000, // 3 hours ago
                status = TransactionStatus.COMPLETED,
                reference = "TXN003"
            ),
            Transaction(
                id = "4",
                type = TransactionType.RECEIVE_MONEY,
                amount = 750.0,
                description = "Received from Jane Smith",
                timestamp = System.currentTimeMillis() - 14400000, // 4 hours ago
                status = TransactionStatus.COMPLETED,
                reference = "TXN004",
                recipient = "Jane Smith"
            ),
            Transaction(
                id = "5",
                type = TransactionType.ADD_MONEY,
                amount = 2000.0,
                description = "Added via Bank Transfer",
                timestamp = System.currentTimeMillis() - 18000000, // 5 hours ago
                status = TransactionStatus.PENDING,
                reference = "TXN005"
            ),
            Transaction(
                id = "6",
                type = TransactionType.INVESTMENT,
                amount = 5000.0,
                description = "Smart Investment Plan",
                timestamp = System.currentTimeMillis() - 21600000, // 6 hours ago
                status = TransactionStatus.COMPLETED,
                reference = "TXN006"
            ),
            Transaction(
                id = "7",
                type = TransactionType.LOAN,
                amount = 10000.0,
                description = "Personal Loan Disbursement",
                timestamp = System.currentTimeMillis() - 25200000, // 7 hours ago
                status = TransactionStatus.COMPLETED,
                reference = "TXN007"
            ),
            Transaction(
                id = "8",
                type = TransactionType.DONATION,
                amount = 100.0,
                description = "Charity Donation",
                timestamp = System.currentTimeMillis() - 28800000, // 8 hours ago
                status = TransactionStatus.COMPLETED,
                reference = "TXN008"
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Transaction History",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Filter options */ }) {
                        Icon(
                            Icons.Default.FilterList,
                            contentDescription = "Filter",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Transaction List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(transactions) { transaction ->
                    TransactionItem(
                        transaction = transaction,
                        onClick = { onTransactionClick(transaction) }
                    )
                }
                
                // Bottom spacing
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
private fun TransactionItem(
    transaction: Transaction,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Transaction Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        brush = OrangeGradient,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = transaction.type.icon,
                    contentDescription = transaction.type.displayName,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Transaction Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = transaction.type.displayName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                
                Text(
                    text = transaction.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = formatTransactionTime(transaction.timestamp),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            // Amount and Status
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${if (transaction.type == TransactionType.RECEIVE_MONEY) "+" else "-"}৳${transaction.amount.toInt()}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (transaction.type == TransactionType.RECEIVE_MONEY) 
                        Color(0xFF4CAF50) 
                    else 
                        Color.Black
                )
                
                Text(
                    text = transaction.status.displayName,
                    fontSize = 12.sp,
                    color = transaction.status.color,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

private fun formatTransactionTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    
    return when {
        diff < 60000 -> "Just now"
        diff < 3600000 -> "${diff / 60000}m ago"
        diff < 86400000 -> "${diff / 3600000}h ago"
        diff < 604800000 -> "${diff / 86400000}d ago"
        else -> {
            val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            formatter.format(Date(timestamp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionHistoryScreenPreview() {
    SmartCitizenClubTheme {
        TransactionHistoryScreen(
            user = User(
                id = "1",
                name = "John Doe",
                email = "john@example.com",
                phone = "01741736354",
                type = UserType.USER
            ),
            onBackClick = {}
        )
    }
}
