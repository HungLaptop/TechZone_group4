-- ============================================
-- SCRIPT SỬA LỖI: Tạo Users và Roles trong SQL Server
-- Chạy script này trong SQL Server Management Studio
-- Database: techzone
-- ============================================

USE techzone;
GO

-- BƯỚC 1: Xóa dữ liệu cũ (nếu có lỗi)
PRINT '🗑️ Cleaning old data...';
DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM roles;
GO

-- BƯỚC 2: Tạo 3 roles cơ bản
PRINT '📋 Creating roles...';
INSERT INTO roles (name) VALUES
('ADMIN'),
('STAFF'),
('CUSTOMER');
GO

-- BƯỚC 3: Tạo 4 users seed với password placeholder
-- PasswordHashFixer sẽ tự động mã hóa khi app khởi động
PRINT '👤 Creating users...';
INSERT INTO users (username, password, raw_password, email, enabled) VALUES
('admin', 'placeholder_will_be_fixed', 'admin123', 'admin@techzone.dev', 1),
('staff1', 'placeholder_will_be_fixed', 'staff123', 'staff1@techzone.dev', 1),
('user1', 'placeholder_will_be_fixed', 'user123', 'user1@techzone.dev', 1),
('user2', 'placeholder_will_be_fixed', 'user456', 'user2@techzone.dev', 1);
GO

-- BƯỚC 4: Lấy IDs của roles và users
PRINT '🔍 Checking created roles:';
SELECT id, name FROM roles ORDER BY id;

PRINT '🔍 Checking created users:';
SELECT id, username, email FROM users ORDER BY id;
GO

-- BƯỚC 5: Gán roles cho users
-- Giả định: role id tự tăng từ 1
-- ADMIN=1, STAFF=2, CUSTOMER=3
-- user id tự tăng từ 1
PRINT '🔗 Assigning roles to users...';

DECLARE @adminRoleId INT, @staffRoleId INT, @customerRoleId INT;
DECLARE @adminUserId INT, @staff1UserId INT, @user1UserId INT, @user2UserId INT;

-- Lấy role IDs
SELECT @adminRoleId = id FROM roles WHERE name = 'ADMIN';
SELECT @staffRoleId = id FROM roles WHERE name = 'STAFF';
SELECT @customerRoleId = id FROM roles WHERE name = 'CUSTOMER';

-- Lấy user IDs
SELECT @adminUserId = id FROM users WHERE username = 'admin';
SELECT @staff1UserId = id FROM users WHERE username = 'staff1';
SELECT @user1UserId = id FROM users WHERE username = 'user1';
SELECT @user2UserId = id FROM users WHERE username = 'user2';

-- Gán roles
INSERT INTO user_roles (user_id, role_id) VALUES
(@adminUserId, @adminRoleId),     -- admin -> ADMIN
(@staff1UserId, @staffRoleId),    -- staff1 -> STAFF
(@user1UserId, @customerRoleId),  -- user1 -> CUSTOMER
(@user2UserId, @customerRoleId);  -- user2 -> CUSTOMER
GO

-- BƯỚC 6: Kiểm tra kết quả
PRINT '✅ Verification:';
SELECT
    u.id,
    u.username,
    u.email,
    u.enabled,
    u.raw_password,
    STRING_AGG(r.name, ', ') AS roles
FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
LEFT JOIN roles r ON ur.role_id = r.id
GROUP BY u.id, u.username, u.email, u.enabled, u.raw_password
ORDER BY u.id;
GO

PRINT '✅ DONE! Bây giờ hãy RESTART ứng dụng Spring Boot.';
PRINT 'PasswordHashFixer sẽ tự động mã hóa passwords từ raw_password.';
GO

