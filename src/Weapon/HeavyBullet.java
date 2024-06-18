package Weapon;

import javax.imageio.ImageIO;
import javax.swing.*;
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
        new Thread(() -> {
            isFlying = false;
            x -= 100;
            y -= 100;
            IMG = new ImageIcon("src/Textures/Bullets/HW_Explosion.gif").getImage();
            try {
                Thread.sleep(1000);
                x = -10000;
                IMG = ImageIO.read(new File("src/Textures/Bullets/HeavyBullet.png"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
