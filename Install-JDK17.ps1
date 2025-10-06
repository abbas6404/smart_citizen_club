# Download and Install JDK 17 Script
# This script downloads OpenJDK 17 and installs it to Program Files\Java

Write-Host "Downloading OpenJDK 17..." -ForegroundColor Green

# JDK 17 download URL (Eclipse Temurin - LTS version)
$jdk17Url = "https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.16%2B8/OpenJDK17U-jdk_x64_windows_hotspot_17.0.16_8.msi"
$downloadPath = "$env:TEMP\OpenJDK17U-jdk_x64_windows_hotspot_17.0.16_8.msi"

try {
    # Download JDK 17
    Write-Host "Downloading from: $jdk17Url" -ForegroundColor Yellow
    Invoke-WebRequest -Uri $jdk17Url -OutFile $downloadPath -UseBasicParsing
    
    Write-Host "Download completed!" -ForegroundColor Green
    Write-Host "Installing JDK 17..." -ForegroundColor Yellow
    
    # Install JDK 17 silently
    Start-Process -FilePath "msiexec.exe" -ArgumentList "/i `"$downloadPath`" /quiet /norestart" -Wait
    
    Write-Host "Installation completed!" -ForegroundColor Green
    
    # Clean up downloaded file
    Remove-Item $downloadPath -Force
    
    # Check installation
    $jdk17Path = "C:\Program Files\Eclipse Adoptium\jdk-17.0.16.8-hotspot"
    if (Test-Path $jdk17Path) {
        Write-Host "JDK 17 successfully installed at: $jdk17Path" -ForegroundColor Green
        
        # Set environment variables
        [Environment]::SetEnvironmentVariable("JAVA_HOME", $jdk17Path, "User")
        $env:JAVA_HOME = $jdk17Path
        
        Write-Host "JAVA_HOME set to: $jdk17Path" -ForegroundColor Cyan
        
        # Test Java version
        Write-Host "Testing Java installation..." -ForegroundColor Yellow
        & "$jdk17Path\bin\java.exe" -version
        
    } else {
        Write-Host "Installation may have failed. Please check manually." -ForegroundColor Red
    }
    
} catch {
    Write-Host "Error downloading or installing JDK 17: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "Please download manually from: https://adoptium.net/temurin/releases/?version=17" -ForegroundColor Yellow
}

Write-Host "`nPress any key to continue..." -ForegroundColor Magenta
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
