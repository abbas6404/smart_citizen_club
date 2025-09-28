package com.example.smartcitizenclub.presentation.feature.investment.ui

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.data.UserType
import com.example.smartcitizenclub.presentation.theme.SmartCitizenClubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestmentScreen(
    user: User,
    onBackClick: () -> Unit,
    onInvestmentSelected: (InvestmentOption) -> Unit = {}
) {
    // Sample investment options
    val investmentOptions = remember {
        listOf(
            InvestmentOption(
                id = "1",
                name = "SCC Savings Plan",
                description = "Low-risk savings with guaranteed returns",
                minAmount = 1000.0,
                maxAmount = 100000.0,
                expectedReturn = 8.5,
                riskLevel = RiskLevel.LOW,
                duration = "1 year",
                category = InvestmentCategory.SAVINGS
            ),
            InvestmentOption(
                id = "2",
                name = "Growth Mutual Fund",
                description = "Diversified portfolio for steady growth",
                minAmount = 5000.0,
                maxAmount = 500000.0,
                expectedReturn = 12.0,
                riskLevel = RiskLevel.MEDIUM,
                duration = "2 years",
                category = InvestmentCategory.MUTUAL_FUNDS
            ),
            InvestmentOption(
                id = "3",
                name = "Blue Chip Stocks",
                description = "Invest in top-performing companies",
                minAmount = 10000.0,
                maxAmount = 1000000.0,
                expectedReturn = 15.0,
                riskLevel = RiskLevel.HIGH,
                duration = "3 years",
                category = InvestmentCategory.STOCKS
            ),
            InvestmentOption(
                id = "4",
                name = "Government Bonds",
                description = "Secure government-backed investments",
                minAmount = 2000.0,
                maxAmount = 200000.0,
                expectedReturn = 7.0,
                riskLevel = RiskLevel.LOW,
                duration = "5 years",
                category = InvestmentCategory.BONDS
            ),
            InvestmentOption(
                id = "5",
                name = "Crypto Portfolio",
                description = "Digital currency investment portfolio",
                minAmount = 5000.0,
                maxAmount = 1000000.0,
                expectedReturn = 25.0,
                riskLevel = RiskLevel.HIGH,
                duration = "1 year",
                category = InvestmentCategory.CRYPTO
            ),
            InvestmentOption(
                id = "6",
                name = "Real Estate Fund",
                description = "Property investment opportunities",
                minAmount = 50000.0,
                maxAmount = 5000000.0,
                expectedReturn = 18.0,
                riskLevel = RiskLevel.MEDIUM,
                duration = "5 years",
                category = InvestmentCategory.REAL_ESTATE
            ),
            InvestmentOption(
                id = "7",
                name = "Gold Investment",
                description = "Precious metal investment plan",
                minAmount = 3000.0,
                maxAmount = 300000.0,
                expectedReturn = 10.0,
                riskLevel = RiskLevel.LOW,
                duration = "2 years",
                category = InvestmentCategory.GOLD
            )
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar with Blue Background
        TopAppBar(
            title = {
                Text(
                    text = "Investment",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = InvestmentColors.Primary
            ),
            modifier = Modifier.statusBarsPadding()
        )
        
        // Main Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Header
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = InvestmentColors.LightGray),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.TrendingUp,
                            contentDescription = "Investment",
                            tint = InvestmentColors.Primary,
                            modifier = Modifier.size(48.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text(
                            text = "Smart Investment Options",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Grow your money with our diverse investment plans",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            
            // Investment Options List
            items(investmentOptions) { option ->
                InvestmentOptionCard(
                    option = option,
                    onClick = { onInvestmentSelected(option) }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun InvestmentOptionCard(
    option: InvestmentOption,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(getCategoryColor(option.category).copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            getCategoryIcon(option.category),
                            contentDescription = option.category.name,
                            tint = getCategoryColor(option.category),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column {
                        Text(
                            text = option.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = option.description,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${option.expectedReturn}%",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = InvestmentColors.Green
                    )
                    Text(
                        text = "Expected Return",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Investment details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Min Amount",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "৳${String.format("%.0f", option.minAmount)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
                
                Column {
                    Text(
                        text = "Max Amount",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "৳${String.format("%.0f", option.maxAmount)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
                
                Column {
                    Text(
                        text = "Duration",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = option.duration,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
                
                Column {
                    Text(
                        text = "Risk Level",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = option.riskLevel.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = getRiskColor(option.riskLevel)
                    )
                }
            }
        }
    }
}

@Composable
fun getCategoryIcon(category: InvestmentCategory): ImageVector {
    return when (category) {
        InvestmentCategory.SAVINGS -> Icons.Default.AccountBalance
        InvestmentCategory.MUTUAL_FUNDS -> Icons.Default.PieChart
        InvestmentCategory.STOCKS -> Icons.Default.TrendingUp
        InvestmentCategory.BONDS -> Icons.Default.Security
        InvestmentCategory.CRYPTO -> Icons.Default.CurrencyBitcoin
        InvestmentCategory.REAL_ESTATE -> Icons.Default.Home
        InvestmentCategory.GOLD -> Icons.Default.Star
    }
}

@Composable
fun getCategoryColor(category: InvestmentCategory): Color {
    return when (category) {
        InvestmentCategory.SAVINGS -> InvestmentColors.Primary
        InvestmentCategory.MUTUAL_FUNDS -> InvestmentColors.Purple
        InvestmentCategory.STOCKS -> InvestmentColors.Green
        InvestmentCategory.BONDS -> InvestmentColors.Orange
        InvestmentCategory.CRYPTO -> InvestmentColors.Gold
        InvestmentCategory.REAL_ESTATE -> InvestmentColors.Red
        InvestmentCategory.GOLD -> InvestmentColors.Gold
    }
}

@Composable
fun getRiskColor(riskLevel: RiskLevel): Color {
    return when (riskLevel) {
        RiskLevel.LOW -> InvestmentColors.Green
        RiskLevel.MEDIUM -> InvestmentColors.Orange
        RiskLevel.HIGH -> InvestmentColors.Red
    }
}

@Preview(showBackground = true)
@Composable
fun InvestmentScreenPreview() {
    SmartCitizenClubTheme {
        InvestmentScreen(
            user = User(
                id = "1",
                name = "Abbas Uddin",
                email = "abbas@example.com",
                phone = "01741736354",
                type = UserType.USER
            ),
            onBackClick = {},
            onInvestmentSelected = {}
        )
    }
}
