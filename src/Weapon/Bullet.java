package Weapon;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bullet {
    public int x;
    public int y;
    public BufferedImage IMG;
    public boolean isFlying;
    public Bullet() {
        isFlying = false;
    }
}
