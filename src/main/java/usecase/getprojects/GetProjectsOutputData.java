package usecase.getprojects;

public class GetProjectsOutputData {
    private final Object[][] projects;

    public GetProjectsOutputData(Object[][] projects){
        this.projects = projects;
    }

    public Object[][] getData(){
        return this.projects;
    }
}
