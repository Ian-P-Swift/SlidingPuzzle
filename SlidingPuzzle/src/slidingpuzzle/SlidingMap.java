package slidingpuzzle;

import java.io.*;
import java.util.*;

public class SlidingMap {
	private int rows;
	private int columns;
	private SlidingBlock goalBlock;
	private ArrayList<SlidingBlock> blocks = new ArrayList<SlidingBlock>();
	private BufferedReader reader = null;
	
	private SlidingBlock generateSlidingBlock(String text)
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
		
		return new SlidingBlock(row, column, width, height, moveHorizontal, moveVertical);
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
			SlidingBlock goalBlock = generateSlidingBlock(text);
			this.goalBlock = goalBlock;
			blocks.add(goalBlock);
			
			//turns remaining lines into blocks
			while ((text = reader.readLine()) != null)
			{
				SlidingBlock moveableBlock = generateSlidingBlock(text);
				blocks.add(moveableBlock);
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
}
