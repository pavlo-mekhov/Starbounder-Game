package Constants.Images;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IMG_GuardianHW {
    public static BufferedImage IMG_Flying_Right;
    public static BufferedImage IMG_Flying_Left;
    public static BufferedImage IMG_10_WJet;
    public static BufferedImage IMG_19_WJet ;
    public static BufferedImage IMG_50_WJet;
    public static BufferedImage IMG_59_WJet;


    public static BufferedImage IMG_Flying_Right_Shooting;
    public static BufferedImage IMG_Flying_Left_Shooting;
    public static BufferedImage IMG_10_WJet_Shooting;
    public static BufferedImage IMG_19_WJet_Shooting;
    public static BufferedImage IMG_50_WJet_Shooting;
    public static BufferedImage IMG_59_WJet_Shooting;



    static {
        try {
            IMG_Flying_Right = ImageIO.read(new File("src/Textures/Guardian/HW_Flying_Right.PNG"));
            IMG_Flying_Left = ImageIO.read(new File("src/Textures/Guardian/HW_Flying_Left.PNG"));
            IMG_10_WJet = ImageIO.read(new File("src/Textures/Guardian/HW_Standing_Still_Right_WJet.PNG"));
            IMG_19_WJet = ImageIO.read(new File("src/Textures/Guardian/HW_Standing_Still_Left_WJet.PNG"));
            IMG_59_WJet = ImageIO.read(new File("src/Textures/Guardian/HW_Crouching_Left_WJet.PNG"));
            IMG_50_WJet = ImageIO.read(new File("src/Textures/Guardian/HW_Crouching_Right_WJet.PNG"));

            IMG_Flying_Right_Shooting = ImageIO.read(new File("src/Textures/Guardian/HW_Flying_Right_Shooting.PNG"));
            IMG_Flying_Left_Shooting = ImageIO.read(new File("src/Textures/Guardian/HW_Flying_Left_Shooting.PNG"));
            IMG_10_WJet_Shooting = ImageIO.read(new File("src/Textures/Guardian/HW_Standing_Still_Right_WJet_Shooting.PNG"));
            IMG_19_WJet_Shooting = ImageIO.read(new File("src/Textures/Guardian/HW_Standing_Still_Left_WJet_Shooting.PNG"));
            IMG_59_WJet_Shooting = ImageIO.read(new File("src/Textures/Guardian/HW_Crouching_Left_WJet_Shooting.PNG"));
            IMG_50_WJet_Shooting = ImageIO.read(new File("src/Textures/Guardian/HW_Crouching_Right_WJet_Shooting.PNG"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
