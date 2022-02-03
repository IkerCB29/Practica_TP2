package simulator.model;

import java.util.List;

public class NewVehicleEvent extends Event{

	private Vehicle v;
	public NewVehicleEvent(int time, String id, int maxSpeed, int
							contClass, List<String> itinerary) {
		super(time);
		v = new Vehicle(id, maxSpeed, contClass, itinerary);
	}
	@Override
	void execute(RoadMap map) {
		map.addVehicle(v);
		v.moveToNextRoad();
		
	}
}
