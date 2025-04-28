import java.awt.Color;
import java.awt.event.MouseEvent;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;


import java.util.ArrayList;

public class WelcomePane extends GraphicsPane {
    private MainApplication mainScreen;
    private ArrayList<GObject> contents = new ArrayList<>();
    // Title box components
    private GRect titleBox;
    private GLabel titleLabel;
    
    private GLabel startLabel;
    private GRect startButton;

    public WelcomePane(MainApplication mainScreen) {
        this.mainScreen = mainScreen;
    }

    @Override
    public void showContent() {
        addPicture();
        addTitleBox();
        addStartButton();
    }

    @Override
    public void hideContent() {
        for (GObject item : contents) {
            mainScreen.remove(item);
        }
        contents.clear();
    }

    /*private void addPicture() {
        GImage background = new GImage("Background (T-minus Infinitum).png", 0, 0);
        background.scale(1, 1);
        contents.add(background);
        mainScreen.add(background);
    }*/
    
    

    private void addPicture() {
        GImage background = new GImage("Background (T-minus Infinitum).png", 0, 0);
        background.scale(1, 1);
        contents.add(background);
        mainScreen.add(background);
    }
    
    private void addTitleBox() {
        String titleText = "T-Minus Infinitum";

        // Create label for the title
        titleLabel = new GLabel(titleText);
        titleLabel.setFont("SansSerif-bold-36");
        titleLabel.setColor(Color.WHITE);

        // Calculate position to center title horizontally near top
        double x = (mainScreen.getWidth() - titleLabel.getWidth()) / 2;
        double y = 100; // vertical position

        titleLabel.setLocation(x, y);

        // Create a rectangle slightly larger than the label for the box
        double padding = 20;
        titleBox = new GRect(
            x - padding / 2,
            y - titleLabel.getAscent() - padding / 2,
            titleLabel.getWidth() + padding,
            titleLabel.getAscent() + titleLabel.getDescent() + padding
        );
        titleBox.setFilled(true);
        titleBox.setFillColor(new Color(0, 0, 0, 150)); // semi-transparent black
        titleBox.setColor(Color.WHITE); // border color

        // Add box and label to the canvas and contents list
        contents.add(titleBox);
        contents.add(titleLabel);
        mainScreen.add(titleBox);
        mainScreen.add(titleLabel);
    }

    private void addStartButton() {
        // Create a rectangle as button background
        startButton = new GRect(100, 40);
        startButton.setFilled(true);
        startButton.setFillColor(Color.RED);

        // Position the button in the center horizontally and at y=400
        double x = (mainScreen.getWidth() - startButton.getWidth()) / 2;
        double y = 300;
        startButton.setLocation(x, y);

        // Create a label for the button text
        startLabel = new GLabel("Start");
        startLabel.setFont("SansSerif-bold-18");
        startLabel.setColor(Color.YELLOW);

        // Center the label inside the button
        double labelX = x + (startButton.getWidth() - startLabel.getWidth()) / 2;
        double labelY = y + (startButton.getHeight() + startLabel.getAscent()) / 2;
        startLabel.setLocation(labelX, labelY);

        // Add both to the contents and to the main screen
        contents.add(startButton);
        contents.add(startLabel);
        mainScreen.add(startButton);
        mainScreen.add(startLabel);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (startButton.contains(e.getX(), e.getY())) {
            mainScreen.launchLevel1();  // This method will start TestingLevel1 in a new window
        }
    }
}
