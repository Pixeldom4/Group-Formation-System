package usecase.createuser;

import dataaccess.DAOImplementationConfig;
import entities.User;
import dataaccess.IUserRepository;
import usecase.BCryptPasswordHasher;
import usecase.IPasswordHasher;

public class CreateUserInteractor implements CreateUserInputBoundary {
    private IUserRepository userRepository = DAOImplementationConfig.getUserRepository();
    private final CreateUserOutputBoundary userPresenter;

    public CreateUserInteractor(CreateUserOutputBoundary userPresenter) {
        this.userPresenter = userPresenter;
    }

    /**
     * Used for testing when save file is in another folder
     * @param userRepository the user repository
     * @param userPresenter the user presenter
     */
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
        IPasswordHasher passwordHasher = new BCryptPasswordHasher();
        String hashedPassword = passwordHasher.hashPassword(inputData.getPassword());
        User user = userRepository.createUser(inputData.getEmail(), inputData.getFirstName(), inputData.getLastName(), inputData.getTags(), inputData.getDesiredCompensation(), hashedPassword) ;

        CreateUserOutputData outputData;
        if (user != null) {
            outputData = new CreateUserOutputData(user.getUserId(), user.getFirstName(), user.getLastName(), user.getUserEmail(), user.getDesiredCompensation(), user.getTags(), true);
            userPresenter.prepareSuccessView(outputData);
        } else {
            userPresenter.prepareFailView("Failed to create user.");
        }
    }
}
