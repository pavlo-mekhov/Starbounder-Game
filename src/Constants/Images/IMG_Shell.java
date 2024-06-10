package Constants.Images;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IMG_Shell {
    public static BufferedImage IMG_Shell;
    public static BufferedImage IMG_Text1;
    public static BufferedImage IMG_Text2;
    public static BufferedImage IMG_Text3;
    public static BufferedImage IMG_Text4;
    public static BufferedImage IMG_Text5;
    public static BufferedImage IMG_Text6;
    public static BufferedImage IMG_Text7;


    static {
        try {
            IMG_Shell = ImageIO.read(new File("src/Textures/Shell/Shell.PNG"));
            IMG_Text1 = ImageIO.read(new File("src/Textures/Shell/Text1.png"));
            IMG_Text2 = ImageIO.read(new File("src/Textures/Shell/Text2.png"));
            IMG_Text3 = ImageIO.read(new File("src/Textures/Shell/Text3.png"));
            IMG_Text4 = ImageIO.read(new File("src/Textures/Shell/Text4.png"));
            IMG_Text5 = ImageIO.read(new File("src/Textures/Shell/Text5.png"));
            IMG_Text6 = ImageIO.read(new File("src/Textures/Shell/Text6.png"));
            IMG_Text6 = ImageIO.read(new File("src/Textures/Shell/Text7.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
