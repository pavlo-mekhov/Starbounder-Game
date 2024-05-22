import Constants.Constants;

import javax.swing.*;
import java.io.IOException;

public class GameApp {
    public static void main(String[] args) throws IOException {
        JFrame gameFrame = new JFrame("Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setContentPane(new GamePanel());
        gameFrame.setVisible(true);
        gameFrame.setResizable(false);
    }
}
