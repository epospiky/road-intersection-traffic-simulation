package road_traffic_simulation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI {
	public GUI() {
		JFrame frame = new JFrame();
		//implementing GUI for Vehicle display section
		JLabel vehicle_l = new JLabel("Vehicles", SwingConstants.CENTER);
		vehicle_l.setBounds(10, 0, 440, 30);
		String[][] v_strings = {
		         { "L23467", "Truck", "10", "Left", "12", "12", "Waiting", "S1" },
		         { "L23467", "Car", "3", "Straight", "4", "5", "Waiting", "S2" },
		         { "L23467", "Bus", "8", "Right", "14", "10", "Crossed", "S1" }
		      };
		String[] v_header = {"Vehicle", "Type", "Crossing Time", "Direction", "Length", "Emission", "Status", "Segment"};
	
		JTable vehicles_t = new JTable(v_strings, v_header);
		vehicles_t.setBounds(0, 0, 440,180);
		JScrollPane vehicle_scroll = new JScrollPane();
		vehicle_scroll.setBounds(10, 30, 450, 200);
		vehicle_scroll.setViewportView(vehicles_t);
		
		//implementing GUI for Statistics
		JLabel statistics_l = new JLabel("Statistics", SwingConstants.CENTER);
		statistics_l.setBounds(600, 0, 350, 30);
		String[][] s_strings = {
				{"S1", "600s","2000m","20s"},
				{"S2", "60s","300m","10s"},
				{"S3", "300s","1500m","15s"},
				{"S4", "40s","100m","10s"}
		};
		String[] s_header = {"Segment", "Waiting Time", "Waiting Length", "Cross Time"};
		
		JTable statistics_t = new JTable(s_strings, s_header);
		statistics_t.setBounds(0,0,250,90);
		JScrollPane statistics_scroll = new JScrollPane();
		statistics_scroll.setBounds(600, 30, 350, 100);
		statistics_scroll.setViewportView(statistics_t);
		
		//implementation for CO2 GUI.
		JLabel co2_l =  new JLabel("CO2", SwingConstants.CENTER);
		co2_l.setBounds(750, 150, 50, 30);
		JTextField co2Field = new JTextField();
		co2Field.setBounds(750, 180, 50, 30);
		JLabel co2_unit = new JLabel("kg");
		co2_unit.setBounds(800, 180, 50, 30);
		
		
		
		JPanel panel = new JPanel(new GridLayout(0,1));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.setLayout(null);
		panel.add(vehicle_l);
		panel.add(vehicle_scroll);
		panel.add(statistics_l);
		panel.add(statistics_scroll);
		panel.add(co2_l);
		panel.add(co2Field);
		panel.add(co2_unit);
		
		frame.setTitle("Road Intersection Traffic Similation");
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(1200, 800);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GUI();
	}

}
