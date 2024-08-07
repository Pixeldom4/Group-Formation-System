package usecase.searchforuser;

import config.DataAccessConfig;
import dataaccess.IUserRepository;
import entities.UserInterface;

/**
 * Local implementation for searching users.
 * Uses a user repository to search for users by email.
 */
public class LocalUserSearchObject implements SearchUserInterface {
    private IUserRepository userDataAccess = DataAccessConfig.getUserRepository();

    /**
     * Creates a new LocalUserSearchObject.
     */
    public LocalUserSearchObject() {
    }

    /**
     * Creates a new LocalUserSearchObject using the given user repository.
     * Used for testing where files are not stored in the user folder.
     *
     * @param userRepository The user repository to use.
     */
    public LocalUserSearchObject(IUserRepository userRepository) {
        userDataAccess = userRepository;
    }

    /**
     * Searches for users by email.
     *
     * @param email The email of the user to search for.
     * @return The user that matches the email.
     */
    @Override
    public UserInterface searchUserByEmail(String email) {
        return userDataAccess.getUserByEmail(email);
    }
}
