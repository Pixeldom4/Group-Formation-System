package dataaccess.local;

import dataaccess.IApplicationRepository;
import entities.Application;
import entities.ApplicationInterface;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the LocalApplicationDAO class.
 */
public class LocalApplicationDAOTest {
    private final static String SAVE_LOCATION = "local_data/test/data_access/local_dao/";
    private static IApplicationRepository applicationRepository;
    private final static File saveFile = new File(SAVE_LOCATION + "applications.csv");

    /**
     * Sets up the test environment before all tests.
     *
     * @throws IOException if an I/O error occurs
     */
    @BeforeAll
    public static void setUp() throws IOException {
        Files.deleteIfExists(saveFile.toPath());
        applicationRepository = new LocalApplicationRepository(SAVE_LOCATION);
        applicationRepository.createApplication(10,
                1,
                "project1",
                "test1".getBytes(StandardCharsets.UTF_8));
        applicationRepository.createApplication(10,
                2,
                "project2",
                "test2".getBytes(StandardCharsets.UTF_8));
        applicationRepository.createApplication(20,
                1,
                "project1",
                "test3".getBytes(StandardCharsets.UTF_8));
        applicationRepository.createApplication(30,
                2,
                "project2",
                "test4".getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Tests the retrieval of an application.
     */
    @Test
    public void testGetApplications() {
        ApplicationInterface application = applicationRepository.getApplication(10, 1);
        assertEquals(10, application.getSenderUserId());
        assertEquals(1, application.getProjectId());
        assertEquals("project1",  application.getText());
        assertArrayEquals(application.getPdfBytes(), "test1".getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Tests the retrieval of applications for a specific user.
     */
    @Test
    public void testGetApplicationsForUser(){
        HashSet<Application> applications = applicationRepository.getApplicationsForUser(10);
        assertEquals(2, applications.size());
        assertEquals(10, applications.iterator().next().getSenderUserId());
    }

    /**
     * Tests the retrieval of applications for a specific project.
     */
    @Test
    public void testGetApplicationsForProject(){
        HashSet<Application> applications = applicationRepository.getApplicationsForProject(1);
        assertEquals(2, applications.size());
        assertEquals(applications.iterator().next().getText(), "project1");
    }

    /**
     * Tests the deletion of an application.
     */
    @Test
    public void testDeleteApplication(){
        assertTrue(applicationRepository.deleteApplication(30, 2));
        assertNull(applicationRepository.getApplication(30, 2));
    }
}
