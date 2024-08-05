package usecase.createverification;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ImageFinderTest {
    @Test
    public void findImage_withValidFolderPath_returnsImagePath() {
        ImageFinder imageFinder = new ImageFinder();
        String imagePath = imageFinder.findImage("src/test/resources/imagefindertest/validimages/");
        assertNotNull(imagePath);
        assertTrue(imagePath.endsWith(".jpg") || imagePath.endsWith(".png") || imagePath.endsWith(".gif"));
    }

    @Test
    public void findImage_withEmptyFolder_returnsEmptyString() {
        ImageFinder imageFinder = new ImageFinder();
        String imagePath = imageFinder.findImage("src/test/resources/emptyfolder/");
        assertEquals("", imagePath);
    }

    @Test
    public void findImage_withNonExistentFolder_returnsEmptyString() {
        ImageFinder imageFinder = new ImageFinder();
        String imagePath = imageFinder.findImage("src/test/resources/nonexistentfolder/");
        assertEquals("", imagePath);
    }

    @Test
    public void findImage_withFolderContainingNoImages_returnsEmptyString() {
        ImageFinder imageFinder = new ImageFinder();
        String imagePath = imageFinder.findImage("src/test/resources/imagefindertest/noimages/");
        assertEquals("", imagePath);
    }
}
