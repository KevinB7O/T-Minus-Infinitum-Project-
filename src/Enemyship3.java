import acm.graphics.*;
import java.awt.*;

public class Enemyship3 extends EnemyShipBasic{
	
	public Enemyship3(SpaceshipType Enemyship3, int startRow, int startCol) {
		super(Enemyship3, startRow, startCol, 3);
	}

	public GPolygon getVisual() {
	    GPolygon triangle = super.getVisual();
	    triangle.setColor(Color.GREEN);
	    return triangle;
	}
}
