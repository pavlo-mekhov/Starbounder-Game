import Constants.Images.*;
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
    Thread PWThread = new Thread(primaryWeapon);
    SpecialWeapon specialWeapon = new SpecialWeapon();
    Thread SWThread = new Thread(specialWeapon);
    HeavyWeapon heavyWeapon = new HeavyWeapon();
    Thread HWThread = new Thread(heavyWeapon);


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
        guardian.image = GuardianWOW.IMG_Standing_Still_Right_WOJet;
        weapon = primaryWeapon;
        PWThread.start();
        SWThread.start();
        HWThread.start();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameStatus == 1) {
            g.setColor(new Color(64, 90, 156));
            g.fillRect(0, 0, Constants.SCREEN_WIDTH, 735);
//            ImageIcon iconBGGif = new ImageIcon("src/Textures/Background/gifBG1.gif");
//            g.drawImage(iconBGGif.getImage(), 0,0,this);
            g.setColor(Color.BLACK);
            g.fillRect(0, 730, Constants.SCREEN_WIDTH, 170);
            g.drawImage(guardian.image, guardian.x, guardian.y, this);

            for (int i = 0; i < primaryWeapon.bullets.length; i++) {
                g.drawImage(primaryWeapon.bullets[i].IMG, primaryWeapon.bullets[i].x, primaryWeapon.bullets[i].y, this);
            }
            for (int i = 0; i < specialWeapon.bullets.length; i++) {
                g.drawImage(specialWeapon.bullets[i].IMG, specialWeapon.bullets[i].x, specialWeapon.bullets[i].y, this);
            }
            for (int i = 0; i < heavyWeapon.bullets.length; i++) {
                g.drawImage(heavyWeapon.bullets[i].IMG, heavyWeapon.bullets[i].x, heavyWeapon.bullets[i].y, this);
            }

            g.setFont(new Font("Bauhaus 93", Font.BOLD, 23));
            g.setColor(Color.BLACK);
            g.drawString(primaryWeapon.ammoInMagazine + " / ∞", 1401, 75);
            g.drawString("  " + specialWeapon.ammoInMagazine + " / " + specialWeapon.ammoTotal, 1400, 100);
            g.drawString("    " + heavyWeapon.ammoInMagazine + " / " + heavyWeapon.ammoTotal, 1403, 125);


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
                    guardian.lastAction = 0;
                }
                case KeyEvent.VK_A -> {
                    guardian.isFlyingLeft = true;
                    guardian.isFlyingRight = false;
                    guardian.lastAction = 9;
                }
                case KeyEvent.VK_W ->
                    guardian.isFlyingUp = true;
                case KeyEvent.VK_S -> {
                    guardian.isCrouching = true;
                }
                case KeyEvent.VK_R ->
                    weapon.reload();
                case KeyEvent.VK_1 ->
                    weapon = primaryWeapon;
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
                    weapon.shoot(guardian.x, guardian.y, guardian.lastAction);
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
            case KeyEvent.VK_A -> {
                guardian.isFlyingLeft = false;
            }
            case KeyEvent.VK_S -> {
                guardian.isCrouching = false;
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
                }
                if (guardian.isFlyingUp) {
                    if (guardian.y > 0) {
                        guardian.y -= 9;
                        repaint();
                    }
                }
                if (guardian.isFlyingLeft) {
                    guardian.x -= 9;
                    repaint();
                } else if (guardian.isFlyingRight) {
                    guardian.x += 9;
                    repaint();
                }
                if (weapon != null) {
                    if (weapon.equals(primaryWeapon)) {
                        if (guardian.isFlyingRight) {
                            if (weapon.isShooting)
                                guardian.image = GuardianPW.IMG_Flying_Right_Shooting;
                            else
                                guardian.image = GuardianPW.IMG_Flying_Right;
                        } else if (guardian.isFlyingLeft) {
                            if (weapon.isShooting)
                                guardian.image = GuardianPW.IMG_Flying_Left_Shooting;
                            else
                                guardian.image = GuardianPW.IMG_Flying_Left;
                        } else if (guardian.isFlyingUp) {
                            if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianPW.IMG_Flying_Right_Shooting;
                                else
                                    guardian.image = GuardianPW.IMG_Flying_Right;
                            } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianPW.IMG_Flying_Left_Shooting;
                                else
                                    guardian.image = GuardianPW.IMG_Flying_Left;
                            }
                        } else if (guardian.isCrouching) {
                            if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianPW.IMG_50_WJet_Shooting;
                                else
                                    guardian.image = GuardianPW.IMG_50_WJet;
                                guardian.lastAction = 50;
                            } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianPW.IMG_59_WJet_Shooting;
                                else
                                    guardian.image = GuardianPW.IMG_59_WJet;
                                guardian.lastAction = 59;
                            }
                        } else {
                            if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianPW.IMG_10_WJet_Shooting;
                                else
                                    guardian.image = GuardianPW.IMG_10_WJet;
                                guardian.lastAction = 10;
                            } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianPW.IMG_19_WJet_Shooting;
                                else
                                    guardian.image = GuardianPW.IMG_19_WJet;
                                guardian.lastAction = 19;
                            }
                        }
                        repaint();
                    } else if (weapon.equals(specialWeapon)) {
                        if (guardian.isFlyingRight) {
                            if (weapon.isShooting)
                                guardian.image = GuardianSW.IMG_Flying_Right_Shooting;
                            else
                                guardian.image = GuardianSW.IMG_Flying_Right;
                        } else if (guardian.isFlyingLeft) {
                            if (weapon.isShooting)
                                guardian.image = GuardianSW.IMG_Flying_Left_Shooting;
                            else
                                guardian.image = GuardianSW.IMG_Flying_Left;
                        } else if (guardian.isFlyingUp) {
                            if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianSW.IMG_Flying_Right_Shooting;
                                else
                                    guardian.image = GuardianSW.IMG_Flying_Right;
                            } else if (guardian.lastAction == 9 || guardian.lastAction == 19|| guardian.lastAction == 59) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianSW.IMG_Flying_Left_Shooting;
                                else
                                    guardian.image = GuardianSW.IMG_Flying_Left;
                            }
                        } else if (guardian.isCrouching) {
                            if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianSW.IMG_50_WJet_Shooting;
                                else
                                    guardian.image = GuardianSW.IMG_50_WJet;
                                guardian.lastAction = 50;
                            } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianSW.IMG_59_WJet_Shooting;
                                else
                                    guardian.image = GuardianSW.IMG_59_WJet;
                                guardian.lastAction = 59;
                            }
                        } else {
                            if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianSW.IMG_10_WJet_Shooting;
                                else
                                    guardian.image = GuardianSW.IMG_10_WJet;
                                guardian.lastAction = 10;
                            } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianSW.IMG_19_WJet_Shooting;
                                else
                                    guardian.image = GuardianSW.IMG_19_WJet;
                                guardian.lastAction = 19;
                            }
                        }
                        repaint();
                    } else if (weapon.equals(heavyWeapon)) {
                        if (guardian.isFlyingRight) {
                            if (weapon.isShooting)
                                guardian.image = GuardianHW.IMG_Flying_Right_Shooting;
                            else
                                guardian.image = GuardianHW.IMG_Flying_Right;
                        } else if (guardian.isFlyingLeft) {
                            if (weapon.isShooting)
                                guardian.image = GuardianHW.IMG_Flying_Left_Shooting;
                            else
                                guardian.image = GuardianHW.IMG_Flying_Left;
                        } else if (guardian.isFlyingUp) {
                            if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianHW.IMG_Flying_Right_Shooting;
                                else
                                    guardian.image = GuardianHW.IMG_Flying_Right;
                            } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianHW.IMG_Flying_Left_Shooting;
                                else
                                    guardian.image = GuardianHW.IMG_Flying_Left;
                            }
                        } else if (guardian.isCrouching) {
                            if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianHW.IMG_50_WJet_Shooting;
                                else
                                    guardian.image = GuardianHW.IMG_50_WJet;
                                guardian.lastAction = 50;
                            } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianHW.IMG_59_WJet_Shooting;
                                else
                                    guardian.image = GuardianHW.IMG_59_WJet;
                                guardian.lastAction = 59;
                            }
                        } else {
                            if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianHW.IMG_10_WJet_Shooting;
                                else
                                    guardian.image = GuardianHW.IMG_10_WJet;
                                guardian.lastAction = 10;
                            } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                                if (weapon.isShooting)
                                    guardian.image = GuardianHW.IMG_19_WJet_Shooting;
                                else
                                    guardian.image = GuardianHW.IMG_19_WJet;
                                guardian.lastAction = 19;
                            }
                        }
                        repaint();
                    }
                } else {

                }
                for (Bullet bullet : primaryWeapon.bullets) {
                    if (bullet.isFlying) {
                        bullet.x += 30;
                        repaint();
                    }
                    if (bullet.x > 1700) {
                        bullet.explode();
                        bullet.isFlying = false;
                        bullet.x = -100;
                        repaint();
                    }
                }
                for (Bullet bullet : specialWeapon.bullets) {
                    if (bullet.isFlying) {
                        bullet.x += 30;
                        repaint();
                    }
                    if (bullet.x > 1700) {
                        bullet.explode();
                        bullet.isFlying = false;
                        bullet.x = -100;
                        repaint();
                    }
                }
                for (Bullet bullet : heavyWeapon.bullets) {
                    if (bullet.isFlying) {
                        bullet.x += 30;
                        bullet.y = (int) ((0.00032) *Math.pow((bullet.x*1.0 - (bullet.iX)), 2.0) + bullet.iY);
                        repaint();
                    }
                    if (bullet.y > 730 || bullet.x > 1700) {
                        bullet.explode();
                        bullet.y = -100;
                        repaint();
                    }
                }
            }
        }
    }
}
