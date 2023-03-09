package road_traffic_simulation;

import java.util.ArrayList;
import java.util.List;

public class Statistics {
	//private List<Vehicle> vehicles;
    private String segmentNumber;
    private int totalWaitingTime;
    private double totalWaitingLength;
    private int averageCrossingTime;
    private double totalCo2Emission;
    
    public Statistics(int i, int totalWaitingTime, double totalWaitingLength, int averageCrossingTime, double totalCo2Emission){
        this.segmentNumber = "S"+i;
        this.totalWaitingTime = totalWaitingTime;
        this.totalWaitingLength = totalWaitingLength;
        this.averageCrossingTime = averageCrossingTime;
        this.totalCo2Emission = totalCo2Emission;
    }
    
    public String getSegmentNumber() {
        return segmentNumber;
    }
    
    public void setSegmentNumber(String segmentNumber) {
        this.segmentNumber = segmentNumber;
    }
    
    public int getTotalWaitingTime() {
        return totalWaitingTime;
    }
    public void setTotalWaitingTime(int totalWaitingTime) {
        this.totalWaitingTime = totalWaitingTime;
    }
    public double getTotalWaitingLength() {
        return totalWaitingLength;
    }
    public void setTotalWaitingLength(double totalWaitingLength) {
        this.totalWaitingLength = totalWaitingLength;
        //System.out.println("Total waiting length for segment " + segmentNumber + ": " + totalWaitingLength);

    }
    
    public int getAverageCrossingTime() {
        return averageCrossingTime;
    }
    
    public void setAverageCrossingTime(int averageCrossingTime) {
        this.averageCrossingTime = averageCrossingTime;
        System.out.println("Average Crossing time is "+averageCrossingTime);
        
    }
    public double getTotalCo2Emission() { 
    	return totalCo2Emission; 
    	}

    public void setTotalCo2Emission(double totalCo2Emission) {
    	this.totalCo2Emission = totalCo2Emission;
    	//System.out.println("Total Co2 Emission is "+totalCo2Emission);
    }

	public static List<Statistics> populateStatistics(List<Vehicle> vehicles) {
	    List<Statistics> statisticsList = new ArrayList<>();

	    // Iterating over the segments and create a Statistics object for each segment
	    for (int i = 1; i <= vehicles.size(); i++) {
	        int totalWaitingTime = 0;
	        int averageCrossingTime = 0;
	        double totalWaitingLength = 0;
	        double totalCo2Emission = 220;

	        for (Vehicle vehicle : vehicles) {
	            if (vehicle.getSegmentNumber().equals("S" + i)) {
	            	averageCrossingTime +=vehicle.getCrossingTime();
                	averageCrossingTime = averageCrossingTime/2;
	                if (vehicle.getVehicleStatus().equals("Waiting")) {
	                    totalWaitingTime += vehicle.getCrossingTime();
	                    totalWaitingLength += vehicle.getWaitingLength();
	                    //totalCo2Emission += vehicle.getCo2Emission();
	                } else if (vehicle.getVehicleStatus().equals("Crossed")) {
	                    averageCrossingTime += vehicle.getCrossingTime();
	                }else {
	                	//totalCo2Emission += vehicle.getCo2Emission();
	                	
	                	}
	            }
	        }

	        Statistics statistics = new Statistics(i, totalWaitingTime, totalWaitingLength, averageCrossingTime, totalCo2Emission);
	        statisticsList.add(statistics);
	    }

	    // Calculate the total CO2 emissions from all vehicles
	    double totalCo2Emission = 0;
	    for (Vehicle vehicle : vehicles) {
	        totalCo2Emission += vehicle.getCo2Emission();
	    }

	    // Add the total CO2 emissions to each Statistics object
	    for (Statistics statistics : statisticsList) {
	        statistics.setTotalCo2Emission(totalCo2Emission);
	    }

	    return statisticsList;
	}

	/*
	 * public static List<Statistics> populateStatistics(List<Vehicle> vehicles) {
	 * List<Statistics> statisticsList = new ArrayList<>();
	 * 
	 * // Iterating over the segments and create a Statistics object for each
	 * segment for (int i = 1; i <= vehicles.size(); i++) { int totalWaitingTime =
	 * 0; int averageCrossingTime = 0; double totalWaitingLength = 0; double
	 * totalCo2Emission = 0; Statistics statistics = new
	 * Statistics(i,totalWaitingTime, totalWaitingLength, averageCrossingTime,
	 * totalCo2Emission); for (Vehicle vehicle : vehicles) { if
	 * (vehicle.getSegmentNumber().equals( "S"+i)) {
	 * //System.out.println("Segment gotten");
	 * if(vehicle.getVehicleStatus().equals("Waiting")) { totalWaitingTime +=
	 * vehicle.getCrossingTime(); totalWaitingLength += vehicle.getWaitingLength();
	 * totalCo2Emission += vehicle.getCo2Emission(); } else if
	 * (vehicle.getVehicleStatus().equals("Crossed")) { averageCrossingTime +=
	 * vehicle.getCrossingTime();
	 * //System.out.println("These are crossed vehicles"); } } }
	 * statistics.setTotalCo2Emission(totalCo2Emission);
	 * statistics.setTotalWaitingLength(totalWaitingLength);
	 * statistics.setTotalWaitingTime(totalWaitingTime);
	 * statistics.setTotalCrossingTime(averageCrossingTime);
	 * statisticsList.add(statistics); }
	 * 
	 * return statisticsList; }
	 */
}
