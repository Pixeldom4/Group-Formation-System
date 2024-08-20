package dataaccess.database;

import dataaccess.database.manager.*;
import entities.Project;
import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ProjectRepositoryTest {
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    private int testProjectId;
    private int testOwnerId;

    private String databaseName;

    @BeforeEach
    void setUp() {
        this.databaseName = "testing.db";

        // Initialize manager classes
        UserTagsManager userTagsManager = new UserTagsManager(databaseName);
        UserProjectsManager userProjectsManager = new UserProjectsManager(databaseName);
        UserManager userManager = new UserManager(databaseName);

        ProjectManager projectManager = new ProjectManager(databaseName);
        ProjectTagsManager projectTagsManager = new ProjectTagsManager(databaseName);
        ProjectEmbeddingsManager projectEmbeddingsManager = new ProjectEmbeddingsManager(databaseName);

        // Create facade instances
        this.userRepository = new UserRepository(userManager, userTagsManager, userProjectsManager);
        this.projectRepository = new ProjectRepository(projectManager, projectTagsManager, projectEmbeddingsManager, userProjectsManager);

        // Connect to the database
        userManager.connect();
        userProjectsManager.connect();
        userTagsManager.connect();
        projectManager.connect();
        projectTagsManager.connect();
        projectEmbeddingsManager.connect();

        // Initialize the database tables
        userManager.initialize();
        userTagsManager.initialize();
        projectManager.initialize();
        projectTagsManager.initialize();
        projectEmbeddingsManager.initialize();
        userProjectsManager.initialize();

        // Clean up any existing user with the test email
        String testEmail = "owner@test.com";
        deleteUserByEmail(testEmail);

        // Create a test user to be the owner of the projects
        HashSet<String> userTags = new HashSet<>();
        userTags.add("Developer");
        User user = userRepository.createUser(testEmail, "Owner", "Test", userTags, 100000.0, "password");
        testOwnerId = user.getUserId();

        HashSet<String> tags = new HashSet<>();
        tags.add("Java");
        tags.add("SQL");

        Project project = projectRepository.createProject("Test Project", 1000.0, "This is a test project", tags, new float[]{0.1f, 0.2f, 0.3f}, testOwnerId);
        testProjectId = project.getProjectId();
    }

    @AfterEach
    void tearDown() {
        if (userRepository != null) {
            deleteUserByEmail("owner@test.com");
        }

        DatabaseConnection.disconnect();
        DatabaseHelper.deleteDatabaseFile(databaseName);
    }

    private void deleteUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email);
        if (user != null) {
            userRepository.deleteUser(user.getUserId());
        }
    }

    @Test
    void createProject() {
        HashSet<String> tags = new HashSet<>();
        tags.add("Java");
        tags.add("SQL");

        Project project = projectRepository.createProject("Another Test Project", 2000.0, "This is another test project", tags, new float[]{0.1f, 0.2f, 0.3f}, testOwnerId);

        assertNotNull(project);
        assertEquals("Another Test Project", project.getProjectTitle());
        assertEquals(2000.0, project.getProjectBudget(), 0);
        assertEquals("This is another test project", project.getProjectDescription());
        assertTrue(project.getProjectTags().contains("Java"));
        assertTrue(project.getProjectTags().contains("SQL"));
    }

    @Test
    void deleteProject() {
        projectRepository.deleteProject(testProjectId);
        Project project = projectRepository.getProjectById(testProjectId);

        assertNull(project);
    }

    @Test
    void getProjectById() {
        Project project = projectRepository.getProjectById(testProjectId);

        assertNotNull(project);
        assertEquals("Test Project", project.getProjectTitle());
        assertEquals(1000.0, project.getProjectBudget(), 0);
        assertEquals("This is a test project", project.getProjectDescription());
        assertTrue(project.getProjectTags().contains("Java"));
        assertTrue(project.getProjectTags().contains("SQL"));
    }

    @Test
    void addTags() {
        HashSet<String> newTags = new HashSet<>();
        newTags.add("NewTag1");
        newTags.add("NewTag2");

        projectRepository.addTags(testProjectId, newTags);

        Project project = projectRepository.getProjectById(testProjectId);

        assertNotNull(project);
        assertTrue(project.getProjectTags().contains("NewTag1"));
        assertTrue(project.getProjectTags().contains("NewTag2"));
    }

    @Test
    void removeTags() {
        HashSet<String> tagsToRemove = new HashSet<>();
        tagsToRemove.add("Java");

        projectRepository.removeTags(testProjectId, tagsToRemove);

        Project project = projectRepository.getProjectById(testProjectId);

        assertNotNull(project);
        assertFalse(project.getProjectTags().contains("Java"));
        assertTrue(project.getProjectTags().contains("SQL"));
    }

    @Test
    void update() {
        HashSet<String> newTags = new HashSet<>();
        newTags.add("UpdatedTag");

        float[] newEmbeddings = {0.4f, 0.5f, 0.6f};

        projectRepository.update(testProjectId, "Updated Title", 1500.0, "Updated Description", newTags, newEmbeddings);

        Project updatedProject = projectRepository.getProjectById(testProjectId);

        assertNotNull(updatedProject);
        assertEquals("Updated Title", updatedProject.getProjectTitle());
        assertEquals(1500.0, updatedProject.getProjectBudget(), 0);
        assertEquals("Updated Description", updatedProject.getProjectDescription());
        assertTrue(updatedProject.getProjectTags().contains("UpdatedTag"));
        assertFalse(updatedProject.getProjectTags().contains("Java"));
        assertFalse(updatedProject.getProjectTags().contains("SQL"));

        // Validate embeddings
        HashMap<Integer, float[]> embeddingsMap = projectRepository.getAllEmbeddings();
        assertTrue(embeddingsMap.containsKey(testProjectId));
        float[] retrievedEmbeddings = embeddingsMap.get(testProjectId);
        assertArrayEquals(newEmbeddings, retrievedEmbeddings);
    }

    @Test
    void getAllEmbeddings() {
        HashMap<Integer, float[]> embeddingsMap = projectRepository.getAllEmbeddings();

        assertNotNull(embeddingsMap);
        assertTrue(embeddingsMap.containsKey(testProjectId));

        float[] embeddings = embeddingsMap.get(testProjectId);
        assertNotNull(embeddings);
        assertEquals(3, embeddings.length);
        assertEquals(0.1f, embeddings[0], 0);
        assertEquals(0.2f, embeddings[1], 0);
        assertEquals(0.3f, embeddings[2], 0);
    }

    @Test
    void getOwnerId() {
        int ownerId = projectRepository.getOwnerId(testProjectId);
        assertEquals(testOwnerId, ownerId);
    }
}
