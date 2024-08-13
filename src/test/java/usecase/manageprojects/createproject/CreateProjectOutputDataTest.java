package usecase.manageprojects.createproject;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CreateProjectOutputData class.
 */
public class CreateProjectOutputDataTest {

    /**
     * Tests the creation of CreateProjectOutputData with valid data.
     * Verifies that all fields are correctly set.
     */
    @Test
    void createProjectOutputDataWithValidData() {
        HashSet<String> tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");
        CreateProjectOutputData outputData = new CreateProjectOutputData(1, "Title", 1000.0, "Description", tags);

        assertEquals(1, outputData.getProjectId());
        assertEquals("Title", outputData.getTitle());
        assertEquals(1000.0, outputData.getBudget());
        assertEquals("Description", outputData.getDescription());
        assertEquals(tags, outputData.getTags());
    }

    /**
     * Tests the creation of CreateProjectOutputData with empty tags.
     * Verifies that the tags field is empty.
     */
    @Test
    void createProjectOutputDataWithEmptyTags() {
        HashSet<String> tags = new HashSet<>();
        CreateProjectOutputData outputData = new CreateProjectOutputData(1, "Title", 1000.0, "Description", tags);

        assertEquals(1, outputData.getProjectId());
        assertEquals("Title", outputData.getTitle());
        assertEquals(1000.0, outputData.getBudget());
        assertEquals("Description", outputData.getDescription());
        assertTrue(outputData.getTags().isEmpty());
    }

    /**
     * Tests the immutability of the tags field in CreateProjectOutputData.
     * Verifies that changes to the original tags set do not affect the tags field in the output data.
     */
    @Test
    void createProjectOutputDataImmutability() {
        HashSet<String> tags = new HashSet<>();
        tags.add("tag1");
        CreateProjectOutputData outputData = new CreateProjectOutputData(1, "Title", 1000.0, "Description", tags);
        tags.add("tag2");

        assertEquals(1, outputData.getProjectId());
        assertEquals("Title", outputData.getTitle());
        assertEquals(1000.0, outputData.getBudget());
        assertEquals("Description", outputData.getDescription());
        assertEquals(1, outputData.getTags().size());
        assertTrue(outputData.getTags().contains("tag1"));
        assertFalse(outputData.getTags().contains("tag2"));
    }
}