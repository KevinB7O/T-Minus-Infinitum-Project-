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
        GImage mainShipImg = new GImage("Media/User spaceship (T-minus Infinitum- 6th version).png");
        mainShipImg.setLocation(x, y);
        return mainShipImg;
    }
    
    public static GImage getEnemySpaceship1(double x, double y) {
        GImage EnemySpaceship1Img = new GImage("Media/Enemy spaceship1(updated).png");
        EnemySpaceship1Img.setLocation(x, y);
        return EnemySpaceship1Img;
    }
    
    public static GImage getEnemySpaceship2(double x, double y) {
        GImage EnemySpaceship2Img = new GImage("Media/Enemy spaceship2(updated).png");
        EnemySpaceship2Img.setLocation(x, y);
        return EnemySpaceship2Img;
    }
    
    public static GImage getEnemySpaceship3(double x, double y) {
        GImage EnemySpaceship3Img = new GImage("Media/Enemy spaceship3(updated).png");
        EnemySpaceship3Img.setLocation(x, y);
        return EnemySpaceship3Img;
    }
    
    public static GImage getEnemySpaceship4(double x, double y) {
        GImage EnemySpaceship4Img = new GImage("Media/Enemy spaceshp4(updated).png");
        EnemySpaceship4Img.setLocation(x, y);
        return EnemySpaceship4Img;
    }
    
    public static GImage getEnemySpaceship5(double x, double y) {
        GImage EnemySpaceship5Img = new GImage("Media/Enemy spaceship5(updated).png");
        EnemySpaceship5Img.setLocation(x, y);
        return EnemySpaceship5Img;
    }
    
    
   // Returns a GImage for the background (fills window)
    public static GImage getBackground() {
        return new GImage("Media/Background (T-minus Infinitum).png", 0, 0);
    }
	
    
    public static GImage getUserBulletImage(double x, double y) {
        GImage mainbulletImage = new GImage("Media/(Blue) bullet asset cropped.png", x, y); // Use your bullet image path
        mainbulletImage.scale(0.5, 0.5); // Adjust scale as needed
        return mainbulletImage;
    }
    
    
    public static GImage getEnemyBulletImageRed(double x, double y) {
        GImage enemybulletImageRed= new GImage("Media/Bullet assets (Red shot) cropped.png", x, y); // Use your bullet image path
        enemybulletImageRed.scale(0.5, 0.5); // Adjust scale as needed
        return enemybulletImageRed;
    }
    
    public static GImage getEnemyBulletImagePink(double x, double y) {
        GImage enemybulletImagePink= new GImage("Media/Bullet assets (pink shot) cropped.png", x, y); // Use your bullet image path
        enemybulletImagePink.scale(0.5, 0.5); // Adjust scale as needed
        return enemybulletImagePink;
    }
	
    public static GImage getEnemyBulletImageYellow(double x, double y) {
        GImage enemybulletImageYellow= new GImage("Media/Bullet assets (Yellow shot) cropped.png", x, y); // Use your bullet image path
        enemybulletImageYellow.scale(0.5, 0.5); // Adjust scale as needed
        return enemybulletImageYellow;
    }
    
    public static GImage getEnemyBulletImageGreen(double x, double y) {
        GImage enemybulletImageGreen= new GImage("Media/Bullet assets (Green shot) cropped.png", x, y); // Use your bullet image path
        enemybulletImageGreen.scale(0.5, 0.5); // Adjust scale as needed
        return enemybulletImageGreen;
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
