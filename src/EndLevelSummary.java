

import acm.graphics.*;
import java.awt.*;
import acm.program.*;

public class EndLevelSummary extends GCompound {
    private int score;
    private int bonusPoints;
    private int elapsedTime;
    private GLabel levelSummary;
    private GLabel scoreLabel;
    private GLabel bonusPointsLabel;
    private GLabel elapsedTimeLabel;
    private GRect nextButton;
    private GLabel nextLabel;
    

    public EndLevelSummary(int score, int bonusPoints, int elapsedTime, Runnable nextLevelAction) {
        this.score = score;
        this.bonusPoints = bonusPoints;
        this.elapsedTime = elapsedTime;

        GImage background = new GImage("Background (T-minus Infinitum).png", 0, 0);
        add(background);
        
        double bgWidth = background.getWidth();
        double xCenter = bgWidth / 2;

        // Level summary
        levelSummary = new GLabel("Level Summary");
        levelSummary.setFont("SansSerif-bold-26");
        levelSummary.setColor(Color.MAGENTA);
        add(levelSummary, xCenter - levelSummary.getWidth() / 2, 60);

        // Score
        scoreLabel = new GLabel("Score: " + score);
        scoreLabel.setFont("SansSerif-bold-16");
        scoreLabel.setColor(Color.YELLOW);
        add(scoreLabel, xCenter - scoreLabel.getWidth() / 2, 120);

        // Bonus points
        bonusPointsLabel = new GLabel("Bonus Points: " + bonusPoints);
        bonusPointsLabel.setFont("SansSerif-bold-16");
        bonusPointsLabel.setColor(Color.RED);
        add(bonusPointsLabel, xCenter - bonusPointsLabel.getWidth() / 2, 150);

        // Elapsed time
        elapsedTimeLabel = new GLabel("Time: " + elapsedTime + "s");
        elapsedTimeLabel.setFont("SansSerif-bold-16");
        elapsedTimeLabel.setColor(Color.BLUE);
        add(elapsedTimeLabel, xCenter - elapsedTimeLabel.getWidth() / 2, 180);

        // Next button
        nextButton = new GRect(100, 40);
        nextButton.setFilled(true);
        nextButton.setFillColor(Color.LIGHT_GRAY);
        add(nextButton, xCenter - nextButton.getWidth() / 2, 230);

        nextLabel = new GLabel("Next");
        nextLabel.setFont("SansSerif-bold-16");
        nextLabel.setColor(Color.BLACK);
        add(nextLabel, xCenter - nextLabel.getWidth() / 2,
            230 + (nextButton.getHeight() + nextLabel.getAscent()) / 2);
        
        // Add mouse listener for "Next" button
        nextButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                nextLevelAction.run(); // Call the provided callback (next level)
            }
        });
    }
}
