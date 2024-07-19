package usecase.manageapplications;

public class ManageApplicationsInputData {
    private int userId;
    private int projectId;

    public ManageApplicationsInputData(int projectId, int userId){
        this.projectId = projectId;
        this.userId = userId;
    }

    public ManageApplicationsInputData(int projectId){
        this.projectId = projectId;
    }

    public int getUserId() {
        return userId;
    }

    public int getProjectId() {
        return projectId;
    }
}
