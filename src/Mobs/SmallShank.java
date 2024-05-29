package Mobs;


import Constants.Images.*;

public class SmallShank extends Mob implements Runnable {


    public SmallShank(int y, int number) {
        this.y = y;
        maxHealth = 200.0;
        currentHealth = maxHealth;
        switch (number) {
            case 1 -> {
                image = Constants.Images.SmallShank.IMG_1;
                x = 1600;
                currentHealth = 67.0;
            }
            case 2 -> {
                image = Constants.Images.SmallShank.IMG_2;
                x = 1500;
                currentHealth = 200.0;
            }
            case 3 -> {
                image = Constants.Images.SmallShank.IMG_3;
                x = 1550;
                currentHealth = 7.0;
            }
            case 4 -> {
                image = Constants.Images.SmallShank.IMG_4;
                x = 1400;
                currentHealth = 158.0;
            }
        }
    }

    public void move() {
        x -= 5;
    }

    public void shoot() {

    }

    public void explode() {
        x = 1800;
        currentHealth = maxHealth;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("");
            if (x > 1250) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                move();
            }
        }
    }
}
