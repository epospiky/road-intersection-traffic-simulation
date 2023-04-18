package road_traffic_simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class AddVehicle extends Observable implements Runnable {
    private Map<Character, Queue<Vehicle>> vehicles;
    private Queue<Intersection_manager> intersection_manager;
    private Queue<Vehicle> segment1;
    private Queue<Vehicle> segment2;
    private Queue<Vehicle> segment3;
    private ArrayList<Double[]> phases;
    int hh = 0, veh_no = 0, dblin = 0;
    Double totalCo2Emission;
    private Queue<Vehicle> segment4;
    Thread mainThread;
    long start_t, end_t;
    boolean cont = true;
    Map<Character, List<Double[]>> segmentStat;

    Double crossingTime, waitingTime, waitingLength, totalEmmission;
    List<Double[]> initStatData = new ArrayList<>();
    private static final DecimalFormat decon = new DecimalFormat("0.00");



    // Reading and adding vehicle
    public void loadCSV(String filename) throws Exception {

            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line = reader.readLine(); // Skip header line
                while ((line = reader.readLine()) != null) {
                    String[] interData = line.split(",");
                    if (interData[2].charAt(0) == '1' || interData[2].charAt(0) == '2' || interData[2].charAt(0) == '3'
                            || interData[2].charAt(0) == '4') {
                        if (interData[4].charAt(0) == 'S' || interData[4].charAt(0) == 'L'
                                || interData[4].charAt(0) == 'R'
                                || interData[4].charAt(0) == 'B') {
                            Vehicle newVehicle = new Vehicle(Integer.parseInt(interData[0]), interData[1], interData[2].charAt(0),
                                    Double.parseDouble(interData[3]), interData[4].charAt(0), Boolean.parseBoolean(interData[5]),
                                    Double.parseDouble(interData[6]), Double.parseDouble(interData[7]));
                            if (interData[2].equals("1")) {

                                segment1.add(newVehicle);
                            } else if (interData[2].equals("2")) {

                                segment2.add(newVehicle);
                            } else if (interData[2].equals("3")) {

                                segment3.add(newVehicle);
                            } else if (interData[2].equals("4")) {

                                segment4.add(newVehicle);
                            }
                        } 
                    } 
                }
                reader.close();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            getVehicleQueue();
            Change(vehicles);

        
    }
    public AddVehicle() {
        segment1 = new LinkedList<>();
        segment2 = new LinkedList<>();
        segment3 = new LinkedList<>();
        segment4 = new LinkedList<>();
        vehicles = new HashMap<>();
        intersection_manager = new LinkedList<>();
        phases = new ArrayList<>();
        Double[] veh_arr = { 0.0, 0.0, 0.0 };
        initStatData.add(veh_arr);
        initStatData.set(0, veh_arr);
        segmentStat = new HashMap<>();
        segmentStat.put('1', initStatData);
        segmentStat.put('2', initStatData);
        segmentStat.put('3', initStatData);
        segmentStat.put('4', initStatData);
        totalCo2Emission = 0.0;

    }

    public void threadAddVehicle( int num, String veh_type, char seg_no, double cross_time, char drection, double leng,
            double co2) {

        if (seg_no == '1' || seg_no == '2' || seg_no == '3' || seg_no == '4') {
        	try {
        		if (drection == 'L' || drection == 'R'
        		|| drection == 'B' || drection == 'S') {
        		Vehicle fc = new Vehicle( num, veh_type, seg_no, cross_time, drection, false, leng, co2);
        	    if (seg_no == '1') {
        	        segment1.add(fc);

        	    } else if (seg_no == '2') {

        	        segment2.add(fc);

        	    } else if (seg_no == '3') {

        	        segment3.add(fc);

        	    } else if (seg_no == '4') {

        	        segment4.add(fc);

        	    }
        	}
        	} catch (Exception e) {
        		System.err.println("An error occurred: " + e.getMessage());
        		}
        }
        Vehicle_logs.getInstance().addToLog(String.format("Vehicle %d has been auto-generated and added to the segment collection", num));
        Change(vehicles);

    }

    // returning all vehicles data
    public Map<Character, Queue<Vehicle>> getVehicleQueue(){

        vehicles.put('1', segment1);
        vehicles.put('2', segment2);
        vehicles.put('3', segment3);
        vehicles.put('4', segment4);

        return vehicles;
    }

    public void Change(Object obj) {
        setChanged();
        notifyObservers(obj);
    }

    // Reading  and adding data from csv file
    public void intersectionData(String filename) {
        String inter = "";
        int i = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while ((inter = br.readLine()) != null) {
                if (i == 0) {
                    i++;
                    continue;
                }
                String[] interData = inter.split(",");
                Intersection_manager intersection_man = new Intersection_manager(Integer.parseInt(interData[0]), Integer.parseInt(interData[1]),
                        interData[2].charAt(0),
                        interData[3].charAt(0), interData[4].charAt(0));
                intersection_manager.add(intersection_man);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Co22 Emission
    public double calcEmission() {
        totalEmmission = 0.0;
        for (Vehicle vhicle : segment1) {
            totalEmmission += vhicle.getCrossingTime();
        }
        for (Vehicle vhicle : segment2) {
            totalEmmission += vhicle.getCrossingTime();
        }
        for (Vehicle vhicle : segment3) {
            totalEmmission += vhicle.getCrossingTime();
        }
        for (Vehicle vhicle : segment4) {
            totalEmmission += vhicle.getCrossingTime();
        }

        return totalEmmission;
    }

    public void initVal() {
        crossingTime = 0.0;
        waitingLength = 0.0;
        waitingTime = 0.0;
    }

    public void startExecTime() {
        start_t = System.currentTimeMillis();
    }

    public long endExecTime() {
        end_t = System.currentTimeMillis() - start_t;
        return end_t;
    }
    public void addvaluePhase(double ph_no, double ph_dur) {
        phases.add(new Double[] { ph_no,
                Double.parseDouble(String.format("%.2f", ph_dur)) });
        Change(phases);
    }

    // generating vehicles
    public void random_gen(){

        String[] typearray = { "Bus", "Car", "Truck" };
        int randomValue = ThreadLocalRandom.current().nextInt(typearray.length);
        String type = typearray[randomValue];
        Random randomGen = new Random();
        int vehNumber = 1000000 + randomGen.nextInt(9000000) + veh_no++;
        char[] segmentarray = { '1', '2', '3', '4' };
        randomValue = ThreadLocalRandom.current().nextInt(segmentarray.length);
        char segmt = segmentarray[randomValue];
        double crosingTime = ThreadLocalRandom.current().nextDouble(10, 25);
        crosingTime = Double.parseDouble(decon.format(crosingTime));
        while (segmt == segmentarray[randomValue]) {
            randomValue = ThreadLocalRandom.current().nextInt(segmentarray.length);
        }
        char[] dirarray = { 'L', 'R', 'S', 'B' };
        randomValue = ThreadLocalRandom.current().nextInt(dirarray.length);
        char dir = dirarray[randomValue];
        double waitingLength = ThreadLocalRandom.current().nextDouble(10, 30);
        waitingLength = Double.parseDouble(decon.format(waitingLength));
        double co2Emssion = ThreadLocalRandom.current().nextDouble(5, 34);
        co2Emssion = Double.parseDouble(decon.format(co2Emssion));
        threadAddVehicle( vehNumber, type, segmt, crosingTime, dir, waitingLength, co2Emssion);

    }

    public void calStatistics(Vehicle initVehicle, Intersection_manager pres_section, long waiting_l) {
        List<Double[]> stat_data = segmentStat.get(pres_section.getSegment());
        Double[] vhcl = stat_data.get(0);

        double wait_l = vhcl[1];
        double cross_t = vhcl[2];
        double end_T = endExecTime();
        end_T = (double) (end_T / 1000F);
        Double[] veh_arr = { end_T, wait_l + initVehicle.getWaitingLength(), cross_t + waiting_l / 1000 };
        List<Double[]> stats_data = new ArrayList<>();
        stats_data.add(veh_arr);
        stats_data.set(0, veh_arr);
        segmentStat.put(pres_section.getSegment(), stats_data);
        Change(segmentStat);
    }

    public void co2total(Vehicle initVehicle, Intersection_manager pres_section, long waiting_l) {
        double totalWait_L = endExecTime();
        double co2Emm = initVehicle.getCo2Emission();
        String type = initVehicle.getVehicleType();
        double veh_type = 1.0;
        totalWait_L = (double) (totalWait_L / 1000F);

        if (type.equals("Truck")) {
            veh_type = 3.0;
        }
        else if(type.equals("Bus")) {
        	veh_type = 2.1;
        } 
        else if (type.equals("Car")) {
            veh_type = 1.5;
        }
        totalCo2Emission += ((waiting_l / 1000) * co2Emm) + (totalWait_L / co2Emm) * veh_type;
        Change(totalCo2Emission);
    }

    // Beginning of threading
    public void startThread(Vehicle initVehicle, Signal t_signal, Intersection_manager pres_section, long waiting_l,
            boolean gen_phase) {

        mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                long thread_end_t;

                synchronized (this) {

                    if (pres_section.getCurrentPos() == initVehicle.getDirection()
                            || pres_section.getNextDir() == initVehicle.getDirection()) {

                        if (t_signal.getLight().equals("green")) {

                            try {
                                wait(waiting_l);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        } else {
                            try {
                                wait(waiting_l);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }

                        thread_end_t = System.currentTimeMillis() - start_t;
                        initVehicle.setCrossStatus(true);
                        Vehicle_logs.getInstance().addToLog(
                        String.format("Vehicle %d has crossed", initVehicle.getVehicle_number()));
                        Change(vehicles);

                    }
                }

            }
        });

        mainThread.start();

    }

    public Queue<Intersection_manager> intersectNumber() {
        return intersection_manager;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Error for unimplemented 'run'");
    }

}