package dataaccess.local;

import dataaccess.IUserProjectsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class LocalUserProjectsDAOTest {
    private final static String SAVE_LOCATION = "local_data/test/data_access/local_dao/";
    private static IUserProjectsRepository userProjectRepository;
    private final static File saveFile = new File(SAVE_LOCATION + "userProjects.csv");

    @BeforeAll
    public static void setUp() throws IOException {
        Files.deleteIfExists(saveFile.toPath());
        userProjectRepository = new LocalUserProjectsRepository(SAVE_LOCATION);
        userProjectRepository.addUserToProject(101, 10);
        userProjectRepository.addUserToProject(102, 10);
        userProjectRepository.addUserToProject(201, 20);
        userProjectRepository.addUserToProject(202, 20);
        userProjectRepository.addUserToProject(203, 20);
        userProjectRepository.addUserToProject(204, 20);
        userProjectRepository.addUserToProject(101, 30);
        userProjectRepository.addUserToProject(1000, 100);
        userProjectRepository.addUserToProject(1000, 101);
        userProjectRepository.addUserToProject(1000, 102);
        userProjectRepository.addUserToProject(1001, 100);
        userProjectRepository.addUserToProject(1001,101);
        userProjectRepository.addUserToProject(1001, 102);
    }

    @Test
    public void testGetProjectForUser(){
        HashSet<Integer> projectIds = userProjectRepository.getProjectIdsForUser(1000);
        assertEquals(3, projectIds.size());
        assertTrue(projectIds.contains(100));
        assertTrue(projectIds.contains(101));
        assertTrue(projectIds.contains(102));
    }

    @Test
    public void testGetUserForProject(){
        HashSet<Integer> userIds = userProjectRepository.getUserIdsForProject(30);
        assertEquals(1, userIds.size());
        assertTrue(userIds.contains(101));
    }

    @Test
    public void testRemoveUserFromProject(){
        userProjectRepository.removeUserFromProject(204, 20);
        HashSet<Integer> userIds = userProjectRepository.getUserIdsForProject(20);
        HashSet<Integer> projectIds = userProjectRepository.getProjectIdsForUser(204);
        assertFalse(userIds.contains(204));
        assertEquals(0, projectIds.size());
    }

    @Test
    public void testRemoveUserFromAllProject(){
        userProjectRepository.removeUserFromAllProjects(1001);
        HashSet<Integer> projectIds = userProjectRepository.getProjectIdsForUser(1001);
        HashSet<Integer> userIds = userProjectRepository.getUserIdsForProject(100);
        assertNull(projectIds);
        assertFalse(userIds.contains(1001));
    }

    @Test
    public void testRemoveProjectFromAllUsers(){
        userProjectRepository.removeProjectFromAllUsers(10);
        HashSet<Integer> projectIds = userProjectRepository.getProjectIdsForUser(101);
        HashSet<Integer> userIds = userProjectRepository.getUserIdsForProject(10);
        assertNull(userIds);
        assertFalse(projectIds.contains(101));
    }

}
