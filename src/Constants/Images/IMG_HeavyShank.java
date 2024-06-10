package Constants.Images;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IMG_HeavyShank {
    public static BufferedImage IMG_1;

    static {
        try {
            IMG_1 = ImageIO.read(new File("src/Textures/Mobs/HeavyShank.PNG"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
