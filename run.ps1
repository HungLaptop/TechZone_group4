# Quick run script for TechZone project
# Usage: powershell -ExecutionPolicy Bypass -File .\run.ps1

param(
    [string]$Profile = "local"
)

if (-not $Profile) {
    $Profile = "local"
}

# 1. Check Maven
$mvn = Get-Command mvn -ErrorAction SilentlyContinue
if (-not $mvn) {
    # Fallback or local maven installation path on common Windows install location
    $mavenHome = 'C:\Program Files\Apache\maven\apache-maven-3.9.14'
    if (Test-Path "$mavenHome\bin\mvn.cmd") {
        $env:PATH = "$mavenHome\bin;" + $env:PATH
        $mvn = Get-Command mvn -ErrorAction SilentlyContinue
    }
}

if (-not $mvn) {
    Write-Host "ERROR: Maven not found in PATH. Install Apache Maven and restart terminal." -ForegroundColor Red
    Write-Host "Current PATH: $env:PATH" -ForegroundColor Yellow
    exit 1
}

# 2. Create DB if not exists
Write-Host "Creating database TechZoneDB if missing..." -ForegroundColor Green

$candidates = @(
    ".\SQLEXPRESS",
    "tcp:localhost,1433",
    "tcp:localhost\\SQLEXPRESS,1433",
    "np:\\.\\pipe\\MSSQL$SQLEXPRESS\\sql\\query"
)

$ok = $false
foreach ($s in $candidates) {
    Write-Host "Trying SQL connection: $s"
    sqlcmd -S $s -U sa -P 1 -i .\setup-db.sql
    if ($LASTEXITCODE -eq 0) {
        $ok = $true
        break
    }
}

if (-not $ok) {
    Write-Host "Failed to run setup-db.sql. Check SQL Server connection and credentials." -ForegroundColor Red
    Write-Host "Ensure SQL Server Browser is running and the instance name is correct." -ForegroundColor Yellow
    Write-Host "Candidate connection opens tried: $candidates" -ForegroundColor Yellow
    exit 1
}

# 3. Build project
Write-Host "Building project..." -ForegroundColor Green
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Host "Maven build failed. Fix compile errors first." -ForegroundColor Red
    exit 1
}

# 4. Run app (non-blocking/diagnostic)
Write-Host "Starting Spring Boot application with profile '$Profile'..." -ForegroundColor Green

# Force profile in both environment and maven property to avoid missing profile case
$env:SPRING_PROFILES_ACTIVE = $Profile
$buildProfileArg = if ($Profile.ToLower() -eq 'default') { '' } else { "-Dspring.profiles.active=$Profile -Dspring-boot.run.profiles=$Profile" }
$runArgs = @()
if ($buildProfileArg) { $runArgs += $buildProfileArg }
$runArgs += 'spring-boot:run'

Write-Host "Launching maven with: mvn $($runArgs -join ' ')" -ForegroundColor Green
$proc = Start-Process -FilePath "mvn" -ArgumentList $runArgs -NoNewWindow -PassThru
Write-Host "Process started (PID $($proc.Id)). Waiting up to 60s for localhost:8080..." -ForegroundColor Cyan

$attempts = 0
$maxAttempts = 12
while ($attempts -lt $maxAttempts) {
    Start-Sleep -Seconds 5
    $attempts++
    $test = Test-NetConnection -ComputerName localhost -Port 8080 -WarningAction SilentlyContinue
    if ($test.TcpTestSucceeded) {
        Write-Host "Application is reachable at http://localhost:8080 (after $($attempts*5)s)" -ForegroundColor Green
        Write-Host "Use Get-Process -Id $($proc.Id) to inspect process, or Stop-Process -Id $($proc.Id) to stop." -ForegroundColor Gray
        exit 0
    }
    Write-Host "Waiting... ($attempts/$maxAttempts)" -ForegroundColor Yellow
    if ($proc.HasExited) {
        Write-Host "Process exited unexpectedly with code $($proc.ExitCode). Check logs for errors." -ForegroundColor Red
        exit 1
    }
}

Write-Host "Timeout waiting for app to be reachable at localhost:8080. Process is still running (PID $($proc.Id))." -ForegroundColor Red
Write-Host "Check logs in terminal from process or run: Get-Content -Path \"target\\spring.log\" -Wait" -ForegroundColor Yellow
exit 1
