package usecase.getprojects;

public class GetProjectsInputData {
    public final int userId;

    public GetProjectsInputData(int userId){
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
