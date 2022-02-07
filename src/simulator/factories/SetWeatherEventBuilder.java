package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.misc.Pair;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{

	SetWeatherEventBuilder() {
		super("set_weather");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		List<Pair<String, Weather>> wl = new ArrayList<>();
		JSONObject aux;
		for(int i = 0; i < data.getJSONArray("info").length(); i++){
			aux = data.getJSONArray("info").getJSONObject(i);
			
			wl.add(new Pair<String,Weather>(aux.getString("road"), Weather.valueOf(aux.getString("weather"))));
		}

		return new SetWeatherEvent(data.getInt("time"),wl);
	}
	
}
