package Weapon;

public class Weapon  {
    public int timer = 0; //1 = 0.25 secs
    boolean isReadyToShoot;
    int magazineCapacity;
    public int ammoInMagazine;
    public int ammoTotal;
    public Bullet[] bullets;
    public void reload() {
        if (ammoTotal > 0) {
            ammoTotal += ammoInMagazine-magazineCapacity;
            ammoInMagazine = magazineCapacity;
        }
    }
    public void shoot(int gX, int gY) {

    }


}
