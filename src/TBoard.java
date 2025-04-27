import java.util.*;
public class TBoard {
	private int rows;
    private int cols;
    private Object[][] grid;

    public TBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new Object[rows][cols];
    }

    // Add object to the board
    public void addObject(int row, int col, Object obj) {
        if (isInBounds(row, col)) {
            grid[row][col] = obj;
        }
    }

    // Remove object from the board
    public void removeObject(int row, int col) {
        if (isInBounds(row, col)) {
            grid[row][col] = null;
        }
    }

    // Get object from the board
    public Object getObject(int row, int col) {
        if (isInBounds(row, col)) {
            return grid[row][col];
        }
        return null;
    }

    // Check if coordinates are inside board bounds
    public boolean isInBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    // Optional: Print board status (for debugging)
    public void printBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print((grid[row][col] != null ? "[X]" : "[ ]"));
            }
            System.out.println();
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
	
}
