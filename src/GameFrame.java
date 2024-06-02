import Constants.Constants;

import javax.swing.*;
import java.io.IOException;

public class GameFrame extends JFrame {
    boolean isChecking = true;
    Thread checkGameStageThread = new Thread(() -> {
        while (isChecking) {
            System.out.print("");
            if (GameApp.savedData.gameStage == 1.0) {
                try {
                    getContentPane().removeAll();
                    getContentPane().invalidate();
                    GamePanel gamePanel = new GamePanel();
                    setContentPane(gamePanel);
                    getContentPane().revalidate();
                    getContentPane().repaint();
                    gamePanel.requestFocusInWindow();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                isChecking = false;
            }
        }
    });

    public GameFrame() throws IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        setLocationRelativeTo(null); //move frame to the middle
        setVisible(true);
        setResizable(false);

        if (GameApp.savedData.isInitialLaunch) {
            setContentPane(new LoadingScreenPanel());
        } else {
            setContentPane(new GamePanel());
        }

        checkGameStageThread.start();
    }
}
