# Script to generate BCrypt password hashes for seed data
Write-Host "Generating BCrypt password hashes..."
Write-Host ""

# Compile and run a simple Java program to generate BCrypt hashes
$javaCode = @"
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerateHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("admin123 -> " + encoder.encode("admin123"));
        System.out.println("staff123 -> " + encoder.encode("staff123"));
        System.out.println("user123 -> " + encoder.encode("user123"));
        System.out.println("user456 -> " + encoder.encode("user456"));
    }
}
"@

# Save Java code to temp file
$javaCode | Out-File -FilePath "GenerateHash.java" -Encoding UTF8

# Run using Maven classpath
mvn exec:java -Dexec.mainClass="GenerateHash" -Dexec.classpathScope=compile

