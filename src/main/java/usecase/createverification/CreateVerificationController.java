package usecase.createverification;

public class CreateVerificationController {

    private final CreateVerificationInputBoundary inputBoundary;

    public CreateVerificationController(CreateVerificationInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void createVerification() {
        inputBoundary.createVerification();
    }
}
