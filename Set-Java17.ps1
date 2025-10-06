# Java 17 Environment Setup Script
# Run this script to set Java 17 as your default Java version

Write-Host "Setting up Java 17 Environment..." -ForegroundColor Green

# Set JAVA_HOME
$javaHome = "C:\Program Files\Eclipse Adoptium\jdk-17.0.16.8-hotspot"
[Environment]::SetEnvironmentVariable("JAVA_HOME", $javaHome, "User")

# Add Java 17 to PATH
$javaBinPath = "$javaHome\bin"
$currentPath = [Environment]::GetEnvironmentVariable("PATH", "User")

if ($currentPath -notlike "*$javaBinPath*") {
    $newPath = if ($currentPath) { "$currentPath;$javaBinPath" } else { $javaBinPath }
    [Environment]::SetEnvironmentVariable("PATH", $newPath, "User")
    Write-Host "Added Java 17 to PATH" -ForegroundColor Yellow
} else {
    Write-Host "Java 17 already in PATH" -ForegroundColor Yellow
}

# Set for current session
$env:JAVA_HOME = $javaHome
$env:PATH = "$javaBinPath;$env:PATH"

Write-Host "Environment variables set successfully!" -ForegroundColor Green
Write-Host "JAVA_HOME: $javaHome" -ForegroundColor Cyan
Write-Host ""
Write-Host "Current Java version:" -ForegroundColor Yellow
java -version

Write-Host ""
Write-Host "Note: Restart your terminal/IDE for permanent changes to take effect." -ForegroundColor Magenta
