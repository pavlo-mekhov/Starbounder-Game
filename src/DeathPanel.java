import Constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DeathPanel extends JPanel implements KeyListener {
    int previousMode;

    DeathPanel(int previousMode) {
        this.previousMode = previousMode;
        repaint();
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 40));
        g.drawString("You Died", 700, 300);
        g.drawString("Press 'Space' to try again", 550, 400);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            GameFrame.wantedPanel = previousMode;
            System.out.println("works");
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {    }
    @Override
    public void keyReleased(KeyEvent e) {    }
}
