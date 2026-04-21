import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerateHashTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "Admin2026!";
        String hash = encoder.encode(password);
        System.out.println("Password: " + password);
        System.out.println("Hash: " + hash);
    }
}
