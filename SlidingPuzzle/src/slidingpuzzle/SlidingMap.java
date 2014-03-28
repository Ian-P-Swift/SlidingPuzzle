package slidingpuzzle;

import java.io.*;
import java.util.*;

public class SlidingMap {
	private int rows;
	private int columns;
	private SlidingBlock goalBlock;
	private ArrayList<SlidingBlock> blocks = new ArrayList<SlidingBlock>();
	private BufferedReader reader = null;
	
	private SlidingBlock generateSlidingBlock(String text, int num)
	{
		int row, column, width, height;
		boolean moveHorizontal, moveVertical;
		int firstSpace;
		
		firstSpace = text.indexOf(" "); //finds a space
		row = Integer.parseInt(text.substring(0, firstSpace)); //turns substring up until space into int row
		text = text.substring(firstSpace+1); //gets the remaining string after the space
		
		firstSpace = text.indexOf(" "); //finds a space
		column = Integer.parseInt(text.substring(0, firstSpace)); //turns substring up until space into int column
		text = text.substring(firstSpace+1); //gets the remaining string after the space
		
		firstSpace = text.indexOf(" "); //finds a space
		width = Integer.parseInt(text.substring(0, firstSpace)); //turns substring up until space into int width
		text = text.substring(firstSpace+1); //gets the remaining string after the space
		
		firstSpace = text.indexOf(" "); //finds a space
		height = Integer.parseInt(text.substring(0, firstSpace)); //turns substring up until space into int height
		text = text.substring(firstSpace+1); //gets the remaining string after the space
		
		if (text.compareTo("b") == 0)
		{
			moveHorizontal = true;
			moveVertical = true;
		}
		else if (text.compareTo("h") == 0)
		{
			moveHorizontal = true;
			moveVertical = false;
		}
		else if(text.compareTo("v") == 0)
		{
			moveHorizontal = false;
			moveVertical = true;
		}
		else
		{
			moveHorizontal = false;
			moveVertical = false;
			System.out.print("Error setting movement directions!");
		}
		
		return new SlidingBlock(row, column, width, height, moveHorizontal, moveVertical, num);
	}
	
	public SlidingMap(String fileName)
	{
		File file = new File(fileName);
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			
			//reads the first line and sets the rows and columns
			text = reader.readLine();
			int firstSpace = text.indexOf(" "); //finds the space
			this.rows = Integer.parseInt(text.substring(0, firstSpace)); //turns substring up until space into int rows
			this.columns = Integer.parseInt(text.substring(firstSpace+1)); //turns everything after the first space into int columns
			
			//creates goal block
			text = reader.readLine();
			SlidingBlock goalBlock = generateSlidingBlock(text, 0);
			this.goalBlock = goalBlock;
			blocks.add(goalBlock);
			
			int block_num = 1;
			//turns remaining lines into blocks
			while ((text = reader.readLine()) != null)
			{
				SlidingBlock moveableBlock = generateSlidingBlock(text, block_num);
				blocks.add(moveableBlock);
				block_num += 1;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
				{
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public int[][] toArray() {
		int[][] myArray = new int[this.rows][this.columns];
		
		//set all elements of the array to be equal to a "unoccupied" -1
		for (int x = 0; x<this.rows; x++)
		{
			for (int y = 0; y<this.columns; y++)
			{
				myArray[x][y] = -1;
			}
		}
		
		for (int x = 0; x<blocks.size(); x++) {  //iterate through all blocks
			SlidingBlock myBlock = blocks.get(x);
			int[][] blockPos = myBlock.getAllPos(); //get a list of x,y coordinates of the block
			for (int y = 0; y < blockPos.length; y++)//iterate through list and update each position with block number
			{
				myArray[blockPos[y][0]-1][blockPos[y][1]-1] = myBlock.getNum(); //positions are stored starting at 1, subtract one to get array position
			}
		}
		
		return myArray;
	}
	
	public void numMoves(int[][] grid)
	{
		for (int i = 0; i < blocks.size(); i++)
		{
			SlidingBlock myBlock = blocks.get(i);
			System.out.print(myBlock.getNum() + ":\n  R:" 
			+ myBlock.numPossibleMovesRight(grid) + "\n  L:"
			+ myBlock.numPossibleMovesLeft(grid) + "\n  U:"
			+ myBlock.numPossibleMovesUp(grid) + "\n  D:" +
			+ myBlock.numPossibleMovesDown(grid) + "\n");
		}
	}
}
