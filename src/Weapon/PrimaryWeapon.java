package Weapon;

import javax.swing.*;
import java.io.IOException;

public class PrimaryWeapon extends Weapon implements Runnable {
    public PrimaryWeapon() throws IOException {
        magazineCapacity = 110;
        ammoInMagazine = magazineCapacity;
        ammoTotal = Integer.MAX_VALUE;
        bullets = new PrimaryBullet[magazineCapacity];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new PrimaryBullet();
        }

        new Thread(() -> {
            while (true) {
                System.out.print("");
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (Bullet bullet:bullets) {
                    if (bullet.isFlying) {
                        bullet.x += 30;
                    }
                    if (bullet.x > 1700) {
                        bullet.explode();
                        bullet.isFlying = false;
                        bullet.x = -100;
                    }
                }
            }
        }).start();
    }

    @Override
    public void shoot(int gX, int gY, int gLastAction) {
        if (ammoInMagazine > 0 && timer == 0) {
            if (gLastAction == 0 || gLastAction == 10) {
                bullets[ammoInMagazine - 1].x = gX + 360;
                bullets[ammoInMagazine - 1].y = gY + 175;
            } else if (gLastAction == 50) {
                bullets[ammoInMagazine - 1].x = gX + 335;
                bullets[ammoInMagazine - 1].y = gY + 300;
            }
            bullets[ammoInMagazine - 1].isFlying = true;
            ammoInMagazine--;
            System.out.println("Shoot");
            timer = 2;
            isShooting = true;
        }
    }

    @Override
    public void run() {
        while (true) {
            System.out.print("");
            if (isReloading) {
                try {
                    while (timerR > 0) {
                        timerR--;
                        Thread.sleep(125);
                        System.out.println(timerR);
                    }
                    if (ammoTotal + ammoInMagazine-magazineCapacity > 0) {
                        ammoTotal += ammoInMagazine-magazineCapacity;
                        ammoInMagazine = magazineCapacity;
                    } else {
                        ammoInMagazine += ammoTotal;
                        ammoTotal = 0;
                    }
                    isReloading = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                while (timer > 0) {
                    if (timer == 1) {
                        isShooting = false;
                    }

                    timer--;
                    Thread.sleep(125);
                    System.out.println(timer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
