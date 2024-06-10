package Mobs;

import Weapon.Bullet;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SmallShankBullet extends Bullet {
    public SmallShankBullet() {
        try {
            IMG = ImageIO.read(new File("src/Textures/Bullets/SmallShankPrimaryBullet1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
