package usecase;

/**
 * Interface for hashing and checking passwords.
 */
public interface PasswordHasher {
    /**
     * Hashes a password.
     *
     * @param password the password to hash.
     * @return the hashed password.
     */
    String hashPassword(String password);

    /**
     * Checks if a password matches a hashed password.
     *
     * @param password the password to check.
     * @param hashed   the hashed password to check against.
     * @return true if the password matches the hashed password, false otherwise.
     */
    boolean checkPassword(String password, String hashed);
}
