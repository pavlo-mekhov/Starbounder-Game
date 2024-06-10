package Mobs;

import Weapon.Bullet;

import java.awt.image.BufferedImage;

public class Mob {
    public double currentHealth;
    public double maxHealth;
    public int x;
    public int y;
    public int magazineCapacity;
    public int ammoInMagazine;
    public Bullet[] bullets;
    public boolean isAttacking;
    public BufferedImage image;

    public void move() {

    }

    public void attack() {
        isAttacking = true;
    }
}
