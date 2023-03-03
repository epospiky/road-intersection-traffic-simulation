package road_traffic_simulation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class GUI {
	public GUI() {
		JFrame frame = new JFrame();
		String[][] rec = {
		         { "1", "Steve", "AUS" },
		         { "2", "Virat", "IND" },
		         { "3", "Kane", "NZ" },
		         { "4", "David", "AUS" },
		         { "5", "Ben", "ENG" },
		         { "6", "Eion", "ENG" },
		      };
		String[] header = {"Rank", "Player", "Country"};
		JTable vehicles_t = new JTable(rec,header);
		vehicles_t.setBounds(50, 60, 100,100);
		
		JButton add =  new JButton("Add");
		//Dimension size = add.getPreferredSize();	
		add.setBounds(50, 570, 80, 30);
		JButton cancel =  new JButton("Cancel");
		cancel.setBounds(200, 570, 80, 30);
		JButton exit =  new JButton("Exit");
		exit.setBounds(650, 570, 80, 30);
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.setLayout(null);
		panel.add(vehicles_t, BorderLayout.CENTER);
		panel.add(add);
		panel.add(cancel);
		panel.add(exit);
		
		frame.setTitle("Road Intersection Traffic Similation");
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(800, 650);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GUI();
	}

}
