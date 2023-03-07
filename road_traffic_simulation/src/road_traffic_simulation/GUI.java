package road_traffic_simulation;


import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class GUI extends JFrame {
	private List<Vehicle> vehicles;
	private JPanel panel;
    private JTable vehiclesTable;
    private JTable statisticsTable;
    private JLabel vehicle_label;

    public GUI() {
    	super("Traffic Intersection System");
    	try {
			this.vehicles = readVehiclesFromCsv("vehicles.csv");
			
	        // Create vehicles table
	        Object[][] vehiclesData = new Object[vehicles.size()][8];
	        for (int i = 0; i < vehicles.size(); i++) {
	            Vehicle vehicle = vehicles.get(i);
	            vehiclesData[i][0] = vehicle.getPlateNumber();
	            vehiclesData[i][1] = vehicle.getVehicleType();
	            vehiclesData[i][2] = vehicle.getCrossingTime();
	            vehiclesData[i][3] = vehicle.getDirection();
	            vehiclesData[i][4] = vehicle.getWaitingLength();
	            vehiclesData[i][5] = vehicle.getCo2Emission();
	            vehiclesData[i][6] = vehicle.getVehicleStatus();
	            vehiclesData[i][7] = vehicle.getSegmentNumber();
	        }
		
    	
    	
    	 String[] vehiclesColumnNames = { "Plate number", "Vehicle type", "Crossing time", "Direction", "Waiting length",
                 "CO2 emission", "Vehicle status", "Segment number" };
         this.vehiclesTable = new JTable(vehiclesData, vehiclesColumnNames);
         vehiclesTable.setBounds(10, 0, 440, 180);

         // Create statistics table
         Object[][] statisticsData = new Object[4][4];
         String[] statisticsColumnNames = { "Segment number", "Total waiting time", "Total waiting length",
                 "Total crossing time" };
         this.statisticsTable = new JTable(statisticsData, statisticsColumnNames);
         
         // Add tables to panels with scroll panes
         JPanel vehiclesPanel = new JPanel(new BorderLayout());
         vehiclesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
         //vehiclesPanel.setLayout(null);
         vehiclesPanel.add(new JScrollPane(vehiclesTable), BorderLayout.CENTER);

         JPanel statisticsPanel = new JPanel(new BorderLayout());
         statisticsPanel.add(new JScrollPane(statisticsTable), BorderLayout.CENTER);

         // Add panels to main frame
         this.setLayout(new BorderLayout());
         this.add(vehiclesPanel, BorderLayout.WEST);
         this.add(statisticsPanel, BorderLayout.EAST);
         this.setSize(1200, 800);
         this.setVisible(true);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * setTitle("CSV Reader"); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * setSize(1200, 800);
		 * 
		 * String FileName = "vehicles.csv"; String line = ""; String splitBy = ",";
		 * List<String[]> data = new ArrayList<>();
		 * 
		 * try (BufferedReader br = new BufferedReader(new FileReader(FileName))) {
		 * while ((line = br.readLine()) != null) { String[] rowData =
		 * line.split(splitBy); data.add(rowData); } } catch (Exception e) {
		 * e.printStackTrace(); } //implementing GUI for Vehicle display section
		 * String[] headers = {"Vehicle", "Type", "Crossing", "Direction", "Length",
		 * "Emission", "Status", "Segment"}; vehicle_label = new JLabel("Vehicles",
		 * SwingConstants.CENTER); vehicle_label.setBounds(10, 0, 450, 30);
		 * vehicle_table = new JTable(data.toArray(new String[data.size()][0]),
		 * headers); vehicle_table.setBounds(10, 0, 440, 180);
		 * 
		 * JScrollPane vehicle_scrollPane = new JScrollPane(vehicle_table);
		 * vehicle_scrollPane.setBounds(10, 30, 450, 200); panel = new JPanel(new
		 * GridLayout(0,1)); panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20,
		 * 20)); panel.setLayout(null); panel.add(vehicle_label);
		 * panel.add(vehicle_scrollPane);
		 * 
		 * //implementing GUI for Statistics JLabel statistics_l = new
		 * JLabel("Statistics", SwingConstants.CENTER); statistics_l.setBounds(600, 0,
		 * 350, 30); String[][] s_strings = { {"S1", "600s","2000m","20s"}, {"S2",
		 * "60s","300m","10s"}, {"S3", "300s","1500m","15s"}, {"S4", "40s","100m","10s"}
		 * }; String[] s_header = {"Segment", "Waiting Time", "Waiting Length",
		 * "Cross Time"};
		 * 
		 * JTable statistics_t = new JTable(s_strings, s_header);
		 * statistics_t.setBounds(0,0,250,90); JScrollPane statistics_scroll = new
		 * JScrollPane(); statistics_scroll.setBounds(600, 30, 350, 100);
		 * statistics_scroll.setViewportView(statistics_t);
		 * 
		 * //implementation for CO2 GUI.
		 * 
		 * JLabel co2_l = new JLabel("CO2", SwingConstants.CENTER); co2_l.setBounds(750,
		 * 150, 50, 30); JTextField co2Field = new JTextField(); co2Field.setBounds(750,
		 * 180, 50, 30); JLabel co2_unit = new JLabel("kg"); co2_unit.setBounds(800,
		 * 180, 50, 30);
		 * 
		 * 
		 * panel.add(statistics_l); panel.add(statistics_scroll); panel.add(co2_l);
		 * panel.add(co2Field); panel.add(co2_unit); add(panel);
		 */        

    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    public static List<Vehicle> readVehiclesFromCsv(String filename) throws IOException {
        List<Vehicle> vehicles = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
        //String headerLine = br.readLine();
        String line;
        while ((line = br.readLine()) != null) {
            String[] fields = line.split(",");
            String plateNumber = fields[0];
            String vehicleType = fields[1];
            int crossingTime = Integer.parseInt(fields[2]);
            String direction = fields[3];
            double waitingLength = Double.parseDouble(fields[4]);
            int co2Emission = Integer.parseInt(fields[5]);
            String vehicleStatus = fields[6];
            String segmentNumber = fields[7];
            Vehicle Vehicle = new Vehicle(plateNumber, vehicleType, crossingTime, direction, 
                                           waitingLength, co2Emission, vehicleStatus, segmentNumber);
            vehicles.add(Vehicle);
        }
        br.close();
        return vehicles;
        }
    }

    public void co2_emission() {
    	
    }
	/*
	 * public static void main(String[] args) { new GUI(); }
	 */

}
