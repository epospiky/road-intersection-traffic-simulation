package advanced;
import javax.swing.*;
import java.awt.*;

public class AddVehicle extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel segmentLabel, idLabel, typeLabel, crossingTimeLabel, directionLabel, crossingStatusLabel, lengthLabel, emissionRateLabel;
    private JComboBox<String> typeComboBox, directionComboBox, segmentComboBox,statusComboBox;
    private JTextField idTextField, crossingTimeTextField, lengthTextField, emissionRateTextField;
    private JButton addButton, CancelButton;

    public AddVehicle() {
        setTitle("Vehicle Management System");
        setSize(800, 400);
        segmentLabel = new JLabel("Segment:");
        idLabel = new JLabel("ID:");
        typeLabel = new JLabel("Type:");
        crossingTimeLabel = new JLabel("Crossing Time:");
        directionLabel = new JLabel("Direction:");
        crossingStatusLabel = new JLabel("Crossing Status:");
        lengthLabel = new JLabel("Length:");
        emissionRateLabel = new JLabel("Emission Rate:");
        String[] types = {"Car", "Bus", "Truck"};
        typeComboBox = new JComboBox<>(types);
        String[] directions = {"Left", "Straight", "Right"};
        directionComboBox = new JComboBox<>(directions);
        String[] status= {"Waiting","crossed"};
        statusComboBox=new JComboBox<>(status);
        String[] segments = {"S1", "S2", "S3", "S4"};
        segmentComboBox = new JComboBox<>(segments);
        idTextField = new JTextField();
        crossingTimeTextField = new JTextField();
        lengthTextField = new JTextField();
        emissionRateTextField = new JTextField();
        addButton = new JButton("Add Vehicle");
        CancelButton = new JButton("Cancel");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor=GridBagConstraints.SOUTH;
        add(segmentLabel, gbc);
        gbc.gridx = -1;
        add(segmentComboBox, gbc);
        gbc.gridx = 2;
        add(idLabel, gbc);
        gbc.gridx = 3;
        add(idTextField, gbc);
        gbc.gridx = 4;
        add(typeLabel, gbc);
        gbc.gridx = 5;
        add(typeComboBox, gbc);
        gbc.gridx = 6;
        add(crossingTimeLabel, gbc);
        gbc.gridx = 7;
        add(crossingTimeTextField, gbc);
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(directionLabel, gbc);
        gbc.gridx = 1;
        add(directionComboBox, gbc);
        gbc.gridx = 2;
        add(crossingStatusLabel, gbc);
        gbc.gridx = 3;
        add(statusComboBox, gbc);
        gbc.gridx = 4;
        gbc.gridwidth = 2;
        add(addButton, gbc);
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(lengthLabel, gbc);
        gbc.gridx = 1;
        add(lengthTextField, gbc);
        gbc.gridx = 2;
        add(emissionRateLabel, gbc);
        gbc.gridx = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 8;
        add(emissionRateTextField, gbc);
        gbc.gridx = 0;
        gbc.gridy=4;
        add(addButton, gbc);
        gbc.gridx = 0;
        gbc.gridy=8;
        add(CancelButton, gbc);
        gbc.gridx++;
        gbc.gridy=0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        add(Box.createVerticalGlue(), gbc);
        idTextField.setPreferredSize(new Dimension(50, 25));
        crossingTimeTextField.setPreferredSize(new Dimension(50, 25));
        lengthTextField.setPreferredSize(new Dimension(50, 25));
        emissionRateTextField.setPreferredSize(new Dimension(50, 25));
      
    }

    public static void main(String[] args) {
        AddVehicle system = new AddVehicle();
        system.setVisible(true);
    }
}
