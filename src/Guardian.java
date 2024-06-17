import java.awt.image.BufferedImage;

public class Guardian {
    int x;
    int y;
    int maxHealth;
    int currentHealth;
    boolean isFlyingRight = false;
    boolean isFlyingLeft = false;
    boolean isFlyingUp = false;
    boolean isCrouching = false;

    int lastAction;
    BufferedImage image;
    Guardian() {
        y = 300;
        x = 100;
        lastAction = 10;
        maxHealth = 450;
        currentHealth = maxHealth;
    }


}
