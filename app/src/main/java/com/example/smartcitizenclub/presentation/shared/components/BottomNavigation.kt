package com.example.smartcitizenclub.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.presentation.UserScreen
import com.example.smartcitizenclub.presentation.feature.account.ui.AccountScreen
import com.example.smartcitizenclub.presentation.feature.billpay.ui.*
import com.example.smartcitizenclub.presentation.feature.cashout.ui.CashOutProvider
import com.example.smartcitizenclub.presentation.feature.home.ui.HomeScreen
import com.example.smartcitizenclub.presentation.feature.loan.ui.*
import com.example.smartcitizenclub.presentation.feature.loanpay.ui.*
import com.example.smartcitizenclub.presentation.feature.messages.components.ChatScreen
import com.example.smartcitizenclub.presentation.feature.messages.screens.AddNewContactScreen
import com.example.smartcitizenclub.presentation.feature.messages.screens.ContactSelectionScreen
import com.example.smartcitizenclub.presentation.feature.messages.screens.CreateNewGroupScreen
import com.example.smartcitizenclub.presentation.feature.messages.ui.MessagesScreen
import com.example.smartcitizenclub.presentation.feature.myscc.ui.MySCCScreen
import com.example.smartcitizenclub.presentation.feature.recharge.ui.MobileOperator
import com.example.smartcitizenclub.presentation.feature.sendmoney.ui.Contact
import com.example.smartcitizenclub.presentation.feature.sendmoney.ui.ConfirmSendMoneyScreen
import com.example.smartcitizenclub.presentation.feature.ticketsupport.ui.*
import com.example.smartcitizenclub.presentation.feature.transaction.ui.TransactionDetailsScreen
import com.example.smartcitizenclub.presentation.feature.transaction.ui.TransactionHistoryScreen
import com.example.smartcitizenclub.presentation.shared.components.HorizontalPagingNavigation
import com.example.smartcitizenclub.presentation.theme.OrangeGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(
    user: User,
    selectedScreen: UserScreen,
    onScreenSelected: (UserScreen) -> Unit,
    onLogout: () -> Unit,
    onSwitchToSidebar: () -> Unit,
    showBottomNavigation: Boolean = true // Controls whether to show the bottom navigation bar
) {
    var showChangePassword by remember { mutableStateOf(false) }
    var showChangePin by remember { mutableStateOf(false) }
    var showKYCSubmit by remember { mutableStateOf(false) }
    var showProfileEdit by remember { mutableStateOf(false) }
    var showSelectContact by remember { mutableStateOf(false) }
    var showNewGroup by remember { mutableStateOf(false) }
    var showNewContact by remember { mutableStateOf(false) }
    var showChatScreen by remember { mutableStateOf(false) }
    var selectedChat by remember { mutableStateOf<com.example.smartcitizenclub.presentation.feature.messages.models.Chat?>(null) }
    
    // Transaction History navigation states
    var showTransactionHistory by remember { mutableStateOf(false) }
    var showTransactionDetails by remember { mutableStateOf(false) }
    var selectedTransaction by remember { mutableStateOf<com.example.smartcitizenclub.presentation.feature.transaction.ui.Transaction?>(null) }
    
    // QR Scan navigation states
    var showQRScan by remember { mutableStateOf(false) }
    
    // Send Money navigation states
    var showSendMoney by remember { mutableStateOf(false) }
    var showSendMoneyAmount by remember { mutableStateOf(false) }
    var showSendMoneyConfirm by remember { mutableStateOf(false) }
    var showSendMoneySuccess by remember { mutableStateOf(false) }
    var showFinalConfirm by remember { mutableStateOf(false) }
    var selectedContact by remember { mutableStateOf<com.example.smartcitizenclub.presentation.feature.sendmoney.ui.Contact?>(null) }
    var selectedAmount by remember { mutableStateOf(0.0) }
    var selectedReference by remember { mutableStateOf<String?>(null) }
    
    // Cash Out navigation states
    var showCashOut by remember { mutableStateOf(false) }
    var showCashOutAmount by remember { mutableStateOf(false) }
    var showCashOutPin by remember { mutableStateOf(false) }
    var showCashOutConfirm by remember { mutableStateOf(false) }
    var showCashOutSuccess by remember { mutableStateOf(false) }
    var cashOutMobileNumber by remember { mutableStateOf("") }
    var cashOutAmount by remember { mutableStateOf(0.0) }
    var cashOutReference by remember { mutableStateOf<String?>(null) }
    
    // Add Money navigation states
    var showAddMoney by remember { mutableStateOf(false) }
    
    // Transfer Money navigation states
    var showTransferMoney by remember { mutableStateOf(false) }
    
    // Limit Upgrade navigation states
    var showLimitUpgrade by remember { mutableStateOf(false) }
    var showPackagePurchaseConfirmation by remember { mutableStateOf(false) }
    var showPackagePurchaseSuccess by remember { mutableStateOf(false) }
    var selectedPackage by remember { mutableStateOf<com.example.smartcitizenclub.presentation.feature.limitupgrade.models.Package?>(null) }
    
    // Mobile Recharge navigation states
    var showMobileRecharge by remember { mutableStateOf(false) }
    var showMobileRechargeAmount by remember { mutableStateOf(false) }
    var showMobileRechargePin by remember { mutableStateOf(false) }
    var showMobileRechargeConfirm by remember { mutableStateOf(false) }
    var showMobileRechargeSuccess by remember { mutableStateOf(false) }
    var rechargePhoneNumber by remember { mutableStateOf("") }
    var rechargeAmount by remember { mutableStateOf(0.0) }
    var rechargeReference by remember { mutableStateOf<String?>(null) }
    
    // Ticket Support navigation states
    var showTicketSupport by remember { mutableStateOf(false) }
    var showTicketDetails by remember { mutableStateOf(false) }
    var selectedTicket by remember { mutableStateOf<SupportTicket?>(null) }
    
    // Donation navigation states
    var showDonation by remember { mutableStateOf(false) }
    
    // Charge and Limit navigation states
    var showChargeLimit by remember { mutableStateOf(false) }
    
    // Investment navigation states
    var showInvestment by remember { mutableStateOf(false) }
    var showInvestmentAmount by remember { mutableStateOf(false) }
    var showInvestmentSuccess by remember { mutableStateOf(false) }
    var selectedInvestmentPackage by remember { mutableStateOf<com.example.smartcitizenclub.presentation.feature.investment.models.InvestmentPackage?>(null) }
    var investmentAmount by remember { mutableStateOf(0.0) }
    
    // Loan navigation states
    var showLoan by remember { mutableStateOf(false) }
    var showLoanConfirm by remember { mutableStateOf(false) }
    var showLoanSuccess by remember { mutableStateOf(false) }
    var loanAmount by remember { mutableStateOf(0.0) }
    
    // Loan Pay navigation states
    var showLoanPay by remember { mutableStateOf(false) }
    var showLoanPaymentAmount by remember { mutableStateOf(false) }
    var showLoanPaymentHistory by remember { mutableStateOf(false) }
    var showLateFeePayment by remember { mutableStateOf(false) }
    var selectedUserLoan by remember { mutableStateOf<UserLoan?>(null) }
    
    // Generic Payment navigation states
    var showPayment by remember { mutableStateOf(false) }
    var showPaymentAmount by remember { mutableStateOf(false) }
    var selectedPayment by remember { mutableStateOf<BillPayment?>(null) }
    
    Scaffold(
        bottomBar = {
            // Show bottom navigation only on main pages: Home, QRScan, Messages, Account, MySCC
            // Hide when any modal/overlay screens are shown
            if (showBottomNavigation && 
                selectedScreen in listOf(
                    UserScreen.Home,
                    UserScreen.QRScan,
                    UserScreen.Messages,
                    UserScreen.Account,
                    UserScreen.MySCC
                ) && 
                !showChangePassword && 
                !showChangePin && 
                !showProfileEdit && 
                !showKYCSubmit && 
                !showSelectContact && 
                !showNewGroup && 
                !showNewContact && 
                !showChatScreen && 
                !showTransactionHistory &&
                !showTransactionDetails &&
                !showQRScan &&
                !showSendMoney && 
                !showSendMoneyAmount && 
                !showSendMoneyConfirm && 
                !showSendMoneySuccess &&
                !showFinalConfirm && 
                !showCashOut && 
                !showCashOutAmount && 
                !showCashOutPin &&
                !showCashOutConfirm &&
                !showCashOutSuccess && 
                !showAddMoney &&
                !showTransferMoney &&
                !showLimitUpgrade &&
                !showPackagePurchaseConfirmation &&
                !showPackagePurchaseSuccess &&
                !showMobileRecharge && 
                !showMobileRechargeAmount && 
                !showMobileRechargePin &&
                !showMobileRechargeConfirm &&
                !showMobileRechargeSuccess && 
                !showTicketSupport &&
                !showTicketDetails && 
                !showDonation && 
                !showChargeLimit && 
                !showInvestment && 
                !showInvestmentAmount && 
                !showInvestmentSuccess && 
                !showLoan && 
                !showLoanConfirm &&
                !showLoanSuccess && 
                !showLoanPay && 
                !showLoanPaymentAmount && 
                !showLoanPaymentHistory && 
                !showLateFeePayment &&
                !showPayment && 
                !showPaymentAmount) {
                // Custom Bottom Navigation Bar with rounded corners and shadow
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .background(
                            MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .padding(vertical = 4.dp)
                        .windowInsetsPadding(WindowInsets.navigationBars), // Dynamic system navigation padding
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Home
                    CustomNavigationBarItem(
                        icon = UserScreen.Home.icon,
                        label = UserScreen.Home.title,
                        selected = selectedScreen == UserScreen.Home,
                        onClick = { onScreenSelected(UserScreen.Home) }
                    )
                    
                    // Messages
                    CustomNavigationBarItem(
                        icon = UserScreen.Messages.icon,
                        label = UserScreen.Messages.title,
                        selected = selectedScreen == UserScreen.Messages,
                        onClick = { onScreenSelected(UserScreen.Messages) }
                    )
                    
                    // QR Scan - 3rd item with border styling
                    QRScanNavigationBarItem(
                        icon = UserScreen.QRScan.icon,
                        label = UserScreen.QRScan.title,
                        selected = selectedScreen == UserScreen.QRScan,
                        onClick = { onScreenSelected(UserScreen.QRScan) }
                    )
                    
                    // Account
                    CustomNavigationBarItem(
                        icon = UserScreen.Account.icon,
                        label = UserScreen.Account.title,
                        selected = selectedScreen == UserScreen.Account,
                        onClick = { onScreenSelected(UserScreen.Account) }
                    )
                    
                    // My SCC
                    CustomNavigationBarItem(
                        icon = UserScreen.MySCC.icon,
                        label = UserScreen.MySCC.title,
                        selected = selectedScreen == UserScreen.MySCC,
                        onClick = { onScreenSelected(UserScreen.MySCC) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        ) {
            when {
                showChangePassword -> {
                    com.example.smartcitizenclub.presentation.feature.myscc.ui.ChangePasswordScreen(
                        onBackClick = { showChangePassword = false },
                        onPasswordChanged = { 
                            showChangePassword = false
                            // You can add success message or other actions here
                        }
                    )
                }
                
                showChangePin -> {
                    com.example.smartcitizenclub.presentation.feature.myscc.ui.ChangePinScreen(
                        onBackClick = { showChangePin = false },
                        onPinChanged = { 
                            showChangePin = false
                            // You can add success message or other actions here
                        }
                    )
                }
                showKYCSubmit -> {
                    com.example.smartcitizenclub.presentation.feature.myscc.ui.KYCSubmitScreen(
                        onBackClick = { showKYCSubmit = false },
                        onKYCSubmitted = { 
                            showKYCSubmit = false
                            // You can add success message or other actions here
                        }
                    )
                }
                
                showProfileEdit -> {
                    com.example.smartcitizenclub.presentation.feature.myscc.ui.ProfileEditScreen(
                        user = user,
                        onBackClick = { showProfileEdit = false },
                        onSaveProfile = { name, phone ->
                            // Handle profile save - you can update user data here
                            showProfileEdit = false
                        }
                    )
                }
                showSelectContact -> {
                    ContactSelectionScreen(
                        onBackClick = { showSelectContact = false },
                        onNewGroup = { 
                            showSelectContact = false
                            showNewGroup = true
                        },
                        onNewContact = { 
                            showSelectContact = false
                            showNewContact = true
                        },
                        onContactSelected = { contact ->
                            // TODO: Start chat with selected contact
                            showSelectContact = false
                        }
                    )
                }
                showNewGroup -> {
                    CreateNewGroupScreen(
                        onBackClick = { showNewGroup = false },
                        onNextClick = { 
                            // TODO: Navigate to group setup screen
                            showNewGroup = false
                        }
                    )
                }
                showNewContact -> {
                    AddNewContactScreen(
                        onBackClick = { showNewContact = false },
                        onSaveContact = { firstName, lastName, countryCode, phoneNumber, showQRCode ->
                            // TODO: Save contact to database
                            showNewContact = false
                        }
                    )
                }
                showChatScreen -> {
                    selectedChat?.let { chat ->
                        ChatScreen(
                            chat = chat,
                            user = user,
                            onBackClick = { 
                                showChatScreen = false
                                selectedChat = null
                            }
                        )
                    }
                }
                showTransactionDetails -> {
                    selectedTransaction?.let { transaction ->
                        TransactionDetailsScreen(
                            transaction = transaction,
                            onBackClick = { 
                                showTransactionDetails = false
                                selectedTransaction = null
                            }
                        )
                    }
                }
                showTransactionHistory -> {
                    TransactionHistoryScreen(
                        user = user,
                        onBackClick = { showTransactionHistory = false },
                        onTransactionClick = { transaction ->
                            selectedTransaction = transaction
                            showTransactionDetails = true
                        }
                    )
                }
                showQRScan -> {
                        com.example.smartcitizenclub.presentation.feature.qrscan.ui.QRScanScreen(
                            onBackClick = { showQRScan = false },
                            onQRCodeScanned = { qrData ->
                                // Handle scanned QR code data
                                // For now, just close the scanner
                                showQRScan = false
                                // TODO: Process QR code data based on content
                            },
                            onSendMoneyClick = { qrData ->
                                // Navigate to send money with QR data
                                showQRScan = false
                                showSendMoney = true
                                // TODO: Pass QR data to send money flow
                            },
                            onCashOutClick = { qrData ->
                                // Navigate to cash out with QR data
                                showQRScan = false
                                showCashOut = true
                                // TODO: Pass QR data to cash out flow
                            }
                        )
                    }
                showSendMoney -> {
                    com.example.smartcitizenclub.presentation.feature.sendmoney.ui.SendMoneyScreen(
                        onBackClick = { showSendMoney = false },
                        onContactClick = { contact ->
                            selectedContact = contact
                            showSendMoney = false
                            showSendMoneyAmount = true
                        },
                        onNavigateToAmount = { recipient, accountNumber, accountName ->
                            // Create a contact with the recipient info
                            selectedContact = Contact(
                                id = "manual_${recipient}",
                                name = accountName ?: "Unknown Contact", // Always show a name
                                phoneNumber = if (accountNumber != null) recipient else "", // If accountNumber exists, recipient is phone
                                accountNumber = accountNumber ?: recipient // If accountNumber is null, recipient is the account number
                            )
                            showSendMoney = false
                            showSendMoneyAmount = true
                        }
                    )
                }
                showSendMoneyAmount -> {
                    selectedContact?.let { contact ->
                        com.example.smartcitizenclub.presentation.feature.sendmoney.ui.SendMoneyAmountScreen(
                            contact = contact,
                            onBackClick = { showSendMoneyAmount = false },
                            onAmountEntered = { amount ->
                                selectedAmount = amount
                                showSendMoneyAmount = false
                                showSendMoneyConfirm = true
                            }
                        )
                    }
                }
                showSendMoneyConfirm -> {
                    selectedContact?.let { contact ->
                        com.example.smartcitizenclub.presentation.feature.sendmoney.ui.EnterPassScreen(
                            contact = contact,
                            amount = selectedAmount,
                            onBackClick = { showSendMoneyConfirm = false },
                            onConfirmClick = { pin, reference ->
                                // Store reference and navigate to final confirm screen
                                selectedReference = reference
                                showSendMoneyConfirm = false
                                showFinalConfirm = true
                            }
                        )
                    }
                }
                showFinalConfirm -> {
                    selectedContact?.let { contact ->
                        com.example.smartcitizenclub.presentation.feature.sendmoney.ui.ConfirmSendMoneyScreen(
                            contact = contact,
                            amount = selectedAmount,
                            reference = selectedReference,
                            onBackClick = { showFinalConfirm = false },
                            onSendComplete = {
                                showFinalConfirm = false
                                showSendMoneySuccess = true
                            }
                        )
                    }
                }
                showSendMoneySuccess -> {
                    selectedContact?.let { contact ->
                        com.example.smartcitizenclub.presentation.feature.sendmoney.ui.SendMoneySuccessScreen(
                            contact = contact,
                            amount = selectedAmount,
                            reference = selectedReference,
                            onBackToHome = {
                                // Reset all send money states and go to home
                                showSendMoneySuccess = false
                                selectedContact = null
                                selectedAmount = 0.0
                                selectedReference = null
                            },
                            onNewTransaction = {
                                // Reset all send money states and start new transaction
                                showSendMoneySuccess = false
                                selectedContact = null
                                selectedAmount = 0.0
                                selectedReference = null
                                showSendMoney = true
                            }
                        )
                    }
                }
                showCashOut -> {
                    com.example.smartcitizenclub.presentation.feature.cashout.ui.CashOutScreen(
                        onBackClick = { showCashOut = false },
                        onNumberEntered = { mobileNumber ->
                            cashOutMobileNumber = mobileNumber
                            showCashOut = false
                            showCashOutAmount = true
                        }
                    )
                }
                showCashOutAmount -> {
                    com.example.smartcitizenclub.presentation.feature.cashout.ui.CashOutAmountScreen(
                        mobileNumber = cashOutMobileNumber,
                        onBackClick = { showCashOutAmount = false },
                        onAmountEntered = { amount ->
                            cashOutAmount = amount
                            showCashOutAmount = false
                            showCashOutPin = true
                        }
                    )
                }
                showCashOutPin -> {
                    com.example.smartcitizenclub.presentation.feature.cashout.ui.CashOutPinScreen(
                        mobileNumber = cashOutMobileNumber,
                        amount = cashOutAmount,
                        onBackClick = { showCashOutPin = false },
                        onPinEntered = { pin, reference ->
                            cashOutReference = reference
                            showCashOutPin = false
                            showCashOutConfirm = true
                        }
                    )
                }
                showCashOutConfirm -> {
                    com.example.smartcitizenclub.presentation.feature.cashout.ui.CashOutConfirmScreen(
                        mobileNumber = cashOutMobileNumber,
                        amount = cashOutAmount,
                        reference = cashOutReference,
                        onBackClick = { showCashOutConfirm = false },
                        onCashOutComplete = {
                            showCashOutConfirm = false
                            showCashOutSuccess = true
                        }
                    )
                }
                showCashOutSuccess -> {
                    com.example.smartcitizenclub.presentation.feature.cashout.ui.CashOutSuccessScreen(
                        mobileNumber = cashOutMobileNumber,
                        amount = cashOutAmount,
                        reference = cashOutReference,
                        onBackToHome = {
                            // Reset all cashout states and go to home
                            showCashOutSuccess = false
                            cashOutMobileNumber = ""
                            cashOutAmount = 0.0
                            cashOutReference = null
                        },
                        onNewTransaction = {
                            // Reset all cashout states and start new transaction
                            showCashOutSuccess = false
                            cashOutMobileNumber = ""
                            cashOutAmount = 0.0
                            cashOutReference = null
                            showCashOut = true
                        }
                    )
                }
                showAddMoney -> {
                    com.example.smartcitizenclub.presentation.feature.addmoney.ui.AddMoneyScreen(
                        onBackClick = { showAddMoney = false },
                        onBinanceClick = { 
                            // TODO: Handle Binance transfer
                            showAddMoney = false
                        },
                        onBkashClick = { 
                            // TODO: Handle Bkash transfer
                            showAddMoney = false
                        },
                        onNagadClick = { 
                            // TODO: Handle Nagad transfer
                            showAddMoney = false
                        },
                        onRocketClick = { 
                            // TODO: Handle Rocket transfer
                            showAddMoney = false
                        }
                    )
                }
                showTransferMoney -> {
                    com.example.smartcitizenclub.presentation.feature.transfermoney.ui.TransferMoneyScreen(
                        onBackClick = { showTransferMoney = false },
                        onSccToBinanceClick = { 
                            // TODO: Handle SCC to Binance transfer
                            showTransferMoney = false
                        },
                        onSccToBkashClick = { 
                            // TODO: Handle SCC to Bkash transfer
                            showTransferMoney = false
                        },
                        onSccToNagadClick = { 
                            // TODO: Handle SCC to Nagad transfer
                            showTransferMoney = false
                        },
                        onSccToRocketClick = { 
                            // TODO: Handle SCC to Rocket transfer
                            showTransferMoney = false
                        }
                    )
                }
                showLimitUpgrade -> {
                    com.example.smartcitizenclub.presentation.feature.limitupgrade.ui.LimitUpgradeScreen(
                        onBackClick = { showLimitUpgrade = false },
                        onPackageSelected = { packageItem ->
                            selectedPackage = packageItem
                            showLimitUpgrade = false
                            showPackagePurchaseConfirmation = true
                        }
                    )
                }
                showPackagePurchaseConfirmation -> {
                    selectedPackage?.let { packageItem ->
                        com.example.smartcitizenclub.presentation.feature.limitupgrade.ui.PackagePurchaseConfirmationScreen(
                            packageItem = packageItem,
                            onBackClick = { 
                                showPackagePurchaseConfirmation = false
                                showLimitUpgrade = true
                            },
                            onConfirmPurchase = {
                                showPackagePurchaseConfirmation = false
                                showPackagePurchaseSuccess = true
                            },
                            onCancelPurchase = {
                                showPackagePurchaseConfirmation = false
                                showLimitUpgrade = true
                            }
                        )
                    }
                }
                showPackagePurchaseSuccess -> {
                    selectedPackage?.let { packageItem ->
                        com.example.smartcitizenclub.presentation.feature.limitupgrade.ui.PackagePurchaseSuccessScreen(
                            packageItem = packageItem,
                            onBackToHome = {
                                showPackagePurchaseSuccess = false
                            },
                            onViewPackages = {
                                showPackagePurchaseSuccess = false
                                showLimitUpgrade = true
                            },
                            onViewTransactionHistory = {
                                showPackagePurchaseSuccess = false
                                showTransactionHistory = true
                            }
                        )
                    }
                }
                showMobileRecharge -> {
                    com.example.smartcitizenclub.presentation.feature.recharge.ui.MobileRechargeScreen(
                        onBackClick = { showMobileRecharge = false },
                        onNumberEntered = { phoneNumber ->
                            rechargePhoneNumber = phoneNumber
                            showMobileRecharge = false
                            showMobileRechargeAmount = true
                        }
                    )
                }
                showMobileRechargeAmount -> {
                    com.example.smartcitizenclub.presentation.feature.recharge.ui.MobileRechargeAmountScreen(
                        phoneNumber = rechargePhoneNumber,
                        onBackClick = { showMobileRechargeAmount = false },
                        onAmountEntered = { amount ->
                            rechargeAmount = amount
                            showMobileRechargeAmount = false
                            showMobileRechargePin = true
                        }
                    )
                }
                showMobileRechargePin -> {
                    com.example.smartcitizenclub.presentation.feature.recharge.ui.MobileRechargePinScreen(
                        phoneNumber = rechargePhoneNumber,
                        amount = rechargeAmount,
                        onBackClick = { showMobileRechargePin = false },
                        onPinEntered = { pin ->
                            showMobileRechargePin = false
                            showMobileRechargeConfirm = true
                        }
                    )
                }
                showMobileRechargeConfirm -> {
                    com.example.smartcitizenclub.presentation.feature.recharge.ui.MobileRechargeConfirmScreen(
                        phoneNumber = rechargePhoneNumber,
                        amount = rechargeAmount,
                        onBackClick = { showMobileRechargeConfirm = false },
                        onRechargeComplete = {
                            showMobileRechargeConfirm = false
                            showMobileRechargeSuccess = true
                        }
                    )
                }
                showMobileRechargeSuccess -> {
                    com.example.smartcitizenclub.presentation.feature.recharge.ui.MobileRechargeSuccessScreen(
                        phoneNumber = rechargePhoneNumber,
                        amount = rechargeAmount,
                        onBackToHome = {
                            // Reset all mobile recharge states and go to home
                            showMobileRechargeSuccess = false
                            rechargePhoneNumber = ""
                            rechargeAmount = 0.0
                            rechargeReference = null
                        },
                        onNewTransaction = {
                            // Reset all mobile recharge states and start new transaction
                            showMobileRechargeSuccess = false
                            rechargePhoneNumber = ""
                            rechargeAmount = 0.0
                            rechargeReference = null
                            showMobileRecharge = true
                        }
                    )
                }
                showTicketSupport -> {
                    TicketSupportScreen(
                        user = user,
                        onBackClick = { showTicketSupport = false },
                        onTicketSubmitted = { ticket ->
                            // TODO: Save ticket to database
                            showTicketSupport = false
                            selectedTicket = ticket
                            showTicketDetails = true
                        }
                    )
                }
                showDonation -> {
                    com.example.smartcitizenclub.presentation.feature.donation.ui.DonationScreen(
                        user = user,
                        onBackClick = { showDonation = false }
                    )
                }
                showChargeLimit -> {
                    com.example.smartcitizenclub.presentation.feature.chargelimit.ui.ChargeLimitScreen(
                        user = user,
                        onBackClick = { showChargeLimit = false }
                    )
                }
                showInvestment -> {
                    com.example.smartcitizenclub.presentation.feature.investment.ui.InvestmentScreen(
                        user = user,
                        onBackClick = { showInvestment = false },
                        onInvestmentSelected = { packageItem ->
                            selectedInvestmentPackage = packageItem
                            showInvestment = false
                            showInvestmentAmount = true
                        }
                    )
                }
                showInvestmentAmount -> {
                    selectedInvestmentPackage?.let { packageItem ->
                        com.example.smartcitizenclub.presentation.feature.investment.ui.InvestmentAmountScreen(
                            investmentPackage = packageItem,
                            onBackClick = { showInvestmentAmount = false },
                            onAmountEntered = { amount ->
                                investmentAmount = amount
                                showInvestmentAmount = false
                                showInvestmentSuccess = true
                            }
                        )
                    }
                }
                showInvestmentSuccess -> {
                    selectedInvestmentPackage?.let { packageItem ->
                        com.example.smartcitizenclub.presentation.feature.investment.ui.InvestmentSuccessScreen(
                            investmentPackage = packageItem,
                            amount = investmentAmount,
                            onBackClick = { 
                                showInvestmentSuccess = false
                                selectedInvestmentPackage = null
                                investmentAmount = 0.0
                            }
                        )
                    }
                }
                showLoan -> {
                    LoanScreen(
                        onBackClick = { showLoan = false },
                        onLoanConfirm = { amount ->
                            loanAmount = amount
                            showLoan = false
                            showLoanConfirm = true
                        }
                    )
                }
                showLoanConfirm -> {
                    LoanConfirmScreen(
                        loanAmount = loanAmount,
                        interestRate = 12.5,
                        tenure = 12,
                        onBackClick = { showLoanConfirm = false },
                        onLoanComplete = {
                            showLoanConfirm = false
                            showLoanSuccess = true
                        }
                    )
                }
                showLoanSuccess -> {
                    LoanSuccessScreen(
                        loanAmount = loanAmount,
                        onBackClick = { 
                            showLoanSuccess = false
                            loanAmount = 0.0
                        }
                    )
                }
                showLoanPay -> {
                    // Sample user loans for demonstration
                    val userLoans = listOf(
                        UserLoan(
                            id = "1",
                            userId = user.id,
                            loanId = "1",
                            loanNumber = "LN001234",
                            subAccountId = "SUB001",
                            amount = 50000.0,
                            tenure = 12,
                            interestRate = 12.5,
                            processingFee = 2.0,
                            emiAmount = 4467.89,
                            totalAmount = 53614.68,
                            startDate = System.currentTimeMillis() - (3 * 30 * 24 * 60 * 60 * 1000L),
                            endDate = System.currentTimeMillis() + (9 * 30 * 24 * 60 * 60 * 1000L),
                            status = LoanStatus.ACTIVE,
                            remainingAmount = 38000.0,
                            nextPaymentDate = System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000L),
                            hasOverduePayments = true,
                            totalLateFeeAmount = 500.0
                        )
                    )
                    LoanPayScreen(
                        userLoans = userLoans,
                        onBackClick = { showLoanPay = false },
                        onLoanPayment = { userLoan ->
                            selectedUserLoan = userLoan
                            showLoanPay = false
                            showLoanPaymentAmount = true
                        },
                        onPaymentHistory = {
                            showLoanPay = false
                            showLoanPaymentHistory = true
                        },
                        onLateFeePayment = { userLoan ->
                            selectedUserLoan = userLoan
                            showLoanPay = false
                            showLateFeePayment = true
                        }
                    )
                }
                showLoanPaymentAmount -> {
                    selectedUserLoan?.let { userLoan ->
                        LoanPaymentAmountScreen(
                            userLoan = userLoan,
                            onBackClick = { showLoanPaymentAmount = false },
                            onAmountEntered = { amount ->
                                // TODO: Process the loan payment
                                showLoanPaymentAmount = false
                                selectedUserLoan = null
                            },
                            onPayLateFees = {
                                showLoanPaymentAmount = false
                                showLateFeePayment = true
                            }
                        )
                    }
                }
                showLoanPaymentHistory -> {
                    // Sample payment history for demonstration
                    val payments = listOf(
                        LoanPayment(
                            id = "1",
                            loanId = "1",
                            amount = 4467.89,
                            paymentDate = System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000L),
                            paymentMethod = "SCC Wallet",
                            status = "Completed",
                            transactionId = "TXN${System.currentTimeMillis()}",
                            paymentType = com.example.smartcitizenclub.presentation.feature.loan.ui.PaymentType.LOAN_PAYMENT,
                            description = "Monthly EMI payment"
                        ),
                        LoanPayment(
                            id = "2",
                            loanId = "1",
                            amount = 500.0,
                            paymentDate = System.currentTimeMillis() - (45 * 24 * 60 * 60 * 1000L),
                            paymentMethod = "SCC Wallet",
                            status = "Completed",
                            transactionId = "TXN${System.currentTimeMillis() - 1000}",
                            paymentType = com.example.smartcitizenclub.presentation.feature.loan.ui.PaymentType.LATE_FEE_PAYMENT,
                            description = "Late fee for overdue payment"
                        )
                    )
                    LoanPaymentHistoryScreen(
                        payments = payments,
                        onBackClick = { showLoanPaymentHistory = false }
                    )
                }
                showLateFeePayment -> {
                    selectedUserLoan?.let { userLoan ->
                        // Sample late fees for demonstration
                        val lateFees = listOf(
                            LateFee(
                                id = "LF001",
                                loanIssuedId = "LI001",
                                subAccountId = userLoan.subAccountId,
                                amount = 300.0,
                                balanceBefore = userLoan.remainingAmount,
                                balanceAfter = userLoan.remainingAmount,
                                description = "Late fee for overdue EMI payment",
                                status = LateFeeStatus.PENDING,
                                createdDate = System.currentTimeMillis() - (10 * 24 * 60 * 60 * 1000L)
                            ),
                            LateFee(
                                id = "LF002",
                                loanIssuedId = "LI001",
                                subAccountId = userLoan.subAccountId,
                                amount = 200.0,
                                balanceBefore = userLoan.remainingAmount,
                                balanceAfter = userLoan.remainingAmount,
                                description = "Late fee for overdue EMI payment",
                                status = LateFeeStatus.PENDING,
                                createdDate = System.currentTimeMillis() - (5 * 24 * 60 * 60 * 1000L)
                            )
                        )
                        LateFeePaymentScreen(
                            userLoan = userLoan,
                            lateFees = lateFees,
                            onBackClick = { 
                                showLateFeePayment = false
                                selectedUserLoan = null
                            },
                            onPayLateFee = { lateFee, amount ->
                                // TODO: Process late fee payment
                                showLateFeePayment = false
                                selectedUserLoan = null
                            }
                        )
                    }
                }
                showPayment -> {
                    // Sample payments for demonstration
                    val payments = listOf(
                        BillPayment(
                            id = "1",
                            type = BillPaymentType.UTILITY,
                            title = "Loan Payment",
                            description = "Monthly loan installment",
                            amount = 4467.89,
                            dueDate = System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000L),
                            status = BillPaymentStatus.PENDING
                        ),
                        BillPayment(
                            id = "2",
                            type = BillPaymentType.MOBILE_INTERNET,
                            title = "Mobile Recharge",
                            description = "Prepaid mobile recharge",
                            amount = 100.0,
                            status = BillPaymentStatus.PENDING
                        )
                    )
                    BillPayScreen(
                        payments = payments,
                        onBackClick = { showPayment = false },
                        onPaymentSelected = { payment ->
                            selectedPayment = payment
                            showPayment = false
                            showPaymentAmount = true
                        }
                    )
                }
                showPaymentAmount -> {
                    selectedPayment?.let { payment ->
                        BillPaymentAmountScreen(
                            payment = payment,
                            onBackClick = { showPaymentAmount = false },
                            onAmountEntered = { amount, paymentMethod, pin ->
                                // TODO: Process the payment
                                showPaymentAmount = false
                                selectedPayment = null
                            }
                        )
                    }
                }
                else -> {
                    // Use HorizontalPagingNavigation for all main screens
                    HorizontalPagingNavigation(
                        user = user,
                        selectedScreen = selectedScreen,
                        onScreenSelected = onScreenSelected,
                        onLogout = onLogout,
                        onShowSelectContact = { showSelectContact = true },
                        onChatClick = { chat ->
                            selectedChat = chat
                            showChatScreen = true
                        },
                        onNavigateToChat = { chat ->
                            selectedChat = chat
                            showChatScreen = true
                        },
                        onShowNewGroup = { showNewGroup = true },
                        onShowNewContact = { showNewContact = true },
                        onShowTransactionHistory = { showTransactionHistory = true },
                        onShowQRScan = { showQRScan = true },
                        onShowSendMoney = { showSendMoney = true },
                        onShowCashOut = { showCashOut = true },
                        onShowMobileRecharge = { showMobileRecharge = true },
                        onShowAddMoney = { showAddMoney = true },
                        onShowTransferMoney = { showTransferMoney = true },
                        onShowLimitUpgrade = { showLimitUpgrade = true },
                        onShowInvestment = { showInvestment = true },
                        onShowLoan = { showLoan = true },
                        onShowLoanPay = { showLoanPay = true },
                        onShowBillPay = { showPayment = true },
                        onShowTicketSupport = { showTicketSupport = true },
                        onShowChargeLimit = { showChargeLimit = true },
                        onShowDonation = { showDonation = true },
                        onShowChangePassword = { showChangePassword = true },
                        onShowChangePin = { showChangePin = true },
                        onShowKYCSubmit = { showKYCSubmit = true },
                        onShowProfileEdit = { showProfileEdit = true }
                    )
                }
            }
        }
    }
}

@Composable
fun CustomNavigationBarItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // Remove focus/click background
            ) { onClick() }
            .padding(vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon with gradient background when selected
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        brush = if (selected) OrangeGradient else Brush.linearGradient(
                            colors = listOf(
                                androidx.compose.ui.graphics.Color.Transparent,
                                androidx.compose.ui.graphics.Color.Transparent
                            )
                        ),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = if (selected) androidx.compose.ui.graphics.Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            // Label
            Text(
                text = label,
                fontSize = 12.sp,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                maxLines = 1,
            )
        }
    }
}

@Composable
fun QRScanNavigationBarItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // Remove focus/click background
            ) { onClick() }
            .padding(vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // QR Scan icon with primary color border
            Box(
                modifier = Modifier
                    .size(48.dp) // Same size as other items
                    .background(
                        color = androidx.compose.ui.graphics.Color.Transparent,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
                    .border(
                        width = 2.dp,
                        brush = OrangeGradient,
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp) // Same size as other items
                )
            }
            
            // Label
            Text(
                text = label,
                fontSize = 12.sp,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
