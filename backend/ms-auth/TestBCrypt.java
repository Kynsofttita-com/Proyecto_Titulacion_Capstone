import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestBCrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "password";
        String hash1 = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36gBS/O6";

        boolean matches = encoder.matches(password, hash1);
        System.out.println("Hash 1 matches 'password': " + matches);

        // Generate a new hash for "password"
        String newHash = encoder.encode(password);
        System.out.println("New hash for 'password': " + newHash);
        System.out.println("Verifying new hash: " + encoder.matches(password, newHash));
    }
}
