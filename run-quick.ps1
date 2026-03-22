param(
    [string]$Profile = "local"
)

# 1. Kiểm tra Maven và JDK
$mvn = Get-Command mvn -ErrorAction SilentlyContinue
if (-not $mvn) {
    $mavenHome = 'C:\Program Files\Apache\maven\apache-maven-3.9.14'
    if (Test-Path "$mavenHome\bin\mvn.cmd") {
        $env:PATH = "$mavenHome\bin;" + $env:PATH
        $mvn = Get-Command mvn -ErrorAction SilentlyContinue
    }
}
if (-not $mvn) {
    Write-Host "ERROR: Maven not found in PATH." -ForegroundColor Red
    Write-Host "Please install Maven or adjust PATH (C:\Program Files\Apache\maven)." -ForegroundColor Yellow
    exit 1
}

Write-Host "Using Maven: $(mvn.Path)" -ForegroundColor Green
Write-Host "Using Java: $(java -version 2>&1 | Select-Object -First 1)" -ForegroundColor Green

# 2. Build
Write-Host "Building project with profile '$Profile'..." -ForegroundColor Cyan
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Host "BUILD FAILED" -ForegroundColor Red
    exit 1
}

# 3. Start app trong cửa sổ mới (background)
$startCmd = "cd '$PWD'; mvn -Dspring.profiles.active=$Profile spring-boot:run"
Write-Host "Starting app in new terminal via: $startCmd" -ForegroundColor Cyan
Start-Process -FilePath "powershell" -ArgumentList "-NoExit", "-Command", $startCmd

# 4. Check kết nối
Start-Sleep -Seconds 6
$test = Test-NetConnection -ComputerName localhost -Port 8080 -WarningAction SilentlyContinue
if ($test.TcpTestSucceeded) {
    Write-Host "OK: App is reachable at http://localhost:8080" -ForegroundColor Green
} else {
    Write-Host "WARN: App not reachable yet. Please wait 10-20s then retry." -ForegroundColor Yellow
}
