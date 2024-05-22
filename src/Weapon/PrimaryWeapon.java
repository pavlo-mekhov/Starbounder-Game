package Weapon;

import java.io.IOException;

public class PrimaryWeapon extends Weapon {
    public PrimaryWeapon() throws IOException {
        magazineCapacity = 110;
        ammoInMagazine = magazineCapacity;
        ammoTotal = Integer.MAX_VALUE;
        bullets = new PrimaryBullet[magazineCapacity];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new PrimaryBullet();
        }
    }
    @Override
    public void shoot(int gX, int gY) {
        if (ammoInMagazine > 0) {
            bullets[ammoInMagazine-1].x = gX + 390;
            bullets[ammoInMagazine-1].y = gY + 185;
            bullets[ammoInMagazine-1].isFlying = true;
            ammoInMagazine--;
            System.out.println("Shoot");
        }
    }
}
