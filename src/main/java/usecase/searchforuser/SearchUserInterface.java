package usecase.searchforuser;

import entities.UserInterface;

/**
 * Interface for searching users.
 */
public interface SearchUserInterface {
    /**
     * Searches for users based on the given email.
     *
     * @param email The email to search for.
     * @return The user that matches the email.
     */
    UserInterface searchUserByEmail(String email);
}
