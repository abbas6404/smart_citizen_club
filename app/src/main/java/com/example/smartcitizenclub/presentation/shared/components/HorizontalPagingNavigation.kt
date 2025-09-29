package com.example.smartcitizenclub.presentation.shared.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.presentation.UserScreen
import com.example.smartcitizenclub.presentation.feature.home.ui.HomeScreen
import com.example.smartcitizenclub.presentation.feature.messages.ui.MessagesScreen
import com.example.smartcitizenclub.presentation.feature.qrscan.ui.QRScanScreen
import com.example.smartcitizenclub.presentation.feature.account.ui.AccountScreen
import com.example.smartcitizenclub.presentation.feature.myscc.ui.MySCCScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagingNavigation(
    user: User,
    selectedScreen: UserScreen,
    onScreenSelected: (UserScreen) -> Unit,
    onLogout: () -> Unit,
    onShowSelectContact: () -> Unit = {},
    onChatClick: (com.example.smartcitizenclub.presentation.feature.messages.models.Chat) -> Unit = {},
    onNavigateToChat: (com.example.smartcitizenclub.presentation.feature.messages.models.Chat) -> Unit = {},
    onShowNewGroup: () -> Unit = {},
    onShowNewContact: () -> Unit = {},
    onShowTransactionHistory: () -> Unit = {},
    onShowQRScan: () -> Unit = {},
    onShowSendMoney: () -> Unit = {},
    onShowCashOut: () -> Unit = {},
    onShowMobileRecharge: () -> Unit = {},
    onShowAddMoney: () -> Unit = {},
    onShowTransferMoney: () -> Unit = {},
    onShowLimitUpgrade: () -> Unit = {},
    onShowInvestment: () -> Unit = {},
    onShowLoan: () -> Unit = {},
    onShowContactUs: () -> Unit = {},
    onShowChargeLimit: () -> Unit = {},
    onShowDonation: () -> Unit = {},
    onShowChangePassword: () -> Unit = {},
    onShowKYCSubmit: () -> Unit = {}
) {
    // Define ALL pages that support horizontal paging (all bottom nav screens)
    val pagingScreens = listOf(
        UserScreen.Home,
        UserScreen.Messages,
        UserScreen.QRScan,
        UserScreen.Account,
        UserScreen.MySCC
    )
    
    // Get current page index
    val currentPageIndex = pagingScreens.indexOf(selectedScreen).takeIf { it >= 0 } ?: 0
    
    // Pager state
    val pagerState = rememberPagerState(
        initialPage = currentPageIndex,
        pageCount = { pagingScreens.size }
    )
    
    // Coroutine scope for smooth scrolling
    val coroutineScope = rememberCoroutineScope()
    
    // Update pager when selectedScreen changes (from bottom nav)
    LaunchedEffect(selectedScreen) {
        val newIndex = pagingScreens.indexOf(selectedScreen)
        if (newIndex >= 0 && newIndex != pagerState.currentPage) {
            pagerState.animateScrollToPage(newIndex)
        }
    }
    
    // Update selectedScreen when pager changes (from swipe)
    LaunchedEffect(pagerState.currentPage) {
        val newScreen = pagingScreens[pagerState.currentPage]
        if (newScreen != selectedScreen) {
            onScreenSelected(newScreen)
        }
    }
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Horizontal Pager for smooth page transitions between ALL screens
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (pagingScreens[page]) {
                UserScreen.Home -> {
                    HomeScreen(
                        user = user,
                        onSendMoneyClick = onShowSendMoney,
                        onCashOutClick = onShowCashOut,
                        onMobileRechargeClick = onShowMobileRecharge,
                        onAddMoneyClick = onShowAddMoney,
                        onTransferMoneyClick = onShowTransferMoney,
                        onLimitUpgradeClick = onShowLimitUpgrade,
                        onInvestmentClick = onShowInvestment,
                        onLoanClick = onShowLoan,
                        onTransactionHistoryClick = onShowTransactionHistory,
                        onContactUsClick = onShowContactUs,
                        onChargeLimitClick = onShowChargeLimit,
                        onDonationClick = onShowDonation
                    )
                }
                
                UserScreen.Messages -> {
                    MessagesScreen(
                        user = user,
                        onShowSelectContact = onShowSelectContact,
                        onChatClick = onChatClick,
                        onNavigateToChat = onNavigateToChat
                    )
                }
                
                UserScreen.QRScan -> {
                    QRScanScreen(
                        onBackClick = { onScreenSelected(UserScreen.Home) },
                        onQRCodeScanned = { qrData ->
                            // Handle scanned QR code data
                            // For now, just go back to home
                            onScreenSelected(UserScreen.Home)
                        }
                    )
                }
                
                UserScreen.Account -> {
                    AccountScreen(
                        user = user,
                        onLogout = onLogout
                    )
                }
                
                UserScreen.MySCC -> {
                    MySCCScreen(
                        user = user,
                        onLogout = onLogout,
                        onNavigateToChangePassword = onShowChangePassword,
                        onNavigateToKYCSubmit = onShowKYCSubmit
                    )
                }
            }
        }
    }
}
