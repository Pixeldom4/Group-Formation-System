package usecase.getapplications;

public class GetApplicationsInputData {
    private int userId;
    private int projectId;

    public GetApplicationsInputData(int userId, int projectId){
        this.userId = userId;
        this.projectId = projectId;
    }

    public GetApplicationsInputData(int userId){
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public int getProjectId() {
        return projectId;
    }
}
