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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    val phoneNumber: String? = null,
    val category: TransactionCategory = TransactionCategory.OTHER
)

// Transaction category enum
enum class TransactionCategory {
    SEND_MONEY,
    RECEIVE_MONEY,
    CASH_OUT,
    MOBILE_RECHARGE,
    BILL_PAYMENT,
    INVESTMENT,
    LOAN,
    CASHBACK,
    REFUND,
    OTHER
}

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
                dateTime = System.currentTimeMillis() - (24 * 60 * 60 * 1000),
                category = TransactionCategory.CASHBACK
            ),
            Transaction(
                id = "2",
                type = "Send Money",
                reference = "01533-619640",
                amount = 1000.0,
                isIncoming = false,
                dateTime = System.currentTimeMillis() - (24 * 60 * 60 * 1000),
                phoneNumber = "01533-619640",
                category = TransactionCategory.SEND_MONEY
            ),
            Transaction(
                id = "3",
                type = "Send Money Fee",
                reference = "#74DM99NE",
                amount = 5.0,
                isIncoming = false,
                dateTime = System.currentTimeMillis() - (24 * 60 * 60 * 1000),
                category = TransactionCategory.SEND_MONEY
            ),
            Transaction(
                id = "4",
                type = "Received Money",
                reference = "01632-213697",
                amount = 1000.0,
                isIncoming = true,
                dateTime = System.currentTimeMillis() - (24 * 60 * 60 * 1000),
                phoneNumber = "01632-213697",
                category = TransactionCategory.RECEIVE_MONEY
            ),
            Transaction(
                id = "5",
                type = "Mobile Recharge",
                reference = "01711-234567",
                amount = 200.0,
                isIncoming = false,
                dateTime = System.currentTimeMillis() - (2 * 24 * 60 * 60 * 1000),
                phoneNumber = "01711-234567",
                category = TransactionCategory.MOBILE_RECHARGE
            ),
            Transaction(
                id = "6",
                type = "Bill Payment",
                reference = "Electricity Bill",
                amount = 1500.0,
                isIncoming = false,
                dateTime = System.currentTimeMillis() - (3 * 24 * 60 * 60 * 1000),
                category = TransactionCategory.BILL_PAYMENT
            ),
            Transaction(
                id = "7",
                type = "Cashback",
                reference = "#74DM99AA",
                amount = 10.0,
                isIncoming = true,
                dateTime = System.currentTimeMillis() - (4 * 24 * 60 * 60 * 1000),
                category = TransactionCategory.CASHBACK
            ),
            Transaction(
                id = "8",
                type = "Investment",
                reference = "SCC Savings Plan",
                amount = 5000.0,
                isIncoming = false,
                dateTime = System.currentTimeMillis() - (5 * 24 * 60 * 60 * 1000),
                category = TransactionCategory.INVESTMENT
            ),
            Transaction(
                id = "9",
                type = "Cash In",
                reference = "Bkash to SCC",
                amount = 2000.0,
                isIncoming = true,
                dateTime = System.currentTimeMillis() - (6 * 24 * 60 * 60 * 1000),
                category = TransactionCategory.RECEIVE_MONEY
            ),
            Transaction(
                id = "10",
                type = "Cash In",
                reference = "Nagad to SCC",
                amount = 1500.0,
                isIncoming = true,
                dateTime = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000),
                category = TransactionCategory.RECEIVE_MONEY
            ),
            Transaction(
                id = "11",
                type = "Cash Out",
                reference = "SCC to Bkash",
                amount = 3000.0,
                isIncoming = false,
                dateTime = System.currentTimeMillis() - (8 * 24 * 60 * 60 * 1000),
                category = TransactionCategory.CASH_OUT
            ),
            Transaction(
                id = "12",
                type = "Cash Out",
                reference = "SCC to Nagad",
                amount = 2500.0,
                isIncoming = false,
                dateTime = System.currentTimeMillis() - (9 * 24 * 60 * 60 * 1000),
                category = TransactionCategory.CASH_OUT
            ),
            Transaction(
                id = "13",
                type = "Cash In",
                reference = "Rocket to SCC",
                amount = 1000.0,
                isIncoming = true,
                dateTime = System.currentTimeMillis() - (10 * 24 * 60 * 60 * 1000),
                category = TransactionCategory.RECEIVE_MONEY
            )
        )
    }
    
    // Filter transactions based on selected filter
    val filteredTransactions = when (selectedFilter) {
        TransactionFilter.ALL -> transactions
        TransactionFilter.IN -> transactions.filter { it.isIncoming }
        TransactionFilter.OUT -> transactions.filter { !it.isIncoming }
    }
    
    // Calculate monthly summary
    val monthlyIncome = transactions.filter { it.isIncoming }.sumOf { it.amount }
    val monthlyExpense = transactions.filter { !it.isIncoming }.sumOf { it.amount }
    val monthlyBalance = monthlyIncome - monthlyExpense
    
    // Calculate cash-in and cash-out specific amounts
    val cashInTransactions = transactions.filter { it.type.contains("Cash In", ignoreCase = true) }
    val cashOutTransactions = transactions.filter { it.type.contains("Cash Out", ignoreCase = true) }
    val totalCashIn = cashInTransactions.sumOf { it.amount }
    val totalCashOut = cashOutTransactions.sumOf { it.amount }
    
    // Format month and year
    val monthFormatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    val monthYearText = monthFormatter.format(currentMonth)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Top Bar with Gradient Background
        TopAppBar(
            title = {
                Text(
                    text = "Finance",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF2196F3) // Blue background
            ),
            modifier = Modifier.statusBarsPadding()
        )
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Monthly Summary Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        // Month/Year Selector
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { 
                                    val calendar = Calendar.getInstance()
                                    calendar.timeInMillis = currentMonth
                                    calendar.add(Calendar.MONTH, -1)
                                    currentMonth = calendar.timeInMillis
                                }
                            ) {
                                Icon(
                                    Icons.Default.ChevronLeft,
                                    contentDescription = "Previous Month",
                                    tint = Color(0xFF2196F3)
                                )
                            }
                            
                            Text(
                                text = monthYearText,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            
                            IconButton(
                                onClick = { 
                                    val calendar = Calendar.getInstance()
                                    calendar.timeInMillis = currentMonth
                                    calendar.add(Calendar.MONTH, 1)
                                    currentMonth = calendar.timeInMillis
                                }
                            ) {
                                Icon(
                                    Icons.Default.ChevronRight,
                                    contentDescription = "Next Month",
                                    tint = Color(0xFF2196F3)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        // Summary Cards
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Income Card
                            Card(
                                modifier = Modifier.weight(1f),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E8)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        Icons.Default.TrendingUp,
                                        contentDescription = "Income",
                                        tint = Color(0xFF4CAF50),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "৳${String.format("%.0f", monthlyIncome)}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF4CAF50)
                                    )
                                    Text(
                                        text = "Income",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                            
                            // Expense Card
                            Card(
                                modifier = Modifier.weight(1f),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        Icons.Default.TrendingDown,
                                        contentDescription = "Expense",
                                        tint = Color(0xFFE53E3E),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "৳${String.format("%.0f", monthlyExpense)}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFE53E3E)
                                    )
                                    Text(
                                        text = "Expense",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                            
                            // Balance Card
                            Card(
                                modifier = Modifier.weight(1f),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        Icons.Default.AccountBalance,
                                        contentDescription = "Balance",
                                        tint = Color(0xFF2196F3),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "৳${String.format("%.0f", monthlyBalance)}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2196F3)
                                    )
                                    Text(
                                        text = "Balance",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Quick Actions
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onLoanPaymentClick() },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFE53E3E).copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Payment,
                                    contentDescription = "Loan Payment",
                                    tint = Color(0xFFE53E3E),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            
                            Spacer(modifier = Modifier.width(12.dp))
                            
                            Column {
                                Text(
                                    text = "Make Loan Payment",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Pay your loan installment",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                        
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = "Navigate",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Filter Tabs
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        FilterTab(
                            text = "All",
                            isSelected = selectedFilter == TransactionFilter.ALL,
                            onClick = { selectedFilter = TransactionFilter.ALL }
                        )
                        
                        FilterTab(
                            text = "Income",
                            isSelected = selectedFilter == TransactionFilter.IN,
                            onClick = { selectedFilter = TransactionFilter.IN }
                        )
                        
                        FilterTab(
                            text = "Expense",
                            isSelected = selectedFilter == TransactionFilter.OUT,
                            onClick = { selectedFilter = TransactionFilter.OUT }
                        )
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Cash In History Section
            if (cashInTransactions.isNotEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
,
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.CallReceived,
                                        contentDescription = "Cash In",
                                        tint = Color(0xFF4CAF50),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Cash In History",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                }
                                Text(
                                    text = "৳${String.format("%.0f", totalCashIn)}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4CAF50)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                cashInTransactions.take(3).forEach { transaction ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = transaction.reference,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = Color.Black
                                            )
                                            Text(
                                                text = formatDateTime(transaction.dateTime),
                                                fontSize = 12.sp,
                                                color = Color.Gray
                                            )
                                        }
                                        Text(
                                            text = "+৳${String.format("%.0f", transaction.amount)}",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF4CAF50)
                                        )
                                    }
                                }
                                
                                if (cashInTransactions.size > 3) {
                                    Text(
                                        text = "View all ${cashInTransactions.size} cash-in transactions",
                                        fontSize = 12.sp,
                                        color = Color(0xFF2196F3),
                                        modifier = Modifier.clickable { /* TODO: Navigate to full cash-in history */ }
                                    )
                                }
                            }
                        }
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            
            // Cash Out History Section
            if (cashOutTransactions.isNotEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
,
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.AttachMoney,
                                        contentDescription = "Cash Out",
                                        tint = Color(0xFF9C27B0),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Cash Out History",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                }
                                Text(
                                    text = "৳${String.format("%.0f", totalCashOut)}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF9C27B0)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                cashOutTransactions.take(3).forEach { transaction ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = transaction.reference,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = Color.Black
                                            )
                                            Text(
                                                text = formatDateTime(transaction.dateTime),
                                                fontSize = 12.sp,
                                                color = Color.Gray
                                            )
                                        }
                                        Text(
                                            text = "-৳${String.format("%.0f", transaction.amount)}",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF9C27B0)
                                        )
                                    }
                                }
                                
                                if (cashOutTransactions.size > 3) {
                                    Text(
                                        text = "View all ${cashOutTransactions.size} cash-out transactions",
                                        fontSize = 12.sp,
                                        color = Color(0xFF2196F3),
                                        modifier = Modifier.clickable { /* TODO: Navigate to full cash-out history */ }
                                    )
                                }
                            }
                        }
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            
            // Transactions Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Transactions",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Text(
                        text = "${filteredTransactions.size} transactions",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // Transactions List
            items(filteredTransactions) { transaction ->
                TransactionItem(
                    transaction = transaction,
                    modifier = Modifier.fillMaxWidth()
                )
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
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color(0xFF2196F3) else Color(0xFF374151)
        )
        
        if (isSelected) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color(0xFF2196F3))
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
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
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
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(getCategoryColor(transaction.category).copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    getCategoryIcon(transaction.category),
                    contentDescription = "Transaction Type",
                    tint = getCategoryColor(transaction.category),
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
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${if (transaction.isIncoming) "+" else "-"}৳${String.format("%.0f", transaction.amount)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (transaction.isIncoming) Color(0xFF4CAF50) else Color(0xFFE53E3E)
                )
                Text(
                    text = if (transaction.isIncoming) "Received" else "Sent",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
private fun getCategoryIcon(category: TransactionCategory): ImageVector {
    return when (category) {
        TransactionCategory.SEND_MONEY -> Icons.Default.Send
        TransactionCategory.RECEIVE_MONEY -> Icons.Default.CallReceived
        TransactionCategory.CASH_OUT -> Icons.Default.AttachMoney
        TransactionCategory.MOBILE_RECHARGE -> Icons.Default.Phone
        TransactionCategory.BILL_PAYMENT -> Icons.Default.Receipt
        TransactionCategory.INVESTMENT -> Icons.Default.TrendingUp
        TransactionCategory.LOAN -> Icons.Default.AccountBalance
        TransactionCategory.CASHBACK -> Icons.Default.CardGiftcard
        TransactionCategory.REFUND -> Icons.Default.Undo
        TransactionCategory.OTHER -> Icons.Default.MoreHoriz
    }
}

@Composable
private fun getCategoryColor(category: TransactionCategory): Color {
    return when (category) {
        TransactionCategory.SEND_MONEY -> Color(0xFFE53E3E)
        TransactionCategory.RECEIVE_MONEY -> Color(0xFF4CAF50)
        TransactionCategory.CASH_OUT -> Color(0xFF9C27B0)
        TransactionCategory.MOBILE_RECHARGE -> Color(0xFF2196F3)
        TransactionCategory.BILL_PAYMENT -> Color(0xFFFF9800)
        TransactionCategory.INVESTMENT -> Color(0xFF4CAF50)
        TransactionCategory.LOAN -> Color(0xFF607D8B)
        TransactionCategory.CASHBACK -> Color(0xFF4CAF50)
        TransactionCategory.REFUND -> Color(0xFF4CAF50)
        TransactionCategory.OTHER -> Color(0xFF9E9E9E)
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