import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Guardian {
    int x;
    int y;
    boolean isFlyingRight = false;
    boolean isFlyingLeft = false;
    boolean isFlyingUp = false;
    int weapon;
    int lastAction;
    BufferedImage IMG_Flying_Right = ImageIO.read(new File("src/Textures/Guardian/Flying_Right.png"));
    BufferedImage IMG_Flying_Left = ImageIO.read(new File("src/Textures/Guardian/Flying_Left.PNG"));
    BufferedImage IMG_Standing_Still_Right_WOJet = ImageIO.read(new File("src/Textures/Guardian/Standing_Still_Right_WOJet.png"));
    BufferedImage IMG_Standing_Still_Left_WOJet = ImageIO.read(new File("src/Textures/Guardian/Standing_Still_Left_WOJet.png"));
    BufferedImage IMG_Standing_Still_Right_WJet = ImageIO.read(new File("src/Textures/Guardian/PW_Standing_Still_Right_WJet.png"));
    BufferedImage IMG_Standing_Still_Left_WJet = ImageIO.read(new File("src/Textures/Guardian/Standing_Still_Left_WJet.png"));
    BufferedImage image;
    Guardian() throws IOException {
        y = 300;
        x = 100;
        image = IMG_Standing_Still_Right_WOJet;
    }


}
