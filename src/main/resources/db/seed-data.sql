INSERT INTO roles (name) VALUES ('ADMIN'), ('STAFF'), ('CUSTOMER');

INSERT INTO users (username, password, raw_password, email, enabled) VALUES
('admin', 'placeholder_will_be_fixed_on_startup', 'admin123', 'admin@techzone.dev', true),
('staff1', 'placeholder_will_be_fixed_on_startup', 'staff123', 'staff1@techzone.dev', true),
('user1', 'placeholder_will_be_fixed_on_startup', 'user123', 'user1@techzone.dev', true),
('user2', 'placeholder_will_be_fixed_on_startup', 'user456', 'user2@techzone.dev', true);

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u JOIN roles r ON r.name='ADMIN' WHERE u.username='admin';
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u JOIN roles r ON r.name='STAFF' WHERE u.username='staff1';
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u JOIN roles r ON r.name='CUSTOMER' WHERE u.username='user1';
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u JOIN roles r ON r.name='CUSTOMER' WHERE u.username='user2';

INSERT INTO categories (name, description) VALUES
('Thiết bị bếp', 'Bếp, lò nướng, máy hút mùi'),
('Điện thoại', 'Smartphone và phụ kiện'),
('Thiết bị gia dụng', 'Máy giặt, tủ lạnh, điều hòa');

INSERT INTO brands (name, description) VALUES
('Panasonic', 'Thương hiệu Nhật Bản'),
('Samsung', 'Thương hiệu Hàn Quốc'),
('Electrolux', 'Thương hiệu châu Âu');

INSERT INTO suppliers (name, contact_info) VALUES
('KTB Supply Co', 'Hà Nội - 024-1234567'),
('HouseTech', 'HCM - 028-7654321');

INSERT INTO products (name, description, price, stock, category_id, brand_id, supplier_id) VALUES
('Bếp từ Panasonic', 'Bếp từ đôi 2200W - tiết kiệm năng lượng', 6500000, 45, 1, 1, 1),
('Tủ lạnh Samsung 356L', 'Tủ lạnh 2 cánh với công nghệ Digital Inverter', 11000000, 20, 3, 2, 2),
('Điện thoại Samsung Galaxy S', 'Smartphone cao cấp 12GB RAM', 18000000, 60, 2, 2, 2),
('Máy giặt Electrolux 9kg', 'Máy giặt cửa trước, nhiều chế độ', 8500000, 30, 3, 3, 1);

INSERT INTO vouchers (code, discount_percent, discount_value, active, start_date, expiry_date) VALUES
('WELCOME10', 10, 0, true, '2026-01-01', '2027-01-01'),
('KTB50', 0, 50000, true, '2026-01-01', '2027-01-01');

INSERT INTO addresses (user_id, street, city, province, postal_code, is_default) VALUES
((SELECT id FROM users WHERE username = 'user1'), '123 Phố Huế', 'Hà Nội', 'Hai Bà Trưng', '100000', true),
((SELECT id FROM users WHERE username = 'user2'), '456 Lê Lợi', 'Hồ Chí Minh', 'Quận 1', '700000', true);

