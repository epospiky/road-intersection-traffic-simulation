package road_traffic_simulation;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
        GUI View = new GUI();
        AddVehicle Model = new AddVehicle();
        Vehicle_manager Controller = new Vehicle_manager(Model, View);
        Controller.getIntersectionData("Intersection.csv");
        Controller.loadData("Vehicles.csv");
        Controller.displayView();
        Controller.start();
        Controller.threadStart();
	}

}
