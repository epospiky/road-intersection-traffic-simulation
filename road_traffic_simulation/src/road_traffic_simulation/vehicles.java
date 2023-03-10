package advanced;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class vehicles extends JFrame {
	private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JTable vehicles_table;
//Reading from a file vehicles.csv
    public vehicles() {
        panel = new JPanel();
        JLabel vehicle_l = new JLabel("Vehicles", SwingConstants.CENTER);
		vehicle_l.setBounds(10, 0, 440, 30);
        String[] columnNames = {"Vehicle", "Type", "Crossing Time", "Direction", "Length", "Emission Rate", "Status", "Segment"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        try (BufferedReader br = new BufferedReader(new FileReader("E:/Masters/Advance SE/vehicles.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //GUI FOR Vehicles
        vehicles_table = new JTable(model);
        vehicles_table.setBounds(60, 60, 440,180);
        JScrollPane scrollPane = new JScrollPane(vehicles_table);
        scrollPane.setBounds(10, 30, 450, 200);
        JPanel panel = new JPanel(new GridLayout(0,1));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.setLayout(null);
		panel.add(vehicle_l);
        panel.add(scrollPane);
        this.add(panel);
        this.setTitle("Vehicle Information");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new vehicles();
    }
}

