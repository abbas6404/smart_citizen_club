package com.example.smartcitizenclub.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.presentation.theme.OrangeGradient
import com.example.smartcitizenclub.presentation.UserScreen
import com.example.smartcitizenclub.presentation.feature.home.ui.HomeScreen
import com.example.smartcitizenclub.presentation.feature.account.ui.AccountScreen
import com.example.smartcitizenclub.presentation.feature.finance.ui.FinanceScreen
import com.example.smartcitizenclub.presentation.feature.messages.ui.MessagesScreen
import com.example.smartcitizenclub.presentation.feature.myscc.ui.MySCCScreen
import com.example.smartcitizenclub.presentation.feature.home.ui.HomeScreen
import com.example.smartcitizenclub.presentation.feature.cashout.ui.CashOutProvider
import com.example.smartcitizenclub.presentation.feature.recharge.ui.MobileOperator
import com.example.smartcitizenclub.presentation.feature.contact.ui.SupportTicket
import com.example.smartcitizenclub.presentation.feature.donation.ui.DonationCampaign
import com.example.smartcitizenclub.presentation.feature.donation.ui.UserDonation
import com.example.smartcitizenclub.presentation.feature.packages.ui.*
import com.example.smartcitizenclub.presentation.feature.loan.ui.*
import com.example.smartcitizenclub.presentation.feature.payments.ui.*
import com.example.smartcitizenclub.presentation.feature.sendmoney.ui.Contact
import com.example.smartcitizenclub.presentation.feature.sendmoney.ui.ConfirmSendMoneyScreen
import com.example.smartcitizenclub.presentation.feature.sendmoney.ui.SendMoneySuccessScreen

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
    var showKYCSubmit by remember { mutableStateOf(false) }
    var showSelectContact by remember { mutableStateOf(false) }
    var showNewGroup by remember { mutableStateOf(false) }
    var showNewContact by remember { mutableStateOf(false) }
    var showChatScreen by remember { mutableStateOf(false) }
    var selectedChat by remember { mutableStateOf<com.example.smartcitizenclub.presentation.feature.messages.ui.Chat?>(null) }
    
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
    
    // Mobile Recharge navigation states
    var showMobileRecharge by remember { mutableStateOf(false) }
    var showMobileRechargeAmount by remember { mutableStateOf(false) }
    var showMobileRechargePin by remember { mutableStateOf(false) }
    var showMobileRechargeConfirm by remember { mutableStateOf(false) }
    var showMobileRechargeSuccess by remember { mutableStateOf(false) }
    var rechargePhoneNumber by remember { mutableStateOf("") }
    var rechargeAmount by remember { mutableStateOf(0.0) }
    var rechargeReference by remember { mutableStateOf<String?>(null) }
    
    // Contact Us navigation states
    var showContactUs by remember { mutableStateOf(false) }
    var showTicketList by remember { mutableStateOf(false) }
    var showTicketDetails by remember { mutableStateOf(false) }
    var selectedTicket by remember { mutableStateOf<com.example.smartcitizenclub.presentation.feature.contact.ui.SupportTicket?>(null) }
    
    // Donation navigation states
    var showDonation by remember { mutableStateOf(false) }
    var showDonationHistory by remember { mutableStateOf(false) }
    var selectedCampaign by remember { mutableStateOf<com.example.smartcitizenclub.presentation.feature.donation.ui.DonationCampaign?>(null) }
    
    // Charge and Limit navigation states
    var showChargeLimit by remember { mutableStateOf(false) }
    
    // Investment navigation states
    var showInvestment by remember { mutableStateOf(false) }
    var showInvestmentAmount by remember { mutableStateOf(false) }
    var showInvestmentPin by remember { mutableStateOf(false) }
    var showInvestmentSuccess by remember { mutableStateOf(false) }
    var selectedInvestmentOption by remember { mutableStateOf<com.example.smartcitizenclub.presentation.feature.investment.ui.InvestmentOption?>(null) }
    var investmentAmount by remember { mutableStateOf(0.0) }
    
    // Loan navigation states
    var showLoan by remember { mutableStateOf(false) }
    var showLoanAmount by remember { mutableStateOf(false) }
    var showLoanConfirm by remember { mutableStateOf(false) }
    var selectedLoan by remember { mutableStateOf<Loan?>(null) }
    var loanAmount by remember { mutableStateOf(0.0) }
    var loanTenure by remember { mutableStateOf(0) }
    
    // Loan Payment navigation states
    var showLoanPayment by remember { mutableStateOf(false) }
    var showLoanPaymentAmount by remember { mutableStateOf(false) }
    var selectedUserLoan by remember { mutableStateOf<UserLoan?>(null) }
    
    // Generic Payment navigation states
    var showPayment by remember { mutableStateOf(false) }
    var showPaymentAmount by remember { mutableStateOf(false) }
    var selectedPayment by remember { mutableStateOf<Payment?>(null) }
    
    Scaffold(
        bottomBar = {
            // Show bottom navigation only on main pages: Home, Finance, Messages, Account, MySCC
            // Hide when any modal/overlay screens are shown
            if (showBottomNavigation && 
                selectedScreen in listOf(
                    UserScreen.Home,
                    UserScreen.Finance,
                    UserScreen.Messages,
                    UserScreen.Account,
                    UserScreen.MySCC
                ) && 
                !showChangePassword && 
                !showKYCSubmit && 
                !showSelectContact && 
                !showNewGroup && 
                !showNewContact && 
                !showChatScreen && 
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
                !showMobileRecharge && 
                !showMobileRechargeAmount && 
                !showMobileRechargePin &&
                !showMobileRechargeConfirm &&
                !showMobileRechargeSuccess && 
                !showContactUs && 
                !showTicketList && 
                !showTicketDetails && 
                !showDonation && 
                !showDonationHistory && 
                !showChargeLimit && 
                !showInvestment && 
                !showInvestmentAmount && 
                !showInvestmentPin && 
                !showInvestmentSuccess && 
                !showLoan && 
                !showLoanAmount && 
                !showLoanConfirm && 
                !showLoanPayment && 
                !showLoanPaymentAmount && 
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
                    
                    // Finance
                    CustomNavigationBarItem(
                        icon = UserScreen.Finance.icon,
                        label = UserScreen.Finance.title,
                        selected = selectedScreen == UserScreen.Finance,
                        onClick = { onScreenSelected(UserScreen.Finance) }
                    )
                    
                    // Messages
                    CustomNavigationBarItem(
                        icon = UserScreen.Messages.icon,
                        label = UserScreen.Messages.title,
                        selected = selectedScreen == UserScreen.Messages,
                        onClick = { onScreenSelected(UserScreen.Messages) }
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
                showKYCSubmit -> {
                    com.example.smartcitizenclub.presentation.feature.myscc.ui.KYCSubmitScreen(
                        onBackClick = { showKYCSubmit = false },
                        onKYCSubmitted = { 
                            showKYCSubmit = false
                            // You can add success message or other actions here
                        }
                    )
                }
                showSelectContact -> {
                    com.example.smartcitizenclub.presentation.feature.messages.ui.SelectContactScreen(
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
                    com.example.smartcitizenclub.presentation.feature.messages.ui.NewGroupScreen(
                        onBackClick = { showNewGroup = false },
                        onNextClick = { 
                            // TODO: Navigate to group setup screen
                            showNewGroup = false
                        }
                    )
                }
                showNewContact -> {
                    com.example.smartcitizenclub.presentation.feature.messages.ui.NewContactScreen(
                        onBackClick = { showNewContact = false },
                        onSaveContact = { firstName, lastName, countryCode, phoneNumber, showQRCode ->
                            // TODO: Save contact to database
                            showNewContact = false
                        }
                    )
                }
                showChatScreen -> {
                    selectedChat?.let { chat ->
                        com.example.smartcitizenclub.presentation.feature.messages.ui.ChatScreen(
                            chat = chat,
                            currentUser = user,
                            onBackClick = { 
                                showChatScreen = false
                                selectedChat = null
                            }
                        )
                    }
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
                        onBasicUpgradeClick = { 
                            // TODO: Handle Basic upgrade
                            showLimitUpgrade = false
                        },
                        onPremiumUpgradeClick = { 
                            // TODO: Handle Premium upgrade
                            showLimitUpgrade = false
                        },
                        onGoldUpgradeClick = { 
                            // TODO: Handle Gold upgrade
                            showLimitUpgrade = false
                        },
                        onPlatinumUpgradeClick = { 
                            // TODO: Handle Platinum upgrade
                            showLimitUpgrade = false
                        }
                    )
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
                showContactUs -> {
                    com.example.smartcitizenclub.presentation.feature.contact.ui.ContactUsScreen(
                        user = user,
                        onBackClick = { showContactUs = false },
                        onTicketSubmitted = { ticket ->
                            // TODO: Save ticket to database
                            showContactUs = false
                            selectedTicket = ticket
                            showTicketDetails = true
                        }
                    )
                }
                showTicketList -> {
                    com.example.smartcitizenclub.presentation.feature.contact.ui.TicketListScreen(
                        user = user,
                        onBackClick = { showTicketList = false },
                        onTicketSelected = { ticket ->
                            selectedTicket = ticket
                            showTicketList = false
                            showTicketDetails = true
                        },
                        onCreateNewTicket = {
                            showTicketList = false
                            showContactUs = true
                        }
                    )
                }
                showTicketDetails -> {
                    selectedTicket?.let { ticket ->
                        com.example.smartcitizenclub.presentation.feature.contact.ui.TicketDetailsScreen(
                            ticket = ticket,
                            onBackClick = { showTicketDetails = false }
                        )
                    }
                }
                showDonation -> {
                    com.example.smartcitizenclub.presentation.feature.donation.ui.DonationScreen(
                        user = user,
                        onBackClick = { showDonation = false },
                        onDonationSuccess = { donation ->
                            // TODO: Save donation to database
                            showDonation = false
                            showDonationHistory = true
                        }
                    )
                }
                showDonationHistory -> {
                    com.example.smartcitizenclub.presentation.feature.donation.ui.DonationHistoryScreen(
                        user = user,
                        onBackClick = { showDonationHistory = false },
                        onDonateAgain = { campaign ->
                            selectedCampaign = campaign
                            showDonationHistory = false
                            showDonation = true
                        }
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
                        onInvestmentSelected = { option ->
                            selectedInvestmentOption = option
                            showInvestment = false
                            showInvestmentAmount = true
                        }
                    )
                }
                showInvestmentAmount -> {
                    selectedInvestmentOption?.let { option ->
                        com.example.smartcitizenclub.presentation.feature.investment.ui.InvestmentAmountScreen(
                            option = option,
                            onBackClick = { showInvestmentAmount = false },
                            onAmountEntered = { amount ->
                                investmentAmount = amount
                                showInvestmentAmount = false
                                showInvestmentPin = true
                            }
                        )
                    }
                }
                showInvestmentPin -> {
                    selectedInvestmentOption?.let { option ->
                        com.example.smartcitizenclub.presentation.feature.investment.ui.InvestmentPinScreen(
                            option = option,
                            amount = investmentAmount,
                            onBackClick = { showInvestmentPin = false },
                            onPinEntered = { pin ->
                                // TODO: Process the investment transaction
                                showInvestmentPin = false
                                showInvestmentSuccess = true
                            }
                        )
                    }
                }
                showInvestmentSuccess -> {
                    selectedInvestmentOption?.let { option ->
                        com.example.smartcitizenclub.presentation.feature.investment.ui.InvestmentSuccessScreen(
                            option = option,
                            amount = investmentAmount,
                            onBackClick = { 
                                showInvestmentSuccess = false
                                selectedInvestmentOption = null
                                investmentAmount = 0.0
                            },
                            onViewPortfolioClick = {
                                // TODO: Navigate to portfolio
                                showInvestmentSuccess = false
                                selectedInvestmentOption = null
                                investmentAmount = 0.0
                            }
                        )
                    }
                }
                showLoan -> {
                    LoanScreen(
                        onBackClick = { showLoan = false },
                        onLoanClick = { loan ->
                            selectedLoan = loan
                            showLoan = false
                            showLoanAmount = true
                        }
                    )
                }
                showLoanAmount -> {
                    selectedLoan?.let { loan ->
                        LoanAmountScreen(
                            loan = loan,
                            onBackClick = { showLoanAmount = false },
                            onAmountEntered = { amount, tenure ->
                                loanAmount = amount
                                loanTenure = tenure
                                showLoanAmount = false
                                showLoanConfirm = true
                            }
                        )
                    }
                }
                showLoanConfirm -> {
                    selectedLoan?.let { loan ->
                        LoanConfirmScreen(
                            loan = loan,
                            amount = loanAmount,
                            tenure = loanTenure,
                            onBackClick = { showLoanConfirm = false },
                            onConfirmClick = { pin ->
                                // TODO: Process the loan application
                                showLoanConfirm = false
                                selectedLoan = null
                                loanAmount = 0.0
                                loanTenure = 0
                            }
                        )
                    }
                }
                showLoanPayment -> {
                    // Sample user loans for demonstration
                    val userLoans = listOf(
                        UserLoan(
                            id = "1",
                            userId = user.id,
                            loanId = "1",
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
                            nextPaymentDate = System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000L)
                        )
                    )
                    LoanPayScreen(
                        userLoans = userLoans,
                        onBackClick = { showLoanPayment = false },
                        onLoanPayment = { userLoan ->
                            selectedUserLoan = userLoan
                            showLoanPayment = false
                            showLoanPaymentAmount = true
                        }
                    )
                }
                showLoanPaymentAmount -> {
                    selectedUserLoan?.let { userLoan ->
                        LoanPaymentAmountScreen(
                            userLoan = userLoan,
                            onBackClick = { showLoanPaymentAmount = false },
                            onAmountEntered = { amount, paymentMethod, pin ->
                                // TODO: Process the loan payment
                                showLoanPaymentAmount = false
                                selectedUserLoan = null
                            }
                        )
                    }
                }
                showPayment -> {
                    // Sample payments for demonstration
                    val payments = listOf(
                        Payment(
                            id = "1",
                            type = PaymentType.LOAN,
                            title = "Loan Payment",
                            description = "Monthly loan installment",
                            amount = 4467.89,
                            dueDate = System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000L),
                            status = PaymentStatus.PENDING
                        ),
                        Payment(
                            id = "2",
                            type = PaymentType.MOBILE_RECHARGE,
                            title = "Mobile Recharge",
                            description = "Prepaid mobile recharge",
                            amount = 100.0,
                            status = PaymentStatus.PENDING
                        )
                    )
                    PaymentScreen(
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
                        PaymentAmountScreen(
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
                when (selectedScreen) {
                    UserScreen.Home -> HomeScreen(
                        user = user,
                        onScanQRClick = { showQRScan = true },
                        onSendMoneyClick = { showSendMoney = true },
                        onCashOutClick = { showCashOut = true },
                        onMobileRechargeClick = { showMobileRecharge = true },
                        onAddMoneyClick = { showAddMoney = true },
                        onTransferMoneyClick = { showTransferMoney = true },
                        onLimitUpgradeClick = { showLimitUpgrade = true },
                        onInvestmentClick = { showInvestment = true },
                        onLoanClick = { showLoan = true },
                        onContactUsClick = { showContactUs = true },
                        onChargeLimitClick = { showChargeLimit = true },
                        onDonationClick = { showDonation = true }
                    )
                    UserScreen.Finance -> FinanceScreen(
                        user = user,
                        onLoanPaymentClick = { showPayment = true }
                    )
                    UserScreen.Messages -> MessagesScreen(
                        user = user,
                        onShowSelectContact = { showSelectContact = true },
                        onChatClick = { chat ->
                            selectedChat = chat
                            showChatScreen = true
                        }
                    )
                    UserScreen.Account -> AccountScreen(user, onLogout)
                    UserScreen.MySCC -> MySCCScreen(
                        user = user, 
                        onLogout = onLogout,
                        onNavigateToChangePassword = { showChangePassword = true },
                        onNavigateToKYCSubmit = { showKYCSubmit = true }
                    )
                }
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
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
