package entities;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Unit tests for the Project class.
 */
public class ProjectTest {

    private int projectId;
    private String projectTitle;
    private double projectBudget;
    private String projectDescription;
    private HashSet<String> projectTags;
    private Project project;

    /**
     * Sets up the test environment before each test.
     */
    @Before
    public void setUp() {
        projectId = 1;
        projectTitle = "Test Project";
        projectBudget = 1000.0;
        projectDescription = "This is a test project.";
        projectTags = new HashSet<>();
        projectTags.add("Test");
        projectTags.add("Project");
        project = new Project(projectId, projectTitle, projectBudget, projectDescription, projectTags);
    }

    /**
     * Tests the constructor of the Project class.
     */
    @Test
    public void testConstructor() {
        assertNotNull("The project object should not be null", project);
        assertEquals("The project ID should match", projectId, project.getProjectId());
        assertEquals("The project title should match", projectTitle, project.getProjectTitle());
        assertEquals("The project budget should match", projectBudget, project.getProjectBudget(), 0.0);
        assertEquals("The project description should match", projectDescription, project.getProjectDescription());
        assertEquals("The project tags should match", projectTags, project.getProjectTags());
    }

    /**
     * Tests the getProjectId and setProjectId methods of the Project class.
     */
    @Test
    public void testGetAndSetProjectId() {
        int newProjectId = 2;
        project.setProjectId(newProjectId);

        assertEquals("The new project ID should match", newProjectId, project.getProjectId());
    }

    /**
     * Tests the getProjectTitle and setProjectTitle methods of the Project class.
     */
    @Test
    public void testGetAndSetProjectTitle() {
        String newProjectTitle = "New Test Project";
        project.setProjectTitle(newProjectTitle);

        assertEquals("The new project title should match", newProjectTitle, project.getProjectTitle());
    }

    /**
     * Tests the getProjectBudget and setProjectBudget methods of the Project class.
     */
    @Test
    public void testGetAndSetProjectBudget() {
        double newProjectBudget = 2000.0;
        project.setProjectBudget(newProjectBudget);

        assertEquals("The new project budget should match", newProjectBudget, project.getProjectBudget(), 0.0);
    }

    /**
     * Tests the getProjectDescription and setProjectDescription methods of the Project class.
     */
    @Test
    public void testGetAndSetProjectDescription() {
        String newProjectDescription = "This is a new test project.";
        project.setProjectDescription(newProjectDescription);

        assertEquals("The new project description should match", newProjectDescription, project.getProjectDescription());
    }

    /**
     * Tests the getProjectTags and setProjectTags methods of the Project class.
     */
    @Test
    public void testGetAndSetProjectTags() {
        HashSet<String> newProjectTags = new HashSet<>();
        newProjectTags.add("New");
        newProjectTags.add("Test");
        project.setProjectTags(newProjectTags);

        assertEquals("The new project tags should match", newProjectTags, project.getProjectTags());
    }
}
