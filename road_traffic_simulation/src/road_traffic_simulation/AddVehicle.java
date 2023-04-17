package road_traffic_simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class AddVehicle extends Observable implements Runnable {
    private Map<Character, Queue<Vehicle>> allvehicle;
    private Queue<Intersection_manager> intersection;
    private Queue<Vehicle> vehicleEast;
    private Queue<Vehicle> vehicleWest;
    private Queue<Vehicle> vehicleNorth;
    private ArrayList<Double[]> phases;
    int hh = 0, pl = 0, dblin = 0;
    Double totalemmsion;
    private Queue<Vehicle> vehicleSouth;
    Thread myThread;
    long startTime, elptime;
    boolean cont = true;
    Map<Character, List<Double[]>> segstat;

    Double crossingTime, waitingTime, waitingLength, totalCo2;
    List<Double[]> dummystat = new ArrayList<>();
    private static final DecimalFormat decon = new DecimalFormat("0.00");

    public AddVehicle() {
        vehicleEast = new LinkedList<>();
        vehicleWest = new LinkedList<>();
        vehicleNorth = new LinkedList<>();
        vehicleSouth = new LinkedList<>();
        allvehicle = new HashMap<>();
        intersection = new LinkedList<>();
        phases = new ArrayList<>();
        Double[] arrayd = { 0.0, 0.0, 0.0 };
        dummystat.add(arrayd);
        dummystat.set(0, arrayd);
        segstat = new HashMap<>();
        segstat.put('1', dummystat);
        segstat.put('2', dummystat);
        segstat.put('3', dummystat);
        segstat.put('4', dummystat);
        totalemmsion = 0.0;

    }

    // Reading vehicle csv file and adding it to stack
    public void loadDataFromCSV(String filename) throws Exception {

            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line = reader.readLine(); // Skip header line
                while ((line = reader.readLine()) != null) {
                    String[] temp = line.split(",");
                    if (temp[2].charAt(0) == '1' || temp[2].charAt(0) == '2' || temp[2].charAt(0) == '3'
                            || temp[2].charAt(0) == '4') {
                        if (temp[4].charAt(0) == 'S' || temp[4].charAt(0) == 'L'
                                || temp[4].charAt(0) == 'R'
                                || temp[4].charAt(0) == 'B') {
                            Vehicle vhi = new Vehicle(Integer.parseInt(temp[0]), temp[1], temp[2].charAt(0),
                                    Double.parseDouble(temp[3]), temp[4].charAt(0), Boolean.parseBoolean(temp[5]),
                                    Double.parseDouble(temp[6]), Double.parseDouble(temp[7]));
                            if (temp[2].equals("1")) {

                                vehicleEast.add(vhi);
                            } else if (temp[2].equals("2")) {

                                vehicleWest.add(vhi);
                            } else if (temp[2].equals("3")) {

                                vehicleNorth.add(vhi);
                            } else if (temp[2].equals("4")) {

                                vehicleSouth.add(vhi);
                            }
                        } 
                    } 
                }
                reader.close();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            getVehicleQueue();
            Change(allvehicle);

        
    }

    // Initializing and adding vehicle class from the data obtained from gui
    public void addVehicleGUI( int num, String typ, char in_s, double cross_time, char direct_to, double leng,
            double co2) {

        if (in_s == '1' || in_s == '2' || in_s == '3' || in_s == '4') {
            if (direct_to == 'L' || direct_to == 'R'
                    || direct_to == 'B' || direct_to == 'S') {
                Vehicle fc = new Vehicle( num, typ, in_s, cross_time, direct_to, false, leng, co2);

                if (in_s == '1') {
                    vehicleEast.add(fc);

                } else if (in_s == '2') {

                    vehicleWest.add(fc);

                } else if (in_s == '3') {

                    vehicleNorth.add(fc);

                } else if (in_s == '4') {

                    vehicleSouth.add(fc);

                }

            } 
        }
        Vehicle_logs.getInstance().addEntry(String.format("Vehicle %d has been added to the segment", num));
        Change(allvehicle);

    }

    // returning all vehicles data
    public Map<Character, Queue<Vehicle>> getVehicleQueue(){

        allvehicle.put('1', vehicleEast);
        allvehicle.put('2', vehicleWest);
        allvehicle.put('3', vehicleNorth);
        allvehicle.put('4', vehicleSouth);

        return allvehicle;
    }

    public void Change(Object obj) {
        setChanged();
        notifyObservers(obj);
    }

    // Reading intersection csv file and adding it to stack
    public void Loaddataintersection(String filename) {
        String inter = "";
        int i = 0;
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(filename));
            while ((inter = bfr.readLine()) != null) {
                if (i == 0) {
                    i++;
                    continue;
                }
                String[] temp = inter.split(",");
                Intersection_manager intersection1 = new Intersection_manager(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]),
                        temp[2].charAt(0),
                        temp[3].charAt(0), temp[4].charAt(0));
                intersection.add(intersection1);
            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // calculatiing emmision
    public double calCo2() {
        totalCo2 = 0.0;
        for (Vehicle vh : vehicleEast) {
            totalCo2 += vh.getCrossingTime();
        }
        for (Vehicle vh : vehicleWest) {
            totalCo2 += vh.getCrossingTime();
        }
        for (Vehicle vh : vehicleNorth) {
            totalCo2 += vh.getCrossingTime();
        }
        for (Vehicle vh : vehicleSouth) {
            totalCo2 += vh.getCrossingTime();
        }

        return totalCo2;
    }

    public void segNill() {
        crossingTime = 0.0;
        waitingLength = 0.0;
        waitingTime = 0.0;
    }

    // initiation
    public void starttime() {
        startTime = System.currentTimeMillis();
    }

    public long elapsedtime() {
        elptime = System.currentTimeMillis() - startTime;
        return elptime;
    }

    // add phases value
    public void addvaluePhase(double numb, double secval) {
        phases.add(new Double[] { numb,
                Double.parseDouble(String.format("%.2f", secval)) });
        Change(phases);
    }

    // random creation of vehicle instances
    public void randomGeneration(){

        String[] typearray = { "Bus", "Car", "Truck" };
        // Used ThreadLocalRandom class inorder to make it thread-safe while generating
        // Random number values
        // rnd variable for generating random int values
        int rnd = ThreadLocalRandom.current().nextInt(typearray.length);
        String type = typearray[rnd];

        int plateno = 2000000 + pl++;
        char[] segmentarray = { '1', '2', '3', '4' };
        rnd = ThreadLocalRandom.current().nextInt(segmentarray.length);
        char seg = segmentarray[rnd];
        double crossigntime = ThreadLocalRandom.current().nextDouble(10, 25);
        crossigntime = Double.parseDouble(decon.format(crossigntime));
        while (seg == segmentarray[rnd]) {
            rnd = ThreadLocalRandom.current().nextInt(segmentarray.length);
        }
        char segto = segmentarray[rnd];
        double waitlen = ThreadLocalRandom.current().nextDouble(10, 30);
        waitlen = Double.parseDouble(decon.format(waitlen));
        double co2eem = ThreadLocalRandom.current().nextDouble(5, 34);
        co2eem = Double.parseDouble(decon.format(co2eem));
        Vehicle_logs.getInstance().addEntry(String.format("New vehile %d has been generated", plateno));
        addVehicleGUI( plateno, type, seg, crossigntime, segto, waitlen, co2eem);

    }

    // stat calculations
    public void statcal(Vehicle curveh, Intersection_manager cursection, long waiting) {
        List<Double[]> dummy = segstat.get(cursection.getSegment_in());
        Double[] vbv = dummy.get(0);

        double wl = vbv[1];
        double ct = vbv[2];
        double elp = elapsedtime();
        elp = (double) (elp / 1000F);
        Double[] arrayd = { elp, wl + curveh.getWaitingLength(), ct + waiting / 1000 };
        List<Double[]> statdum = new ArrayList<>();
        statdum.add(arrayd);
        statdum.set(0, arrayd);
        segstat.put(cursection.getSegment_in(), statdum);
        Change(segstat);
    }

    public void co2total(Vehicle curveh, Intersection_manager cursection, long waiting) {
        double totlwait = elapsedtime();
        double emmision = curveh.getCo2Emission();
        String type = curveh.getVehicleType();
        double typ = 1.0;
        totlwait = (double) (totlwait / 1000F);

        if (type.equals("Car")) {
            typ = 1.8;
        }
        else if(type.equals("Bus")) {
        	typ = 2.2;
        } 
        else if (type.equals("Truck")) {
            typ = 2.9;
        }
        totalemmsion += ((waiting / 1000) * emmision) + (totlwait / emmision) * typ;
        Change(totalemmsion);
    }

    // threading
    public void startThread(Vehicle curveh, Signal trafficLight, Intersection_manager cursection, long waiting,
            boolean newphase) {

        myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Do something with the parameter passed in

                long elapsedTime;

                synchronized (this) {

                    if (cursection.getDirection_1() == curveh.getDirection()
                            || cursection.getDirection_2() == curveh.getDirection()) {

                        if (trafficLight.getLight().equals("green")) {

                            try {
                                wait(waiting); // wait until the thread is finished
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        } else {
                            try {
                                wait(waiting); // wait until the thread is finished
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }

                        elapsedTime = System.currentTimeMillis() - startTime;
                        double elp = (double) (elapsedTime / 1000F);

                        curveh.setCrossStatus(true);
                        Vehicle_logs.getInstance().addEntry(
                                String.format("Vehicle %d has crossed the intersection", curveh.getVehicle_number()));
                        Change(allvehicle);

                    }
                }

            }
        });

        myThread.start();

    }

    public Queue<Intersection_manager> getIntersection() {
        return intersection;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

}