import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;


	
	public class WelcomePane extends GraphicsPane {
	    private MainApplication mainScreen;

	    public WelcomePane(MainApplication mainScreen) {
	        this.mainScreen = mainScreen;
	    }

	    @Override
	    public void showContent() {
	        addPicture();
	        addStartButton();
	    }

	    @Override
	    public void hideContent() {
	        for (GObject item : contents) {
	            mainScreen.remove(item);
	        }
	        contents.clear();
	    }

	    private void addPicture() {
	        GImage background = new GImage("Background (T-minus Infinitum).png", 900, 600);
	        background.scale(1, 1);
	        background.setLocation((mainScreen.getWidth() - background.getWidth()) / 2, 70);

	        contents.add(background);
	        mainScreen.add(background);
	    }

	    private void addStartButton() {
	        GImage startButton = new GImage("start_button.png", 200, 400);
	        startButton.scale(5, 5);
	        startButton.setLocation((mainScreen.getWidth() - startButton.getWidth()) / 2, 400);

	        contents.add(startButton);
	        mainScreen.add(startButton);
	    }

	    @Override
	    public void mouseClicked(MouseEvent e) {
	        if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(1)) {
	            mainScreen.startGame(); // Assuming a method to start the game
	        }
	    }
	}

