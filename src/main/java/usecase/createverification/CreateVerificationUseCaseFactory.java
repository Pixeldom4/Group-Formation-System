package usecase.createverification;

public class CreateVerificationUseCaseFactory {

    private CreateVerificationUseCaseFactory() {
    }

    public static CreateVerificationController createController(CreateVerificationViewModel viewModel) {
        CreateVerificationPresenter presenter = new CreateVerificationPresenter(viewModel);
        CreateVerificationInteractor interactor = new CreateVerificationInteractor(presenter, new ImageFinder());
        return new CreateVerificationController(interactor);
    }
}
