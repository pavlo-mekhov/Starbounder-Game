package Mobs;

import Constants.Images.IMG_HeavyShank;
import Weapon.Bullet;
import Weapon.HeavyBullet;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class HeavyShank extends Mob implements Runnable{
    int timer;
    boolean isUp;
    public boolean isAlive;

    public HeavyShank() throws IOException {
        image = IMG_HeavyShank.IMG_1;
        maxHealth = 600;
        currentHealth = maxHealth;
        magazineCapacity = 6;
        bullets = new HeavyShankBullet[magazineCapacity];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new HeavyShankBullet();
        }
        y = -10000;
        x = -10000;
    }

    @Override
    public void move() {
        x -= 3;
    }

    @Override
    public void attack() {
        if (ammoInMagazine > 0) {
            super.attack();
            ammoInMagazine--;
            bullets[ammoInMagazine].x = x+17;
            bullets[ammoInMagazine].y = y+160;
            bullets[ammoInMagazine].isFlying = true;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ammoInMagazine--;
            bullets[ammoInMagazine].x = x+20;
            bullets[ammoInMagazine].y = y+186;
            bullets[ammoInMagazine].isFlying = true;
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ammoInMagazine = magazineCapacity;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.print("");
            if (x > 1250)
                move();

            if (timer % 10 == 0)
                isUp = !isUp;

            if (isUp)
                y += 1;
            else
                y -= 1;
            timer++;
            try {
                for (Bullet bullet:bullets) {
                    if (bullet.isFlying) {
                        bullet.x -= 20;
                        if (bullet.x < -100)
                            bullet.explode();
                    }
                }
            } catch (Exception e) {
                System.out.print("");
            }

        }
    }
}
