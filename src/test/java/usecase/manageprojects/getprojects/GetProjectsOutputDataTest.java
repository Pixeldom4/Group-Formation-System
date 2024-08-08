package usecase.manageprojects.getprojects;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the GetProjectsOutputData class.
 */
public class GetProjectsOutputDataTest {

    /**
     * Tests the constructor and getData method of GetProjectsOutputData.
     */
    @Test
    public void testGetProjectsOutputData() {
        // Create a sample set of ProjectData objects
        HashSet<ProjectData> projects = new HashSet<>();

        // Create GetProjectsOutputData instance
        GetProjectsOutputData outputData = new GetProjectsOutputData(projects);

        // Verify the data
        assertEquals(projects, outputData.getData());
    }
}
