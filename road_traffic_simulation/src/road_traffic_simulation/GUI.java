package road_traffic_simulation;


import java.awt.BorderLayout;
import java.awt.Color;
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
    private DefaultTableModel vehicleModel,phaseModel, statsModel;
    private JTable vehicleTable, phaseTable, statisticsTable,addVehicleTable;
    private JButton add, cancel, exit;    
    JTextField emissionField;
    Collection<Vehicle> vehicleList;


    public GUI() {
        Jframe = new JFrame();
        Jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Jframe.setTitle("Road Intersection Traffic Simulation"); 
        Jframe.setSize(1100, 600); 
        Jframe.setLocationRelativeTo(null); 
        Jframe.setLayout(new BorderLayout());

        // Creating vehicle Table
        String[] vehicleTableHeader = { "Vehicle no", "Type", "Segment", "Cross Time", "Direction", "Status", "Length" };

        vehicleModel = new DefaultTableModel();
        vehicleModel.setColumnIdentifiers(vehicleTableHeader);
        JLabel vehicleLabel = new JLabel("Vehicles", SwingConstants.CENTER);
		vehicleLabel.setBounds(10, 0, 450, 30);
        vehicleTable = new JTable();
        vehicleTable.setModel(vehicleModel);
        vehicleTable.getTableHeader().setReorderingAllowed(false); 
        vehicleTable.setAutoCreateRowSorter(true);
        vehicleTable.setEnabled(false);
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

        //Creating Statistics table
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
        statisticsTable.setAutoCreateRowSorter(true); 
        JScrollPane statisticsScroll = new JScrollPane(); 
        statisticsScroll.setBounds(700, 30, 350, 100);
        statisticsScroll.setViewportView(statisticsTable);
        
        
        //Creating co2 emission field
        JLabel co2_l = new JLabel("CO2:", SwingConstants.CENTER); 
        co2_l.setBounds(910,220, 50, 30);
        String co2Val = "0.0";
        emissionField = new JTextField();
        emissionField.setText(co2Val);
        emissionField.setEditable(false);
        emissionField.setBounds(900,250, 50, 30);
        JLabel co2_unit = new JLabel("kg"); 
        co2_unit.setBounds(950,250, 50, 30);
        
        // Info label
        JLabel infoLabel = new JLabel("Please exercise some patience for the simulation to start...");
        Color inforColor = new Color(255, 150, 150);
        infoLabel.setForeground(inforColor);
        infoLabel.setBounds(20, 300, 350, 30);
        
        // Creating Add vehicle table
        String[] addTableHeader = { "Vehicle no", "Type", "Segment", "Crossing Time", "Direction", "Length", "Co2" };
        Object[][] addTableData = { { "565432", "Car", "1", "15.8", "L", "9.0", "19.0" } };
        
        JComboBox<String> typeOpt = new JComboBox<String>();
        typeOpt.addItem("Truck");
        typeOpt.addItem("Car");
        typeOpt.addItem("Bus");
        JComboBox<String> segOpt = new JComboBox<String>();
        segOpt.addItem("1");
        segOpt.addItem("2");
        segOpt.addItem("3");
        segOpt.addItem("4");
        JComboBox<String> dirOpt = new JComboBox<String>();
        dirOpt.addItem("L");
        dirOpt.addItem("R");
        dirOpt.addItem("S");
        dirOpt.addItem("B");
        addVehicleTable = new JTable(addTableData, addTableHeader);
        JLabel addVehicleLabel = new JLabel("Add Vehicles", SwingConstants.CENTER);
        addVehicleLabel.setBounds(10, 370, 300, 30);
        TableColumn typeCol = addVehicleTable.getColumnModel().getColumn(1);
        typeCol.setCellEditor(new DefaultCellEditor(typeOpt));
        TableColumn segCol = addVehicleTable.getColumnModel().getColumn(2);
        segCol.setCellEditor(new DefaultCellEditor(segOpt));
        TableColumn dirCol = addVehicleTable.getColumnModel().getColumn(4);
        dirCol.setCellEditor(new DefaultCellEditor(dirOpt));
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
        Jpanel = new JPanel(new GridLayout(0,1));
        Jpanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        Jpanel.setLayout(null);
        Jpanel.add(vehicleLabel);
		Jpanel.add(vehicleScroll);
		Jpanel.add(phaseLabel);
		Jpanel.add(phaseScroll);
		Jpanel.add(statisticsLabel);
		Jpanel.add(statisticsScroll);
		Jpanel.add(infoLabel);
		Jpanel.add(addVehicleLabel);
		Jpanel.add(addVehicleScroll);
		Jpanel.add(co2_l);
		Jpanel.add(emissionField);
		Jpanel.add(co2_unit);
		Jpanel.add(add);
		Jpanel.add(cancel);
		Jpanel.add(exit);
        
		// Adding panel to Jframe
        Jframe.add(Jpanel, BorderLayout.CENTER);
        Jframe.setResizable(false);
        Jframe.setVisible(true);
        // Exit button action
        exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                                try {
                                        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                                        	Vehicle_logs.getInstance().writeLogsToFile("output.txt");
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



//Loading vehicles from file
    @SuppressWarnings("unchecked")
	public void update(Observable obs, Object arg) {

            Queue<Vehicle> updatedVeh;
            Double vehicleList;
            if (obs instanceof AddVehicle) {
                    if (arg instanceof Map) {

                            Map<?, ?> vehicles = (Map<?, ?>) arg;

                            if (vehicles.values().iterator().next() instanceof Queue<?>) {
                                    vehicleModel.setRowCount(0);
                                    for (Entry<?, ?> vehicl : vehicles.entrySet()) {

                                            updatedVeh = (Queue<Vehicle>) vehicl.getValue();

                                            for (Vehicle vehicle : updatedVeh) {

                                                    Object[] rowData = { "L" + vehicle.getVehicle_number(), vehicle.getVehicleType(),
                                                                    vehicle.getSegmentNumber(),
                                                                    vehicle.getCrossingTime(),
                                                                    vehicle.getDirection(),
                                                                    vehicle.getCrossStatus(),
                                                                    vehicle.getWaitingLength(),
                                                                    vehicle.getCo2Emission() };
                                                    vehicleModel.addRow(rowData);

                                            }
                                    }
                            } else if (vehicles.values().iterator().next() instanceof List<?>) {

                                    statsModel.setRowCount(0);
                                    for (Entry<?, ?> statis : vehicles.entrySet()) {
                                            List<Double[]> statData = (List<Double[]>) statis.getValue();
                                            Double[] statDataVal = statData.get(0);

                                            Object[] rowData = { statis.getKey(), statDataVal[0] + " s", statDataVal[1] + "m",
                                                            statDataVal[2] + " s" };
                                            statsModel.addRow(rowData);

                                    }
                            }

                    }

                    if (arg instanceof ArrayList) {
                            vehicleList = 0.0;
                            ArrayList<Double[]> intersect = (ArrayList<Double[]>) arg;
                            phaseModel.setRowCount(0);
                            for (Double[] intersection : intersect) {
                                    double dvl = intersection[1] - vehicleList;
                                    Object[] rowData = { intersection[0].intValue(),
                                                    Double.parseDouble(String.format("%.2f", dvl)) };
                                    vehicleList = intersection[1];
                                    phaseModel.addRow(rowData);
                            }
                    }

                    if (arg instanceof Double) {

                            Double intersect = (Double) arg;
                            intersect = intersect / 1000;
                            String statDataVal = intersect.toString();

                            emissionField.setText(String.format("%.2f", intersect));
                    }

            }

    }

    public void show() {
            Jframe.setVisible(true);
    }

//getting report
    public String getContent(TableModel vehicles, TableModel phases, TableModel statistics, String co2) {
            String content = "\n\t\t\t\tSTATISTICS REPORT\n\n\n";

            content += String.format("\n%s\n",  getstat(statistics));
            content += String.format("Average Waiting Time\t:\t%s s\n\n", getAvgWait(statistics));
            content += String.format("Total Emission \t\t:\t%s KG\n\n", co2.toString());
            content += "Total vehicles crossed :" + "\t" +
                    get_veh_count(vehicles) + "\n\n";
            return content;

    }

    //getting number of crossed vehicles
    public String get_veh_count(TableModel tab_for_count) {
            int crossed_vehicles = 0;
            for (int i = 0; i < tab_for_count.getRowCount(); i++) {
                    if (tab_for_count.getValueAt(i, 5).toString().equals("Crossed")) 
                    {
                            crossed_vehicles += 1;
                    }
            }
            return Integer.toString(crossed_vehicles);
    }

    @SuppressWarnings("unused")
	private String getstat(TableModel statistics) {
            String statData = "Segment\t\t" + "Total Waiting Time\t"
                            + "Total Waiting Length\t"
                            + "Total Crossing Time";
            String segme = null;
            if (statistics.getRowCount() > 0) {
                    for (int i = 0; i < statistics.getRowCount(); i++) {
                            if (statistics.getValueAt(i, 0).equals('1'))
                                    segme = "S1";
                            else if (statistics.getValueAt(i, 0).equals('2'))
                                    segme = "S2";
                            else if (statistics.getValueAt(i, 0).equals('3'))
                                    segme = "S3";
                            else if (statistics.getValueAt(i, 0).equals('4'))
                                    segme = "S4";
                            statData += String.format("\n%-7s %18s %20s %24s\n", segme,
                                            statistics.getValueAt(i, 1).toString(),
                                            statistics.getValueAt(i, 2).toString(),
                                            statistics.getValueAt(i, 3).toString());
                    }
            } else
                    statData = "\tNo statistics data\n";
            return statData;
    }

    private Object getAvgWait(TableModel statistics) {
            double avgWait = 0.0;
            for (int i = 0; i < statistics.getRowCount(); i++) {
                    avgWait += Double.parseDouble(statistics.getValueAt(i, 1).toString().split(" ")[0]);
            }
            avgWait = avgWait / statistics.getRowCount();
            return avgWait;
    }


	public void setTotalEmissionLabel(double totalCo2Emission) {
		// TODO Auto-generated method stub
		
	}


    public String[] tableData() {

            String data0 = addVehicleData(addVehicleTable, 0, 0);
            String data1 = addVehicleData(addVehicleTable, 0, 1);
            String data2 = addVehicleData(addVehicleTable, 0, 2);
            String data3 = addVehicleData(addVehicleTable, 0, 3);
            String data4 = addVehicleData(addVehicleTable, 0, 4);
            String data5 = addVehicleData(addVehicleTable, 0, 5);
            String data6 = addVehicleData(addVehicleTable, 0, 6);

            data0.getClass().equals(String.class);

            String[] dataCol = new String[] { data0, data1, data2, data3, data4,
                            data5, data6 };
            return dataCol;

    }
    public String addVehicleData(JTable addVehicleTable, int i, int j) {
            return String.valueOf(addVehicleTable.getModel().getValueAt(i, j));
    }



    public JButton getAddButton() {
        return add;
    }




}

