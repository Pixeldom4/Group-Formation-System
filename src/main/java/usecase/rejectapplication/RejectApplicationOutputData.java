package usecase.rejectapplication;

public class RejectApplicationOutputData {

    private final String rejectedName;

    public RejectApplicationOutputData(String acceptedName) {
        this.rejectedName = acceptedName;
    }

    public String getRejectedName() {
        return rejectedName;
    }

}
