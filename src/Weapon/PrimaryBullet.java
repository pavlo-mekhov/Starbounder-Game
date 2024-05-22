package Weapon;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PrimaryBullet extends Bullet {


    public PrimaryBullet() throws IOException {
        super();
        x = 200;
        y = 100;
        IMG = ImageIO.read(new File("src/Textures/Bullets/PrimaryBullet.png"));
    }
}
