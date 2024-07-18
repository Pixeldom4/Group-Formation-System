package usecase.searchforuser;

import dataaccess.DAOImplementationConfig;
import dataaccess.IUserRepository;
import entities.User;
import entities.UserInterface;

public class LocalUserSearchObject implements SearchUserInterface {
    private IUserRepository userDataAccess = DAOImplementationConfig.getUserRepository();

    /**
     * Creates a new LocalUserSearchObject.
     */
    public LocalUserSearchObject() {
    }

    /**
     * Creates a new LocalUserSearchObject using the given user repository.
     * Used for testing where files are not stored in the user folder.
     *
     * @param userRepository The project repository to use.
     */
    public LocalUserSearchObject(IUserRepository userRepository) {
        userDataAccess = userRepository;
    }

    /**
     * Searches for users by email.
     *
     * @param email The email of the user to search for.
     */
    @Override
    public UserInterface searchUserByEmail(String email) {
        User result = userDataAccess.getUserByEmail(email);
        return result;
    }
}