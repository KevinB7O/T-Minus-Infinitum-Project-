import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import acm.graphics.*;

public class GraphicsPane {
	protected MainApplication mainScreen;
	protected ArrayList<GObject> contents;
	
	public GraphicsPane() {
		contents = new ArrayList<GObject>();
	}
	
	
	// Returns a GImage for the player's spaceship at (x, y)
    public static GImage getMainSpaceshipImage(double x, double y) {
        GImage mainShipImg = new GImage("Media/User spaceship (T-minus Infinitum- 5th version).png");
        mainShipImg.setLocation(x, y);
        return mainShipImg;
    }
    
    
   // Returns a GImage for the background (fills window)
    public static GImage getBackground() {
        return new GImage("Media/Background (T-minus Infinitum).png", 0, 0);
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	public void showContent() {
	}

	public void hideContent() {
	}

	public void mousePressed(MouseEvent e) {
		//Name the object
		//if(SwingUtilities.isteftMouseButton(e) )
		//else if (SwingUtilities.isRightMouseButton (e) )
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		//Plan A free movement and the spaceship is connected with the mouse.
		//plan C have it snap 
		System.out.println("mouse moved");
		/*float mouseX;
		mouseX
		= Camera.main.ScreenToWorldPoint(Input.mousePosition).x;
		transform. position = new Vector3(mouseX, transform.position.y, transform.position.z);
		*/
	}

	public void keyPressed(KeyEvent e) {
		//plan b use the keys for the movement of the user spaceship
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

}
