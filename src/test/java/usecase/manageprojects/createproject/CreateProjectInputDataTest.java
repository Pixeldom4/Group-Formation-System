package usecase.manageprojects.createproject;

import org.junit.jupiter.api.Test;
import usecase.manageprojects.createproject.CreateProjectInputData;

import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CreateProjectInputData class.
 */
public class CreateProjectInputDataTest {

    /**
     * Tests that the constructor initializes all fields correctly.
     */
    @Test
    void constructorInitializesFieldsCorrectly() {
        String title = "Project Title";
        double budget = 5000.0;
        String description = "Project Description";
        HashSet<String> tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");
        int creatorUserId = 1;

        CreateProjectInputData inputData = new CreateProjectInputData(title, budget, description, tags, creatorUserId);

        assertEquals(title, inputData.getTitle());
        assertEquals(budget, inputData.getBudget());
        assertEquals(description, inputData.getDescription());
        assertEquals(tags, inputData.getTags());
        assertEquals(creatorUserId, inputData.getCreatorUserId());
    }

    /**
     * Tests that the constructor handles null tags correctly.
     */
    @Test
    void handlesNullTags() {
        String title = "Project Title";
        double budget = 5000.0;
        String description = "Project Description";
        HashSet<String> tags = null;
        int creatorUserId = 1;

        CreateProjectInputData inputData = new CreateProjectInputData(title, budget, description, tags, creatorUserId);

        assertNull(inputData.getTags());
    }

    /**
     * Tests that the constructor handles an empty title correctly.
     */
    @Test
    void handlesEmptyTitle() {
        String title = "";
        double budget = 5000.0;
        String description = "Project Description";
        HashSet<String> tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");
        int creatorUserId = 1;

        CreateProjectInputData inputData = new CreateProjectInputData(title, budget, description, tags, creatorUserId);

        assertEquals(title, inputData.getTitle());
    }

    /**
     * Tests that the constructor handles a zero budget correctly.
     */
    @Test
    void handlesZeroBudget() {
        String title = "Project Title";
        double budget = 0.0;
        String description = "Project Description";
        HashSet<String> tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");
        int creatorUserId = 1;

        CreateProjectInputData inputData = new CreateProjectInputData(title, budget, description, tags, creatorUserId);

        assertEquals(budget, inputData.getBudget());
    }
}