package slidingpuzzle;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SlidingPuzzle extends JFrame {

	private JPanel contentPane;

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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		SlidingMap map1 = new SlidingMap("src/map.txt");
		int[][] myArray = map1.toArray();
		
		/*
		 * print the contents of to array
		 * for (int x = 0; x < myArray.length; x++)
		{
			for (int y = 0; y < myArray[x].length; y++)
			{
				System.out.print(myArray[x][y] + " ");
			}
			System.out.print("\n");
		}*/
	}

}
