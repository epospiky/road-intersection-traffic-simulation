package road_traffic_simulation;

public class Main {

	public static void main(String[] args) throws Exception {
        GUI View = new GUI();
        AddVehicle Model = new AddVehicle();
        Vehicle_manager Controller = new Vehicle_manager(Model, View);
        Controller.getIntersectionData("Intersection.csv");
        Controller.loadData("Vehicles.csv");
        Controller.start();
        Controller.threadStart();
	}

}
