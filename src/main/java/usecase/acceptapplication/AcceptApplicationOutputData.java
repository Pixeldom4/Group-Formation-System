package usecase.acceptapplication;

public class AcceptApplicationOutputData {
    private final String acceptedName;

    public AcceptApplicationOutputData(String acceptedName) {
        this.acceptedName = acceptedName;
    }

    public String getAcceptedName() {
        return acceptedName;
    }

}
