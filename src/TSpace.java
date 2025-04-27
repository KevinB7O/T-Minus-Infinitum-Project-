
public class TSpace {

	private int Row;
	private int Column;
	
	public TSpace(int row, int col)
	{
		this.Row = row;
		this.Column = col;
	}
	
	//Declare getters and setters
	
	public int getRow()
	{
		return Row;
	}
	
	public int getColumn()
	{
		return Column;
	}
	
	public void setRow(int row)
	{
		this.Row = row;
	}
	
	public void setColumn(int col)
	{
		this.Column = col;
	}
	
	@Override
	public String toString() {
	    return "(" + Row + "," + Column + ")";
	}
	
	public static void main(String[] args) {
		TSpace one = new TSpace(3, 4);
		TSpace two = new TSpace(1, 6);
		two.setRow(two.getRow()+1);
		two.setColumn(two.getColumn()-1);
		System.out.println("one r: " + one.getRow() + ", c: " + one.getColumn());
		System.out.println("two r: " + two.getRow() + ", c: " + two.getColumn());
		}
}
