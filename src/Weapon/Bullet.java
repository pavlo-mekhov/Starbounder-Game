package Weapon;

import java.awt.image.BufferedImage;

public class Bullet {
    public int x;
    public int y;
    public int iX;
    public int iY;
    public BufferedImage IMG;
    public boolean isFlying;
    public int damage;
    public Bullet() {
        isFlying = false;
    }

    public void explode() {
        isFlying = false;
        x = -100;
        y = -100;
    }
}
