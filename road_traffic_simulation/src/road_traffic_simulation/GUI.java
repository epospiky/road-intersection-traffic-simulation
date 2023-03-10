package road_traffic_simulation;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame {
	private List<Vehicle> vehicles;
	private List<Emission> emission;
	private List<Vehicle_manager> vehicle_manager;
	private List<Statistics> statistics;
	private JLabel totalEmissionLabel;
	
	//private JPanel panel;
    private JTable vehiclesTable;
    private JTable statisticsTable;
    private JTable addVehicleTable;
    private JTextField emissionField;
    private JLabel vehicleLabel;
	private JLabel addVehicleLabel;
    //private JLabel vehicle_label;

    public GUI() {

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
         vehiclesTable.setDefaultEditor(Object.class, null);
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
         statisticsTable.setDefaultEditor(Object.class, null);
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
         
         String[] add_vehicle_headings = {"Vehicle", "Type", "Crossing Time", "Direction", "Length", "Emission", "Status", "Segment"};
         Object[][] add_vehicle_data = {{"", "", "", "", "", "", "", ""}};         
         DefaultTableModel addVehicleModel = new DefaultTableModel(add_vehicle_data, add_vehicle_headings) {
             @Override
             public Class<?> getColumnClass(int columnIndex) {
                 if (columnIndex == 1) {
                     return JComboBox.class; // return JComboBox class for the Type column
                 }
                 else if(columnIndex == 3){
                	 return JComboBox.class; // return JComboBox class for the Type column
                 }
                 else if(columnIndex == 6){
                	 return JComboBox.class; // return JComboBox class for the Type column
                 }
                 else if(columnIndex == 7){
                	 return JComboBox.class; // return JComboBox class for the Type column
                 }
                 else {
                     return String.class; // return String class for all other columns
                 }
             }  
         };


   
         //Creating addVehicle Table
         this.addVehicleLabel = new JLabel("Add Vehicles");
         addVehicleLabel.setBounds(10, 320, 300, 30);

         this.addVehicleTable = new  JTable(addVehicleModel);
         addVehicleTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JComboBox<>(new String[]{"Truck", "Car", "Bus"})));
         addVehicleTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JComboBox<>(new String[]{"Straigth", "Right", "Left"})));
         addVehicleTable.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JComboBox<>(new String[]{"Waiting", "Crossed"})));
         addVehicleTable.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(new JComboBox<>(new String[]{"S1", "S2", "S3", "S4"})));
         JScrollPane addVehicleScroll = new JScrollPane();
         addVehicleScroll.setBounds(10,350, 550, 50);
         addVehicleScroll.setViewportView(addVehicleTable);
         
         JButton add =  new JButton("Add");
 		//Dimension size = add.getPreferredSize();	
 		add.setBounds(50, 570, 80, 30);
 		add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get values from table
                String vehicle = (String) addVehicleModel.getValueAt(0, 0);
                String type = (String) addVehicleModel.getValueAt(0, 1);
                String crossingTime = (String) addVehicleModel.getValueAt(0, 2);
                String direction = (String) addVehicleModel.getValueAt(0, 3);
                String crossingLength = (String) addVehicleModel.getValueAt(0, 4);
                String emission = (String) addVehicleModel.getValueAt(0, 5);
                String status = (String) addVehicleModel.getValueAt(0, 6);
                String segmentNumber = (String) addVehicleModel.getValueAt(0, 7);

                // Format values as a string

                // Write record to file
                if(!vehicle.isEmpty()&&!type.isEmpty()&&!crossingTime.isEmpty()&&!direction.isEmpty()&&!crossingLength.isEmpty()&&!emission.isEmpty()&&!status.isEmpty()&&!segmentNumber.isEmpty()) {
                    String record = vehicle + "," + type + "," + crossingTime + "," + direction + ","
                            + crossingLength + "," + emission + "," + status + "," + segmentNumber;

                    try {
                        FileWriter writer = new FileWriter("vehicles.csv", true); // true means append to existing file
                        writer.write("\n"); // Add newline character
                        writer.write(record);
                        writer.close();
                        
                        JOptionPane.showMessageDialog(GUI.this, "Record added successfully!");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(GUI.this, "Error writing record to file!");
                        ex.printStackTrace();
                    }
                }else {
                	JOptionPane.showMessageDialog(GUI.this, "Please Enter All fields!");
                }
                
            }
        });
 		JButton cancel =  new JButton("Cancel");
 		cancel.setBounds(200, 570, 80, 30);
 		cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear all input fields
            	addVehicleModel.setValueAt("", 0, 0);
            	addVehicleModel.setValueAt("", 0, 1);
            	addVehicleModel.setValueAt("", 0, 2);
            	addVehicleModel.setValueAt("", 0, 3);
            	addVehicleModel.setValueAt("", 0, 4);
            	addVehicleModel.setValueAt("", 0, 5);
            	addVehicleModel.setValueAt("", 0, 6);
            	addVehicleModel.setValueAt("", 0, 7);
            }
        });
 		JButton exit =  new JButton("Exit");
 		exit.setBounds(650, 570, 80, 30);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	List<Statistics> statistics = Statistics.populateStatistics(vehicles);
            	 try (PrintWriter writer = new PrintWriter(new FileWriter("statistics.csv"))) {
                     // Write the statistics to the file
                     for (Statistics statistic : statistics) {
                         writer.printf("%s,%s,%s\n", statistic.getSegmentNumber(), statistic.getTotalWaitingTime(), statistic.getAverageCrossingTime());
                     }
                 } catch (IOException ex) {
                     ex.printStackTrace();
                 }
                dispose(); // Close the GUI
            }
        });
         
         // Adding tables to panels with scroll panes
         JPanel Panel = new JPanel(new GridLayout(0,1));
         Panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
         Panel.setLayout(null);
         Panel.add(vehicleLabel);
         Panel.add(vehicleScroll);
         //Adding Statistics table to panel
         Panel.add(statisticsLabel);
         Panel.add(statisticsScroll);
         //Adding Add vehicle table to panel
         totalEmissionLabel = new JLabel("");
         totalEmissionLabel.setBounds(30, 500, 100, 20);
         Panel.add(totalEmissionLabel);
         
         //Adding Action buttons and emission to panel
         Panel.add(addVehicleLabel);
         Panel.add(addVehicleScroll);
 		Panel.add(add);
 		Panel.add(cancel);
 		Panel.add(exit);
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
         this.setSize(1000, 650);
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
                "Average Crossing time"
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
    public void setTotalEmissionLabel(double totalCo2Emission) {
        totalEmissionLabel.setText("Total Co2 Emission is " + totalCo2Emission);
        
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

