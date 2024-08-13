package usecase.manageprojects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageprojects.createproject.CreateProjectInputBoundary;
import usecase.manageprojects.deleteproject.DeleteProjectInputBoundary;
import usecase.manageprojects.editproject.EditProjectInputBoundary;
import usecase.manageprojects.getprojects.GetProjectsInputBoundary;

import static org.mockito.Mockito.*;

public class ManageProjectsControllerTest {
    private ManageProjectsController controller;
    private GetProjectsInputBoundary getProjectsInteractor;
    private CreateProjectInputBoundary createProjectInteractor;
    private EditProjectInputBoundary editProjectInteractor;
    private DeleteProjectInputBoundary deleteProjectInteractor;

    @BeforeEach
    public void setUp() {
        getProjectsInteractor = mock(GetProjectsInputBoundary.class);
        createProjectInteractor = mock(CreateProjectInputBoundary.class);
        editProjectInteractor = mock(EditProjectInputBoundary.class);
        deleteProjectInteractor = mock(DeleteProjectInputBoundary.class);
        controller = new ManageProjectsController(getProjectsInteractor, createProjectInteractor,
                                                  editProjectInteractor, deleteProjectInteractor);
    }

    @Test
    public void testCallGetProjectsInteractor() {
        controller.getProjects(1);
        verify(getProjectsInteractor, times(1)).getProjects(any());
    }

    @Test
    public void testCallCreateProjectInteractor() {
        controller.createProject("title", 1.0, "description", null, 1);
        verify(createProjectInteractor, times(1)).createProject(any());
    }

    @Test
    public void testCallEditProjectInteractor() {
        controller.editProject(1, "newTitle", 1.0, "newDescription",
                               null, 1);
        verify(editProjectInteractor, times(1)).editProject(any());
    }

    @Test
    public void testCallDeleteProjectInteractor() {
        controller.deleteProject(1);
        verify(deleteProjectInteractor, times(1)).deleteProject(any());
    }
}
