package usecase.getapplications;

public class GetApplicationsInputData {
    private int userId;
    private int projectId;

    public GetApplicationsInputData(int projectId, int userId){
        this.projectId = projectId;
        this.userId = userId;
    }

    public GetApplicationsInputData(int projectId){
        this.projectId = projectId;
    }

    public int getUserId() {
        return userId;
    }

    public int getProjectId() {
        return projectId;
    }
}
