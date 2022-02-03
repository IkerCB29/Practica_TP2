package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event>{

	NewVehicleEventBuilder() {
		super("new_vehicle");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		//TODO, DEVOLVER NULL SI ALGO ESTÁ MAL
		//QUIZAS HACERLO COMPROBANDO SI DATA TIENE ESAS CLAVES

		List<String> itinerary = new ArrayList<>();
		for(int i = 0; i < data.getJSONArray("itinerary").length(); i++){
			itinerary.add(data.getJSONArray("itinerary").getString(i));
		}

		return new NewVehicleEvent(data.getInt("time"), data.getString("id"), data.getInt("maxspeed"), data.getInt("class"), itinerary);

	}
	
}
