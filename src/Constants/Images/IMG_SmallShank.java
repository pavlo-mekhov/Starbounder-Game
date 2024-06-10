package Constants.Images;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IMG_SmallShank {
    //each number is different angle of weapon
    public static BufferedImage IMG_1;
    public static BufferedImage IMG_2;
    public static BufferedImage IMG_3;
    public static BufferedImage IMG_4;

    static {
        try {
            IMG_1 = ImageIO.read(new File("src/Textures/Mobs/Small_Shank1.PNG"));
            IMG_2 = ImageIO.read(new File("src/Textures/Mobs/Small_Shank2.PNG"));
            IMG_3 = ImageIO.read(new File("src/Textures/Mobs/Small_Shank3.PNG"));
            IMG_4 = ImageIO.read(new File("src/Textures/Mobs/Small_Shank4.PNG"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
