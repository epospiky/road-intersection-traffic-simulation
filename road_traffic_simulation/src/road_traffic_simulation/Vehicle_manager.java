package road_traffic_simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Queue;

public class Vehicle_manager extends Thread {
	Signal t_signal;
    private AddVehicle model;
    private GUI view;


    public Vehicle_manager(AddVehicle Model, GUI View) {
        model = Model;
        view = View;

        // Register the view as an observer of the model
        model.addObserver(view);

        view.AddVehButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] rowData = view.tableData();
                    model.threadAddVehicle(Integer.parseInt(rowData[0]), rowData[1],
                            rowData[2].charAt(0),
                            Double.parseDouble(rowData[3]), rowData[4].charAt(0),
                            Double.parseDouble(rowData[5]),
                            Double.parseDouble(rowData[6]));
                } catch (NumberFormatException ex) {
                    // Handling nad exception caused by invalid number format
                    System.err.println("Invalid number format: " + ex.getMessage());
                } catch (ArrayIndexOutOfBoundsException ex) {
                    // Handling an exception caused by accessing an invalid array index
                    System.err.println("Array index out of bounds: " + ex.getMessage());
                } catch (Exception ex) {
                    System.err.println("An error occurred: " + ex.getMessage());
                }
            }

        });

    }

    public void loadData(String fileName) throws Exception {
        try {
            model.loadCSV(fileName);
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }


    public void getIntersectionData(String fileName) throws FileNotFoundException {
        try {
            model.intersectionData(fileName);
        } catch (Exception ex) {
            System.err.println("An error occurred: " + ex.getMessage());
        }
    }



    public void threadStart() {
        boolean phase = false;
        t_signal = new Signal("green");
        int t_sig = 0;
        long sig_wait = 0;
        Queue<Intersection_manager> intersct = model.intersectNumber();
        model.startExecTime();

        while (true) {
            model.random_gen();
            model.random_gen();
            model.random_gen();
            Intersection_manager currentsection = intersct.poll();
            Map<Character, Queue<Vehicle>> vehicls = model.getVehicleQueue();
            Queue<Vehicle> t_vehichle = vehicls.get(currentsection.getSegment());
            synchronized (this) {
                for (Vehicle vehc : t_vehichle) {

                    if (!vehc.hasCrossed()) {
                        if (t_sig % 4 == 0) {
                            t_signal.setLight("red");
                        }

                        sig_wait = (long) vehc.getCrossingTime() * 1000;
                        if (t_signal.getLight().equals("red")) {
                            sig_wait += 10000;
                        }
                        if (t_signal.getLight().equals("red")) {
                            t_signal.setLight("green");
                        }
                        model.startThread(vehc, t_signal, currentsection, sig_wait, phase);

                        try {
                            wait(sig_wait); 
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        model.calStatistics(vehc, currentsection, sig_wait);
                        model.co2total(vehc, currentsection, sig_wait);

                        t_sig++;
                    } else {

                        continue;
                    }

                }
                double f_time = model.endExecTime();
                double fac = (double) (f_time / 1000F);
                model.addvaluePhase(
                        currentsection.getPhases(), fac);
                model.random_gen();

                if (intersct.isEmpty()) {
                    model.intersectionData("Intersection.csv");
                    intersct = model.intersectNumber();
                }

            }

        }
    }
}