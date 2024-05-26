package Weapon;

import java.io.IOException;

public class SpecialWeapon extends Weapon implements Runnable {
    public SpecialWeapon() throws IOException {
        magazineCapacity = 14;
        ammoInMagazine = magazineCapacity;
        ammoTotal = 56;
        bullets = new SpecialBullet[magazineCapacity];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new SpecialBullet();
        }
    }

    @Override
    public void shoot(int gX, int gY, int gLastAction) {
        if (ammoInMagazine > 0 && timer == 0) {
            if (gLastAction == 0 || gLastAction == 10) {
                bullets[ammoInMagazine - 1].x = gX + 390;
                bullets[ammoInMagazine - 1].y = gY + 185;
            } else if (gLastAction == 50) {
                bullets[ammoInMagazine - 1].x = gX + 365;
                bullets[ammoInMagazine - 1].y = gY + 310;
            }
            bullets[ammoInMagazine - 1].isFlying = true;
            ammoInMagazine--;
            System.out.println("Shoot");
            timer = 6;
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
                    if (timer == 2) {
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
