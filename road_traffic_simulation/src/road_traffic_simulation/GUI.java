package road_traffic_simulation;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class GUI implements Observer  {
    JFrame Jframe;
    JPanel Jpanel;
    private DefaultTableModel tableModel,phaseModel, statsModel;
    private JTable vehicleTable, phaseTable, statisticsTable;
    JTable addVehicleTable;
    private JButton add, exit, cancel;
    Collection<Vehicle> vehicleList;
    JTextField emissionField;

    public GUI() {
        Jframe = new JFrame();
        
        Jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // program to be exited if window is
                                                                           // closed
        Jframe.setTitle("Road Intersection Traffic Simulation"); // title of main window
        Jframe.setSize(1100, 600); // seting the width and height of the main window
        Jframe.setLocationRelativeTo(null); // position the nframe in the middle of the screen
        Jframe.setLayout(new BorderLayout()); // initiate layout of nframe

        // Creating vehicle Table
        String[] veh_tbl_head = { "Vehicle no", "Type", "Segment", "Cross time", "Direction", "Status", "Length" };

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(veh_tbl_head);
        JLabel vehicleLabel = new JLabel("Vehicles", SwingConstants.CENTER);
		vehicleLabel.setBounds(10, 0, 450, 30);
        vehicleTable = new JTable();
        vehicleTable.setModel(tableModel);

        vehicleTable.getTableHeader().setReorderingAllowed(false); // setting coumn reordering order as false
        vehicleTable.setAutoCreateRowSorter(true); // setting row sorting order as true for the table
        vehicleTable.setEnabled(false); // Setting the table's data non-editable
        JScrollPane vehicleScroll = new JScrollPane(vehicleTable);
        //vehicleScroll.setViewportView();
        vehicleScroll.setBounds(10, 30, 450, 200);

        //Creating phase table
        String[] phaseTableHeader = { "Phase", "Duration" };
        phaseModel = new DefaultTableModel();
        phaseModel.setColumnIdentifiers(phaseTableHeader);
        phaseTable = new JTable();
		JLabel phaseLabel = new JLabel("Statistics", SwingConstants.CENTER);
		phaseLabel.setBounds(500, 0, 150, 30);
        phaseTable.setModel(phaseModel);
        phaseTable.getTableHeader().setReorderingAllowed(false);
        phaseTable.setEnabled(false);
        JScrollPane phaseScroll = new JScrollPane(); 
        phaseScroll.setBounds(500, 30, 150, 200);
        phaseScroll.setViewportView(phaseTable);

        // 3.Statistics table
        String[] statsTableHeader = { "Segment", "Waiting time", "Waiting Length",
                        "Cross time" };
        statsModel = new DefaultTableModel();
        statsModel.setColumnIdentifiers(statsTableHeader);
        statisticsTable = new JTable();
		JLabel statisticsLabel = new JLabel("Statistics", SwingConstants.CENTER);
		statisticsLabel.setBounds(600, 0, 350, 30);
        statisticsTable.setModel(statsModel);

        statisticsTable.getTableHeader().setReorderingAllowed(false);
        statisticsTable.setEnabled(false);
        statisticsTable.setAutoCreateRowSorter(true); // enables sorting
        JScrollPane statisticsScroll = new JScrollPane(); 
        statisticsScroll.setBounds(700, 30, 350, 100);
        statisticsScroll.setViewportView(statisticsTable);
        
        

        
        //Creating co2 field
        JLabel co2_l = new JLabel("CO2:", SwingConstants.CENTER); 
        co2_l.setBounds(910,220, 50, 30);
        String tco2 = "0.0";
        emissionField = new JTextField();
        emissionField.setText(tco2);
        emissionField.setEditable(false);
        emissionField.setBounds(900,250, 50, 30);
        JLabel co2_unit = new JLabel("kg"); 
        co2_unit.setBounds(950,250, 50, 30);
        


        // Creating Add vehicle table
        String[] add_tbl_head = { "Vehicle no", "Type", "Segment", "Crossing Time", "Direction", "Length", "Co2" };
        Object[][] add_data = { { "23232", "car", "1", "32.9", "L", "12.0", "32.0" } };

        JComboBox<String> segOpt = new JComboBox<String>();
        segOpt.addItem("1");
        segOpt.addItem("2");
        segOpt.addItem("3");
        segOpt.addItem("4");
        JComboBox<String> typeOpt = new JComboBox<String>();
        typeOpt.addItem("Truck");
        typeOpt.addItem("Car");
        typeOpt.addItem("Bus");
        addVehicleTable = new JTable(add_data, add_tbl_head);
        JLabel addVehicleLabel = new JLabel("Add Vehicles", SwingConstants.CENTER);
        addVehicleLabel.setBounds(10, 370, 300, 30);
        TableColumn typeCol = addVehicleTable.getColumnModel().getColumn(1);
        typeCol.setCellEditor(new DefaultCellEditor(typeOpt));
        TableColumn segCol = addVehicleTable.getColumnModel().getColumn(2);
        segCol.setCellEditor(new DefaultCellEditor(segOpt));
        JScrollPane addVehicleScroll = new JScrollPane(); 
        addVehicleScroll.setBounds(10, 400, 450, 50);
        addVehicleScroll.setViewportView(addVehicleTable);

        // Creating Buttons
        add = new JButton("Add");
        add.setBounds(50, 500, 80, 30);
        cancel =  new JButton("Cancel");
 		cancel.setBounds(200, 500, 80, 30);
        exit = new JButton("Exit");
        exit.setBounds(650, 500, 80, 30);
 		

        // Adding all components to Jpanel
        JPanel jPanel2 = new JPanel(new GridLayout(0,1));
        jPanel2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        jPanel2.setLayout(null);
        jPanel2.add(vehicleLabel);
		jPanel2.add(vehicleScroll);
		jPanel2.add(phaseLabel);
		jPanel2.add(phaseScroll);
		jPanel2.add(statisticsLabel);
		jPanel2.add(statisticsScroll);
		jPanel2.add(addVehicleLabel);
		jPanel2.add(addVehicleScroll);
		jPanel2.add(co2_l);
		jPanel2.add(emissionField);
		jPanel2.add(co2_unit);
		jPanel2.add(add);
		jPanel2.add(cancel);
		jPanel2.add(exit);
        
		// Adding panel to Jframe
        Jframe.add(jPanel2, BorderLayout.CENTER);
        Jframe.setResizable(false);
        Jframe.setVisible(true);
        // Exit button action
        exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                                try {
                                        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                                        	Vehicle_logs.getInstance().writeLogsToFile("log.txt");
                                        }));
                                        FileWriter writer = new FileWriter("statistics.txt");
                                        writer.write(getContent(vehicleTable.getModel(), phaseTable.getModel(),
                                                        statisticsTable.getModel(), emissionField.getText()));
                                        writer.close();
                                  
                                        System.exit(0);
                                } catch (IOException ex) {
                                        ex.printStackTrace();
                                }
                        
                }
        });

    	
    }



    /**
     * Function to get Vehicle Data from vehicle.csv
     */
    @SuppressWarnings("unchecked")
	public void update(Observable obs, Object arg) {

            Queue<Vehicle> upvehicle;
            Double dumv;
            if (obs instanceof AddVehicle) {
                    if (arg instanceof Map) {

                            Map<?, ?> allvehicle = (Map<?, ?>) arg;

                            if (allvehicle.values().iterator().next() instanceof Queue<?>) {
                                    tableModel.setRowCount(0);
                                    for (Entry<?, ?> veh : allvehicle.entrySet()) {

                                            upvehicle = (Queue<Vehicle>) veh.getValue();

                                            for (Vehicle vehicle : upvehicle) {

                                                    Object[] rowData = { "L" + vehicle.getVehicle_number(), vehicle.getVehicleType(),
                                                                    vehicle.getSegmentNumber(),
                                                                    vehicle.getCrossingTime(),
                                                                    vehicle.getDirection(),
                                                                    vehicle.getCrossStatus(),
                                                                    vehicle.getWaitingLength(),
                                                                    vehicle.getCo2Emission() };
                                                    tableModel.addRow(rowData);

                                            }
                                    }
                            } else if (allvehicle.values().iterator().next() instanceof List<?>) {

                                    statsModel.setRowCount(0);
                                    for (Entry<?, ?> stat : allvehicle.entrySet()) {
                                            List<Double[]> sval = (List<Double[]>) stat.getValue();
                                            Double[] dd = sval.get(0);

                                            Object[] rowData = { stat.getKey(), dd[0] + " s", dd[1],
                                                            dd[2] + " s" };
                                            statsModel.addRow(rowData);

                                    }
                            }

                    }

                    if (arg instanceof ArrayList) {
                            dumv = 0.0;
                            ArrayList<Double[]> inter = (ArrayList<Double[]>) arg;
                            phaseModel.setRowCount(0);
                            for (Double[] intersection : inter) {
                                    double dvl = intersection[1] - dumv;
                                    Object[] rowData = { intersection[0].intValue(),
                                                    Double.parseDouble(String.format("%.2f", dvl)) };
                                    dumv = intersection[1];
                                    phaseModel.addRow(rowData);
                            }
                    }

                    if (arg instanceof Double) {

                            Double inter = (Double) arg;
                            inter = inter / 1000;
                            String dd = inter.toString();

                            emissionField.setText(String.format("%.2f", inter));
                    }

            }

    }

    public void show() {
            Jframe.setVisible(true);
    }

//getting report
    public String getContent(TableModel vehicles, TableModel phases, TableModel statistics, String co2) {
            String content = "\t\t\tREPORT\n\n\n";

            content += "Number of Vehicles Crossed during Simulation" + "\t:\t" +
                            get_veh_count(vehicles) + " Vehicles\n\n";
            content += "\t\t\tStatistics of each Segment\n\t\t\t---------- -- ---- -------\n";
            content += String.format("\n%s\n", getstat(statistics));

            content += String.format("Average Waiting Time to cross\t:\t%s Seconds\n\n", getavg_wait(statistics));
            content += String.format("Total CO2 Emmision during the simulation \t\t:\t%s KG\n", co2.toString());
            return content;

    }

    //Counts for vehicle
    public String get_veh_count(TableModel mytbl) {
            int c_veh_count = 0; // Crossed vehicle count
            for (int i = 0; i < mytbl.getRowCount(); i++) {
                    if (mytbl.getValueAt(i, 5).toString().equals("crossed")) 
                    {
                            c_veh_count += 1;
                    }
            }
            return Integer.toString(c_veh_count);
    }

    private String getstat(TableModel statistics) {
            String Content = "Segment\t\t" + "Total Waiting Time\t"
                            + "Total Waiting length(vehicles)\t"
                            + "Total Crosstime";
            Content += "\n*******\t\t" + "******************\t"
                            + "******************************\t"
                            + "***************";
            String seg = null;
            if (statistics.getRowCount() > 0) {
                    for (int i = 0; i < statistics.getRowCount(); i++) {
                            if (statistics.getValueAt(i, 0).equals('S'))
                                    seg = "South";
                            else if (statistics.getValueAt(i, 0).equals('E'))
                                    seg = "East";
                            else if (statistics.getValueAt(i, 0).equals('N'))
                                    seg = "North";
                            else if (statistics.getValueAt(i, 0).equals('W'))
                                    seg = "West";
                            Content += String.format("\n%-7s %18s %20s %24s\n", seg,
                                            statistics.getValueAt(i, 1).toString(),
                                            statistics.getValueAt(i, 2).toString(),
                                            statistics.getValueAt(i, 3).toString());
                    }
            } else
                    Content = "\tSimulation did not run long enough to produce statistics\n";
            return Content;
    }

    private Object getavg_wait(TableModel statistics) {
            double val = 0.0;
            for (int i = 0; i < statistics.getRowCount(); i++) {
                    val += Double.parseDouble(statistics.getValueAt(i, 1).toString().split(" ")[0]);
            }
            val = val / statistics.getRowCount();
            return val;
    }

    public JButton getAddButton() {
            return add;
    }



    public String[] getTableinfo() {

            String table_data_0 = GetData(addVehicleTable, 0, 0);
            String table_data_1 = GetData(addVehicleTable, 0, 1);
            String table_data_2 = GetData(addVehicleTable, 0, 2);
            String table_data_3 = GetData(addVehicleTable, 0, 3);
            String table_data_4 = GetData(addVehicleTable, 0, 4);
            String table_data_5 = GetData(addVehicleTable, 0, 5);
            String table_data_6 = GetData(addVehicleTable, 0, 6);

            table_data_0.getClass().equals(String.class);

            String[] sp = new String[] { table_data_0, table_data_1, table_data_2, table_data_3, table_data_4,
                            table_data_5, table_data_6 };
            return sp;

    }
    public String GetData(JTable addVehicleTable, int i, int j) {
            return String.valueOf(addVehicleTable.getModel().getValueAt(i, j));
    }



	public void setTotalEmissionLabel(double totalCo2Emission) {
		// TODO Auto-generated method stub
		
	}




}

