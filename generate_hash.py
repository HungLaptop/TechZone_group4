import bcrypt

passwords = {
    'admin123': 'admin123',
    'staff123': 'staff123',
    'user123': 'user123',
    'user456': 'user456'
}

print("=== BCrypt Password Hashes ===")
print()
for name, password in passwords.items():
    # Generate BCrypt hash (compatible with Spring Security BCrypt)
    # Using rounds=10 which is the default for BCryptPasswordEncoder
    hashed = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt(rounds=10))
    # BCrypt in Python returns bytes, need to decode to string
    hash_str = hashed.decode('utf-8')
    print(f"{name}: {hash_str}")
    print()

