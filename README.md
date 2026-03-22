# TechZone Group4 - Java Spring Boot Project

## Setup nhanh

1. Mở SQL Server Management Studio (SSMS) hoặc chạy `sqlcmd`.
2. Chạy file `setup-db.sql`:
   - `sqlcmd -S localhost -U sa -P 1 -i setup-db.sql`
3. Cập nhật `src/main/resources/application.yml` nếu bạn dùng user khác:
   ```yaml
   spring:
     datasource:
       url: jdbc:sqlserver://localhost:1433;databaseName=TechZoneDB;encrypt=false
       username: sa
       password: 1
       driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver
   ```

## Chạy app

1. Mở PowerShell ở thư mục gốc `D:\TechZone_GROUP4`
2. Chạy: `powershell -ExecutionPolicy Bypass -File .\run.ps1`

## Test nhanh

- Web UI: `http://localhost:8080/`
- Customer dashboard: `http://localhost:8080/customer/dashboard`
- Staff dashboard: `http://localhost:8080/staff/dashboard`
- Admin dashboard: `http://localhost:8080/admin/dashboard`
- Google OAuth: `http://localhost:8080/oauth2/authorization/google`
- Rest API Login: `POST http://localhost:8080/api/auth/login`

## Các bước tiếp theo được hoàn thành sẵn

- Cart/Order/Payment + checkout
- Lịch sử order user
- REST API + JWT
- Redis caching + Elasticsearch config
- Realtime WebSocket chat và dashboard
- Miễn phí mô-đun quản lý role admin/staff/customer

## Môi trường dev (nên cài)

- Java 21
- Apache Maven 3.9+
- SQL Server (1433)
- Redis (6379)
- Elasticsearch (9200)
- Chrome/Firefox
