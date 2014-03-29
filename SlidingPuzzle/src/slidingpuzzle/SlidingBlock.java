package slidingpuzzle;	


public class SlidingBlock {
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean moveHorizontal;
	private boolean moveVertical;
	private int num;
	
	public SlidingBlock(int x, int y, int width, int height, boolean moveHorizontal, boolean moveVertical, int num)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.moveHorizontal = moveHorizontal;
		this.moveVertical = moveVertical;
		this.num = num;
	}
	
	public SlidingBlock(SlidingBlock original) {
		this.x = original.x;
		this.y = original.y;
		this.height = original.height;
		this.width = original.width;
		this.moveHorizontal = original.moveHorizontal;
		this.moveVertical = original.moveVertical;
		this.num = original.num;
	}
	
	//creates an array of all positions (first index being the set number of x,y coordinates,
	//second index being the actual x,y coordinates in positions 0 and 1 respectively)
	public int[][] getAllPos()
	{
		int [][] myArray = new int[this.width*this.height][2];
		int index = 0;
		for (int x = 0; x<this.width; x++) {
			for (int y = 0; y<this.height; y++)
			{
				myArray[index][0] = this.x + y;
				myArray[index][1] = this.y + x;
				index = index + 1;
			}
		} 
		return myArray;
	}
	
	public int getNum()
	{
		return this.num;
	}
	
	public void moveX(int distance)
	{
		this.x = this.x + distance;
	}
	public void moveY(int distance)
	{
		this.y = this.y + distance;
	}
	
	public int numPossibleMovesRight(int[][] grid)
	{
		//returns the number the block can move to the right
		int numPossibleMoves = -1; //will be incremented to 0 in first iteration of loop
		int[][] myPos = this.getAllPos(); //gets position of this box
		boolean possible = true; //remains true as long as you can move in that direction
		while(possible) {
			numPossibleMoves++; //keeps track of how far it can move
			for (int i = 0; i < myPos.length; i++) { //iterates through all positions of box
				if (myPos[i][1] + numPossibleMoves>= grid[0].length || //checks position to make sure it's in the bounds of the grid
						(grid[myPos[i][0] - 1][myPos[i][1]+numPossibleMoves] != -1 && //checks if it is not empty (can move to empty squares)
						grid[myPos[i][0] - 1][myPos[i][1]+numPossibleMoves] != this.num)) //checks if not self (self is moving anyway)
				{
					possible = false; //if not in grid, not empty, and not self, it can move no further!
					break;
				}
			}
		}
		return numPossibleMoves;
	}
	public int numPossibleMovesLeft(int[][] grid)
	{
		//returns the number the block can move to the left
		int numPossibleMoves = -1; //will be incremented to 0 in first iteration of loop
		int[][] myPos = this.getAllPos(); //gets position of this box
		boolean possible = true; //remains true as long as you can move in that direction
		while(possible) {
			numPossibleMoves++; //keeps track of how far it can move
			for (int i = 0; i < myPos.length; i++) { //iterates through all positions of box
				if (myPos[i][1] - numPossibleMoves - 2 < 0 || //checks position to make sure it's in the bounds of the grid
						(grid[myPos[i][0] - 1][myPos[i][1]-numPossibleMoves-2] != -1 && //checks if it is not empty (can move to empty squares)
						grid[myPos[i][0] - 1][myPos[i][1]-numPossibleMoves-2] != this.num)) //checks if not self (self is moving anyway)
				{
					possible = false; //if not in grid, not empty, and not self, it can move no further!
					break;
				}
			}
		}
		return numPossibleMoves;
	}
	public int numPossibleMovesUp(int[][] grid)
	{
		//returns the number the block can move upwards
		int numPossibleMoves = -1; //will be incremented to 0 in first iteration of loop
		int[][] myPos = this.getAllPos(); //gets position of this box
		boolean possible = true; //remains true as long as you can move in that direction
		while(possible) {
			numPossibleMoves++; //keeps track of how far it can move
			for (int i = 0; i < myPos.length; i++) { //iterates through all positions of box
				if (myPos[i][0] - numPossibleMoves - 2 < 0 || //checks position to make sure it's in the bounds of the grid
						(grid[myPos[i][0]-numPossibleMoves-2][myPos[i][1]-1] != -1 && //checks if it is not empty (can move to empty squares)
						grid[myPos[i][0]-numPossibleMoves-2][myPos[i][1]-1] != this.num)) //checks if not self (self is moving anyway)
				{
					possible = false; //if not in grid, not empty, and not self, it can move no further!
					break;
				}
			}
		}
		return numPossibleMoves;
	}
	public int numPossibleMovesDown(int[][] grid)
	{
		//returns the number the block can move downwards
		int numPossibleMoves = -1; //will be incremented to 0 in first iteration of loop
		int[][] myPos = this.getAllPos(); //gets position of this box
		boolean possible = true; //remains true as long as you can move in that direction
		while(possible) {
			numPossibleMoves++; //keeps track of how far it can move
			for (int i = 0; i < myPos.length; i++) { //iterates through all positions of box
				//System.out.print((myPos[i][0] + numPossibleMoves + 1) + ", " + grid.length + "\n");
				if (myPos[i][0] + numPossibleMoves >= grid.length || //checks position to make sure it's in the bounds of the grid
						(grid[myPos[i][0]+numPossibleMoves][myPos[i][1]-1] != -1 && //checks if it is not empty (can move to empty squares)
						grid[myPos[i][0]+numPossibleMoves][myPos[i][1]-1] != this.num)) //checks if not self (self is moving anyway)
				{
					possible = false; //if not in grid, not empty, and not self, it can move no further!
					break;
				}
			}
		}
		return numPossibleMoves;
	}
	
	public boolean isOut(int puzzle_width)
	{
		if (this.y + this.width - 1 == puzzle_width)
		{
			return true;
		}
		return false;
	}
}
