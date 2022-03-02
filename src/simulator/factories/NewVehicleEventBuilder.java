package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event>{

	private final static String NEW_VEHICLE = "new_vehicle";
	
	public NewVehicleEventBuilder() {
		super(NEW_VEHICLE);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		List<String> itinerary = new ArrayList<>();
		for(int i = 0; i < data.getJSONArray("itinerary").length(); i++){
			itinerary.add(data.getJSONArray("itinerary").getString(i));
		}

		return new NewVehicleEvent(data.getInt("time"), data.getString("id"), data.getInt("maxspeed"), data.getInt("class"), itinerary);
	}
	
}
