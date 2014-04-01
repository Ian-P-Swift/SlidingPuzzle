//This contains the main class
//It creates a GUI for the user and deals with all the mouse events

package slidingpuzzle;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class SlidingPuzzle extends JFrame implements ActionListener, MouseListener {

	private JPanel contentPane;
	private JMenuItem exit;
	private JMenuItem Instructions;
	private JMenuItem Hint;
	private JMenuItem Solve;
	private JMenuItem About;
	private JButton buttons[][];
	private GridBagConstraints bounds;
	@SuppressWarnings("unused")
	private javax.swing.Timer timer2;
	private int nSeconds = 0;
	private int left = 0;
	private int blockNumber;
	private boolean isClicked = false;
	private int num_clicks = 0;

	static SlidingPuzzle frame;

	private int startX;
	private int startY;
	private SlidingMap currentMap;
	private List<SlidingMap> solution;
	private int solution_time = -1;
	
	private Timer timer = new Timer();
	private JLabel timeLabel = new JLabel(" ", JLabel.CENTER);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new SlidingPuzzle();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SlidingPuzzle() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Create timer
		timer2 = new javax.swing.Timer(1000,this); 
		JPanel timer2 = new JPanel();
		timer2.add(timeLabel); 
		timer2.setBounds(413,12,79,28);
		contentPane.add(timer2);
				
		//Creates a time label
		JLabel lblTime = new JLabel("Time :");
		lblTime.setToolTipText("Time in Seconds");
		lblTime.setFont(new Font("Verdana", Font.BOLD, 14));
		lblTime.setBounds(300, 12, 91, 29);
		contentPane.add(lblTime);
		
		//Displays a flags left label
		JLabel lblFlagsLeft = new JLabel("Move Count");
		lblFlagsLeft.setForeground(Color.DARK_GRAY);
		lblFlagsLeft.setBackground(Color.WHITE);
		lblFlagsLeft.setFont(new Font("Verdana", Font.BOLD, 12));
		lblFlagsLeft.setHorizontalAlignment(SwingConstants.CENTER);
		lblFlagsLeft.setBounds(20, 11, 91, 28);
		contentPane.add(lblFlagsLeft);
		
		//Creates a Progress bar
		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setString("** SlidingPuzzle **");
		progressBar.setStringPainted(true);
		progressBar.setIndeterminate(true);
		progressBar.setBounds(114, 11, 150, 29);
		contentPane.add(progressBar);
		
		currentMap = new SlidingMap("src/map.txt");
		int[][] myArray = currentMap.toArray();
		this.setTitle("Sliding Block Puzzle");
		bounds = new GridBagConstraints();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu file;
		
		// Setting the menu Bar
		menuBar.setBounds(0, 0, 97, 21);  
		//contentPane.add(menuBar);  
		setJMenuBar(menuBar);
		
		file = new JMenu("Game");
		menuBar.add(file);
		exit = new JMenuItem("Quit");
		Hint = new JMenuItem("Hint");
		Solve = new JMenuItem("Solve");
		file.add(Hint);
		file.add(Solve);
		file.add(exit);
		
		JMenu option;
		option = new JMenu(" Help ");
		
		Instructions = new JMenuItem("Instructions");
		About = new JMenuItem("About");
		menuBar.add(option);
		option.add(Instructions);
		option.add(About);
		file.setBounds(105, 0, 107, 22);
		
		//Action Listener for all the menu bar items
		exit.addActionListener(this);
		Instructions.addActionListener(this);
		About.addActionListener(this);
		Hint.addActionListener(this);
		Solve.addActionListener(this);
		
		buttons = new JButton[myArray.length][myArray[0].length];
		System.out.println("Size of the array is" + myArray.length);
		if(myArray.length <= 5){
		setBounds(100, 20, myArray.length*130, myArray.length*100);
		}
		
		if(myArray.length > 6 ){
			setBounds(100, 20, myArray.length*100, myArray.length*100);
		}
		
		//print the contents of to array
		for (int x = 0; x < myArray.length; x++)
		{
			for (int y = 0; y < myArray[x].length; y++)
			{
				buttons[x][y] = new JButton();
				bounds.gridx = getX();
				if(myArray.length <= 5){
				buttons[x][y].setBounds(10+(x*myArray.length*15), 54+(y*myArray.length*15), myArray.length*15, myArray.length*15);
				}
				if(myArray.length > 6 ){
					buttons[x][y].setBounds(10+(x*myArray.length*10), 54+(y*myArray.length*10), myArray.length*10, myArray.length*10);
				}
				buttons[x][y].setBackground(Color.WHITE);
				if (myArray[y][x] > 0)
				{
					buttons[x][y].setText(myArray[y][x] + "");
				}
				else if (myArray[y][x] == 0)
				{
					buttons[x][y].setText("Z");
					buttons[x][y].setBackground(Color.RED);
				}
				buttons[x][y].addMouseListener(this);
				contentPane.add(buttons[x][y]);
				System.out.print(myArray[x][y] + " ");
			}
			System.out.print("\n");
		}
		

		solution = currentMap.FindSolution();
		System.out.print("Hello world!");

		for (int i = 0; i < solution.size(); i++)
		{
			System.out.print("Step " + i + ":\n");
			int[][] myStep = solution.get(i).toArray();
			for (int x = 0; x < myStep.length; x++)
			{
				for (int y = 0; y < myStep[x].length; y++)
				{
					System.out.print(myStep[x][y] + " ");
				}
				System.out.print("\n");
			}
		}
	}

	//Everytime a button is clicked it refers to this event
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int mbutton = e.getButton();
			if(mbutton == 1){
				left++;
				if(left==1){  //left button click
					timer.schedule(new UpdateUITask(), 0, 1000); 
				}
				if (this.isClicked == false)
				{
					if (((JButton)e.getSource()).getText() != "")
					{
						if (((JButton)e.getSource()).getText() == "Z")
						{
							this.blockNumber = 0;
						}
						else
						{
							this.blockNumber = Integer.parseInt(((((JButton)e.getSource()).getText())));
						}
						this.isClicked = true;
						for (int x = 0; x < buttons.length; x++)
						{
							for (int y = 0; y < buttons.length; y++)
							{
								if  (buttons[x][y] == e.getSource()){
									this.startX = x;
									this.startY = y;
									System.out.print(x + ", " + y + "\n");
								}
							}
						}
					}
				}
				else if (this.isClicked == true)
				{
					this.isClicked = false;
					int endX = -1;
					int endY = -1;
					for (int x = 0; x < buttons.length; x++)
					{
						for (int y = 0; y < buttons.length; y++)
						{
							if  (buttons[x][y] == e.getSource()){
								endX = x;
								endY = y;
							}
						}
					}
					if (endX != -1 && endX == this.startX && endY > this.startY)
					{
						SlidingBlock myBlock = currentMap.getBlock(this.blockNumber);
						if (endY - this.startY <= myBlock.numPossibleMovesDown(currentMap.toArray()))
						{
							myBlock.moveX(endY-this.startY);
						}
					}
					if (endX != -1 && endX == this.startX && endY < this.startY)
					{
						SlidingBlock myBlock = currentMap.getBlock(this.blockNumber);
						if (this.startY - endY <= myBlock.numPossibleMovesUp(currentMap.toArray()))
						{
							myBlock.moveX(endY-this.startY);
						}
					}
					if (endX != -1 && endY == this.startY && endX > this.startX)
					{
						SlidingBlock myBlock = currentMap.getBlock(this.blockNumber);
						if (endX - this.startX <= myBlock.numPossibleMovesRight(currentMap.toArray()))
						{
							myBlock.moveY(endX-this.startX);
						}
					}
					if (endX != -1 && endY == this.startY && endX < this.startX)
					{
						SlidingBlock myBlock = currentMap.getBlock(this.blockNumber);
						if (this.startX - endX <= myBlock.numPossibleMovesLeft(currentMap.toArray()))
						{
							myBlock.moveY(endX-this.startX);
						}
					}
					int[][] myArray = currentMap.toArray();
					for (int x = 0; x < myArray.length; x++)
					{
						for (int y = 0; y < myArray[x].length; y++)
						{
							if (myArray[y][x] > 0)
							{
								buttons[x][y].setText(myArray[y][x] + "");
								buttons[x][y].setBackground(Color.WHITE);
							}
							else if (myArray[y][x] == 0)
							{
								buttons[x][y].setText("Z");
								buttons[x][y].setBackground(Color.RED);
							}
							else
							{
								buttons[x][y].setText("");
								buttons[x][y].setBackground(Color.WHITE);
							}
						}
					}
					if (this.currentMap.getBlock(0).isOut(this.currentMap.getColumns()))
					{
						this.NextPuzzle();
					}
					this.solution = currentMap.FindSolution();
				}
			}
		}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO
	}
	
	//Loads the next puzzle everytime the user wins the game
	public void NextPuzzle(){
		int choice = 0;
		JOptionPane.showMessageDialog(null,"You Win, Want to play another puzzle?!!","Game Won",JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION){
			JFileChooser fileChooser = new JFileChooser();
	        int returnValue = fileChooser.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	          File selectedFile = fileChooser.getSelectedFile();
	          System.out.println(selectedFile.getName());
			//Scanner in = new Scanner(System.in);
			//JOptionPane.showInputDialog("Please Enter the filename");
			//String filename = in.nextLine();
			SlidingMap map1 = new SlidingMap("selectedFile");
			SlidingPuzzle game = new SlidingPuzzle();
			SlidingPuzzle game1 = new SlidingPuzzle();
			frame.setVisible(false);
			game.setVisible(true);
			int count =2;
			if (count ==2){
				dispose();
				game.setVisible(false);
				JOptionPane.showMessageDialog(null, "New puzzle laoded","puzzle loaded", JOptionPane.OK_CANCEL_OPTION);
				game1.setVisible(true);
			}
			int[][] myArray = map1.toArray();
	        }
		}
	}

	//Actions for the instructions and other buttons
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit)
			System.exit(0);
		
		if (e.getSource() == Instructions)
			JOptionPane.showMessageDialog(null,"<html><p> To move a button in the puzzle, select the button to move and <br> and then left click on the place where you want to move the button </p><html>","Instructions",JOptionPane.INFORMATION_MESSAGE);
		
		if (e.getSource() == About)
		JOptionPane.showMessageDialog(null,"<html><p> Author : Abinav Saini and Ian Swift <br> Date : 3/30/2014 <p><html> "," About SlidingPuzzle ",JOptionPane.INFORMATION_MESSAGE);
		
		if (e.getSource() == Hint)
		{
			System.out.print("trying to move....");
			this.currentMap = solution.get(1+num_clicks);
			num_clicks++;
			for (int i = 0; i < solution.size(); i++)
			{
			int[][] myStep = solution.get(i).toArray();
			System.out.print("Step " + i + "\n");
			for (int x = 0; x < myStep.length; x++)
			{
				for (int y = 0; y < myStep[x].length; y++)
				{
					System.out.print(myStep[x][y] + " ");
				}
				System.out.print("\n");
			}
			}
			this.solution = this.currentMap.FindSolution();
			int[][] myArray = currentMap.toArray();
			for (int x = 0; x < myArray.length; x++)
			{
				for (int y = 0; y < myArray[x].length; y++)
				{
					if (myArray[y][x] > 0)
					{
						buttons[x][y].setText(myArray[y][x] + "");
						buttons[x][y].setBackground(Color.WHITE);
					}
					else if (myArray[y][x] == 0)
					{
						buttons[x][y].setText("Z");
						buttons[x][y].setBackground(Color.RED);
					}
					else
					{
						buttons[x][y].setText("");
						buttons[x][y].setBackground(Color.WHITE);
					}
				}
			}
			if (this.currentMap.getBlock(0).isOut(this.currentMap.getColumns()))
			{
				this.NextPuzzle();
			}
		}
			//JOptionPane.showMessageDialog(null,"<html><p> To be added later </p><html>","Hint",JOptionPane.INFORMATION_MESSAGE);
		if (e.getSource() == Solve)
		{
			left++;
			if(left==1){  //left button click
				timer.schedule(new UpdateUITask(), 0, 1000); 
			}
			this.solution_time = 1 + num_clicks;
			System.out.print(solution_time);
		}
	}
	
	//Task for the timer class
	private class UpdateUITask extends TimerTask {

        @Override
        public void run() {
            EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    timeLabel.setText(String.valueOf(nSeconds+=1));
                    
                    if (solution_time != -1) {
                   System.out.print("Attempting to solve!");
                    currentMap = solution.get(solution_time);

    				int[][] myArray = currentMap.toArray();
    				for (int x = 0; x < myArray.length; x++)
    				{
    					for (int y = 0; y < myArray[x].length; y++)
    					{
    						if (myArray[y][x] > 0)
    						{
    							buttons[x][y].setText(myArray[y][x] + "");
    							buttons[x][y].setBackground(Color.WHITE);
    						}
    						else if (myArray[y][x] == 0)
    						{
    							buttons[x][y].setText("Z");
    							buttons[x][y].setBackground(Color.RED);
    						}
    						else
    						{
    							buttons[x][y].setText("");
    							buttons[x][y].setBackground(Color.WHITE);
    						}
    					}
    				}
    				solution_time++;    
    				if (currentMap.getBlock(0).isOut(currentMap.getColumns()))
    				{
    					solution_time = -1;
    					NextPuzzle();
    				}
                }
                
            }
           });
    }
	
	}
}
