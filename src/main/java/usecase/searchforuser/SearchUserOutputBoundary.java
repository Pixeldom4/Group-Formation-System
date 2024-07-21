package usecase.searchforuser;

import entities.UserInterface;

/**
 * Output boundary interface for searching users.
 * Defines methods to present the search results.
 */
public interface SearchUserOutputBoundary {
    /**
     * Presents the user to the presenter.
     *
     * @param user The user to present.
     */
    void presentUser(UserInterface user);
}
