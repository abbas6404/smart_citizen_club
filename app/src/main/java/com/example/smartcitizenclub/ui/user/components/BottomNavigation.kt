package com.example.smartcitizenclub.ui.user.components

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import com.example.smartcitizenclub.data.User
import com.example.smartcitizenclub.presentation.theme.OrangeGradient
import com.example.smartcitizenclub.ui.user.UserScreen
import com.example.smartcitizenclub.ui.user.screens.*
import com.example.smartcitizenclub.ui.user.screens.cashout.CashOutProvider
import com.example.smartcitizenclub.ui.user.screens.recharge.MobileOperator
import com.example.smartcitizenclub.ui.user.screens.contact.SupportTicket
import com.example.smartcitizenclub.ui.user.screens.donation.DonationCampaign
import com.example.smartcitizenclub.ui.user.screens.donation.UserDonation
import com.example.smartcitizenclub.ui.user.screens.packages.*
import com.example.smartcitizenclub.ui.user.screens.loan.*
import com.example.smartcitizenclub.ui.user.screens.payments.*
import com.example.smartcitizenclub.ui.user.screens.sendmoney.Contact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(
    user: User,
    selectedScreen: UserScreen,
    onScreenSelected: (UserScreen) -> Unit,
    onLogout: () -> Unit,
    onSwitchToSidebar: () -> Unit
) {
    var showChangePassword by remember { mutableStateOf(false) }
    var showKYCSubmit by remember { mutableStateOf(false) }
    var showSelectContact by remember { mutableStateOf(false) }
    var showNewGroup by remember { mutableStateOf(false) }
    var showNewContact by remember { mutableStateOf(false) }
    var showChatScreen by remember { mutableStateOf(false) }
    var selectedChat by remember { mutableStateOf<com.example.smartcitizenclub.ui.user.screens.Chat?>(null) }
    
    // Send Money navigation states
    var showSendMoney by remember { mutableStateOf(false) }
    var showSendMoneyAmount by remember { mutableStateOf(false) }
    var showSendMoneyConfirm by remember { mutableStateOf(false) }
    var selectedContact by remember { mutableStateOf<com.example.smartcitizenclub.ui.user.screens.sendmoney.Contact?>(null) }
    var selectedAmount by remember { mutableStateOf(0.0) }
    
    // Cash Out navigation states
    var showCashOut by remember { mutableStateOf(false) }
    var showCashOutAmount by remember { mutableStateOf(false) }
    var showCashOutConfirm by remember { mutableStateOf(false) }
    var selectedProvider by remember { mutableStateOf<com.example.smartcitizenclub.ui.user.screens.cashout.CashOutProvider?>(null) }
    var cashOutAmount by remember { mutableStateOf(0.0) }
    
    // Mobile Recharge navigation states
    var showMobileRecharge by remember { mutableStateOf(false) }
    var showMobileRechargeAmount by remember { mutableStateOf(false) }
    var showMobileRechargeConfirm by remember { mutableStateOf(false) }
    var selectedOperator by remember { mutableStateOf<com.example.smartcitizenclub.ui.user.screens.recharge.MobileOperator?>(null) }
    var rechargePhoneNumber by remember { mutableStateOf("") }
    var rechargeAmount by remember { mutableStateOf(0.0) }
    
    // Contact Us navigation states
    var showContactUs by remember { mutableStateOf(false) }
    var showTicketList by remember { mutableStateOf(false) }
    var showTicketDetails by remember { mutableStateOf(false) }
    var selectedTicket by remember { mutableStateOf<com.example.smartcitizenclub.ui.user.screens.contact.SupportTicket?>(null) }
    
    // Limit and Charges navigation states
    var showLimitCharges by remember { mutableStateOf(false) }
    
    // Donation navigation states
    var showDonation by remember { mutableStateOf(false) }
    var showDonationHistory by remember { mutableStateOf(false) }
    var selectedCampaign by remember { mutableStateOf<com.example.smartcitizenclub.ui.user.screens.donation.DonationCampaign?>(null) }
    
    // Package Purchase navigation states
    var showPackagePurchase by remember { mutableStateOf(false) }
    var showPackageConfirm by remember { mutableStateOf(false) }
    var selectedPackage by remember { mutableStateOf<Package?>(null) }
    
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
            if (!showChangePassword && !showKYCSubmit && !showSelectContact && !showNewGroup && !showNewContact && !showChatScreen && !showSendMoney && !showSendMoneyAmount && !showSendMoneyConfirm && !showCashOut && !showCashOutAmount && !showCashOutConfirm && !showMobileRecharge && !showMobileRechargeAmount && !showMobileRechargeConfirm && !showContactUs && !showTicketList && !showTicketDetails && !showLimitCharges && !showDonation && !showDonationHistory && !showPackagePurchase && !showPackageConfirm && !showLoan && !showLoanAmount && !showLoanConfirm && !showLoanPayment && !showLoanPaymentAmount && !showPayment && !showPaymentAmount) {
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
                    
                    // Transactions
                    CustomNavigationBarItem(
                        icon = UserScreen.Transactions.icon,
                        label = UserScreen.Transactions.title,
                        selected = selectedScreen == UserScreen.Transactions,
                        onClick = { onScreenSelected(UserScreen.Transactions) }
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
                    com.example.smartcitizenclub.ui.user.screens.myscc.ChangePasswordScreen(
                        onBackClick = { showChangePassword = false },
                        onPasswordChanged = { 
                            showChangePassword = false
                            // You can add success message or other actions here
                        }
                    )
                }
                showKYCSubmit -> {
                    com.example.smartcitizenclub.ui.user.screens.myscc.KYCSubmitScreen(
                        onBackClick = { showKYCSubmit = false },
                        onKYCSubmitted = { 
                            showKYCSubmit = false
                            // You can add success message or other actions here
                        }
                    )
                }
                showSelectContact -> {
                    com.example.smartcitizenclub.ui.user.screens.messages.SelectContactScreen(
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
                    com.example.smartcitizenclub.ui.user.screens.messages.NewGroupScreen(
                        onBackClick = { showNewGroup = false },
                        onNextClick = { 
                            // TODO: Navigate to group setup screen
                            showNewGroup = false
                        }
                    )
                }
                showNewContact -> {
                    com.example.smartcitizenclub.ui.user.screens.messages.NewContactScreen(
                        onBackClick = { showNewContact = false },
                        onSaveContact = { firstName, lastName, countryCode, phoneNumber, showQRCode ->
                            // TODO: Save contact to database
                            showNewContact = false
                        }
                    )
                }
                showChatScreen -> {
                    selectedChat?.let { chat ->
                        com.example.smartcitizenclub.ui.user.screens.messages.ChatScreen(
                            chat = chat,
                            currentUser = user,
                            onBackClick = { 
                                showChatScreen = false
                                selectedChat = null
                            }
                        )
                    }
                }
                showSendMoney -> {
                    com.example.smartcitizenclub.ui.user.screens.sendmoney.SendMoneyScreen(
                        onBackClick = { showSendMoney = false },
                        onContactClick = { contact ->
                            selectedContact = contact
                            showSendMoney = false
                            showSendMoneyAmount = true
                        }
                    )
                }
                showSendMoneyAmount -> {
                    selectedContact?.let { contact ->
                        com.example.smartcitizenclub.ui.user.screens.sendmoney.SendMoneyAmountScreen(
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
                        com.example.smartcitizenclub.ui.user.screens.sendmoney.SendMoneyConfirmScreen(
                            contact = contact,
                            amount = selectedAmount,
                            onBackClick = { showSendMoneyConfirm = false },
                            onConfirmClick = { pin ->
                                // TODO: Process the transaction
                                showSendMoneyConfirm = false
                                selectedContact = null
                                selectedAmount = 0.0
                            }
                        )
                    }
                }
                showCashOut -> {
                    com.example.smartcitizenclub.ui.user.screens.cashout.CashOutScreen(
                        onBackClick = { showCashOut = false },
                        onProviderClick = { provider ->
                            selectedProvider = provider
                            showCashOut = false
                            showCashOutAmount = true
                        }
                    )
                }
                showCashOutAmount -> {
                    selectedProvider?.let { provider ->
                        com.example.smartcitizenclub.ui.user.screens.cashout.CashOutAmountScreen(
                            provider = provider,
                            onBackClick = { showCashOutAmount = false },
                            onAmountEntered = { amount ->
                                cashOutAmount = amount
                                showCashOutAmount = false
                                showCashOutConfirm = true
                            }
                        )
                    }
                }
                showCashOutConfirm -> {
                    selectedProvider?.let { provider ->
                        com.example.smartcitizenclub.ui.user.screens.cashout.CashOutConfirmScreen(
                            provider = provider,
                            amount = cashOutAmount,
                            onBackClick = { showCashOutConfirm = false },
                            onConfirmClick = { pin ->
                                // TODO: Process the cash out transaction
                                showCashOutConfirm = false
                                selectedProvider = null
                                cashOutAmount = 0.0
                            }
                        )
                    }
                }
                showMobileRecharge -> {
                    com.example.smartcitizenclub.ui.user.screens.recharge.MobileRechargeScreen(
                        onBackClick = { showMobileRecharge = false },
                        onOperatorClick = { operator ->
                            selectedOperator = operator
                            showMobileRecharge = false
                            showMobileRechargeAmount = true
                        }
                    )
                }
                showMobileRechargeAmount -> {
                    selectedOperator?.let { operator ->
                        com.example.smartcitizenclub.ui.user.screens.recharge.MobileRechargeAmountScreen(
                            operator = operator,
                            phoneNumber = rechargePhoneNumber,
                            onBackClick = { showMobileRechargeAmount = false },
                            onAmountEntered = { amount ->
                                rechargeAmount = amount
                                showMobileRechargeAmount = false
                                showMobileRechargeConfirm = true
                            },
                            onPackageSelected = { pkg ->
                                rechargeAmount = pkg.amount
                                showMobileRechargeAmount = false
                                showMobileRechargeConfirm = true
                            }
                        )
                    }
                }
                showMobileRechargeConfirm -> {
                    selectedOperator?.let { operator ->
                        com.example.smartcitizenclub.ui.user.screens.recharge.MobileRechargeConfirmScreen(
                            operator = operator,
                            phoneNumber = rechargePhoneNumber,
                            amount = rechargeAmount,
                            onBackClick = { showMobileRechargeConfirm = false },
                            onConfirmClick = { pin ->
                                // TODO: Process the mobile recharge transaction
                                showMobileRechargeConfirm = false
                                selectedOperator = null
                                rechargePhoneNumber = ""
                                rechargeAmount = 0.0
                            }
                        )
                    }
                }
                showContactUs -> {
                    com.example.smartcitizenclub.ui.user.screens.contact.ContactUsScreen(
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
                    com.example.smartcitizenclub.ui.user.screens.contact.TicketListScreen(
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
                        com.example.smartcitizenclub.ui.user.screens.contact.TicketDetailsScreen(
                            ticket = ticket,
                            onBackClick = { showTicketDetails = false }
                        )
                    }
                }
                showLimitCharges -> {
                    com.example.smartcitizenclub.ui.user.screens.limits.LimitChargesScreen(
                        user = user,
                        onBackClick = { showLimitCharges = false }
                    )
                }
                showDonation -> {
                    com.example.smartcitizenclub.ui.user.screens.donation.DonationScreen(
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
                    com.example.smartcitizenclub.ui.user.screens.donation.DonationHistoryScreen(
                        user = user,
                        onBackClick = { showDonationHistory = false },
                        onDonateAgain = { campaign ->
                            selectedCampaign = campaign
                            showDonationHistory = false
                            showDonation = true
                        }
                    )
                }
                showPackagePurchase -> {
                    PackagePurchaseScreen(
                        onBackClick = { showPackagePurchase = false },
                        onPackageSelected = { pkg ->
                            selectedPackage = pkg
                            showPackagePurchase = false
                            showPackageConfirm = true
                        }
                    )
                }
                showPackageConfirm -> {
                    selectedPackage?.let { pkg ->
                        PackageConfirmScreen(
                            pkg = pkg,
                            onBackClick = { showPackageConfirm = false },
                            onConfirmClick = { paymentMethod, pin ->
                                // TODO: Process the package purchase transaction
                                showPackageConfirm = false
                                selectedPackage = null
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
                        onSendMoneyClick = { showSendMoney = true },
                        onCashOutClick = { showCashOut = true },
                        onMobileRechargeClick = { showMobileRecharge = true },
                        onPackagePurchaseClick = { showPackagePurchase = true },
                        onLoanClick = { showLoan = true },
                        onContactUsClick = { showContactUs = true },
                        onLimitChargesClick = { showLimitCharges = true },
                        onDonationClick = { showDonation = true }
                    )
                    UserScreen.Transactions -> FinanceScreen(
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
