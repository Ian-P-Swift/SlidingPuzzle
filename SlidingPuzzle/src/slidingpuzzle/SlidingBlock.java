package slidingpuzzle;


public class SlidingBlock {
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean moveHorizontal;
	private boolean moveVertical;
	
	public SlidingBlock(int x, int y, int width, int height, boolean moveHorizontal, boolean moveVertical)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.moveHorizontal = moveHorizontal;
		this.moveVertical = moveVertical;
	}
}
