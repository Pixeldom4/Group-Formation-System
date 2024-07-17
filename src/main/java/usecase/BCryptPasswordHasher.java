package usecase;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordHasher implements IPasswordHasher {

    private final BCryptPasswordEncoder passwordEncoder;

    public BCryptPasswordHasher() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean checkPassword(String password, String hashed) {
        return passwordEncoder.matches(password, hashed);
    }
}
