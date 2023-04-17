package road_traffic_simulation;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
        AddVehicle model = new AddVehicle();
        GUI view = new GUI();
        Vehicle_manager controller = new Vehicle_manager(model, view);

        controller.loadData("Vehicles.csv");
        controller.loadDataInter("Intersection.csv");
        controller.showView();
        controller.start();

        controller.threadrun();
	}

}
