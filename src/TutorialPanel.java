import Constants.*;
import Constants.Images.*;
import Mobs.*;
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
    SmallShank[] shanks = new SmallShank[3];
    Weapon weapon;
    PrimaryWeapon primaryWeapon = new PrimaryWeapon(this);
    SpecialWeapon specialWeapon = new SpecialWeapon();
    HeavyWeapon heavyWeapon = new HeavyWeapon();
    double tutorialStage;
    Timer timer;
    int BGX = -700;
    int BGY = -10;
    int GX = -700;
    int GY = 730;
    int kills = 0;
    boolean isSkippable;
    boolean isRunning;

    Thread damageCheck = new Thread(() -> {
        while (isRunning) {
            System.out.print("");
            try {
                if (tutorialStage >= 1.0) {
                    for (Bullet bullet : primaryWeapon.bullets) {
                        for (SmallShank shank : shanks) {
                            if (bullet.x >= shank.x + 13 && bullet.x <= shank.x + 180 && bullet.y >= shank.y + 50 && bullet.y <= shank.y + 140) {
                                shank.currentHealth -= 24;
                                bullet.explode();
                            }
                        }
                    }
                    for (SmallShank shank : shanks) {
                        if (shank.currentHealth <= 0) {
                            shank.explode();
                            kills++;
                            if (kills > 6) {
                                for (SmallShank smallShank :shanks) {
                                    smallShank.isSpawning = false;
                                }
                            }
                            GameApp.savedData.amountOfKills++;
                        }

                        for (Bullet bullet: shank.bullets) {
                            if (!guardian.isCrouching) {
                                if (bullet.x <= guardian.x + 240 && bullet.x >= guardian.x + 140 && bullet.y >= guardian.y + 40 && bullet.y <= guardian.y + 320) {
                                    guardian.currentHealth -= 8;
                                    bullet.explode();
                                }
                            }

                        }
                    }

                    if (guardian.currentHealth <= 0) {
                        guardian.image = null;
                    }
                }
            } catch (Exception e) {
                System.out.print("");
            }
        }
    });

    public void spawnShanks() {
        for (int i = 0; i < shanks.length; i++) {
            shanks[i] = new SmallShank(i*110 + 120, i*100 + 1600, 1 );
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
            System.out.println("text5");
            shell.textImage = IMG_Shell.IMG_Text5;
            isSkippable = true;
            tutorialStage = 0.5;
            while (tutorialStage < 0.6) {
                Thread.sleep(30);
            }
            Thread.sleep(1000);
            new Thread(primaryWeapon).start();
            weapon = primaryWeapon;
            Thread.sleep(4000);
            System.out.println("text6");
            shell.textImage = IMG_Shell.IMG_Text6;
            isSkippable = true;
            while (tutorialStage < 0.7) {
                Thread.sleep(30);
            }
            Thread.sleep(2000);

            tutorialStage = 1.0;
            spawnShanks();
            System.out.println("shanks");
            damageCheck.start();
            for (SmallShank smallShank : shanks) {
                new Thread(smallShank).start();
            }
            Thread.sleep(1000);
            System.out.println("text7");
            shell.textImage = IMG_Shell.IMG_Text7;
            Thread.sleep(2000);

            shell.textImage = null;
            new Thread(() -> {
                while (true) {
                    System.out.print("");
                    try {
                        Thread.sleep(300);
                        for (SmallShank shank:shanks) {
                            Thread.sleep(80);
                            shank.attack();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();

            shell.y = -1000;
//            while (shell.y > -100) {
//                shell.y += 30;
//                Thread.sleep(30);
//            }
            shell.isBoundToGuardian = false;

            while (kills < 9) {
                Thread.sleep(30);
            }
            shell.bindToGuardian(guardian.x, guardian.y, this);

            while (!shell.isBoundToGuardian){
                Thread.sleep(30);
            }

            shell.textImage = IMG_Shell.IMG_Text8;
            isSkippable = true;
            while (tutorialStage < 1.1) {
                Thread.sleep(30);
            }
            Thread.sleep(1000);

            shell.textImage = IMG_Shell.IMG_Text9;
            isSkippable = true;
            while (tutorialStage < 1.2) {
                Thread.sleep(30);
            }
            Thread.sleep(1000);

            shell.textImage = IMG_Shell.IMG_Text10;
            isSkippable = true;
            while (tutorialStage < 1.3) {
                Thread.sleep(30);
            }
            Thread.sleep(1000);

            shell.textImage = IMG_Shell.IMG_Text11;
            Thread.sleep(500);
            isSkippable = true;
            while (tutorialStage < 1.4) {
                Thread.sleep(30);
            }

            GameFrame.wantedPanel = 80;


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
            for (SmallShank shank:shanks) {
                g.drawImage(shank.image, shank.x, shank.y, this);
                g.setColor(new Color(0, 45, 0));
                g.fillRect(shank.x + 20, shank.y + 20, 140, 7);
                g.setColor(Color.GREEN);
                g.fillRect(shank.x + 20, shank.y + 20, (int) (shank.currentHealth/shank.maxHealth * 140), 7);
                g.setFont(new Font("Bauhaus 93", Font.BOLD, 10));
                g.setColor(Color.BLACK);
                g.drawString( (int) shank.currentHealth + " / " + (int) shank.maxHealth, shank.x + 60, shank.y + 26);

                for (Bullet bullet:shank.bullets) {
                    g.drawImage(bullet.IMG, bullet.x, bullet.y, this);
                }
            }
        } catch (Exception e) {
            System.out.print("");
        }

        try {
            for (Bullet bullet:primaryWeapon.bullets)
                g.drawImage(bullet.IMG, bullet.x, bullet.y, this);
            for (Bullet bullet:specialWeapon.bullets)
                g.drawImage(bullet.IMG, bullet.x, bullet.y, this);
            for (Bullet bullet:heavyWeapon.bullets)
                g.drawImage(bullet.IMG, bullet.x, bullet.y, this);
        } catch (Exception e) {
            System.out.print("");
        }

        if (tutorialStage > 0.5) {
            g.setFont(new Font("Bauhaus 93", Font.BOLD, 20));
            g.setColor(Color.BLACK);
            g.drawImage(IMG_Background.Weapon_Select_OnlyPW, 20, 20, this);
            g.drawString(primaryWeapon.ammoInMagazine + " / ∞", 190, 80);

            g.setColor(new Color(250, 120, 120));
            g.fillRect(20, 220, 250, 40);
            g.setColor(new Color(250, 30, 30));
            g.fillRect(20, 220, (int) (((guardian.currentHealth*1.0)/(guardian.maxHealth*1.0)) * 250), 40);

            g.setFont(new Font("Bauhaus 93", Font.BOLD, 20));
            g.setColor(Color.BLACK);
            g.drawString(guardian.currentHealth + " / " + guardian.maxHealth, 110, 240);

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
        if (tutorialStage >= 0.6) {
            if (e.getKeyCode() == KeyEvent.VK_L)
                weapon.shoot(guardian.x, guardian.y, guardian.lastAction);
        }
        if (tutorialStage >= 1.0) {
            switch (e.getKeyCode()) {

                case KeyEvent.VK_R -> weapon.reload();
                case KeyEvent.VK_1 -> weapon = primaryWeapon;
                case KeyEvent.VK_2 -> weapon = specialWeapon;
                case KeyEvent.VK_3 -> weapon = heavyWeapon;
            }

        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> guardian.isFlyingUp = false;
            case KeyEvent.VK_D -> guardian.isFlyingRight = false;
            case KeyEvent.VK_A -> guardian.isFlyingLeft = false;
            case KeyEvent.VK_S -> guardian.isCrouching = false;
        }
    }
}
