package simulator.model;

import java.util.Collections;
import java.util.List;

import simulator.misc.Utils;

public class MoveFirstStrategy implements DequeuingStrategy{

	public MoveFirstStrategy() {}

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		Vehicle [] firstVehicle = new Vehicle[1];
		firstVehicle[0] = q.get(0);
		return Utils.arrayToList(firstVehicle);
	}
	

}
