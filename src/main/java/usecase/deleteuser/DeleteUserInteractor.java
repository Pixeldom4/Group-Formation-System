package usecase.deleteuser;

import dataaccess.IUserRepository;

/**
 * Interactor for the delete user use case.
 * Handles the business logic for deleting a user..
 */
public class DeleteUserInteractor implements DeleteUserInputBoundary {
    private final IUserRepository userRepository;
    private final DeleteUserPresenter deleteUserPresenter;

    /**
     * Constructs a DeleteUserInteractor with the specified repository and presenter.
     *
     * @param userRepository the repository to interact with the database.
     * @param deleteUserPresenter the presenter to handle the output presentation.
     */
    public DeleteUserInteractor(IUserRepository userRepository, DeleteUserPresenter deleteUserPresenter) {
        this.userRepository = userRepository;
        this.deleteUserPresenter = deleteUserPresenter;
    }

    /**
     * Deletes a user with the provided input data.
     *
     * @param inputData the input data required to delete the user.
     */
    @Override
    public void deleteUser(DeleteUserInputData inputData) {
        int userId = inputData.getUserId();

        if (userRepository.deleteUser(userId)) {
            DeleteUserOutputData outputData = new DeleteUserOutputData(userId);
            deleteUserPresenter.prepareSuccessView(outputData);
        } else {
            deleteUserPresenter.prepareFailView("Failed to delete user.");
        }
    }
}
