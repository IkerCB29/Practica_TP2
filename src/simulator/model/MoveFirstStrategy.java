package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy{

	public MoveFirstStrategy() {}

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> firstVehicle = new ArrayList<Vehicle>();
		if(q.size() > 0)
			firstVehicle.add(q.get(0));
		return firstVehicle;
	}

}
