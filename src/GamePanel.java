import Weapon.*;
import Constants.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    Guardian guardian = new Guardian();
    int gameStatus; //1 - running, 0 - escape menu
    int previousGameStatus;
    Weapon weapon;
    PrimaryWeapon primaryWeapon = new PrimaryWeapon();
    SpecialWeapon specialWeapon = new SpecialWeapon();
    Thread childThread = new Thread(specialWeapon);
    HeavyWeapon heavyWeapon = new HeavyWeapon();

    boolean isRunning;
    Timer timer;

    GamePanel() throws IOException {
        addKeyListener(this);
        setFocusable(true);
        startGame();
    }



    public void startGame() {
        isRunning = true;
        timer = new Timer(30, this);
        timer.start();
        gameStatus = 1;
        weapon = specialWeapon;
        childThread.start();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameStatus == 1) {
            g.setColor(new Color(64, 90, 156));
            g.fillRect(0,0,Constants.SCREEN_WIDTH, 735);
            g.setColor(Color.BLACK);
            g.fillRect(0, 730, Constants.SCREEN_WIDTH, 170);
            g.drawImage(guardian.image, guardian.x, guardian.y, this);

            for (int i = 0; i < primaryWeapon.bullets.length; i++) {
                g.drawImage(primaryWeapon.bullets[i].IMG, primaryWeapon.bullets[i].x, primaryWeapon.bullets[i].y, this);
            }
            for (int i = 0; i < specialWeapon.bullets.length; i++) {
                g.drawImage(specialWeapon.bullets[i].IMG, specialWeapon.bullets[i].x, specialWeapon.bullets[i].y, this);
            }

            g.setFont(new Font("Bauhaus 93", Font.BOLD, 23));
            g.setColor(Color.BLACK);
            g.drawString(primaryWeapon.ammoInMagazine + " / ∞" , 1401, 75);
            g.drawString("  " + specialWeapon.ammoInMagazine + " / " + specialWeapon.ammoTotal, 1400, 100);
            g.drawString("    " + heavyWeapon.ammoInMagazine + " / " + heavyWeapon.ammoTotal, 1403, 125);

//            if (weapon.equals(primaryWeapon)) {
//                g.drawString(weapon.ammoInMagazine + " / ∞" , 1400, 75);
//            } else if (weapon.equals(specialWeapon)){
//                g.drawString(weapon.ammoInMagazine + " / " + weapon.ammoTotal, 1400, 100);
//            } else {
//                g.drawString(weapon.ammoInMagazine + " / " + weapon.ammoTotal, 1400, 125);
//            }
        } else if (gameStatus == 0) {
            g.setColor(Color.ORANGE);
            ImageIcon gifBG = new ImageIcon("src/Textures/gif.gif");
            g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
            g.drawImage(gifBG.getImage(), 100, 100, this);
            g.setFont(new Font("Bauhaus 93", Font.BOLD, 47));
            g.setColor(Color.BLACK);
            g.drawString("Game paused", 700, 450);
        }

    }

    public void saveGame() {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameStatus == 1) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_D -> {
                    guardian.isFlyingRight = true;
                    guardian.isFlyingLeft = false;
                    guardian.image = guardian.IMG_Flying_Right;
                    guardian.lastAction = 0;
                }
                case KeyEvent.VK_A -> {
                    guardian.isFlyingLeft = true;
                    guardian.isFlyingRight = false;
                    guardian.image = guardian.IMG_Flying_Left;
                    guardian.lastAction = 9;
                }
                case KeyEvent.VK_W -> {
                    guardian.isFlyingUp = true;
                    if (guardian.lastAction == 0) {
                        guardian.image = guardian.IMG_Flying_Right;
                    } else {
                        guardian.image = guardian.IMG_Flying_Left;
                    }
                }
                case KeyEvent.VK_R -> {
                    weapon.reload();
                }
                case KeyEvent.VK_1 -> {
                    weapon = primaryWeapon;
                }
                case KeyEvent.VK_2 -> {
                    weapon = specialWeapon;
                }
                case KeyEvent.VK_3 -> {
                    weapon = heavyWeapon;
                }
                case KeyEvent.VK_ESCAPE -> {
                    previousGameStatus = gameStatus;
                    gameStatus = 0;
                }
                case KeyEvent.VK_L -> {

                    weapon.shoot(guardian.x, guardian.y);
                }
            }
            repaint();
        } else if (gameStatus == 0) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE -> {
                    gameStatus = previousGameStatus;
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                guardian.isFlyingUp = false;
            }
            case KeyEvent.VK_D -> {
                guardian.isFlyingRight = false;
            }
            case KeyEvent.VK_A-> {
                guardian.isFlyingLeft = false;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            if (gameStatus == 1) {
                if (guardian.y < 310 && !guardian.isFlyingUp) {
                    guardian.y += 23;
                    repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (guardian.isFlyingUp) {
                    if (guardian.y > 0) {
                        guardian.y -=9;
                        repaint();
                    }
                }
                if (guardian.isFlyingLeft) {
                    guardian.x -=9;
                    repaint();
                } else if (guardian.isFlyingRight) {
                    guardian.x += 9;
                    repaint();
                } else if (guardian.lastAction == 0 && !guardian.isFlyingUp) {
                    guardian.image = guardian.IMG_Standing_Still_Right_WJet;
                    repaint();
                } else if (guardian.lastAction == 9 && !guardian.isFlyingUp) {
                    guardian.image = guardian.IMG_Standing_Still_Left_WJet;
                    repaint();
                }
                for (Bullet bullet: primaryWeapon.bullets) {
                    if (bullet.isFlying) {
                        bullet.x +=30;
                        repaint();
                    } if (bullet.x > 1700) {
                        bullet.isFlying = false;
                        bullet.x = 10;
                        repaint();
                    }
                }
                for (Bullet bullet: specialWeapon.bullets) {
                    if (bullet.isFlying) {
                        bullet.x +=30;
                        repaint();
                    } if (bullet.x > 1700) {
                        bullet.isFlying = false;
                        bullet.x = 10;
                        repaint();
                    }
                }
                for (Bullet bullet: heavyWeapon.bullets) {
                    if (bullet.isFlying) {
                        bullet.x +=30;
                        repaint();
                    } if (bullet.x > 1700) {
                        bullet.isFlying = false;
                        bullet.x = 10;
                        repaint();
                    }
                }
            }
        }
    }
}
