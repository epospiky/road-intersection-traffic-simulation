package road_traffic_simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;
import java.util.Queue;

public class Vehicle_manager extends Thread {
    private AddVehicle model;
    private GUI view;
    Signal trafficLight;

    public Vehicle_manager(AddVehicle model, GUI view) {
        this.model = model;
        this.view = view;

        // Register the view as an observer of the model
        model.addObserver(view);

        view.getAddButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                    String[] vida = view.getTableinfo();
                    model.addVehicleGUI(Integer.parseInt(vida[0]), vida[1],
                            vida[2].charAt(0),
                            Double.parseDouble(vida[3]), vida[4].charAt(0),
                            Double.parseDouble(vida[5]),
                            Double.parseDouble(vida[6]));
               
            }
        });

    }

    public void loadData(String fileName) throws Exception {
        try {
            model.loadDataFromCSV(fileName);
        } catch (IOException e) {
            System.err.println("Error loading data from CSV file: " + e.getMessage());
        }
    }


    public void loadDataInter(String fileName) {
        model.Loaddataintersection(fileName);
    }

    public void showView() {
        view.show();
    }

    public void threadrun() {
        boolean newphase = false;
        Vehicle_logs.getInstance().addEntry("\tSimulation Started");
        // Initial state of traffic light is set to GREEN
        trafficLight = new Signal("green");
        int sigtest = 0;
        long waiting = 0;
        Queue<Intersection_manager> intersection = model.getIntersection();
        model.starttime();

        while (true) {
            model.randomGeneration();
            model.randomGeneration();
            model.randomGeneration();
            Intersection_manager currentsection = intersection.poll();
            Map<Character, Queue<Vehicle>> allvehicle = model.getVehicleQueue();
            Queue<Vehicle> tempveh = allvehicle.get(currentsection.getSegment_in());
            synchronized (this) {
                for (Vehicle vc : tempveh) {

                    if (!vc.isCrossedinB()) {
                        if (sigtest % 4 == 0) {
                            trafficLight.setState("red");
                        }

                        waiting = (long) vc.getCrossingTime() * 1000;
                        if (trafficLight.getLight().equals("red")) {
                            waiting += 10000;
                        }
                        if (trafficLight.getLight().equals("red")) {
                            trafficLight.setState("green");
                        }
                        model.startThread(vc, trafficLight, currentsection, waiting, newphase);

                        try {
                            wait(waiting); // wait until the thread is finished
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        model.statcal(vc, currentsection, waiting);
                        model.co2total(vc, currentsection, waiting);

                        sigtest++;
                    } else {

                        continue;
                    }

                }
                double elapsedTime = model.elapsedtime();
                double fac = (double) (elapsedTime / 1000F);
                model.addvaluePhase(
                        currentsection.getPhases(), fac);
                model.randomGeneration();

                if (intersection.isEmpty()) {
                    model.Loaddataintersection("Intersection.csv");
                    intersection = model.getIntersection();
                }

            }

        }
    }
}