package usecase;

public interface PasswordHasher {
    String hashPassword(String password);
    boolean checkPassword(String password, String hashed);
}
