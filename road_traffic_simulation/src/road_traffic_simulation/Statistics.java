package road_traffic_simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    private String segmentNumber;
    private double totalWaitingLength;
    private int totalWaitingTime;
    private int averageCrossingTime;

    public Statistics(int i, int totalWaitingTime, double totalWaitingLength, int averageCrossingTime) {
        this.segmentNumber = "S" + i;
        this.totalWaitingTime = totalWaitingTime;
        this.totalWaitingLength = totalWaitingLength;
        this.averageCrossingTime = averageCrossingTime;
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
    }

    public int getAverageCrossingTime() {
        return averageCrossingTime;
    }

    public void setAverageCrossingTime(int averageCrossingTime) {
        this.averageCrossingTime = averageCrossingTime;
    }

    public static List<Statistics> populateStatistics(List<Vehicle> vehicles) {
        List<Statistics> statisticsList = new ArrayList<>();
        Map<String, Integer> segmentCounts = new HashMap<>();
        
        // looping for statistics in each segment
        for (int i = 1; i <= 4; i++) {
            int totalWaitingTime = 0;
            int averageCrossingTime = 0;
            double totalWaitingLength = 0;
            try {
                for (Vehicle vehicle : vehicles) {
                    if (vehicle.getSegmentNumber()== ('S' + i)) {
                        String segmentNumber = toString(vehicle.getSegmentNumber());
                        segmentCounts.put(segmentNumber, segmentCounts.getOrDefault(segmentNumber, 0) + 1);

                        averageCrossingTime += vehicle.getCrossingTime();
                        totalWaitingLength += vehicle.getWaitingLength();

                        if (vehicle.getCrossStatus().equals("waiting")) {
                            totalWaitingTime += vehicle.getCrossingTime();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error for segment " + i);
                e.printStackTrace();
            }

            Statistics statistics = new Statistics(i, totalWaitingTime, totalWaitingLength, averageCrossingTime);
            statisticsList.add(statistics);
            
            
        }

        return statisticsList;
    }

	private static String toString(char segmentNumber2) {
		// TODO Auto-generated method stub
		return null;
	}

}
