import Constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameFrame extends JFrame {
    boolean isChecking = true;
    Thread checkGameStageThread = new Thread(() -> {
        while (isChecking) {
            System.out.print("");
            if (GameApp.gameStatus == 80) {
                try {
                    getContentPane().removeAll();
                    getContentPane().invalidate();
                    GamePanel gamePanel = new GamePanel();
                    setContentPane(gamePanel);
                    getContentPane().revalidate();
                    getContentPane().repaint();
                    gamePanel.requestFocusInWindow();
                    isChecking = false;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });

    public GameFrame() throws IOException {
        setTitle("The Game...");
        setBackground(new Color(6, 0 ,15));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        setLocationRelativeTo(null); //move frame to the middle
        setVisible(true);
        setResizable(false);

        setContentPane(new TutorialPanel());

//        if (GameApp.savedData.isInitialLaunch) {
//            setContentPane(new LoadingScreenPanel());
//        } else {
//            setContentPane(new GamePanel());
//        }

        checkGameStageThread.start();
    }
}
