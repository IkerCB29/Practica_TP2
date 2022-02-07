package simulator.model;

import java.util.List;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event{

	private List<Pair<String, Weather>> ws;
	//TODO MENSAJE DE LA EXCEPCION

	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws){
		super(time);
		if(ws == null) throw new IllegalArgumentException();
		this.ws = ws;
	
	}

	@Override
	void execute(RoadMap map){
		for(Pair<String, Weather> p : ws){
			Road r = map.getRoad(p.getFirst());

			if(r == null) throw new IllegalArgumentException();

			r.setWeather(p.getSecond());
		}		
	}
	
}
