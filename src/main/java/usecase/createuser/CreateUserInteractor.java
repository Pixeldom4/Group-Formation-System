package usecase.createuser;

import dataaccess.DAOImplementationConfig;
import entities.User;
import dataaccess.IUserRepository;
import usecase.PasswordHasher;

public class CreateUserInteractor implements CreateUserInputBoundary {
    private IUserRepository userRepository = DAOImplementationConfig.getUserRepository();
    private final CreateUserOutputBoundary userPresenter;
    private final PasswordHasher passwordHasher;

    public CreateUserInteractor(CreateUserOutputBoundary userPresenter, PasswordHasher passwordHasher) {
        this.userPresenter = userPresenter;
        this.passwordHasher = passwordHasher;
    }

    /**
     * Used for testing when save file is in another folder
     * @param userRepository the user repository
     * @param userPresenter the user presenter
     */
    public CreateUserInteractor(IUserRepository userRepository, CreateUserOutputBoundary userPresenter, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.userPresenter = userPresenter;
        this.passwordHasher = passwordHasher;
    }

    /**
     * Creates a user with the provided input data.
     *
     * @param inputData the input data required to create a user.
     */
    @Override
    public void createUser(CreateUserInputData inputData) {
        if (userRepository.getUserByEmail(inputData.getEmail()) != null) {
            userPresenter.prepareFailView("Email is already in use.");
            return;
        }
        String hashedPassword = this.passwordHasher.hashPassword(inputData.getPassword());
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
