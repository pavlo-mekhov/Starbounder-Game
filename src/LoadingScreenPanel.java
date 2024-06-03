import Constants.Images.Background;

import javax.swing.*;
import java.awt.*;

public class LoadingScreenPanel extends JPanel {
    int loadingBGX;
    int loadingBGY;
    int loadingBGWidth;
    int loadingBGHeight;
    boolean isDrawingText1;
    boolean isDrawingText2;
    boolean isDrawingText3;
    StringBuilder toPrint1 = new StringBuilder();
    StringBuilder toPrint2 = new StringBuilder();
    StringBuilder toPrint3 = new StringBuilder();

    Thread textPresentsThread = new Thread(() -> {
        try {
            String text1;
            String text2;
            if (GameApp.savedData.isInitialLaunch) {
                text1 = "Pavlo Mekhov Corporation";
                text2 = "PRESENTS";
            } else {
                text1 = "Welcome Back";
                text2 = "To";
            }

            String text3 = "THE GAME...";
            for (int i = 1; i <= text1.length(); i++) {
                toPrint1 = new StringBuilder(text1.substring(0, i) + "|");
                Thread.sleep(300);//0.3s pause between characters
            }
            toPrint1 = new StringBuilder(text1);
            isDrawingText2 = true;
            for (int i = 1; i <= text2.length(); i++) {
                toPrint2 = new StringBuilder(text2.substring(0, i) + "|");
                Thread.sleep(200);//0.2s pause between characters
            }
            toPrint2 = new StringBuilder(text2);
            Thread.sleep(700);
            isDrawingText1 = false;
            isDrawingText2 = false;
            Thread.sleep(1000);
            isDrawingText3 = true;
            for (int i = 1; i <= text3.length(); i++) {
                toPrint3 = new StringBuilder(text3.substring(0, i) + "|");
                Thread.sleep(200);//0.2s pause between characters
            }
            for (int i = 0; i <= 5; i++) {
                toPrint3 = new StringBuilder(" ");
                Thread.sleep(100);
                toPrint3 = new StringBuilder(text3);
                Thread.sleep(100);
            }
            isDrawingText3 = false;
            GameApp.savedData.isInitialLaunch = false;

            GameApp.gameStatus = 80;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    });

    //Background animation
    Thread loadingAnimationThread = new Thread(() -> {
        int i = 0;
        int j = 0;
        while (loadingBGWidth > 1) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (i % 2 == 0)
                loadingBGY += 2;
            else {
                if (j % 2 == 0)
                    loadingBGY += 2;
                else
                    loadingBGY += 3;
                j++;
            }
            loadingBGX += 4;
            loadingBGWidth -= 8;
            if (i % 2 == 0)
                loadingBGHeight -= 4;
            else
                loadingBGHeight -= 5;
            i++;

            if (i == 600) {
                textPresentsThread.start();
                isDrawingText1 = true;
            }
            repaint();
        }
        GameApp.savedData.gameStage = 1.0;
    });

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1600, 900);
        g.drawImage(Background.LoadingBG.getImage(), loadingBGX, loadingBGY, loadingBGWidth, loadingBGHeight, this);

        if (GameApp.savedData.isInitialLaunch) {
            if (isDrawingText1) {
                g.setFont(new Font("Agency FB", Font.PLAIN, 44));
                g.setColor(Color.WHITE);
                g.drawString(toPrint1.toString(), 645, 440);
            }

            if (isDrawingText2) {
                g.setFont(new Font("Agency FB", Font.PLAIN, 60));
                g.setColor(Color.WHITE);
                g.drawString(toPrint2.toString(), 735, 500);
            }
        } else {
            if (isDrawingText1) {
                g.setFont(new Font("Agency FB", Font.PLAIN, 44));
                g.setColor(Color.WHITE);
                g.drawString(toPrint1.toString(), 630, 440);
            }

            if (isDrawingText2) {
                g.setFont(new Font("Agency FB", Font.PLAIN, 60));
                g.setColor(Color.WHITE);
                g.drawString(toPrint2.toString(), 700, 500);
            }
        }


        if (isDrawingText3) {
            g.setFont(new Font("Agency FB", Font.PLAIN, 80));
            g.setColor(Color.WHITE);
            g.drawString(toPrint3.toString(), 690, 450);
        }
    }

    public LoadingScreenPanel() {
        startLoading();

        loadingBGHeight = 9000;
        loadingBGWidth = 16000;

        loadingBGX = -7200;
        loadingBGY = -4050;
    }

    public void startLoading() {


        loadingAnimationThread.start();

    }


}
