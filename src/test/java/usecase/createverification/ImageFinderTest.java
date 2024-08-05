package usecase.createverification;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ImageFinderTest {
    @Test
    public void checkWithValidFolderPathReturnsImagePath() {
        ImageFinder imageFinder = new ImageFinder();
        String imagePath = imageFinder.findImage("src/test/resources/imagefindertest/validimages/");
        assertNotNull(imagePath);
        assertTrue(imagePath.endsWith(".jpg") || imagePath.endsWith(".png") || imagePath.endsWith(".gif"));
    }

    @Test
    public void checkWithEmptyFolderReturnsEmptyString() {
        ImageFinder imageFinder = new ImageFinder();
        String imagePath = imageFinder.findImage("src/test/resources/emptyfolder/");
        assertEquals("", imagePath);
    }

    @Test
    public void checkWithNonExistentFolderReturnsEmptyString() {
        ImageFinder imageFinder = new ImageFinder();
        String imagePath = imageFinder.findImage("src/test/resources/nonexistentfolder/");
        assertEquals("", imagePath);
    }

    @Test
    public void checkWithNoImagesReturnsEmptyString() {
        ImageFinder imageFinder = new ImageFinder();
        String imagePath = imageFinder.findImage("src/test/resources/imagefindertest/noimages/");
        assertEquals("", imagePath);
    }
}
