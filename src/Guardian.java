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
    boolean isCrouching = false;

    int lastAction;
    BufferedImage image;
    Guardian() throws IOException {
        y = 300;
        x = 100;
        lastAction = 10;
    }


}
