package Mobs;

import Weapon.Bullet;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class HeavyShankBullet extends Bullet {
    public HeavyShankBullet() throws IOException {
        IMG = ImageIO.read(new File("src/Textures/Bullets/HeavyShankBullet.png"));
        x = -10000;
        y = -10000;
    }
}
