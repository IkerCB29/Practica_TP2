package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;

public class NewCityRoadEventBuilder extends Builder<Event>{

	public NewCityRoadEventBuilder() {
		super("new_city_road");
	}

	//TODO
	@Override
	protected Event createTheInstance(JSONObject data) {
		return null;
	}
	
}
