import java.awt.Color;

import acm.graphics.GPolygon;

public class UserSpaceship {
	private int startRow;
	private int startColumn;
	private SpaceshipType sType;
	
	public static final int SIZE = 40;
	
	public UserSpaceship(SpaceshipType Spaceship, int startRow, int startCol)
	{
		this.startRow = startRow;
		this.startColumn = startCol;
		this.sType = Spaceship;
	}
	
	//Getter and setter for Start Row
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
	public SpaceshipType getSpaceship()
	{
		return sType;
	}
	
	public void setSpaceshipType (SpaceshipType Spaceship)
	{
		this.sType = Spaceship;
	}

    //prints out more legibly the row & columns for an array of spaces
	public static void printSpaces(TSpace[] arr) {
		 for(int i = 0; i < arr.length; i++) {
		System.out.print("r" + arr[i].getRow() + "c" + arr[i].getColumn() + "; ");
		 }
		 System.out.println();
		}
	
	public GPolygon getVisualMainShip() {
	    int x = startColumn * SIZE;
	    int y = startRow * SIZE;

	    GPolygon triangle = new GPolygon();

	    // Triangle pointing downward:
	    triangle.addVertex(0, -SIZE / 2);          // Bottom center
	    triangle.addVertex(SIZE / 2, SIZE / 2);   // Top right
	    triangle.addVertex(-SIZE / 2, SIZE / 2);  // Top left
	    triangle.setColor(Color.BLACK);
	    triangle.setFilled(true);
	    triangle.setLocation(x, y);

	    return triangle;
	}
	
	//Collision on enemies (TBA)
}
