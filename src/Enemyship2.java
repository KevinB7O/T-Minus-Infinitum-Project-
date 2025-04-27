import acm.graphics.*;
import java.awt.*;

public class Enemyship2 extends EnemyShipBasic{
	
	public Enemyship2(SpaceshipType Enemyship2, int startRow, int startCol) {
		super(Enemyship2, startRow, startCol, 2);
	}

	public GPolygon getVisual() {
	    GPolygon triangle = super.getVisual();
	    triangle.setColor(Color.ORANGE);
	    return triangle;
	}
}