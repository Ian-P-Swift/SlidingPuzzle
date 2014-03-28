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
}
