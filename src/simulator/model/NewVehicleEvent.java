package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event{

	private Vehicle v;
	private int maxSpeed, contClass;
	private String id;
	private List<String> itinerary;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, 
			List<String> itinerary) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
	}

	@Override
	void execute(RoadMap map) {
		List<Junction> junIt = new ArrayList<>();
		for(String s : itinerary){
			junIt.add(map.getJunction(s));
		}
		v = new Vehicle(id, maxSpeed, contClass, junIt);
		map.addVehicle(v);
		v.moveToNextRoad();
	}
	
	@Override
	public String toString() {
		return "New Vehicle '" + id + "'";
	}
}
