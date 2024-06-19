import Constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameFrame extends JFrame {
    int currentPanel;
    public static int wantedPanel; //variable to which other parts of code will assign request to change panel
    Thread checkGameStageThread = new Thread(() -> { //checking if game needs a different panel
        while (true) { // always
            System.out.print(""); //for some reason Threads need this line of code to work more stable
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (wantedPanel != currentPanel) { //check if any part of code requested panel change
                try {
                    JPanel newPanel;
                    System.out.println("received request");
                    switch (wantedPanel) {
                        case 0 -> newPanel = new DeathPanel(currentPanel);
                        case 1 -> newPanel = new TutorialPanel();
                        case 80 -> newPanel = new GamePanel();
                        default -> throw new Exception();
                    }
                    getContentPane().removeAll(); //remove all active panels
                    getContentPane().invalidate(); //reload
                    setContentPane(newPanel);
                    getContentPane().revalidate();
                    getContentPane().repaint();
                    newPanel.requestFocusInWindow();
                    currentPanel = wantedPanel;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    System.out.println("Requesting not existing panel. GameFrame: 17  " +
                            "Requested Panel" + wantedPanel);
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

        setContentPane(new LoadingScreenPanel()); //initial panel where game starts


        checkGameStageThread.start();
    }
}
