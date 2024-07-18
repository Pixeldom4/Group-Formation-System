package usecase.searchforuser;

public interface SearchUserInputBoundary {
    /**
     * Search for a user by its email.
     *
     * @param email the email to search for
     */

    void searchUserByEmail(String email);
}
