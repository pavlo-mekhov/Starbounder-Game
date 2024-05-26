package Weapon;

public class Weapon implements Runnable {
    public int timer = 0; //1 = 0.125 secs
    public int timerR = 0; //1 = 0.125 secs
    public boolean isReloading;
    int magazineCapacity;
    public int ammoInMagazine;
    public int ammoTotal;
    public Bullet[] bullets;
    public boolean isShooting;
    public void reload() {
        if (ammoInMagazine < magazineCapacity && ammoTotal > 0) {
            timerR = 6;
            isReloading = true;
        }


    }
    public void shoot(int gX, int gY, int gLastAction) {

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
                    } else
                        ammoInMagazine += ammoTotal;
                    isReloading = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
