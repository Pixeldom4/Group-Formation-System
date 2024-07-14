package usecase.createuser;

import entities.User;
import dataaccess.IUserRepository;

public class CreateUserInteractor implements CreateUserInputBoundary {
    private final IUserRepository userRepository;
    private final CreateUserOutputBoundary userPresenter;

    public CreateUserInteractor(IUserRepository userRepository, CreateUserOutputBoundary userPresenter) {
        this.userRepository = userRepository;
        this.userPresenter = userPresenter;
    }

    /**
     * Creates a user with the provided input data.
     *
     * @param inputData the input data required to create a user.
     */
    @Override
    public void createUser(CreateUserInputData inputData) {
        User user = userRepository.createUser(inputData.getEmail(), inputData.getFirstName(), inputData.getLastName(), inputData.getTags(), inputData.getDesiredCompensation(), inputData.getPassword()) ;

        CreateUserOutputData outputData;
        if (user != null) {
            outputData = new CreateUserOutputData(user.getUserId(), user.getFirstName(), user.getLastName(), user.getUserEmail(), user.getDesiredCompensation(), user.getTags(), true);
            userPresenter.prepareSuccessView(outputData);
        } else {
            userPresenter.prepareFailView("Failed to create user.");
        }
    }
}
