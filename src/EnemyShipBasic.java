import acm.graphics.*;
import java.awt.*;

public class EnemyShipBasic{
	private int startRow;
	private int startColumn;
	private SpaceshipType sType;
	private int hitPoints;
	
	 public static final int SIZE = 40;
	
	public EnemyShipBasic(SpaceshipType Enemyship1, int startRow, int startCol, int hitPoints)
	{
		this.startRow = startRow;
		this.startColumn = startCol;
		this.sType = Enemyship1;
		this.hitPoints = hitPoints;
	}
	
	//Getter and setter for Start Rows
	public int getStartRow()
	{
		return startRow;
	}
	
	public void setStartRow(int startRow)
	{
		this.startRow = startRow;
	}
	
	//Getter and setter for Start Column
	public int getStartColumn()
	{
		return startColumn;
	}
	
	public void setStartColumn(int startCol)
	{
		this.startColumn = startCol;
	}
	
	//Getter and setter for SpaceshipType
	public SpaceshipType getEnemyship1()
	{
		return sType;
	}
	
	public void setSpaceshipType (SpaceshipType Enemyship1)
	{
		this.sType = Enemyship1;
	}
	
	//Getter and setter for health
	public int getHitPoints()
	{
		return hitPoints;
	}
		
	public void setHitPoints (int hitPoints)
	{
		this.hitPoints = hitPoints;
	}
	
	public void reduceHitPoints() {
        hitPoints--;
    }

    public boolean isDestroyed() {
        return hitPoints <= 0;
    }
    
    public void applyDamage(int damage) {
        hitPoints -= damage;
    }
	
	public GPolygon getVisual() {
	    int x = startColumn * SIZE;
	    int y = startRow * SIZE;

	    GPolygon triangle = new GPolygon();

	    // Triangle pointing downward:
	    triangle.addVertex(0, SIZE / 2);          // Bottom center
	    triangle.addVertex(SIZE / 2, -SIZE / 2);   // Top right
	    triangle.addVertex(-SIZE / 2, -SIZE / 2);  // Top left

	    triangle.setFilled(true);
	    triangle.setLocation(x, y);

	    return triangle;
	}
}
