import Constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameFrame extends JFrame {
    boolean isChecking = true;
    int currentPanel;
    public static int wantedPanel;
    Thread checkGameStageThread = new Thread(() -> { //checking if game needs a different panel
        while (isChecking) {
            System.out.print("");

            if (wantedPanel == 80 && currentPanel != 80) {
                try {
                    getContentPane().removeAll();
                    getContentPane().invalidate();
                    GamePanel gamePanel = new GamePanel();
                    setContentPane(gamePanel);
                    getContentPane().revalidate();
                    getContentPane().repaint();
                    gamePanel.requestFocusInWindow();
                    currentPanel = 80;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (wantedPanel == 1 && currentPanel != 1) {
                try {
                    getContentPane().removeAll();
                    getContentPane().invalidate();
                    TutorialPanel tutorialPanel = new TutorialPanel();
                    setContentPane(tutorialPanel);
                    getContentPane().revalidate();
                    getContentPane().repaint();
                    tutorialPanel.requestFocusInWindow();
                    currentPanel = 1;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });

    public GameFrame() throws IOException {
        setTitle("Starbounder");
        setBackground(new Color(6, 0 ,15));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        setLocationRelativeTo(null); //move frame to the middle
        setVisible(true);
        setResizable(false);
        setIconImage(new ImageIcon("src/GameIcon.jpg").getImage());

        setContentPane(new GamePanel()); //initial panel where game starts


        checkGameStageThread.start();
    }
}
