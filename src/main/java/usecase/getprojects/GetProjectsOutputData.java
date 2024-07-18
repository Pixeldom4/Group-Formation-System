package usecase.getprojects;

public class GetProjectsOutputData {
    private final String[][] projects;

    public GetProjectsOutputData(String[][] projects){
        this.projects = projects;
    }

    public String[][] getData(){
        return this.projects;
    }
}
