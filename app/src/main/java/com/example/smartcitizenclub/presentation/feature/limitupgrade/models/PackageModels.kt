package com.example.smartcitizenclub.presentation.feature.limitupgrade.models

// Auto Income Package data model based on your database seeder
data class Package(
    val id: String,
    val name: String,
    val amount: Double,
    val description: String,
    val withdrawalLimit: Double,
    val maxLoanLimit: Double,
    val isActive: Boolean = true
)

// Sample auto income package data (matching your database seeder)
object PackageData {
    val packages = listOf(
        Package(
            id = "1",
            name = "Basic Package",
            amount = 100.00,
            description = "Basic auto income package with 1:5 withdrawal ratio",
            withdrawalLimit = 500.00,
            maxLoanLimit = 50.00
        ),
        Package(
            id = "2",
            name = "Starter Package",
            amount = 200.00,
            description = "Starter auto income package with 1:5 withdrawal ratio",
            withdrawalLimit = 1000.00,
            maxLoanLimit = 100.00
        ),
        Package(
            id = "3",
            name = "Standard Package",
            amount = 500.00,
            description = "Standard auto income package with 1:5 withdrawal ratio",
            withdrawalLimit = 2500.00,
            maxLoanLimit = 250.00
        ),
        Package(
            id = "4",
            name = "Premium Package",
            amount = 1000.00,
            description = "Premium auto income package with 1:5 withdrawal ratio",
            withdrawalLimit = 5000.00,
            maxLoanLimit = 500.00
        ),
        Package(
            id = "5",
            name = "Gold Package",
            amount = 2000.00,
            description = "Gold auto income package with 1:5 withdrawal ratio",
            withdrawalLimit = 10000.00,
            maxLoanLimit = 1000.00
        ),
        Package(
            id = "6",
            name = "Platinum Package",
            amount = 5000.00,
            description = "Platinum auto income package with 1:5 withdrawal ratio",
            withdrawalLimit = 25000.00,
            maxLoanLimit = 2500.00
        ),
        Package(
            id = "7",
            name = "Diamond Package",
            amount = 10000.00,
            description = "Diamond auto income package with 1:5 withdrawal ratio",
            withdrawalLimit = 50000.00,
            maxLoanLimit = 5000.00
        ),
        Package(
            id = "8",
            name = "Executive Package",
            amount = 20000.00,
            description = "Executive auto income package with 1:5 withdrawal ratio",
            withdrawalLimit = 100000.00,
            maxLoanLimit = 10000.00
        ),
        Package(
            id = "9",
            name = "VIP Package",
            amount = 50000.00,
            description = "VIP auto income package with 1:5 withdrawal ratio",
            withdrawalLimit = 250000.00,
            maxLoanLimit = 25000.00
        ),
        Package(
            id = "10",
            name = "Ultimate Package",
            amount = 100000.00,
            description = "Ultimate auto income package with 1:5 withdrawal ratio",
            withdrawalLimit = 500000.00,
            maxLoanLimit = 50000.00
        )
    )
}
