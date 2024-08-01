package usecase.createverification;

public class CreateVerificationPresenter implements CreateVerificationOutputBoundary {

    private final CreateVerificationViewModel viewModel;

    public CreateVerificationPresenter(CreateVerificationViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void verificationCreated(CreateVerificationOutputData outputData) {
        viewModel.displayVerificationImage(outputData.getVerificationImageLocation());
    }
}
