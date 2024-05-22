package Weapon;

public class HeavyWeapon extends Weapon {
    public HeavyWeapon() {
        magazineCapacity = 4;
        ammoInMagazine = magazineCapacity;
        ammoTotal = 24;
        bullets = new HeavyBullet[magazineCapacity];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new HeavyBullet();
        }
    }
}
