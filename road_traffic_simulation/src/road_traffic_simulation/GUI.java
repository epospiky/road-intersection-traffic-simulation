package road_traffic_simulation;


import java.awt.BorderLayout;
import java.awt.GridLayout;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame {
	private List<Vehicle> vehicles;
	private List<Statistics> statistics;
	
	//private JPanel panel;
    private JTable vehiclesTable;
    private JTable statisticsTable;
    private JTextField emissionField;
    private JLabel vehicleLabel;
    //private JLabel vehicle_label;

    public GUI() {
    	//co2_emission();
    	try {
			this.vehicles = readVehiclesFromCsv("vehicles.csv");
			
	        // Creating vehicles table
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
		
    	
    	
    	 String[] vehiclesColumnNames = {"Vehicle number", "Type", "Crossing time", "Direction", "Length",
                 "Emission", "Status", "Segment" };
         vehicleLabel = new JLabel("Vehicles", SwingConstants.CENTER);
 		 vehicleLabel.setBounds(10, 0, 450, 30);
         vehiclesTable = new JTable(vehiclesData, vehiclesColumnNames);
         vehiclesTable.setBounds(10, 0, 440, 180);
         JScrollPane vehicleScroll = new JScrollPane(vehiclesTable);
         //vehicleScroll.setViewportView();
         vehicleScroll.setBounds(10, 30, 450, 200);

         // Creating statistics table
         Object[][] statisticsData = new Object[vehicles.size()][4];
         String[] statisticsColumnNames = { "Segment", "Waiting time", "Waiting length",
                 "Crossing time" };
 		 JLabel statisticsLabel = new JLabel("Statistics", SwingConstants.CENTER);
 		 statisticsLabel.setBounds(600, 0, 350, 30);
         this.statisticsTable = new JTable(statisticsData, statisticsColumnNames);
         statisticsTable.setBounds(500, 0, 300, 150);
         JScrollPane statisticsScroll = new JScrollPane(); 
         statisticsScroll.setBounds(600, 30, 350, 100);
         statisticsScroll.setViewportView(statisticsTable);
         
         
         //Creating co2 field
         Statistics stat = new Statistics(0,0,0,0,0);
         JLabel co2_l = new JLabel("CO2", SwingConstants.CENTER); 
         co2_l.setBounds(750,150, 50, 30);
         double totalCo2Emission = stat.getTotalCo2Emission();
         System.out.println(totalCo2Emission);
         this.emissionField = new JTextField();
         emissionField.setBounds(750,180, 50, 30);
         emissionField.setText(Double.toString(totalCo2Emission));
         JLabel co2_unit = new JLabel("kg"); 
         co2_unit.setBounds(800, 180, 50, 30);
         co2_l.setBounds(750,150, 50, 30); 
         co2_unit.setBounds(800,180, 50, 30);
         
         
         
         // Adding tables to panels with scroll panes
         JPanel Panel = new JPanel(new GridLayout(0,1));
         Panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
         Panel.setLayout(null);
         Panel.add(vehicleLabel);
         Panel.add(vehicleScroll);
         //Adding Statistics table to panel
         Panel.add(statisticsLabel);
         Panel.add(statisticsScroll);
         
         //Adding emission to panel
         
         Panel.add(co2_l);
         Panel.add(co2_unit);
         Panel.add(emissionField);
         List<Statistics> statistics = Statistics.populateStatistics(vehicles);
         DefaultTableModel statisticsTableModel = createStatisticsTableModel(statistics);
         statisticsTable.setModel(statisticsTableModel);

         // Adding panels to main frame
         //this.setLayout(new BorderLayout());
     
         this.setTitle("Road Intersection Traffic Similation");
 		 this.add(Panel, BorderLayout.CENTER);
         this.setSize(1200, 800);
         this.setResizable(false);
         this.setVisible(true);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    

    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }

	public DefaultTableModel createStatisticsTableModel(List<Statistics> statistics) {
        // Create the table model with the appropriate column names
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[] {
                "Segment",
                "Waiting time",
                "Waiting length",
                "Crossing time"
        });

        // Add each Statistics object to the table
        for (Statistics stats : statistics) {
            tableModel.addRow(new Object[] {
                    stats.getSegmentNumber(),
                    stats.getTotalWaitingTime(),
                    stats.getTotalWaitingLength(),
                    stats.getAverageCrossingTime()
            });
        }

        return tableModel;
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
       // Statistics stats1 = new Statistics(1,0,0,0,0);
        //stats1.getTotalCo2Emission();
        	
    }
	/*
	 * public List<Statistics> populateStatistics(List<Vehicle> vehicles) {
	 * List<Statistics> statisticsList = new ArrayList<>();
	 * 
	 * // Iterating over the segments, creating a Statistics object for each segment
	 * for (int i = 1; i <= 4; i++) { int totalWaitingTime = 0; int
	 * totalCrossingTime = 0; Statistics statistics = new Statistics(i,
	 * totalWaitingTime, 0, totalCrossingTime); Integer.toString(i); for (Vehicle
	 * vehicle : vehicles) { if (vehicle.getSegmentNumber().equals("S"+i)) {
	 * System.out.println("Segment gotten"); if
	 * (vehicle.getVehicleStatus().equals("waiting")) { totalWaitingTime +=
	 * vehicle.getCrossingTime(); statistics.setTotalWaitingLength(vehicles); } else
	 * if (vehicle.getVehicleStatus().equals("crossed")) { totalCrossingTime +=
	 * vehicle.getCrossingTime(); } }
	 * 
	 * } statistics.setTotalWaitingLength(totalWaitingLength);
	 * statistics.setTotalWaitingTime(totalWaitingTime);
	 * statistics.setTotalCrossingTime(totalCrossingTime);
	 * statisticsList.add(statistics); }
	 * 
	 * return statisticsList; }
	 */
    


	/*
	 * public static void main(String[] args) { new GUI(); }
	 */
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
	 * e.printStackTrace(); } 
	 * 
	 * //implementing GUI for Vehicle display section
	 * String[] headers = {"Vehicle", "Type", "Crossing", "Direction", "Length",
	 * "Emission", "Status", "Segment"}; 
	 * vehicle_label = new JLabel("Vehicles",
	 * SwingConstants.CENTER); vehicle_label.setBounds(10, 0, 450, 30);
	 * vehicle_table = new JTable(data.toArray(new String[data.size()][0]),
	 * headers); 
	 * vehicle_table.setBounds(10, 0, 440, 180);
	 * 
	 * JScrollPane vehicle_scrollPane = new JScrollPane(vehicle_table);
	 * vehicle_scrollPane.setBounds(10, 30, 450, 200); 
	 * panel = new JPanel(new
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

}
