import Constants.Images.IMG_Shell;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Shell implements Runnable {
    int x;
    int y;
    int textX = -100;
    int textY = -100;
    boolean isUp;
    int timer;
    boolean isBoundToGuardian;
    BufferedImage image;
    BufferedImage textImage;
    Shell() {
        image = IMG_Shell.IMG_Shell;
        x = -100;
        y = -100;
    }

    public void bindToGuardian(int GX, int GY, JPanel panel) {
        x = GX + 400 + 1500;
        y = GY - 40 - 750;
        new Thread(() -> {
            try {
                int n = 0;
                while (x > GX + 400) {
                    Thread.sleep(10);
                    y += 10;
                    x -= 20;
                    panel.repaint();
                }
                isBoundToGuardian = true;
                while (isBoundToGuardian) {
                    System.out.print("");
                    Thread.sleep(40);
                    x = GX + 400;
                    y = GY - 40 + n;
                    if (timer % 20 == 0) {
                        isUp = !isUp;
                    }
                    if (isUp)
                        n++;
                    else
                        n--;

                    textX = x+60;
                    textY = y-80;

                    timer++;
                    panel.repaint();
                }
            } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        }).start();
    }

    @Override
    public void run() {
        
    }
}
