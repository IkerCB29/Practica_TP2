package simulator.model;

import java.util.List;
import simulator.misc.Pair;

public class NewSetContClassEvent extends Event{

	private List<Pair<String, Integer>> cs;
	//TODO MENSAJE
	NewSetContClassEvent(int time, List<Pair<String, Integer>> cs) throws IllegalArgumentException{
		super(time);
		if(cs == null) throw new IllegalArgumentException();
		this.cs = cs;
	}

	@Override
	void execute(RoadMap map) throws IllegalArgumentException{
		Vehicle v;
		
		for(Pair<String, Integer> p : cs){
			v = map.getVehicle(p.getFirst());

			if(v == null) throw new IllegalArgumentException();
			v.setContamination(p.getSecond());
		}		
	}
	
}
