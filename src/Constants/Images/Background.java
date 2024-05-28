package Constants.Images;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background {
    public static BufferedImage Weapon_Select_WOW;
    public static BufferedImage Weapon_Select_PW;
    public static BufferedImage Weapon_Select_SW;
    public static BufferedImage Weapon_Select_HW;
    public static BufferedImage Background_Moon;


    static {
        try {
            Weapon_Select_WOW = ImageIO.read(new File("src/Textures/Background/WeaponSelectWOW.png"));
            Weapon_Select_PW = ImageIO.read(new File("src/Textures/Background/WeaponSelectPW.png"));
            Weapon_Select_SW = ImageIO.read(new File("src/Textures/Background/WeaponSelectSW.png"));
            Weapon_Select_HW = ImageIO.read(new File("src/Textures/Background/WeaponSelectHW.png"));
            Background_Moon = ImageIO.read(new File("src/Textures/Background/BackgroundMoonL.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
