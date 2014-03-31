package slidingpuzzle;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SlidingPuzzle extends JFrame implements ActionListener, MouseListener {

	private JPanel contentPane;
	private JMenuItem exit;
	private JMenuItem Instructions;
	private JMenuItem Hint;
	private JMenuItem Solve;
	private JMenuItem About;
	private JButton buttons[];
	private GridBagConstraints bounds;

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
				buttons[x].addActionListener(this);
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
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
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

}