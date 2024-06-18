import Constants.Images.*;
import Mobs.SmallShank;
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
    int BGX = -700;
    int BGY = -10;
    int GX = -700;
    int GY = 730;
    int gameStatus; //1 - running, 0 - escape menu
    int previousGameStatus;
    Weapon weapon;
    PrimaryWeapon primaryWeapon = new PrimaryWeapon(this);
    Thread PWThread = new Thread(primaryWeapon);
    SpecialWeapon specialWeapon = new SpecialWeapon();
    Thread SWThread = new Thread(specialWeapon);
    HeavyWeapon heavyWeapon = new HeavyWeapon();
    Thread HWThread = new Thread(heavyWeapon);
    SmallShank[] shanks = new SmallShank[4];


    Thread damageCheck = new Thread(() -> {
        while (true) {
            System.out.print("");
            if (gameStatus == 1) {
                for (Bullet bullet: primaryWeapon.bullets) {
                    for (SmallShank shank:shanks) {
                        if (bullet.x >= shank.x + 13 && bullet.x <= shank.x + 180 && bullet.y >= shank.y + 50 && bullet.y <= shank.y + 140) {
                            shank.currentHealth -= 24;
                            bullet.explode();
                        }
                    }
                }
                for (Bullet bullet: specialWeapon.bullets) {
                    for (SmallShank shank:shanks) {
                        if (bullet.x >= shank.x + 13 && bullet.x <= shank.x + 180 && bullet.y >= shank.y + 50 && bullet.y <= shank.y + 140) {
                            shank.currentHealth -= 80;
                            bullet.explode();
                        }
                    }
                }
                for (Bullet bullet: heavyWeapon.bullets) {
                    for (SmallShank shank:shanks) {
                        if (bullet.x >= shank.x + 13 && bullet.x <= shank.x + 180 && bullet.y >= shank.y + 50 && bullet.y <= shank.y + 140) {
                            shank.currentHealth -= 120;
                            bullet.explode();
                        }
                    }
                }
                for (SmallShank shank:shanks) {
                    if (shank.currentHealth <= 0) {
                        shank.explode();
                        shank.spawn();
                        GameApp.savedData.amountOfKills++;
                    }

                    for (Bullet bullet: shank.bullets) {
                        if (!guardian.isCrouching) {
                            if (bullet.x <= guardian.x + 240 && bullet.x >= guardian.x + 140 && bullet.y >= guardian.y + 40 && bullet.y <= guardian.y + 320) {
                                guardian.currentHealth -= 800;
                                bullet.explode();
                            }
                        } else {
                            if (bullet.x <= guardian.x + 240 && bullet.x >= guardian.x + 140 && bullet.y >= guardian.y + 184 && bullet.y <= guardian.y + 320) {
                                guardian.currentHealth -= 8;
                                bullet.explode();
                            }
                        }
                    }
                }
                if (guardian.currentHealth <= 0) {
                    GameFrame.wantedPanel = 0;
                }
            }
        }
    });


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
        guardian.image = IMG_GuardianWOW.IMG_Standing_Still_Right_WOJet;
        weapon = primaryWeapon;
        new Thread(primaryWeapon).start();
        new Thread(specialWeapon).start();
        new Thread(heavyWeapon).start();
        damageCheck.start();

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

        for (int i = 0; i < shanks.length; i++) {
            shanks[i] = new SmallShank((i * 150) + 50, i*100 + 1600, i+1);
            shanks[i].spawn();
        }

        for (SmallShank shank:shanks) {
            new Thread(shank).start();
        }

        gameStatus = 1;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameStatus == 1) {
            g.setColor(new Color(6, 0 ,15));
            g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT/2);
            g.setColor(new Color(9, 0 ,22));
            g.fillRect(0, Constants.SCREEN_HEIGHT/2, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
            g.drawImage(IMG_Background.Background_Moon, BGX, BGY, this);
            g.drawImage(IMG_Background.Ground, GX, GY, this);
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


            g.setColor(new Color(250, 120, 120));
            g.fillRect(20, 220, 250, 40);
            g.setColor(new Color(250, 30, 30));
            g.fillRect(20, 220, (int) (((guardian.currentHealth*1.0)/(guardian.maxHealth*1.0)) * 250), 40);

            g.setFont(new Font("Bauhaus 93", Font.BOLD, 20));
            g.setColor(Color.BLACK);
            g.drawString(guardian.currentHealth + " / " + guardian.maxHealth, 110, 240);

            if (weapon != null) {
                if (weapon.equals(primaryWeapon)) {
                    g.drawImage(IMG_Background.Weapon_Select_PW, 20, 20, this);
                } else if (weapon.equals(specialWeapon)) {
                    g.drawImage(IMG_Background.Weapon_Select_SW, 20, 20, this);
                } else if (weapon.equals(heavyWeapon)) {
                    g.drawImage(IMG_Background.Weapon_Select_HW, 20, 20, this);
                }
                g.setFont(new Font("Bauhaus 93", Font.BOLD, 20));
                g.setColor(Color.BLACK);

                g.drawString(primaryWeapon.ammoInMagazine + " / ∞", 190, 80);
                g.drawString("  " + specialWeapon.ammoInMagazine + " / " + specialWeapon.ammoTotal, 190, 140);
                g.drawString("   " + heavyWeapon.ammoInMagazine + " / " + heavyWeapon.ammoTotal, 190, 205);

                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
                g.drawString("Kills: " + GameApp.savedData.amountOfKills, 350, 100);
            }


        } else if (gameStatus == 0) {
            g.setColor(Color.ORANGE);
            ImageIcon gifBG = new ImageIcon("src/Textures/gif.gif");
            g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
            g.drawImage(gifBG.getImage(), 100, 100, this);
            g.setFont(new Font("Bauhaus 93", Font.BOLD, 47));
            g.setColor(Color.BLACK);
            g.drawString("Game paused", 700, 250);


            g.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
            g.drawString("Press 'Esc' to return to the game", 600, 300);
            g.drawString("Press 'T' to complete tutorial again", 600, 350);
        }

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
                case KeyEvent.VK_W -> guardian.isFlyingUp = true;
                case KeyEvent.VK_S -> guardian.isCrouching = true;
                case KeyEvent.VK_R -> weapon.reload();
                case KeyEvent.VK_1 -> weapon = primaryWeapon;
                case KeyEvent.VK_2 -> weapon = specialWeapon;
                case KeyEvent.VK_3 -> weapon = heavyWeapon;
                case KeyEvent.VK_ESCAPE -> {
                    previousGameStatus = gameStatus;
                    gameStatus = 0;
                }
                case KeyEvent.VK_L -> weapon.shoot(guardian.x, guardian.y, guardian.lastAction);
            }
            repaint();
        } else if (gameStatus == 0) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE -> gameStatus = previousGameStatus;
                case KeyEvent.VK_T -> GameFrame.wantedPanel = 1;
            }
        }

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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            if (gameStatus == 1) {
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
                if (weapon != null) {
                    if (weapon.equals(primaryWeapon)) {
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
                    } else if (weapon.equals(specialWeapon)) {
                        if (guardian.isFlyingRight) {
                            if (weapon.isShooting)
                                guardian.image = IMG_GuardianSW.IMG_Flying_Right_Shooting;
                            else
                                guardian.image = IMG_GuardianSW.IMG_Flying_Right;
                        } else if (guardian.isFlyingLeft) {
                            if (weapon.isShooting)
                                guardian.image = IMG_GuardianSW.IMG_Flying_Left_Shooting;
                            else
                                guardian.image = IMG_GuardianSW.IMG_Flying_Left;
                        } else if (guardian.isFlyingUp) {
                            if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                                if (weapon.isShooting)
                                    guardian.image = IMG_GuardianSW.IMG_Flying_Right_Shooting;
                                else
                                    guardian.image = IMG_GuardianSW.IMG_Flying_Right;
                            } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                                if (weapon.isShooting)
                                    guardian.image = IMG_GuardianSW.IMG_Flying_Left_Shooting;
                                else
                                    guardian.image = IMG_GuardianSW.IMG_Flying_Left;
                            }
                        } else if (guardian.isCrouching) {
                            if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                                if (weapon.isShooting)
                                    guardian.image = IMG_GuardianSW.IMG_50_WJet_Shooting;
                                else
                                    guardian.image = IMG_GuardianSW.IMG_50_WJet;
                                guardian.lastAction = 50;
                            } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                                if (weapon.isShooting)
                                    guardian.image = IMG_GuardianSW.IMG_59_WJet_Shooting;
                                else
                                    guardian.image = IMG_GuardianSW.IMG_59_WJet;
                                guardian.lastAction = 59;
                            }
                        } else {
                            if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                                if (weapon.isShooting)
                                    guardian.image = IMG_GuardianSW.IMG_10_WJet_Shooting;
                                else
                                    guardian.image = IMG_GuardianSW.IMG_10_WJet;
                                guardian.lastAction = 10;
                            } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                                if (weapon.isShooting)
                                    guardian.image = IMG_GuardianSW.IMG_19_WJet_Shooting;
                                else
                                    guardian.image = IMG_GuardianSW.IMG_19_WJet;
                                guardian.lastAction = 19;
                            }
                        }
                    } else if (weapon.equals(heavyWeapon)) {
                        if (guardian.isFlyingRight) {
                            if (weapon.isShooting)
                                guardian.image = IMG_GuardianHW.IMG_Flying_Right_Shooting;
                            else
                                guardian.image = IMG_GuardianHW.IMG_Flying_Right;
                        } else if (guardian.isFlyingLeft) {
                            if (weapon.isShooting)
                                guardian.image = IMG_GuardianHW.IMG_Flying_Left_Shooting;
                            else
                                guardian.image = IMG_GuardianHW.IMG_Flying_Left;
                        } else if (guardian.isFlyingUp) {
                            if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                                if (weapon.isShooting)
                                    guardian.image = IMG_GuardianHW.IMG_Flying_Right_Shooting;
                                else
                                    guardian.image = IMG_GuardianHW.IMG_Flying_Right;
                            } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                                if (weapon.isShooting)
                                    guardian.image = IMG_GuardianHW.IMG_Flying_Left_Shooting;
                                else
                                    guardian.image = IMG_GuardianHW.IMG_Flying_Left;
                            }
                        } else if (guardian.isCrouching) {
                            if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                                if (weapon.isShooting)
                                    guardian.image = IMG_GuardianHW.IMG_50_WJet_Shooting;
                                else
                                    guardian.image = IMG_GuardianHW.IMG_50_WJet;
                                guardian.lastAction = 50;
                            } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                                if (weapon.isShooting)
                                    guardian.image = IMG_GuardianHW.IMG_59_WJet_Shooting;
                                else
                                    guardian.image = IMG_GuardianHW.IMG_59_WJet;
                                guardian.lastAction = 59;
                            }
                        } else {
                            if (guardian.lastAction == 0 || guardian.lastAction == 10 || guardian.lastAction == 50) {
                                if (weapon.isShooting)
                                    guardian.image = IMG_GuardianHW.IMG_10_WJet_Shooting;
                                else
                                    guardian.image = IMG_GuardianHW.IMG_10_WJet;
                                guardian.lastAction = 10;
                            } else if (guardian.lastAction == 9 || guardian.lastAction == 19 || guardian.lastAction == 59) {
                                if (weapon.isShooting)
                                    guardian.image = IMG_GuardianHW.IMG_19_WJet_Shooting;
                                else
                                    guardian.image = IMG_GuardianHW.IMG_19_WJet;
                                guardian.lastAction = 19;
                            }
                        }
                    }
                }
                for (Bullet bullet : specialWeapon.bullets) {
                    if (bullet.isFlying) {
                        bullet.x += 30;
                    }
                    if (bullet.x > 1700) {
                        bullet.explode();
                        bullet.isFlying = false;
                        bullet.x = -100;
                    }
                }
                for (Bullet bullet : heavyWeapon.bullets) {
                    if (bullet.isFlying) {
                        bullet.x += 30;
                        bullet.y = (int) ((0.00032) * Math.pow((bullet.x * 1.0 - (bullet.iX)), 2.0) + bullet.iY);
                        repaint();
                    }
                    if (bullet.y > 730) {
                        bullet.explode();
                    }
                }
            }
        }
        repaint();
    }
}
