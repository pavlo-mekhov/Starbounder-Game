package Weapon;

import java.io.IOException;

public class SpecialWeapon extends Weapon implements Runnable {
    public SpecialWeapon() throws IOException {
        magazineCapacity = 14;
        ammoInMagazine = magazineCapacity;
        ammoTotal = 56;
        isReadyToShoot = true;
        bullets = new SpecialBullet[magazineCapacity];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new SpecialBullet();
        }
    }

    @Override
    public void shoot(int gX, int gY) {
        if (ammoInMagazine > 0 && timer == 0) {
            bullets[ammoInMagazine-1].x = gX + 390;
            bullets[ammoInMagazine-1].y = gY + 185;
            bullets[ammoInMagazine-1].isFlying = true;
            ammoInMagazine--;
            System.out.println("Shoot");
            isReadyToShoot = false;
            timer = 3;

        }
    }

    @Override
    public void run() {
        while (true) {
            System.out.print("");
            try {
                for (int i = 0; i < timer ; i++) {
                    timer--;
                    Thread.sleep(250);
                    System.out.println(timer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
