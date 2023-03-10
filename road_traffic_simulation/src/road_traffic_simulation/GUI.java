package road_traffic_simulation;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame {
	private List<Vehicle> vehicles;
	private List<Emission> emission;
	private List<Vehicle_manager> vehicle_manager;
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
         String[] statisticsColumnNames = { "Segment", "Waiting time", "Waiting length","Crossing time" };
 		 JLabel statisticsLabel = new JLabel("Statistics", SwingConstants.CENTER);
 		 statisticsLabel.setBounds(600, 0, 350, 30);
         this.statisticsTable = new JTable(statisticsData, statisticsColumnNames);
         statisticsTable.setBounds(500, 0, 300, 150);
         JScrollPane statisticsScroll = new JScrollPane(); 
         statisticsScroll.setBounds(600, 30, 350, 100);
         statisticsScroll.setViewportView(statisticsTable);
         
         
         //Creating co2 field
         Emission emi = new Emission(0,0);
         JLabel co2_l = new JLabel("CO2", SwingConstants.CENTER); 
         co2_l.setBounds(750,150, 50, 30);
         double totalCo2Emission = emi.getTotalCo2Emission();
         //System.out.println(totalCo2Emission);
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
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] fields = line.split(",");
                    String plateNumber = fields[0];
                    String vehicleType = fields[1];
                    int crossingTime = Integer.parseInt(fields[2]);
                    String direction = fields[3];
                    double waitingLength = Double.parseDouble(fields[4]);
                    int co2Emission = Integer.parseInt(fields[5]);
                    String vehicleStatus = fields[6];
                    String segmentNumber = fields[7];
                    Vehicle vehicle = new Vehicle(plateNumber, vehicleType, crossingTime, direction, 
                                                   waitingLength, co2Emission, vehicleStatus, segmentNumber);
                    vehicles.add(vehicle);
                } catch (NumberFormatException e) {
                    // handlig invalid integer or double values
                    System.err.println("Error: Invalid number format in line: " + line);
                } catch (ArrayIndexOutOfBoundsException e) {
                    // Handling mising or incorrect number of fields
                    System.err.println("Error: Invalid number of fields in line: " + line);
                } catch (Exception e) {
                    // Handling any other exception
                    System.err.println("Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            // Handling file I/O exception
            System.err.println("Error: Unable to read file: " + filename);
            throw e;
        }
        return vehicles;
    }

}
