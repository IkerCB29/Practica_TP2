package simulator.model;

import java.util.List;
import java.util.Collections;

public class MoveAllStrategy implements DequeuingStrategy{

	 MoveAllStrategy(){}
	 
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		return Collections.unmodifiableList(q);
	}

}
