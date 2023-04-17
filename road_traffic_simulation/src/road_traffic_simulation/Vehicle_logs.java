package road_traffic_simulation;

	import java.io.FileWriter;
	import java.io.IOException;
	import java.io.PrintWriter;
	import java.util.ArrayList;

	public class Vehicle_logs {
	    private static Vehicle_logs instance = null;
	    private ArrayList<String> Entries = new ArrayList<>();

	    // Checks and create class instance if instance doesn't already exists
	    public static synchronized Vehicle_logs getInstance() {
	        // Checking whether the instance exists already
	        if (instance == null) {
	            // Intializing instance if doesn't exists
	            instance = new Vehicle_logs();
	        }
	        return instance;
	    }

	    // Adding each log Entries to an ArrayList
	    public synchronized void addEntry(String log_msg) {
	        Entries.add(log_msg);
	    }

	    // creating logs
	    public void writeLogsToFile(String FileName) {
	        try (PrintWriter writer = new PrintWriter(new FileWriter(FileName))) {
	            writer.println("\t\t Log History");
	            writer.println();
	            for (String entry : Entries) {
	                writer.println(entry);
	            }
	            writer.println("\tEnd of Simulation");
	        } catch (IOException e) {
	            System.err.println("Error creating logs: " + e.getMessage());
	        }
	    }
	}

