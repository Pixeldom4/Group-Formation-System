package usecase.createuser;

import viewmodel.CreateUserPanelViewModel;

public class CreateUserUseCaseFactory {

    private CreateUserUseCaseFactory() {}

    public static CreateUserController create(CreateUserPanelViewModel createUserViewModel) {
        CreateUserPresenter presenter = new CreateUserPresenter(createUserViewModel);
        CreateUserInputBoundary interactor = new CreateUserInteractor(presenter);
        return new CreateUserController(interactor);
    }

}
