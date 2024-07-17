package usecase;

public interface IPasswordHasher {
    String hashPassword(String password);
    boolean checkPassword(String password, String hashed);
}
