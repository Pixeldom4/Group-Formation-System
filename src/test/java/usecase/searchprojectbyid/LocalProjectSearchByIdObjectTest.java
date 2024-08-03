package usecase.searchprojectbyid;

import dataaccess.IProjectRepository;
import entities.Project;
import entities.ProjectInterface;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the LocalProjectSearchByIdObject class.
 */
public class LocalProjectSearchByIdObjectTest {

    /**
     * Tests that searchProjectById returns the correct project when the ID exists in the repository.
     */
    @Test
    void searchProjectByIdReturnsCorrectProject() {
        IProjectRepository mockRepository = mock(IProjectRepository.class);
        Project mockProject = new Project(1, "Test Project", 0.0, "Description", new HashSet<>());
        when(mockRepository.getProjectById(1)).thenReturn(mockProject);

        LocalProjectSearchByIdObject searchObject = new LocalProjectSearchByIdObject(mockRepository);
        ProjectInterface result = searchObject.searchProjectById(1);

        assertEquals(mockProject, result);
    }

    /**
     * Tests that searchProjectById returns null when the ID does not exist in the repository.
     */
    @Test
    void searchProjectByIdReturnsNullForNonExistentId() {
        IProjectRepository mockRepository = mock(IProjectRepository.class);
        when(mockRepository.getProjectById(999)).thenReturn(null);

        LocalProjectSearchByIdObject searchObject = new LocalProjectSearchByIdObject(mockRepository);
        ProjectInterface result = searchObject.searchProjectById(999);

        assertNull(result);
    }

    /**
     * Tests that searchProjectById handles an ID of zero.
     */
    @Test
    void searchProjectByIdHandlesZeroId() {
        IProjectRepository mockRepository = mock(IProjectRepository.class);
        when(mockRepository.getProjectById(0)).thenReturn(null);

        LocalProjectSearchByIdObject searchObject = new LocalProjectSearchByIdObject(mockRepository);
        ProjectInterface result = searchObject.searchProjectById(0);

        assertNull(result);
    }

    /**
     * Tests that searchProjectById handles a negative ID.
     */
    @Test
    void searchProjectByIdHandlesNegativeId() {
        IProjectRepository mockRepository = mock(IProjectRepository.class);
        when(mockRepository.getProjectById(-1)).thenReturn(null);

        LocalProjectSearchByIdObject searchObject = new LocalProjectSearchByIdObject(mockRepository);
        ProjectInterface result = searchObject.searchProjectById(-1);

        assertNull(result);
    }
}