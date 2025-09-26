package com.example.smartcitizenclub.presentation.feature.finance.ui

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme
import java.text.SimpleDateFormat
import java.util.*

// Transaction data class
data class Transaction(
    val id: String,
    val type: String,
    val reference: String,
    val amount: Double,
    val isIncoming: Boolean,
    val dateTime: Long,
    val phoneNumber: String? = null
)

// Transaction filter enum
enum class TransactionFilter {
    ALL, IN, OUT
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceScreen(
    user: User,
    onLoanPaymentClick: () -> Unit = {}
) {
    var selectedFilter by remember { mutableStateOf(TransactionFilter.ALL) }
    var currentMonth by remember { mutableStateOf(System.currentTimeMillis()) }
    
    // Sample transaction data
    val transactions = remember {
        listOf(
            Transaction(
                id = "1",
                type = "Cashback",
                reference = "#74DM99NZ",
                amount = 5.0,
                isIncoming = true,
                dateTime = System.currentTimeMillis() - (24 * 60 * 60 * 1000) // Yesterday
            ),
            Transaction(
                id = "2",
                type = "Send Money",
                reference = "01533-619640",
                amount = 1000.0,
                isIncoming = false,
                dateTime = System.currentTimeMillis() - (24 * 60 * 60 * 1000),
                phoneNumber = "01533-619640"
            ),
            Transaction(
                id = "3",
                type = "Send Money Fee",
                reference = "#74DM99NE",
                amount = 5.0,
                isIncoming = false,
                dateTime = System.currentTimeMillis() - (24 * 60 * 60 * 1000)
            ),
            Transaction(
                id = "4",
                type = "Received Money",
                reference = "01632-213697",
                amount = 1000.0,
                isIncoming = true,
                dateTime = System.currentTimeMillis() - (24 * 60 * 60 * 1000),
                phoneNumber = "01632-213697"
            ),
            Transaction(
                id = "5",
                type = "Mobile Recharge",
                reference = "01711-234567",
                amount = 200.0,
                isIncoming = false,
                dateTime = System.currentTimeMillis() - (2 * 24 * 60 * 60 * 1000),
                phoneNumber = "01711-234567"
            ),
            Transaction(
                id = "6",
                type = "Bill Payment",
                reference = "Electricity Bill",
                amount = 1500.0,
                isIncoming = false,
                dateTime = System.currentTimeMillis() - (3 * 24 * 60 * 60 * 1000)
            ),
            Transaction(
                id = "7",
                type = "Cashback",
                reference = "#74DM99AA",
                amount = 10.0,
                isIncoming = true,
                dateTime = System.currentTimeMillis() - (4 * 24 * 60 * 60 * 1000)
            )
        )
    }
    
    // Filter transactions based on selected filter
    val filteredTransactions = when (selectedFilter) {
        TransactionFilter.ALL -> transactions
        TransactionFilter.IN -> transactions.filter { it.isIncoming }
        TransactionFilter.OUT -> transactions.filter { !it.isIncoming }
    }
    
    // Format month and year
    val monthFormatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    val monthYearText = monthFormatter.format(currentMonth)
    
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Transactions",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFE53E3E) // Red background
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
                // Month/Year Selector and Filter Tabs in one card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column {
                        // Month/Year Selector
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { 
                                    // Previous month logic
                                    val calendar = Calendar.getInstance()
                                    calendar.timeInMillis = currentMonth
                                    calendar.add(Calendar.MONTH, -1)
                                    currentMonth = calendar.timeInMillis
                                }
                            ) {
                                Icon(
                                    Icons.Default.ChevronLeft,
                                    contentDescription = "Previous Month",
                                    tint = Color.Black
                                )
                            }
                            
                            Text(
                                text = monthYearText,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            
                            IconButton(
                                onClick = { 
                                    // Next month logic
                                    val calendar = Calendar.getInstance()
                                    calendar.timeInMillis = currentMonth
                                    calendar.add(Calendar.MONTH, 1)
                                    currentMonth = calendar.timeInMillis
                                }
                            ) {
                                Icon(
                                    Icons.Default.ChevronRight,
                                    contentDescription = "Next Month",
                                    tint = Color.Black
                                )
                            }
                        }
                        
                        // Filter Tabs
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            FilterTab(
                                text = "ALL",
                                isSelected = selectedFilter == TransactionFilter.ALL,
                                onClick = { selectedFilter = TransactionFilter.ALL }
                            )
                            
                            Spacer(modifier = Modifier.width(24.dp))
                            
                            FilterTab(
                                text = "IN",
                                isSelected = selectedFilter == TransactionFilter.IN,
                                onClick = { selectedFilter = TransactionFilter.IN }
                            )
                            
                            Spacer(modifier = Modifier.width(24.dp))
                            
                            FilterTab(
                                text = "OUT",
                                isSelected = selectedFilter == TransactionFilter.OUT,
                                onClick = { selectedFilter = TransactionFilter.OUT }
                            )
                        }
                    }
                }
                
                // Loan Payment Button
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { onLoanPaymentClick() },
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE53E3E)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.Payment,
                            contentDescription = "Loan Payment",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = "Make Loan Payment",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = "Navigate",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                
                // Transactions List
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp) // Space for bottom navigation
                ) {
                    items(filteredTransactions) { transaction ->
                        TransactionItem(
                            transaction = transaction,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color(0xFFE53E3E) else Color(0xFF374151)
        )
        
        if (isSelected) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color(0xFFE53E3E))
            )
        } else {
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
private fun TransactionItem(
    transaction: Transaction,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Transaction Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        if (transaction.isIncoming) 
                            Color(0xFF4CAF50).copy(alpha = 0.1f)
                        else 
                            Color(0xFFE53E3E).copy(alpha = 0.1f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (transaction.isIncoming) Icons.Default.KeyboardArrowRight else Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Transaction Type",
                    tint = if (transaction.isIncoming) Color(0xFF4CAF50) else Color(0xFFE53E3E),
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Transaction Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = transaction.type,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                
                Text(
                    text = transaction.reference,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                
                Text(
                    text = formatDateTime(transaction.dateTime),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            // Amount
            Text(
                text = "${if (transaction.isIncoming) "৳" else "-৳"} ${String.format("%.0f", transaction.amount)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (transaction.isIncoming) Color(0xFF4CAF50) else Color(0xFFE53E3E)
            )
        }
    }
}

private fun formatDateTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    val days = diff / (24 * 60 * 60 * 1000)
    
    return when {
        days == 0L -> "Today, ${SimpleDateFormat("hh:mm a", Locale.getDefault()).format(timestamp)}"
        days == 1L -> "Yesterday, ${SimpleDateFormat("hh:mm a", Locale.getDefault()).format(timestamp)}"
        days < 7L -> "${SimpleDateFormat("EEEE, hh:mm a", Locale.getDefault()).format(timestamp)}"
        else -> SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault()).format(timestamp)
    }
}

@Preview(showBackground = true)
@Composable
fun FinanceScreenPreview() {
    SmartCitizenClubTheme {
        FinanceScreen(
            user = User(
                id = "1",
                name = "John Doe",
                email = "john@example.com",
                phone = "+8801234567890",
                type = UserType.USER
            )
        )
    }
}