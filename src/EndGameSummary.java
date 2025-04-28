import acm.graphics.*;
import java.awt.*;
import java.util.ArrayList;

public class EndGameSummary extends GraphicsPane {
    private MainApplication mainScreen;
    private ArrayList<GObject> contents = new ArrayList<>();

    public EndGameSummary(MainApplication mainScreen) {
        this.mainScreen = mainScreen;
    }

    @Override
    public void showContent() {
        GameData data = GameData.getInstance();

        GLabel title = new GLabel("End Game Summary");
        title.setFont("SansSerif-bold-32");
        title.setLocation(250, 100);
        contents.add(title);
        mainScreen.add(title);

        GLabel scoreLabel = new GLabel("Total Points: " + data.getTotalScore());
        scoreLabel.setFont("SansSerif-bold-24");
        scoreLabel.setLocation(250, 200);
        contents.add(scoreLabel);
        mainScreen.add(scoreLabel);

        GLabel bonusLabel = new GLabel("Total Bonus: " + data.getTotalBonusPoints());
        bonusLabel.setFont("SansSerif-bold-24");
        bonusLabel.setLocation(250, 250);
        contents.add(bonusLabel);
        mainScreen.add(bonusLabel);

        GLabel timeLabel = new GLabel("Total Time Played: " + data.getTotalElapsedTime() + " seconds");
        timeLabel.setFont("SansSerif-bold-24");
        timeLabel.setLocation(250, 300);
        contents.add(timeLabel);
        mainScreen.add(timeLabel);
    }

    @Override
    public void hideContent() {
        for (GObject obj : contents) {
            mainScreen.remove(obj);
        }
        contents.clear();
    }
}