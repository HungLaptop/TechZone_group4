# ===========================================
# SCRIPT TỰ ĐỘNG: Setup Database cho TechZone
# ===========================================
# Chạy script này trong PowerShell
# Yêu cầu: SQL Server đang chạy trên localhost:1433
# ===========================================

Write-Host "🔧 TechZone Database Setup Script" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host ""

# Kiểm tra SQL Server có đang chạy không
Write-Host "1️⃣  Checking SQL Server connection..." -ForegroundColor Yellow

try {
    $sqlCmd = @"
USE techzone;

-- Xóa dữ liệu cũ
DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM roles;

-- Tạo roles
INSERT INTO roles (name) VALUES ('ADMIN'), ('STAFF'), ('CUSTOMER');

-- Tạo users với raw password
INSERT INTO users (username, password, raw_password, email, enabled) VALUES
('admin', 'temp_will_be_fixed', 'admin123', 'admin@techzone.dev', 1),
('staff1', 'temp_will_be_fixed', 'staff123', 'staff1@techzone.dev', 1),
('user1', 'temp_will_be_fixed', 'user123', 'user1@techzone.dev', 1),
('user2', 'temp_will_be_fixed', 'user456', 'user2@techzone.dev', 1);

-- Gán roles
DECLARE @adminRole INT = (SELECT id FROM roles WHERE name = 'ADMIN');
DECLARE @staffRole INT = (SELECT id FROM roles WHERE name = 'STAFF');
DECLARE @customerRole INT = (SELECT id FROM roles WHERE name = 'CUSTOMER');

INSERT INTO user_roles (user_id, role_id) VALUES
((SELECT id FROM users WHERE username = 'admin'), @adminRole),
((SELECT id FROM users WHERE username = 'staff1'), @staffRole),
((SELECT id FROM users WHERE username = 'user1'), @customerRole),
((SELECT id FROM users WHERE username = 'user2'), @customerRole);

-- Hiển thị kết quả
SELECT
    u.id,
    u.username,
    u.email,
    u.raw_password,
    r.name AS role
FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
LEFT JOIN roles r ON ur.role_id = r.id
ORDER BY u.id;
"@

    # Lưu SQL vào file tạm
    $sqlFile = Join-Path $env:TEMP "techzone-setup.sql"
    $sqlCmd | Out-File -FilePath $sqlFile -Encoding UTF8

    Write-Host "✅ SQL script created at: $sqlFile" -ForegroundColor Green
    Write-Host ""

    # Chạy SQL bằng sqlcmd (nếu có)
    if (Get-Command sqlcmd -ErrorAction SilentlyContinue) {
        Write-Host "2️⃣  Running SQL script..." -ForegroundColor Yellow
        sqlcmd -S localhost -U sa -P 123456 -d techzone -i $sqlFile

        if ($LASTEXITCODE -eq 0) {
            Write-Host ""
            Write-Host "✅ Database setup completed!" -ForegroundColor Green
            Write-Host ""
            Write-Host "📋 Next steps:" -ForegroundColor Cyan
            Write-Host "   1. Stop app trong IntelliJ (Ctrl+F2)" -ForegroundColor White
            Write-Host "   2. Run lại app (Shift+F10)" -ForegroundColor White
            Write-Host "   3. Đợi log: 'Fixed 4 password hash(es)'" -ForegroundColor White
            Write-Host "   4. Test login: admin/admin123" -ForegroundColor White
            Write-Host ""
        } else {
            Write-Host ""
            Write-Host "❌ SQL execution failed!" -ForegroundColor Red
            Write-Host "Lỗi: Check username/password hoặc database name" -ForegroundColor Red
        }
    } else {
        Write-Host "⚠️  sqlcmd not found in PATH" -ForegroundColor Yellow
        Write-Host ""
        Write-Host "📝 Manual steps:" -ForegroundColor Cyan
        Write-Host "   1. Mở SQL Server Management Studio" -ForegroundColor White
        Write-Host "   2. Connect: localhost (sa/123456)" -ForegroundColor White
        Write-Host "   3. Mở file: $sqlFile" -ForegroundColor White
        Write-Host "   4. Execute (F5)" -ForegroundColor White
        Write-Host ""
    }

} catch {
    Write-Host "❌ Error: $_" -ForegroundColor Red
}

Write-Host ""
Write-Host "🔗 Useful debug URLs:" -ForegroundColor Cyan
Write-Host "   http://localhost:8081/api/debug/status" -ForegroundColor White
Write-Host "   http://localhost:8081/api/debug/users" -ForegroundColor White
Write-Host "   http://localhost:8081/api/debug/test-login/admin/admin123" -ForegroundColor White
Write-Host ""

