import acm.graphics.*;
import java.awt.*;

public class Enemyship1 extends EnemyShipBasic{
	
	public Enemyship1(SpaceshipType Enemyship1, int startRow, int startCol) {
		super(Enemyship1, startRow, startCol,1);
	}

	public GPolygon getVisual() {
	    GPolygon triangle = super.getVisual();
	    triangle.setColor(Color.RED);
	    return triangle;
	}
}
