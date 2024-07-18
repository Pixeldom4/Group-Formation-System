package usecase.searchforuser;

import entities.UserInterface;

public interface SearchUserInterface {
    /**
     * Searches for users based on the given email.
     *
     * @param email the email to search for
     *
     * @return the list of users that match the email
     */

    UserInterface searchUserByEmail(String email);
}
