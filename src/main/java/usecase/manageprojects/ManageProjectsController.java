package usecase.manageprojects;

import usecase.createproject.CreateProjectController;
import usecase.createproject.CreateProjectInputBoundary;
import usecase.createproject.CreateProjectInputData;
import usecase.deleteproject.DeleteProjectController;
import usecase.deleteproject.DeleteProjectInputBoundary;
import usecase.deleteproject.DeleteProjectInputData;
import usecase.editproject.EditProjectController;
import usecase.editproject.EditProjectInputBoundary;
import usecase.editproject.EditProjectInputData;
import usecase.getprojects.GetProjectsInputBoundary;
import usecase.getprojects.GetProjectsInputData;
import usecase.getprojects.GetProjectsOutputBoundary;

import java.util.HashSet;

public class ManageProjectsController {
    private final GetProjectsInputBoundary getProjectsController;
    private final CreateProjectInputBoundary createProjectController;
    private final EditProjectInputBoundary editProjectController;
    private final DeleteProjectInputBoundary deleteProjectController;


    public ManageProjectsController(
            GetProjectsInputBoundary getProjectsController,
            CreateProjectInputBoundary createProjectController,
            EditProjectInputBoundary editProjectController,
            DeleteProjectInputBoundary deleteProjectController){
        this.getProjectsController = getProjectsController;
        this.createProjectController = createProjectController;
        this.editProjectController = editProjectController;
        this.deleteProjectController = deleteProjectController;


    }

    /**
     * Retrieves projects for the logged-in user.
     */
    public void getProjects(int userId) {
        GetProjectsInputData inputData = new GetProjectsInputData(userId);
        getProjectsController.getProjects(inputData);
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
        createProjectController.createProject(inputData);
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
        editProjectController.editProject(inputData);
    }

    /**
     * Calls the interactor to delete a project.
     *
     * @param projectId the ID of the project to be deleted.
     */
    public void deleteProject(int projectId) {
        DeleteProjectInputData inputData = new DeleteProjectInputData(projectId);
        deleteProjectController.deleteProject(inputData);
    }

}
