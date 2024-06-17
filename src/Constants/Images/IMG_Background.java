package Constants.Images;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IMG_Background {
    public static BufferedImage Weapon_Select_WOW;
    public static BufferedImage Weapon_Select_PW;

    public static BufferedImage Weapon_Select_OnlyPW;
    public static BufferedImage Weapon_Select_SW;
    public static BufferedImage Weapon_Select_HW;
    public static BufferedImage Background_Moon;
    public static BufferedImage Ground;
    public static ImageIcon LoadingBG;


    static {
        try {
            Weapon_Select_WOW = ImageIO.read(new File("src/Textures/Background/WeaponSelectWOW.png"));
            Weapon_Select_PW = ImageIO.read(new File("src/Textures/Background/WeaponSelectPW.png"));
            Weapon_Select_OnlyPW = ImageIO.read(new File("src/Textures/Background/WeaponSelectOnlyPW.png"));
            Weapon_Select_SW = ImageIO.read(new File("src/Textures/Background/WeaponSelectSW.png"));
            Weapon_Select_HW = ImageIO.read(new File("src/Textures/Background/WeaponSelectHW.png"));
            Background_Moon = ImageIO.read(new File("src/Textures/Background/BackgroundMoonL.png"));
            Ground = ImageIO.read(new File("src/Textures/Background/GroundL.png"));
            LoadingBG = new ImageIcon("src/Textures/Background/LoadingBG.PNG");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
