package Mobs;


import Constants.Images.IMG_SmallShank;
import Weapon.Bullet;

public class SmallShank extends Mob implements Runnable {

    int timer;
    boolean isUp;
    int type;
    public boolean isSpawning;


    public SmallShank(int y, int x, int type) {
        magazineCapacity = 10;
        isSpawning = true;
        ammoInMagazine = magazineCapacity;
        this.y = y;
        this.type = type;
        maxHealth = 200.0;
        currentHealth = maxHealth;
        bullets = new Bullet[10];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new SmallShankBullet(type);
        }
        switch (type) {
            case 1 -> {
                image = IMG_SmallShank.IMG_1;
                this.x = x;
            }
            case 2 -> {
                image = IMG_SmallShank.IMG_2;
                this.x = x;
            }
            case 3 -> {
                image = IMG_SmallShank.IMG_3;
                this.x = x;
            }
            case 4 -> {
                image = IMG_SmallShank.IMG_4;
                this.x = x;
            }
        }
    }

    public void move() {
        x -= 5;
    }

    @Override
    public void attack() {
        if (ammoInMagazine > 0) {
            super.attack();
            switch (type) {
                case 1 -> {
                    ammoInMagazine--;
                    bullets[ammoInMagazine].x = x+10;
                    bullets[ammoInMagazine].y = y+130;
                    bullets[ammoInMagazine].isFlying = true;
                }
            }
        } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ammoInMagazine = magazineCapacity;
        }

    }

    public void explode() {
        x = -10000;
        currentHealth = maxHealth;
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
            if (isSpawning) {
                if (x < 0){
                    x = 1900;
                }
                if (x > 1250)
                    move();
            }


            if (timer % 10 == 0) {
                isUp = !isUp;
            }
            if (isUp)
                y += 1;
            else
                y -= 1;
            timer++;


            for (Bullet bullet:bullets) {
                if (bullet.isFlying) {
                    bullet.x -= 30;

                    if (bullet.x < -100)
                        bullet.isFlying = false;
                }
            }
        }
    }
}
