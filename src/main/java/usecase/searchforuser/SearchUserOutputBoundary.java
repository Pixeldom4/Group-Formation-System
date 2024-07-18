package usecase.searchforuser;

import entities.UserInterface;

public interface SearchUserOutputBoundary {
    /**
     * Present the user to the presenter.
     *
     * @param user The user to present.
     */

    void presentUser(UserInterface user);
}
