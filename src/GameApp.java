import javax.swing.*;
import java.io.*;

public class GameApp {

    public static SavedData savedData = null; //variable for saved data
    public static boolean isReadyToSave; //boolean in case of not savable moment


    public static void main(String[] args) throws InterruptedException {
        SwingUtilities.invokeLater(() -> { //safe launch
            try {
                new GameFrame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        isReadyToSave = true;
        //serialization, more info: https://www.tutorialspoint.com/java/java_serialization.htm
        try { //trying to open existing data (try-catch, so in case of exception we can run alternative code)
            FileInputStream fileIn = new FileInputStream("savedData.ser"); //path
            ObjectInputStream in = new ObjectInputStream(fileIn);
            savedData = (SavedData) in.readObject(); //trying to put data from path to variable
            //closing streams
            in.close();
            fileIn.close();
            System.out.println("Saved data found");
        } catch (FileNotFoundException e) { //case if we didn't find saved data
            System.out.println("Saved data not found");
            savedData = new SavedData(); //creating new save class
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("SaveData class not found");
            c.printStackTrace();
            return;
        }


        Thread autoSave = new Thread(() -> { //auto save
            while (true) { //always
                if (isReadyToSave) { // if it can be saved
                    System.out.print(""); //for some reason Threads need this line of code to work more stable
                    try {
                        Thread.sleep(10000); //every 10 secs
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //some more serialization, same link
                    try {
                        FileOutputStream fileOut = new FileOutputStream("savedData.ser"); //path to create/save data
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(savedData); //writing info from saved data object to file by path
                        //closing streams
                        out.close();
                        fileOut.close();
                        System.out.println("Serialized data is saved in /tmp/savedData.ser");
                    } catch (IOException i) {
                        i.printStackTrace();
                    }
                }
            }
        });
        autoSave.start(); //launching thread
    }
}
