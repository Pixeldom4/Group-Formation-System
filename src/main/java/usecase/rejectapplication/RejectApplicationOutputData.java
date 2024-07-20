package usecase.rejectapplication;

public class RejectApplicationOutputData {

    private String rejectedName;

    public RejectApplicationOutputData(String acceptedName) {
        this.rejectedName = acceptedName;
    }

    public String getRejectedName() {
        return rejectedName;
    }

}
