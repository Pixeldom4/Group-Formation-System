package usecase.createuser;

import config.DataAccessConfig;
import entities.User;
import dataaccess.IUserRepository;
import usecase.PasswordHasher;

/**
 * Interactor class for creating users.
 * Implements the input boundary to handle user creation logic.
 */
public class CreateUserInteractor implements CreateUserInputBoundary {
    private IUserRepository userRepository = DataAccessConfig.getUserRepository();
    private final CreateUserOutputBoundary userPresenter;
    private final PasswordHasher passwordHasher;

    /**
     * Constructs a CreateUserInteractor with the specified presenter and password hasher.
     *
     * @param userPresenter  the presenter to handle output.
     * @param passwordHasher the password hasher.
     */
    public CreateUserInteractor(CreateUserOutputBoundary userPresenter, PasswordHasher passwordHasher) {
        this.userPresenter = userPresenter;
        this.passwordHasher = passwordHasher;
    }

    /**
     * Constructs a CreateUserInteractor with the specified user repository, presenter, and password hasher.
     *
     * @param userRepository  the user repository.
     * @param userPresenter   the presenter to handle output.
     * @param passwordHasher  the password hasher.
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
        String email = inputData.getEmail();

        if (userRepository.getUserByEmail(email) != null) {
            userPresenter.prepareFailView("Email is already in use.");
            return;
        }

        boolean isValidEmail = email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

        if (!isValidEmail) {
            userPresenter.prepareFailView("Please enter a valid email.");
            return;
        }

        if (inputData.getPassword().length() < 5) {
            userPresenter.prepareFailView("Password must be at least 5 characters long.");
            return;
        }

        String hashedPassword = this.passwordHasher.hashPassword(inputData.getPassword());
        User user = userRepository.createUser(email, inputData.getFirstName(), inputData.getLastName(), inputData.getTags(), inputData.getDesiredCompensation(), hashedPassword);

        CreateUserOutputData outputData;
        if (user != null) {
            outputData = new CreateUserOutputData(user.getUserId(), user.getFirstName(), user.getLastName(),email, user.getDesiredCompensation(), user.getTags(), true);
            userPresenter.prepareSuccessView(outputData);
        } else {
            userPresenter.prepareFailView("Failed to create user.");
        }
    }
}
