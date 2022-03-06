package simulator.model;

import java.util.List;
import simulator.misc.Pair;

public class SetContClassEvent extends Event{

	private final static String NULL_STRING_CONTAMINATION_LIST = "string-contamination list is null";
	private final static String NULL_VEHICLE = "vehicle is null";
	
	private List<Pair<String, Integer>> cs;
	//TODO MENSAJE
	public SetContClassEvent(int time, List<Pair<String, Integer>> cs){
		super(time);
		if(cs == null) throw new IllegalArgumentException(NULL_STRING_CONTAMINATION_LIST);
		this.cs = cs;
	}

	@Override
	void execute(RoadMap map){
		Vehicle v;
		
		for(Pair<String, Integer> p : cs){
			v = map.getVehicle(p.getFirst());

			if(v == null) throw new IllegalArgumentException(NULL_VEHICLE);
			v.setContClass(p.getSecond());
		}		
	}
	
}
