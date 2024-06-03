import java.io.Serializable;

public class SavedData implements Serializable {
    boolean isTutorialCompleted;
    boolean isInitialLaunch;
    int amountOfKills;
    double gameStage; //if < 10 - tutorial
    public SavedData() {
        isTutorialCompleted = false;
        isInitialLaunch = true;
        amountOfKills = 0;
        gameStage = 0.0;
    }
}
