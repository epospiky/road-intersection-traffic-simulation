package road_traffic_simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private String plateNumber;
    private String vehicleType;
    private int crossingTime;
    private String direction;
    private double waitingLength;
    private int co2Emission;
    private String vehicleStatus;
    private String segmentNumber;
    
    public Vehicle(String plateNumber, String vehicleType, int crossingTime, 
                   String direction, double waitingLength, int co2Emission, 
                   String vehicleStatus, String segmentNumber) {
        this.plateNumber = plateNumber;
        this.vehicleType = vehicleType;
        this.crossingTime = crossingTime;
        this.direction = direction;
        this.waitingLength = waitingLength;
        this.co2Emission = co2Emission;
        this.vehicleStatus = vehicleStatus;
        this.segmentNumber = segmentNumber;
    }
    
    // Getters and setters for all attributes
    public String getPlateNumber() {
        return plateNumber;
    }
    
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
    
    public String getVehicleType() {
        return vehicleType;
    }
    
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    
    public int getCrossingTime() {
        return crossingTime;
    }
    
    public void setCrossingTime(int crossingTime) {
        this.crossingTime = crossingTime;
    }
    
    public String getDirection() {
        return direction;
    }
    
    public void setDirection(String direction) {
        this.direction = direction;
    }
    
    public double getWaitingLength() {
        return waitingLength;
    }
    
    public void setWaitingLength(double waitingLength) {
        this.waitingLength = waitingLength;
    }
    
    public int getCo2Emission() {
        return co2Emission;
    }
    
    public void setCo2Emission(int co2Emission) {
        this.co2Emission = co2Emission;
    }
    
    public String getVehicleStatus() {
        return vehicleStatus;
    }
    
    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
    
    public String getSegmentNumber() {
        return segmentNumber;
    }
    
    public void setSegmentNumber(String segmentNumber) {
        this.segmentNumber = segmentNumber;
    }
    

    
}

