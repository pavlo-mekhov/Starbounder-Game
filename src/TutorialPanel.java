import Constants.Constants;
import Constants.Images.IMG_Background;
import Constants.Images.IMG_GuardianPW;
import Constants.Images.IMG_GuardianWOW;
import Constants.Images.IMG_Shell;
import Mobs.HeavyShank;
import Mobs.SmallShank;
import Weapon.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class TutorialPanel extends JPanel implements KeyListener, ActionListener {
    Guardian guardian = new Guardian();
    Shell shell = new Shell();
    HeavyShank heavyShank = new HeavyShank();
    SmallShank[] shanks = new SmallShank[4];
    Weapon weapon;
    PrimaryWeapon primaryWeapon = new PrimaryWeapon();
    SpecialWeapon specialWeapon = new SpecialWeapon();
    HeavyWeapon heavyWeapon = new HeavyWeapon();
    double tutorialStage;
    Timer timer;
    int BGX = -700;
    int BGY = -10;
    int GX = -700;
    int GY = 730;
    boolean isSkippable;
    boolean isRunning;

    Thread damageCheck = new Thread(() -> {
        while (isRunning) {
            System.out.print("");
            if (tutorialStage >= 1.0) {
                for (Bullet bullet: primaryWeapon.bullets) {
                    for (SmallShank shank:shanks) {
                        if (bullet.x >= shank.x + 13 && bullet.x <= shank.x + 180 && bullet.y >= shank.y + 50 && bullet.y <= shank.y + 140) {
                            shank.currentHealth -= 24;
                            bullet.explode();
                        }
                    }
                }
                for (SmallShank shank:shanks) {
                    if (shank.currentHealth <= 0) {
                        shank.explode();
                        GameApp.savedData.amountOfKills++;
                    }
                }
            }
        }
    });

    public void spawnShanks() {
        for (int i = 0; i < shanks.length; i++) {
            shanks[i] = new SmallShank(i*90 + 30, i*100 + 1600, 1 );
        }
    }

    Thread storyLineThread = new Thread(() -> {
        try {
            Thread.sleep(3000);
            guardian.x = 200;
            guardian.y = -300;
            guardian.image = IMG_GuardianWOW.IMG_Crouching_Right_WOJet_Fallen;

            // Falling
            while (guardian.y < 310) {
                guardian.y += 25;
                repaint();
                Thread.sleep(30);
            }
            Thread.sleep(90);

            // Shell's flight
            shell.bindToGuardian(guardian.x, guardian.y, this);

            // Wait to bind
            while (!shell.isBoundToGuardian) {
                Thread.sleep(30);
            }

            // Lift head
            guardian.image = IMG_GuardianWOW.IMG_Crouching_Right_WOJet;

            Thread.sleep(90);
            tutorialStage = 0.1;

            // TEXT 1
            shell.textImage = IMG_Shell.IMG_Text1;
            isSkippable = true;
            while (tutorialStage < 0.2) {
                Thread.sleep(30);
            }
            Thread.sleep(800);

            // TEXT 2
            shell.textImage = IMG_Shell.IMG_Text2;
            isSkippable = true;
            while (tutorialStage < 0.3) {
                Thread.sleep(30);
            }
            Thread.sleep(700);

            // NO TEXT
            shell.textImage = null;
            guardian.image = IMG_GuardianWOW.IMG_Crouching_Right_WJet;
            Thread.sleep(700);

            // TEXT 3
            shell.textImage = IMG_Shell.IMG_Text3;
            isSkippable = true;
            while (tutorialStage < 0.4) {
                Thread.sleep(30);
            }
            Thread.sleep(4000);

            // TEXT 4
            shell.textImage = IMG_Shell.IMG_Text4;
            isSkippable = true;
            while (tutorialStage < 0.5) {
                Thread.sleep(30);
            }
            Thread.sleep(2000);

            // TEXT 5
            shell.textImage = IMG_Shell.IMG_Text5;
            isSkippable = true;
            while (tutorialStage < 0.6) {
                Thread.sleep(30);
            }
            Thread.sleep(1000);
            new Thread(primaryWeapon).start();
            weapon = primaryWeapon;
            Thread.sleep(4000);

            tutorialStage = 1.0;
            spawnShanks();
            damageCheck.start();
            for (int i = 0; i < shanks.length; i++) {
                new Thread(shanks[i]).start();
            }
            Thread.sleep(1000);
            shell.textImage = IMG_Shell.IMG_Text7;
            Thread.sleep(2000);
            shell.textImage = IMG_Shell.IMG_Text6;
            Thread.sleep(2000);


            shell.textImage = null;
            tutorialStage = 1.1;


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    });

    public TutorialPanel() throws IOException {
        addKeyListener(this);
        setFocusable(true);
        isRunning = true;
        timer = new Timer(30, this);
        timer.start();
        if (!GameApp.savedData.isTutorialCompleted)
            tutorialStage = GameApp.savedData.gameStage;
        else
            tutorialStage = 0.0;
        storyLineThread.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(6, 0, 15));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / 2);
        g.setColor(new Color(9, 0, 22));
        g.fillRect(0, Constants.SCREEN_HEIGHT / 2, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        g.drawImage(IMG_Background.Background_Moon, BGX, BGY, this);
        g.drawImage(IMG_Background.Ground, GX, GY, this);
        g.drawImage(guardian.image, guardian.x, guardian.y, this);
        g.drawImage(shell.image, shell.x, shell.y, this);
        if (shell.textImage != null) {
            g.drawImage(shell.textImage, shell.textX, shell.textY, this);
        }

        try {
            for (SmallShank shank:shanks) {
                g.drawImage(shank.image, shank.x, shank.y, this);
            }

            for (Bullet bullet:primaryWeapon.bullets)
                g.drawImage(bullet.IMG, bullet.x, bullet.y, this);
            for (Bullet bullet:specialWeapon.bullets)
                g.drawImage(bullet.IMG, bullet.x, bullet.y, this);
            for (Bullet bullet:heavyWeapon.bullets)
                g.drawImage(bullet.IMG, bullet.x, bullet.y, this);
        } catch (Exception e) {
            System.out.print("");
        }


        g.drawImage(heavyShank.image, heavyShank.x, heavyShank.y, this);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (tutorialStage >= 0.4) {
            if (guardian.y < 310 && !guardian.isFlyingUp) {
                guardian.y += 15;
                BGY -= 3;
                GY -= 9;
            }
            if (guardian.isFlyingUp) {
                if (guardian.y > 0) {
                    guardian.y -= 5;
                    BGY++;
                    GY += 3;
                }
            }
            if (guardian.isFlyingLeft) {
                guardian.x -= 5;
                BGX += 2;
                GX += 6;
            } else if (guardian.isFlyingRight) {
                guardian.x += 7;
                BGX -= 2;
                GX -= 6;
            }

            if (weapon == null) {
                if (guardian.isFlyingRight)
                    guardian.image = IMG_GuardianWOW.IMG_Flying_Right;
                else if (guardian.isFlyingLeft)
                    guardian.image = IMG_GuardianWOW.IMG_Flying_Left;
                else if (guardian.isFlyingUp) {
                    if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50)
                        guardian.image = IMG_GuardianWOW.IMG_Flying_Right;
                    else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59)
                        guardian.image = IMG_GuardianWOW.IMG_Flying_Left;
                } else if (guardian.isCrouching) {
                    if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                        guardian.image = IMG_GuardianWOW.IMG_Crouching_Right_WJet;
                        guardian.lastAction = 50;
                    } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                        guardian.image = IMG_GuardianWOW.IMG_Crouching_Left_WJet;
                        guardian.lastAction = 59;
                    }
                } else {
                    if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                        guardian.image = IMG_GuardianWOW.IMG_Standing_Still_Right_WJet;
                        guardian.lastAction = 10;
                    } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                        guardian.image = IMG_GuardianWOW.IMG_Standing_Still_Left_WJet;
                        guardian.lastAction = 19;
                    }
                }
            } else if (weapon.equals(primaryWeapon)) {
                if (guardian.isFlyingRight) {
                    if (weapon.isShooting)
                        guardian.image = IMG_GuardianPW.IMG_Flying_Right_Shooting;
                    else
                        guardian.image = IMG_GuardianPW.IMG_Flying_Right;
                } else if (guardian.isFlyingLeft) {
                    if (weapon.isShooting)
                        guardian.image = IMG_GuardianPW.IMG_Flying_Left_Shooting;
                    else
                        guardian.image = IMG_GuardianPW.IMG_Flying_Left;
                } else if (guardian.isFlyingUp) {
                    if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                        if (weapon.isShooting)
                            guardian.image = IMG_GuardianPW.IMG_Flying_Right_Shooting;
                        else
                            guardian.image = IMG_GuardianPW.IMG_Flying_Right;
                    } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                        if (weapon.isShooting)
                            guardian.image = IMG_GuardianPW.IMG_Flying_Left_Shooting;
                        else
                            guardian.image = IMG_GuardianPW.IMG_Flying_Left;
                    }
                } else if (guardian.isCrouching) {
                    if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                        if (weapon.isShooting)
                            guardian.image = IMG_GuardianPW.IMG_50_WJet_Shooting;
                        else
                            guardian.image = IMG_GuardianPW.IMG_50_WJet;
                        guardian.lastAction = 50;
                    } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                        if (weapon.isShooting)
                            guardian.image = IMG_GuardianPW.IMG_59_WJet_Shooting;
                        else
                            guardian.image = IMG_GuardianPW.IMG_59_WJet;
                        guardian.lastAction = 59;
                    }
                } else {
                    if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                        if (weapon.isShooting)
                            guardian.image = IMG_GuardianPW.IMG_10_WJet_Shooting;
                        else
                            guardian.image = IMG_GuardianPW.IMG_10_WJet;
                        guardian.lastAction = 10;
                    } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                        if (weapon.isShooting)
                            guardian.image = IMG_GuardianPW.IMG_19_WJet_Shooting;
                        else
                            guardian.image = IMG_GuardianPW.IMG_19_WJet;
                        guardian.lastAction = 19;
                    }
                }
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isSkippable) {
            isSkippable = false;
            tutorialStage += 0.1;
        }
        if (tutorialStage >= 0.4) {
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
                case KeyEvent.VK_W -> guardian.isFlyingUp = true;
            }
        }
        if (tutorialStage >= 0.5) {
            if (e.getKeyCode() == KeyEvent.VK_S)
                guardian.isCrouching = true;
        }
        if (tutorialStage >= 1.0) {
            switch (e.getKeyCode()) {

                case KeyEvent.VK_R -> weapon.reload();
                case KeyEvent.VK_1 -> weapon = primaryWeapon;
                case KeyEvent.VK_2 -> {
                    weapon = specialWeapon;
                }
                case KeyEvent.VK_3 -> {
                    weapon = heavyWeapon;
                }
                case KeyEvent.VK_ESCAPE -> {
//                    previousGameStatus = gameStatus;
//                    gameStatus = 0;
                }
                case KeyEvent.VK_L -> {
                    weapon.shoot(guardian.x, guardian.y, guardian.lastAction);
                }
            }

        }
        repaint();
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
}
