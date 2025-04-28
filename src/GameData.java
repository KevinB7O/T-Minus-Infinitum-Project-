
public class GameData {
	private int totalScore = 0;
    private int totalBonusPoints = 0;
    private int totalElapsedTime = 0;
	
	private static GameData instance;
    
    /*public GameData (int totalScore, int totalBonusPoints) {
    	this.totalScore = totalScore;
    	this.totalBonusPoints = totalBonusPoints;
    }*/
	
	public GameData() {
		
	}  
	
	public static GameData getInstance() {
		if (instance == null) {
			instance = new GameData();
		}
		return instance;
	}
    
    
    //Getter and setter
    public int getTotalScore() {
        return totalScore;
    }
    
    public void setTotalScore (int totalScore) {
    	this.totalScore = totalScore;
    }
    
    //Getter and setter
    public int getTotalBonusPoints() {
        return totalBonusPoints;
    }
    public void setTotalBonusPoints (int totalBonusPoints) {
    	this.totalBonusPoints = totalBonusPoints;
    }
    
    //Getter and setter
    public int getTotalElapsedTime() {
        return totalElapsedTime;
    }
    public void setTotalElapsedTime(int totalElapsedTime) {
        this.totalElapsedTime = totalElapsedTime;
    }
    public void addElapsedTime(int seconds) {
        totalElapsedTime += seconds;
    }
    
    public void addScore(int score) {
        totalScore += score;
    }
    
    public void addBonus(int bonus) {
        totalBonusPoints += bonus;
    }
    
    public void reset() {
        totalScore = 0;
        totalBonusPoints = 0;
        totalElapsedTime = 0;
    }
    
}
