package usecase.manageusers.getusers;

import java.util.HashSet;

/**
 * Data transfer object for output data containing a set of user data.
 */
public class GetUsersOutputData {
    private final HashSet<UserData> users;

    /**
     * Constructs a GetUsersOutputData object with the specified users.
     *
     * @param users a set of UserData objects.
     */
    public GetUsersOutputData(HashSet<UserData> users) {
        this.users = users;
    }

    /**
     * Gets the set of UserData objects.
     *
     * @return the set of UserData objects.
     */
    public HashSet<UserData> getUsers() {
        return this.users;
    }
}
