package simulator.model;

import java.util.List;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event{

	private final static String NULL_STRING_WEATHER_LIST = "string-weather list is null";
	private final static String NULL_ROAD = "road is null";
	
	private List<Pair<String, Weather>> ws;
	//TODO MENSAJE DE LA EXCEPCION

	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws){
		super(time);
		if(ws == null) throw new IllegalArgumentException( NULL_STRING_WEATHER_LIST);
		this.ws = ws;
	
	}

	@Override
	void execute(RoadMap map){
		for(Pair<String, Weather> p : ws){
			Road r = map.getRoad(p.getFirst());

			if(r == null) throw new IllegalArgumentException(NULL_ROAD);

			r.setWeather(p.getSecond());
		}		
	}
	
	@Override
	public String toString() {
		return "Change Weather: " + ws.toString();
	}
	
}
