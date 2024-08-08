package usecase.manageprojects;

import usecase.manageprojects.createproject.CreateProjectInputBoundary;
import usecase.manageprojects.createproject.CreateProjectInputData;
import usecase.manageprojects.deleteproject.DeleteProjectInputBoundary;
import usecase.manageprojects.deleteproject.DeleteProjectInputData;
import usecase.manageprojects.editproject.EditProjectInputBoundary;
import usecase.manageprojects.editproject.EditProjectInputData;
import usecase.manageprojects.getprojects.GetProjectsInputBoundary;
import usecase.manageprojects.getprojects.GetProjectsInputData;

import java.util.HashSet;

public class ManageProjectsController {
    private final GetProjectsInputBoundary getProjectsInteractor;
    private final CreateProjectInputBoundary createProjectInteractor;
    private final EditProjectInputBoundary editProjectInteractor;
    private final DeleteProjectInputBoundary deleteProjectInteractor;


    public ManageProjectsController(
            GetProjectsInputBoundary getProjectsInteractor,
            CreateProjectInputBoundary createProjectInteractor,
            EditProjectInputBoundary editProjectInteractor,
            DeleteProjectInputBoundary deleteProjectInteractor){
        this.getProjectsInteractor = getProjectsInteractor;
        this.createProjectInteractor = createProjectInteractor;
        this.editProjectInteractor = editProjectInteractor;
        this.deleteProjectInteractor = deleteProjectInteractor;


    }

    /**
     * Retrieves projects for the logged-in user.
     */
    public void getProjects(int userId) {
        GetProjectsInputData inputData = new GetProjectsInputData(userId);
        getProjectsInteractor.getProjects(inputData);
    }

    /**
     * Calls the interactor to create a new project.
     *
     * @param title the title of the project.
     * @param budget the budget of the project.
     * @param description the description of the project.
     * @param tags the tags of the project.
     * @param creatorUserId the user ID of the user creating the project.
     */
    public void createProject(String title, double budget, String description, HashSet<String> tags, int creatorUserId) {
        CreateProjectInputData inputData = new CreateProjectInputData(title, budget, description, tags, creatorUserId);
        createProjectInteractor.createProject(inputData);
    }

    /**
     * Edits a project with the provided input data.
     *
     * @param projectId     the ID of the project to be edited.
     * @param newTitle      the new title of the project.
     * @param newBudget     the new budget of the project.
     * @param newDescription the new description of the project.
     * @param newTags       the new tags of the project.
     * @param editorId      the ID of the user editing the project.
     */
    public void editProject(int projectId, String newTitle, double newBudget, String newDescription, HashSet<String> newTags, int editorId) {
        EditProjectInputData inputData = new EditProjectInputData(projectId, newTitle, newBudget, newDescription, newTags, editorId);
        editProjectInteractor.editProject(inputData);
    }

    /**
     * Calls the interactor to delete a project.
     *
     * @param projectId the ID of the project to be deleted.
     */
    public void deleteProject(int projectId) {
        DeleteProjectInputData inputData = new DeleteProjectInputData(projectId);
        deleteProjectInteractor.deleteProject(inputData);
    }

}
