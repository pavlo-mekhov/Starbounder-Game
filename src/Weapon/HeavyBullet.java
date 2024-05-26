package Weapon;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class HeavyBullet extends Bullet {
    public HeavyBullet() throws IOException {
        super();
        x = -200;
        y = -100;
        IMG = ImageIO.read(new File("src/Textures/Bullets/HeavyBullet.png"));
    }

    @Override
    public void explode() {
        super.explode();
    }
}
