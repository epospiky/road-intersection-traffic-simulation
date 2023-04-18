package road_traffic_simulation;

public class Vehicle {

    private int vehicle_number;
    private String vehicle_type;
    private char segmentNumber;
    private double crossingTime;
    private char direction;
    private boolean hasCrossed;
    private double waitingLenght;
    private double co2Emission;



    public Vehicle(int vehicle_num, String veh_type,  char segment_num, double crossing_t, char direct, boolean cross, double waitingLen,
            double co2_Em) {
        vehicle_number = vehicle_num;
        vehicle_type = veh_type;
        segmentNumber = segment_num;
        crossingTime = crossing_t;
        direction = direct;
        hasCrossed = cross;
        waitingLenght = waitingLen;
        co2Emission = co2_Em;
    }

    public String getVehicleType() {
        return vehicle_type;
    }

    public int getVehicle_number() {
        return vehicle_number;
    }

    public char getSegmentNumber() {
        return segmentNumber;
    }

    public double getCrossingTime() {
        return crossingTime;
    }

    public char getDirection() {
        return direction;
    }

    public String getCrossStatus() {
        if (hasCrossed == false) {
            return "Waiting";
        }
        return "Crossed";
    }

    public boolean hasCrossed() {

        return hasCrossed;
    }

    public double getWaitingLength() {
        return waitingLenght;
    }

    public double getCo2Emission() {
        return co2Emission;
    }

    public double estimatedEmmision() {
        return co2Emission * crossingTime + waitingLenght;
    }

    public void setCrossStatus(boolean c) {
        hasCrossed = c;
    }

}
