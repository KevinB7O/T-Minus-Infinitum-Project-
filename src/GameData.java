
public class GameData {
    private int totalScore = 0;
    private int totalBonusPoints = 0;
    private int totalElapsedTime = 0; // Stores time in seconds

    private static GameData instance;

    private GameData() { }

    public static GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }

    // Getter and setter for totalElapsedTime
    public long getTotalElapsedTime() {
        return totalElapsedTime;
    }

    public void addElapsedTime(long levelTime) {
        totalElapsedTime += levelTime;
    }
}

