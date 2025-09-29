package com.example.smartcitizenclub.presentation.feature.investment.models

// Investment Package data model based on your database seeder
data class InvestmentPackage(
    val id: String,
    val name: String,
    val description: String,
    val amount: Double,
    val returnRate: Double, // 200% return rate
    val durationDays: Int,
    val dailyProfitAmount: Double,
    val tasksPerDay: Int,
    val profitPerTask: Double,
    val withdrawalLimit: Double,
    val termsConditions: List<String>,
    val status: String = "active"
)

// Sample investment package data with better names
object InvestmentPackageData {
    val packages = listOf(
        InvestmentPackage(
            id = "1",
            name = "Starter Gold",
            description = "Perfect entry-level investment with 200% return rate",
            amount = 1800.00,
            returnRate = 200.00,
            durationDays = 45,
            dailyProfitAmount = 40.00,
            tasksPerDay = 4,
            profitPerTask = 10.00,
            withdrawalLimit = 3600.00,
            termsConditions = listOf(
                "Minimum investment period: 45 days",
                "Daily tasks must be completed to earn profit",
                "Early withdrawal penalty: 10%",
                "Profit is calculated daily and credited at the end of the term"
            )
        ),
        InvestmentPackage(
            id = "2",
            name = "Premium Silver",
            description = "Standard investment package with excellent returns",
            amount = 4500.00,
            returnRate = 200.00,
            durationDays = 45,
            dailyProfitAmount = 100.00,
            tasksPerDay = 10,
            profitPerTask = 10.00,
            withdrawalLimit = 9000.00,
            termsConditions = listOf(
                "Minimum investment period: 45 days",
                "Daily tasks must be completed to earn profit",
                "Early withdrawal penalty: 8%",
                "Profit is calculated daily and credited at the end of the term"
            )
        ),
        InvestmentPackage(
            id = "3",
            name = "Elite Platinum",
            description = "Premium investment package for experienced investors",
            amount = 9000.00,
            returnRate = 200.00,
            durationDays = 45,
            dailyProfitAmount = 200.00,
            tasksPerDay = 20,
            profitPerTask = 10.00,
            withdrawalLimit = 18000.00,
            termsConditions = listOf(
                "Minimum investment period: 45 days",
                "Daily tasks must be completed to earn profit",
                "Early withdrawal penalty: 5%",
                "Profit is calculated daily and credited at the end of the term"
            )
        ),
        InvestmentPackage(
            id = "4",
            name = "Royal Diamond",
            description = "High-value investment package with maximum returns",
            amount = 20000.00,
            returnRate = 200.00,
            durationDays = 40,
            dailyProfitAmount = 500.00,
            tasksPerDay = 5,
            profitPerTask = 100.00,
            withdrawalLimit = 40000.00,
            termsConditions = listOf(
                "Minimum investment period: 40 days",
                "Daily tasks must be completed to earn profit",
                "Early withdrawal penalty: 3%",
                "Profit is calculated daily and credited at the end of the term"
            )
        ),
        InvestmentPackage(
            id = "5",
            name = "Crown Emerald",
            description = "VIP investment package for high-net-worth investors",
            amount = 40000.00,
            returnRate = 200.00,
            durationDays = 40,
            dailyProfitAmount = 1000.00,
            tasksPerDay = 10,
            profitPerTask = 100.00,
            withdrawalLimit = 80000.00,
            termsConditions = listOf(
                "Minimum investment period: 40 days",
                "Daily tasks must be completed to earn profit",
                "Early withdrawal penalty: 2%",
                "Profit is calculated daily and credited at the end of the term"
            )
        ),
        InvestmentPackage(
            id = "6",
            name = "Imperial Ruby",
            description = "Elite investment package with exceptional returns",
            amount = 80000.00,
            returnRate = 200.00,
            durationDays = 40,
            dailyProfitAmount = 2000.00,
            tasksPerDay = 20,
            profitPerTask = 100.00,
            withdrawalLimit = 160000.00,
            termsConditions = listOf(
                "Minimum investment period: 40 days",
                "Daily tasks must be completed to earn profit",
                "Early withdrawal penalty: 1%",
                "Profit is calculated daily and credited at the end of the term"
            )
        ),
        InvestmentPackage(
            id = "7",
            name = "Supreme Sapphire",
            description = "Ultimate investment package for institutional investors",
            amount = 175000.00,
            returnRate = 200.00,
            durationDays = 35,
            dailyProfitAmount = 5000.00,
            tasksPerDay = 50,
            profitPerTask = 100.00,
            withdrawalLimit = 350000.00,
            termsConditions = listOf(
                "Minimum investment period: 35 days",
                "Daily tasks must be completed to earn profit",
                "Early withdrawal penalty: 0.5%",
                "Profit is calculated daily and credited at the end of the term"
            )
        )
    )
}
