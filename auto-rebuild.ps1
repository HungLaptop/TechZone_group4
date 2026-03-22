# ============================================
# SCRIPT TỰ ĐỘNG: Rebuild và chạy TechZone App
# ============================================
# Chạy script này trong PowerShell
# ============================================

Write-Host ""
Write-Host "🚀 TechZone Auto Rebuild & Run Script" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$projectDir = "C:\Users\VinhHung\Desktop\TechZone_GROUP4\TechZone_GROUP4"

# BƯỚC 1: Stop app đang chạy
Write-Host "1️⃣  Stopping running app..." -ForegroundColor Yellow
$conn = Get-NetTCPConnection -LocalPort 8081 -State Listen -ErrorAction SilentlyContinue
if ($conn) {
    Stop-Process -Id $conn.OwningProcess -Force
    Write-Host "   ✅ Stopped process PID: $($conn.OwningProcess)" -ForegroundColor Green
    Start-Sleep -Seconds 2
} else {
    Write-Host "   ℹ️  No app running on port 8081" -ForegroundColor Gray
}

# BƯỚC 2: Clean target directory
Write-Host ""
Write-Host "2️⃣  Cleaning target directory..." -ForegroundColor Yellow
Remove-Item -Path "$projectDir\target" -Recurse -Force -ErrorAction SilentlyContinue
Write-Host "   ✅ Target cleaned!" -ForegroundColor Green

# BƯỚC 3: Hướng dẫn rebuild trong IntelliJ
Write-Host ""
Write-Host "3️⃣  BÂY GIỜ TRONG INTELLIJ:" -ForegroundColor Yellow
Write-Host ""
Write-Host "   📋 Làm theo 2 bước này:" -ForegroundColor White
Write-Host ""
Write-Host "   BƯỚC 1: REBUILD" -ForegroundColor Cyan
Write-Host "      - Menu: Build → Rebuild Project" -ForegroundColor White
Write-Host "      - Đợi rebuild xong (~20 giây)" -ForegroundColor White
Write-Host ""
Write-Host "   BƯỚC 2: RUN" -ForegroundColor Cyan
Write-Host "      - Nhấn Shift+F10" -ForegroundColor White
Write-Host "      - Đợi log: 'Started TechZoneGroup4Application'" -ForegroundColor White
Write-Host ""

# BƯỚC 4: Hướng dẫn test
Write-Host "4️⃣  SAU KHI APP CHẠY, TEST:" -ForegroundColor Yellow
Write-Host ""
Write-Host "   A. Mở trình duyệt:" -ForegroundColor Cyan
Write-Host "      http://localhost:8081/register" -ForegroundColor White
Write-Host ""
Write-Host "   B. Hard refresh để xóa cache:" -ForegroundColor Cyan
Write-Host "      Nhấn: Ctrl + Shift + R" -ForegroundColor White
Write-Host ""
Write-Host "   C. Điền form đăng ký:" -ForegroundColor Cyan
Write-Host "      Username: testuser" -ForegroundColor White
Write-Host "      Email: testuser@test.com" -ForegroundColor White
Write-Host "      Password: test123456" -ForegroundColor White
Write-Host "      Confirm: test123456" -ForegroundColor White
Write-Host ""
Write-Host "   D. Click 'Register'" -ForegroundColor Cyan
Write-Host ""
Write-Host "   E. Phải thấy:" -ForegroundColor Cyan
Write-Host "      ✅ Chuyển đến /login" -ForegroundColor Green
Write-Host "      ✅ Hộp xanh: 'Đăng ký thành công!'" -ForegroundColor Green
Write-Host ""

# BƯỚC 5: Debug URLs
Write-Host "5️⃣  DEBUG URLs (kiểm tra sau khi đăng ký):" -ForegroundColor Yellow
Write-Host ""
Write-Host "   http://localhost:8081/api/debug/status" -ForegroundColor White
Write-Host "   http://localhost:8081/api/debug/users" -ForegroundColor White
Write-Host "   http://localhost:8081/api/debug/test-login/testuser/test123456" -ForegroundColor White
Write-Host ""

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "🎯 BÂY GIỜ HÃY REBUILD TRONG INTELLIJ!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

