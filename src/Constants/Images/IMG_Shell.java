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
    public static BufferedImage IMG_Text8;
    public static BufferedImage IMG_Text9;
    public static BufferedImage IMG_Text10;
    public static BufferedImage IMG_Text11;


    static {
        try {
            IMG_Shell = ImageIO.read(new File("src/Textures/Shell/Shell.PNG"));
            IMG_Text1 = ImageIO.read(new File("src/Textures/Shell/Text1.png"));
            IMG_Text2 = ImageIO.read(new File("src/Textures/Shell/Text2.png"));
            IMG_Text3 = ImageIO.read(new File("src/Textures/Shell/Text3.png"));
            IMG_Text4 = ImageIO.read(new File("src/Textures/Shell/Text4.png"));
            IMG_Text5 = ImageIO.read(new File("src/Textures/Shell/Text5.png"));
            IMG_Text6 = ImageIO.read(new File("src/Textures/Shell/Text6.png"));
            IMG_Text7 = ImageIO.read(new File("src/Textures/Shell/Text7.png"));
            IMG_Text8 = ImageIO.read(new File("src/Textures/Shell/Text8.png"));
            IMG_Text9 = ImageIO.read(new File("src/Textures/Shell/Text9.png"));
            IMG_Text10 = ImageIO.read(new File("src/Textures/Shell/Text10.png"));
            IMG_Text11 = ImageIO.read(new File("src/Textures/Shell/Text11.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
