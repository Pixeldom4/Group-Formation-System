package usecase;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Implementation of the PasswordHasher interface using BCrypt.
 */
public class BCryptPasswordHasher implements PasswordHasher {

    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructs a BCryptPasswordHasher with a new BCryptPasswordEncoder.
     */
    public BCryptPasswordHasher() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Hashes a password using BCrypt.
     *
     * @param password the password to hash.
     * @return the hashed password.
     */
    @Override
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Checks if a password matches a hashed password using BCrypt.
     *
     * @param password the password to check.
     * @param hashed   the hashed password to check against.
     * @return true if the password matches the hashed password, false otherwise.
     */
    @Override
    public boolean checkPassword(String password, String hashed) {
        return passwordEncoder.matches(password, hashed);
    }
}
