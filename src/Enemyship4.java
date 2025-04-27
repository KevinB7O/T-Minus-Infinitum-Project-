import acm.graphics.*;
import java.awt.*;

public class Enemyship4 extends EnemyShipBasic{
	
	public Enemyship4(SpaceshipType Enemyship4, int startRow, int startCol) {
		super(Enemyship4, startRow, startCol,4);
	}

	public GPolygon getVisual() {
	    GPolygon triangle = super.getVisual();
	    triangle.setColor(Color.BLUE);
	    return triangle;
	}
}
