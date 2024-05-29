package Weapon;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bullet {
    public int x;
    public int y;
    public int iX;
    public int iY;
    public BufferedImage IMG;
    public boolean isFlying;
    public Bullet() {
        isFlying = false;
    }

    public void explode() {
        isFlying = false;
        x = -100;
    }
}
