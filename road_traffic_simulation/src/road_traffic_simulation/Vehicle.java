package road_traffic_simulation;
public class Vehicle {

    private String vehicle_type;
    private int vehicle_number;
    private char segmentNumber;
    private double crossing_time;
    private char direction;
    private boolean crossed;
    private double length;
    private double co2_emission;



    public Vehicle(int num, String veh_type,  char segment_num, double cross_time, char direct, boolean cross, double leng,
            double co2) {
        vehicle_type = veh_type;
        vehicle_number = num;
        segmentNumber = segment_num;
        crossing_time = cross_time;
        direction = direct;
        crossed = cross;
        length = leng;
        co2_emission = co2;
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
        return crossing_time;
    }

    public char getDirection() {
        return direction;
    }

    public String getCrossStatus() {
        if (crossed == false) {
            return "Waiting";
        }
        return "Crossed";
    }

    public boolean hasCrossed() {

        return crossed;
    }

    public double getWaitingLength() {
        return length;
    }

    public double getCo2Emission() {
        return co2_emission;
    }

    public double estimatedEmmision() {
        return co2_emission * crossing_time + length;
    }

    public void setCrossStatus(boolean c) {
        crossed = c;
    }

}
