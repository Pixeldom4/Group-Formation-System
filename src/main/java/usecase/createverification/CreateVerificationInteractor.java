package usecase.createverification;

public class CreateVerificationInteractor implements CreateVerificationInputBoundary {

    private final CreateVerificationOutputBoundary outputBoundary;
    private final ImageFinderInterface imageFinder;
    private final String imageFolder = "verifyimages/";

    public CreateVerificationInteractor(CreateVerificationOutputBoundary outputBoundary, ImageFinderInterface imageFinder) {
        this.outputBoundary = outputBoundary;
        this.imageFinder = imageFinder;
    }

    @Override
    public void createVerification() {
        String imageLocation = imageFinder.findImage(imageFolder);
        CreateVerificationOutputData outputData = new CreateVerificationOutputData(imageLocation);
        outputBoundary.verificationCreated(outputData);
    }
}
