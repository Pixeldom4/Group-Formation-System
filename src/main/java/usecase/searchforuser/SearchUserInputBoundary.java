package usecase.searchforuser;

/**
 * Input boundary interface for searching users.
 * Defines the method to search for users based on the given email.
 */
public interface SearchUserInputBoundary {
    /**
     * Searches for a user by email.
     *
     * @param email The email to search for.
     */
    void searchUserByEmail(String email);
}
