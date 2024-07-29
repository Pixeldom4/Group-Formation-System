package usecase.createverification;

public class CreateVerificationOutputData {
    private final String verificationImageLocation;

    public CreateVerificationOutputData(String verificationImageLocation) {
        this.verificationImageLocation = verificationImageLocation;
    }

    public String getVerificationImageLocation() {
        return verificationImageLocation;
    }
}
