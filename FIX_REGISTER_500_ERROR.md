# 🚨 SỬA LỖI WHITELABEL ERROR PAGE 500 - /register

## ❌ VẤN ĐỀ:
Khi truy cập `http://localhost:8081/register` → Hiện **Whitelabel Error Page (500)**

## ✅ ĐÃ SỬA:
1. Sửa `layout.html` - Cấu trúc layout không đúng
2. Thêm `-parameters` flag vào `pom.xml`
3. Cải thiện Debug API endpoints

---

## 🔧 HƯỚNG DẪN SỬA NGAY (3 phút):

### **BƯỚC 1: Stop app đang chạy**

Trong IntelliJ IDEA:
- Click nút **Stop** (hình vuông đỏ) ở góc trên
- HOẶC nhấn `Ctrl+F2`

---

### **BƯỚC 2: Clean và Rebuild**

1. Menu: **Build** → **Clean Project**
2. Đợi 5 giây
3. Menu: **Build** → **Rebuild Project** 
4. Đợi rebuild hoàn tất (xem thanh progress ở dưới)

---

### **BƯỚC 3: Run lại app**

1. Click nút **Run** (hình tam giác xanh)
2. HOẶC nhấn `Shift+F10`
3. **Đợi log hiển thị:** `Started TechZoneGroup4Application`

---

### **BƯỚC 4: Kiểm tra trang register**

Mở trình duyệt:
```
http://localhost:8081/register
```

**✅ THÀNH CÔNG - Bạn sẽ thấy:**
- Trang đăng ký với form đầy đủ
- Các trường: Username, Email, Password, Xác nhận Password
- Nút "Register" màu xanh

**❌ NẾU VẪN LỖI:**
- Chụp màn hình lỗi
- Copy log từ IntelliJ console
- Gửi cho tôi

---

### **BƯỚC 5: Kiểm tra database trước khi test đăng ký**

Mở trình duyệt:
```
http://localhost:8081/api/debug/status
```

**Kết quả mong đợi:**
```json
{
  "totalUsers": 4,
  "totalRoles": 3,
  "usersWithRawPassword": 4
}
```

**❌ NẾU `totalUsers: 0` hoặc số nhỏ hơn 4:**

→ Database trống! Cần chạy SQL script.

---

## 🗄️ NẾU DATABASE TRỐNG - CHẠY SQL SCRIPT

### **Cách 1: Dùng SQL Server Management Studio (SSMS)**

1. **Mở SSMS**
2. **Connect** đến: `localhost` (username: `sa`, password: `123456`)
3. **Chọn database:** `techzone`
4. **Mở file hoặc paste SQL:**

```sql
USE techzone;
GO

-- Xóa dữ liệu cũ
DELETE FROM user_roles;
DELETE FROM users;  
DELETE FROM roles;
GO

-- Tạo roles
INSERT INTO roles (name) VALUES ('ADMIN'), ('STAFF'), ('CUSTOMER');
GO

-- Tạo users
INSERT INTO users (username, password, raw_password, email, enabled) VALUES
('admin', 'temp', 'admin123', 'admin@techzone.dev', 1),
('staff1', 'temp', 'staff123', 'staff1@techzone.dev', 1),
('user1', 'temp', 'user123', 'user1@techzone.dev', 1),
('user2', 'temp', 'user456', 'user2@techzone.dev', 1);
GO

-- Gán roles (dùng biến để tự động lấy ID)
DECLARE @adminRole INT = (SELECT id FROM roles WHERE name = 'ADMIN');
DECLARE @staffRole INT = (SELECT id FROM roles WHERE name = 'STAFF');
DECLARE @customerRole INT = (SELECT id FROM roles WHERE name = 'CUSTOMER');

DECLARE @adminUser INT = (SELECT id FROM users WHERE username = 'admin');
DECLARE @staff1User INT = (SELECT id FROM users WHERE username = 'staff1');
DECLARE @user1User INT = (SELECT id FROM users WHERE username = 'user1');
DECLARE @user2User INT = (SELECT id FROM users WHERE username = 'user2');

INSERT INTO user_roles (user_id, role_id) VALUES
(@adminUser, @adminRole),
(@staff1User, @staffRole),
(@user1User, @customerRole),
(@user2User, @customerRole);
GO

-- Kiểm tra
SELECT u.id, u.username, u.email, u.raw_password, r.name AS role
FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
LEFT JOIN roles r ON ur.role_id = r.id
ORDER BY u.id;
GO
```

5. **Click Execute (F5)**
6. **Kiểm tra kết quả** - Phải thấy 4 users với roles

---

### **Cách 2: Dùng Azure Data Studio hoặc DBeaver**

Nếu không có SSMS, dùng Azure Data Studio hoặc DBeaver:
- Connect đến: `localhost:1433`
- Database: `techzone`
- User: `sa`
- Password: `123456`
- Paste và chạy SQL ở trên

---

### **SAU KHI CHẠY SQL:**

1. **Stop app** trong IntelliJ (Ctrl+F2)
2. **Run lại app** (Shift+F10)
3. **Xem log phải có:**
   ```
   🔧 Checking and fixing password hashes...
   🔨 Re-hashing password for user: admin
   🔨 Re-hashing password for user: staff1
   🔨 Re-hashing password for user: user1
   🔨 Re-hashing password for user: user2
   ✅ Fixed 4 password hash(es)
   ```

4. **Kiểm tra lại:**
   ```
   http://localhost:8081/api/debug/status
   ```
   
   Phải thấy: `"totalUsers": 4`

---

## 🧪 TEST TOÀN BỘ HỆ THỐNG

### **1. Test trang register hiển thị OK:**
```
http://localhost:8081/register
```
→ Phải thấy form đăng ký, KHÔNG có Whitelabel Error

### **2. Test đăng ký user mới:**

Trên trang register:
- Username: `newuser123`
- Email: `newuser123@test.com`
- Password: `password123`
- Xác nhận: `password123`
- Click **Register**

→ Phải chuyển đến `/login` với thông báo xanh

### **3. Test login với user mới:**

Trên trang login:
- Username: `newuser123`
- Password: `password123`
- Click **Login**

→ Phải vào được `/customer/dashboard`

### **4. Test login với tài khoản seed:**

**Admin:**
- Username: `admin`
- Password: `admin123`
→ Vào `/admin/dashboard`

**User1:**
- Username: `user1`
- Password: `user123`
→ Vào `/customer/dashboard`

---

## 📸 GỬI CHO TÔI SAU KHI TEST:

1. Screenshot trang `/register` (phải hiển thị form, không có lỗi)
2. Screenshot `/api/debug/status` (totalUsers phải >= 4)
3. Screenshot kết quả đăng ký user mới (thông báo xanh)
4. Screenshot dashboard sau khi login thành công
5. Nếu có lỗi → Copy toàn bộ error stack trace từ console

---

## 🎯 CHECKLIST:

- [ ] Stop app
- [ ] Rebuild project
- [ ] Chạy SQL script (nếu database trống)
- [ ] Run app
- [ ] Kiểm tra `/api/debug/status` → totalUsers >= 4
- [ ] Mở `/register` → Thấy form (không lỗi)
- [ ] Đăng ký user mới → Thành công
- [ ] Login với user mới → Vào dashboard
- [ ] Login với admin/admin123 → Thành công
- [ ] Login với user1/user123 → Thành công

---

## ⏱️ THỜI GIAN THỰC HIỆN:

- Rebuild: ~30 giây
- Chạy SQL (nếu cần): ~1 phút
- Run app: ~10 giây
- Test: ~2 phút

**TỔNG: ~4 phút**

---

BÂY GIỜ HÃY:
1. ✅ **Rebuild project**
2. ✅ **Run lại app**
3. ✅ **Mở** `http://localhost:8081/register`
4. ✅ **Gửi screenshot** cho tôi

Nếu vẫn thấy Whitelabel Error → Copy toàn bộ error từ console và gửi cho tôi! 🚀

