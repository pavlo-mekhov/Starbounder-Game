package Mobs;


import Constants.Images.IMG_SmallShank;
import Weapon.Bullet;

public class SmallShank extends Mob implements Runnable {

    int timer;
    boolean isUp;
    int type;


    public SmallShank(int y, int x, int type) {
        this.y = y;
        this.type = type;
        maxHealth = 200.0;
        currentHealth = maxHealth;
        bullets = new Bullet[10];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new SmallShankBullet();
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
        super.attack();
        new Thread(() -> {
            switch (type) {
                case 1 -> {

                }
            }
        });
    }

    public void explode() {
        x = 1800;
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
            if (x > 1250)
                move();

            if (timer % 10 == 0) {
                isUp = !isUp;
            }
            if (isUp)
                y += 1;
            else
                y -= 1;
            timer++;
        }
    }
}
