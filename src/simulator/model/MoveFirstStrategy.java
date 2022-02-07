package simulator.model;

import java.util.List;

import simulator.misc.Utils;

public class MoveFirstStrategy implements DequeuingStrategy{

    MoveFirstStrategy () {}
	
    @Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		Vehicle [] aux = new Vehicle[1];
		aux[0] = q.get(0);
		return Utils.arrayToList(aux);
	}

}
