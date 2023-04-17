package road_traffic_simulation;

import java.util.ArrayList;
import java.util.List;

public class Emission {
	private String segmentNumber;
	private double totalCo2Emission;
	
	public Emission(int i, double totalCo2Emission) {
		this.segmentNumber = "S"+i;
		this.totalCo2Emission = totalCo2Emission;
	}
	public String getSegmentNumber() {
	        return segmentNumber;    
	        }
	    
	public void setSegmentNumber(String segmentNumber) {
	        this.segmentNumber = segmentNumber;
	}
	public double getTotalCo2Emission() { 
    	return totalCo2Emission; 
  	}

    public void setTotalCo2Emission(double totalCo2Emission) {
    	this.totalCo2Emission = totalCo2Emission;
    }
    
    public static List<Emission> addEmission(List<Vehicle> vehicles) {
    	List<Emission> emissionList = new ArrayList<>();
    	GUI gui = new GUI();
        for (int i = 1; i <= vehicles.size(); i++) {
            double totalCo2Emission = 0; 
	    	for (Vehicle vehicle : vehicles) {
	    		if (vehicle.getSegmentNumber() == ('S' + i)) {    
	    		    totalCo2Emission += vehicle.getCo2Emission();
	    		    System.out.println("how far " + totalCo2Emission);
	    		}

	        }
	    	Emission emi = new Emission(i, totalCo2Emission);
            emissionList.add(emi);
            
        }	
        gui.setTotalEmissionLabel(emissionList.get(0).getTotalCo2Emission());

		return emissionList;
    
    }
   // , totalCo2Emission
}


