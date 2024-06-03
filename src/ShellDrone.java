import java.awt.image.BufferedImage;

public class ShellDrone implements Runnable {
    int x;
    int y;
    boolean isUp;
    int timer;
    BufferedImage image;
    ShellDrone() {

    }

    @Override
    public void run() {
        while (true) {
            System.out.print("");
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
