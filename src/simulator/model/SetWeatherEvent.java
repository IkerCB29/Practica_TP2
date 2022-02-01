package simulator.model;

import java.util.List;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event{

	private List<Pair<String, Weather>> ws;
	//TODO MENSAJE DE LA EXCEPCION

	SetWeatherEvent(int time, List<Pair<String,Weather>> ws) throws IllegalArgumentException{
		super(time);
		if(ws == null) throw new IllegalArgumentException();
		this.ws = ws;
	
	}

	@Override
	void execute(RoadMap map) throws IllegalArgumentException{
		for(Pair<String, Weather> p : ws){
			Road r = map.getRoad(p.getFirst());

			if(r == null) throw new IllegalArgumentException();

			r.setWeather(p.getSecond());
		}		
	}
	
}
