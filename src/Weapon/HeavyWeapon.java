package Weapon;

import java.io.IOException;

public class HeavyWeapon extends Weapon implements Runnable {
    public HeavyWeapon() throws IOException {
        magazineCapacity = 4;
        ammoInMagazine = magazineCapacity;
        ammoTotal = 24;
        bullets = new HeavyBullet[magazineCapacity];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new HeavyBullet();
        }
    }


    @Override
    public void shoot(int gX, int gY, int gLastAction) {
        if (ammoInMagazine > 0 && timer == 0) {
            if (gLastAction == 0 || gLastAction == 10) {
                bullets[ammoInMagazine - 1].iX = gX + 375;
                bullets[ammoInMagazine - 1].iY = gY + 155;
                bullets[ammoInMagazine - 1].x = gX + 375;
                bullets[ammoInMagazine - 1].y = gY + 155;
            } else if (gLastAction == 50) {
                bullets[ammoInMagazine - 1].x = gX + 345;
                bullets[ammoInMagazine - 1].y = gY + 280;
                bullets[ammoInMagazine - 1].iX = gX + 345;
                bullets[ammoInMagazine - 1].iY = gY + 280;
            }

            bullets[ammoInMagazine - 1].isFlying = true;
            ammoInMagazine--;
            System.out.println("Shoot");
            timer = 8;
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
                    if (timer == 4) {
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
