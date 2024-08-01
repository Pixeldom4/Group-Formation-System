package usecase.createverification;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ImageFinder implements ImageFinderInterface {
    @Override
    public String findImage(String folderPath) {
        String image = "";
        try {
            File folder = new File(folderPath);
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".gif"));
            if (files != null && files.length > 0) {
                Random rand = new Random();
                File imageFile = files[rand.nextInt(files.length)];
                image = imageFile.getPath();
            } else {
                throw new IOException("No image files found in the folder");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
