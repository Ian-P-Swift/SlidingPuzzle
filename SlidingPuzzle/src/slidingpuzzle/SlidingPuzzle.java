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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
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
	private JButton buttons[];
	@SuppressWarnings("unused")
	private GridBagConstraints bounds;
	@SuppressWarnings("unused")
	private javax.swing.Timer timer2;
	private int nSeconds = 0;
	private int left = 0;
	private int blockNumber;
	private boolean isClicked = false;
	
	private Timer timer = new Timer();
	private JLabel timeLabel = new JLabel(" ", JLabel.CENTER);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SlidingPuzzle frame = new SlidingPuzzle();
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
		setBounds(100, 100, 500, 600);
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
		lblTime.setBounds(354, 12, 91, 29);
		contentPane.add(lblTime);
		
		//Displays a flags left label
		JLabel lblFlagsLeft = new JLabel("Score");
		lblFlagsLeft.setForeground(Color.DARK_GRAY);
		lblFlagsLeft.setBackground(Color.WHITE);
		lblFlagsLeft.setFont(new Font("Verdana", Font.BOLD, 14));
		lblFlagsLeft.setHorizontalAlignment(SwingConstants.CENTER);
		lblFlagsLeft.setBounds(20, 11, 91, 28);
		contentPane.add(lblFlagsLeft);
		
		//Creates a Progress bar
		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setString("** SlidingPuzzle **");
		progressBar.setStringPainted(true);
		progressBar.setIndeterminate(true);
		progressBar.setBounds(144, 11, 189, 29);
		contentPane.add(progressBar);
		
		SlidingMap map1 = new SlidingMap("src/map.txt");
		int[][] myArray = map1.toArray();
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
		
		exit.addActionListener(this);
		Instructions.addActionListener(this);
		About.addActionListener(this);
		Hint.addActionListener(this);
		Solve.addActionListener(this);
		
		buttons = new JButton[myArray.length];
		
		//print the contents of to array
		for (int x = 0; x < myArray.length; x++)
		{
			for (int y = 0; y < myArray[x].length; y++)
			{
				buttons[x] = new JButton();
				//bounds.gridx = ;
				buttons[x].setBounds(10+(x*50), 54+(y*50), 50, 50);
				if (myArray[y][x] >= 0)
				{
					buttons[x].setText(myArray[y][x] + "");
				}
				buttons[x].addMouseListener(this);
				contentPane.add(buttons[x]);
				System.out.print(myArray[x][y] + " ");
			}
			System.out.print("\n");
		}

		List<SlidingMap> solution = map1.FindSolution();
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int mbutton = e.getButton();
			if(mbutton == 1){
				left++;
				if(left==1){
					timer.schedule(new UpdateUITask(), 0, 1000); 
				}
				if (this.isClicked == false)
				{
					if (((JButton)e.getSource()).getText() != null)
					{
						this.blockNumber = Integer.parseInt(((((JButton)e.getSource()).getText())));
					}
					this.isClicked = true;
				}
				else if (this.isClicked == true)
				{
					((JButton)e.getSource()).setText(blockNumber + "");
				}
			}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit)
			System.exit(0);
		
		if (e.getSource() == Instructions)
			JOptionPane.showMessageDialog(null,"<html><p> Sliding Puzzle. </p><html>","Instructions",JOptionPane.INFORMATION_MESSAGE);
		
		if (e.getSource() == About)
		JOptionPane.showMessageDialog(null,"<html><p> Author : Abinav Saini and Ian Swift <br> Date : 3/30/2014 <p><html> "," About SlidingPuzzle ",JOptionPane.INFORMATION_MESSAGE);
		
		if (e.getSource() == Hint)
			JOptionPane.showMessageDialog(null,"<html><p> To be added later </p><html>","Hint",JOptionPane.INFORMATION_MESSAGE);
	}
	
	private class UpdateUITask extends TimerTask {

        @Override
        public void run() {
            EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    timeLabel.setText(String.valueOf(nSeconds+=1));}
                
            });
        }
    }

}