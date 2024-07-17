package usecase.createuser;

import usecase.BCryptPasswordHasher;
import usecase.PasswordHasher;
import viewmodel.CreateUserPanelViewModel;

public class CreateUserUseCaseFactory {

    private CreateUserUseCaseFactory() {}

    public static CreateUserController create(CreateUserPanelViewModel createUserViewModel) {
        CreateUserPresenter presenter = new CreateUserPresenter(createUserViewModel);
        PasswordHasher passwordHasher = new BCryptPasswordHasher();
        CreateUserInputBoundary interactor = new CreateUserInteractor(presenter, passwordHasher);
        return new CreateUserController(interactor);
    }

}
