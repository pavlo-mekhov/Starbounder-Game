package Mobs;

import Weapon.Bullet;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SmallShankBullet extends Bullet {
    public SmallShankBullet(int type) {
        damage = 8;
        try {
            switch (type) {
                case 1 -> IMG = ImageIO.read(new File("src/Textures/Bullets/SmallShankPrimaryBullet1.png"));
                case 2 -> IMG = ImageIO.read(new File("src/Textures/Bullets/SmallShankPrimaryBullet2.png"));
                case 3 -> IMG = ImageIO.read(new File("src/Textures/Bullets/SmallShankPrimaryBullet3.png"));
                case 4 -> IMG = ImageIO.read(new File("src/Textures/Bullets/SmallShankPrimaryBullet4.png"));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
