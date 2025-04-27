import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class GameOverScreen {
    private GLabel gameOverLabel;
    private GLabel scoreLabel;
    private GLabel timeLabel;
    private GLabel bonusPointsLabel;
    private GRect retryButton;
    private GLabel retryLabel;

    private Consumer<Void> retryCallback;

    public GameOverScreen() {}

    public void show(GraphicsProgram program, int score, int timeSurvived, int bonusPoints, Consumer<Void> onRetry) {
        this.retryCallback = onRetry;

        gameOverLabel = new GLabel("GAME OVER", program.getWidth() / 2 - 100, program.getHeight() / 2 - 50);
        gameOverLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        gameOverLabel.setColor(Color.RED);
        program.add(gameOverLabel);

        scoreLabel = new GLabel("Score: " + score, program.getWidth() / 2 - 40, program.getHeight() / 2);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        program.add(scoreLabel);

        timeLabel = new GLabel("You survived: " + timeSurvived + " seconds", program.getWidth() / 2 - 110, program.getHeight() / 2 + 40);
        timeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        timeLabel.setColor(Color.BLUE);
        program.add(timeLabel);

        bonusPointsLabel = new GLabel("Bonus Points: " + bonusPoints, program.getWidth() / 2 - 110, program.getHeight() / 2 + 80);
        bonusPointsLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        bonusPointsLabel.setColor(Color.GREEN);
        program.add(bonusPointsLabel);

        retryButton = new GRect(program.getWidth() / 2 - 50, program.getHeight() / 2 + 120, 100, 40);
        retryButton.setFilled(true);
        retryButton.setFillColor(Color.LIGHT_GRAY);
        program.add(retryButton);

        retryLabel = new GLabel("Retry", program.getWidth() / 2 - 25, program.getHeight() / 2 + 153);
        retryLabel.setFont("SansSerif-bold-18");
        program.add(retryLabel);
    }

    public void handleMouseClick(GraphicsProgram program, MouseEvent e) {
        if (retryButton != null) {
            double buttonX = retryButton.getX();
            double buttonY = retryButton.getY();
            double buttonWidth = retryButton.getWidth();
            double buttonHeight = retryButton.getHeight();

            if (e.getX() >= buttonX && e.getX() <= buttonX + buttonWidth &&
                e.getY() >= buttonY && e.getY() <= buttonY + buttonHeight) {
                removeFrom(program);
                if (retryCallback != null) {
                    retryCallback.accept(null);
                }
            }
        }
    }

    public void removeFrom(GraphicsProgram program) {
        program.remove(gameOverLabel);
        program.remove(scoreLabel);
        program.remove(timeLabel);
        program.remove(bonusPointsLabel);
        program.remove(retryButton);
        program.remove(retryLabel);
    }
}
