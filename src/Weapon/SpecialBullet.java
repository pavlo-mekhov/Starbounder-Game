package Weapon;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SpecialBullet extends Bullet {
    public SpecialBullet() throws IOException {
        super();
        x = 100;
        y = 100;
        IMG = ImageIO.read(new File("src/Textures/Bullets/SpecialBullet.png"));
    }
}
