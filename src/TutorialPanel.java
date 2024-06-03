import Constants.Constants;
import Constants.Images.Background;
import Constants.Images.GuardianWOW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class TutorialPanel extends JPanel implements KeyListener, ActionListener {
    Guardian guardian = new Guardian();
    ShellDrone shell;
    double tutorialStage;
    Timer timer;
    int BGX = -700;
    int BGY = -10;
    int GX = -700;
    int GY = 730;

    Thread storyLineThread = new Thread(() -> {
        try {
            Thread.sleep(13000);
            guardian.x = 200;
            guardian.y = -300;
            guardian.image = GuardianWOW.IMG_Crouching_Right_WOJet_Fallen;
            while (guardian.y < 310) {
                guardian.y += 25;
                repaint();
                Thread.sleep(30);
            }
            shell = new ShellDrone();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    });

    public TutorialPanel() throws IOException {
        addKeyListener(this);
        setFocusable(true);
        timer = new Timer(30, this);
        if (!GameApp.savedData.isTutorialCompleted)
            tutorialStage = GameApp.savedData.gameStage;
        else
            tutorialStage = 0.0;
        storyLineThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(6, 0 ,15));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT/2);
        g.setColor(new Color(9, 0 ,22));
        g.fillRect(0, Constants.SCREEN_HEIGHT/2, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        g.drawImage(Background.Background_Moon, BGX, BGY, this);
        g.drawImage(Background.Ground, GX, GY, this);
        g.drawImage(guardian.image, guardian.x, guardian.y, this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (guardian.y < 310 && !guardian.isFlyingUp) {
            guardian.y += 15;
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
