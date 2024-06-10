package Mobs;

import Constants.Images.IMG_HeavyShank;

public class HeavyShank extends Mob implements Runnable{
    int timer;
    boolean isUp;

    public HeavyShank() {
        image = IMG_HeavyShank.IMG_1;
        y = -600;
        x = -1400;
    }

    @Override
    public void move() {
        x -= 5;
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

            if (timer % 10 == 0)
                isUp = !isUp;

            if (isUp)
                y += 1;
            else
                y -= 1;
            timer++;
        }
    }
}
