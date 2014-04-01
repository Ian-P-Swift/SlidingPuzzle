//This class makes the puzzle from a file
//It also verifies the spaces and calculates the number of moves

package slidingpuzzle;

import java.io.*;
import java.util.*;

//gets the co-ordinates for the sliding map
public class SlidingMap {
	private int rows;
	private int columns;
	private SlidingBlock goalBlock;
	private ArrayList<SlidingBlock> blocks = new ArrayList<SlidingBlock>();
	private BufferedReader reader = null;
	private SlidingMap parent = null;
	
	public int getColumns()
	{
		return this.columns;
	}
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
	
	public SlidingBlock getBlock(int index)
	{
		return this.blocks.get(index);
	}
	
	public SlidingMap(SlidingMap original)
	{
		this.rows = original.rows;
		this.columns = original.columns;
		this.goalBlock = new SlidingBlock(original.goalBlock);
		this.blocks.add(goalBlock);
		for (int i = 1; i < original.blocks.size(); i++)
		{
			this.blocks.add(new SlidingBlock(original.blocks.get(i)));
		}
		this.parent = original;
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
	
	//set all elements of the array to be equal to a "unoccupied" -1
	public int[][] toArray() {
		int[][] myArray = new int[this.rows][this.columns];
		
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
	
	//gets a list of possible number of moves
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
	
	//finds a solution for the current puzzle
	public List<SlidingMap> FindSolution()
	{
		SlidingMap goal = this.FindSolutionHelper();
		if (goal == null)
		{
			return null;
		}
		List<SlidingMap> path = new ArrayList();
		while (goal != null)
		{
			path.add(goal);
			goal = goal.parent;
		}
		Collections.reverse(path);
		return path;
	}
	
	//helper function for finding a solution
	private SlidingMap FindSolutionHelper()
	{
		List<SlidingMap> solution = new ArrayList<SlidingMap>();
		solution.add(this);
		List<int[][]> closedList = new ArrayList<int[][]>();
		Queue<SlidingMap> myQueue = new LinkedList<SlidingMap>();
		
		myQueue.add(this);
		
		int count = 0;
		while(!myQueue.isEmpty())
		{
			System.out.print(count + "\n");
			count++;
			SlidingMap current = myQueue.remove();
			if (InClosedList(current.toArray(), closedList)) {
				continue;
			}
			
			if (current.blocks.get(0).isOut(current.columns))
			{
				return current;
			}
			int[][] myArray = current.toArray();
			for (int x = 0; x < myArray.length; x++)
			{
				for (int y = 0; y < myArray[x].length; y++)
				{
					System.out.print(myArray[x][y] + " ");
				}
				System.out.print("\n");
			}
			closedList.add(current.toArray());
			for (int i = 0; i<this.blocks.size(); i++)
			{
				int rightMoves = current.blocks.get(i).numPossibleMovesRight(current.toArray());
				int leftMoves = current.blocks.get(i).numPossibleMovesLeft(current.toArray());
				int upMoves = current.blocks.get(i).numPossibleMovesUp(current.toArray());
				int downMoves = current.blocks.get(i).numPossibleMovesDown(current.toArray());
				
				for (int j = 0; j<rightMoves; j++)
				{
					SlidingMap new_map = new SlidingMap(current);
					new_map.blocks.get(i).moveY(j+1);
					myQueue.add(new_map);
				}
				for (int j = 0; j<leftMoves; j++)
				{
					SlidingMap new_map = new SlidingMap(current);
					new_map.blocks.get(i).moveY(-j-1);
					myQueue.add(new_map);
				}
				for (int j = 0; j<upMoves; j++)
				{
					SlidingMap new_map = new SlidingMap(current);
					new_map.blocks.get(i).moveX(-j-1);
					myQueue.add(new_map);
				}
				for (int j = 0; j<downMoves; j++)
				{
					SlidingMap new_map = new SlidingMap(current);
					new_map.blocks.get(i).moveX(j+1);
					myQueue.add(new_map);
				}
			}
		}
		return null;
	}

	//Helper function for breadth first search, checks to see if any of the elements are the same
	public static boolean InClosedList(int[][] myPos, List<int[][]> myList)
	{
		for (int i = 0; i < myList.size(); i++)
		{
			boolean no_difference = true;
			for (int j = 0; j < myList.get(i).length; j++)
			{
				for (int k = 0; k < myList.get(i)[j].length; k++)
				{
					if (myPos[j][k] != myList.get(i)[j][k])
					{
						no_difference = false;
					}
				}
			}
			if (no_difference)
			{
				return true;
			}
		}
		return false;
	}
}
