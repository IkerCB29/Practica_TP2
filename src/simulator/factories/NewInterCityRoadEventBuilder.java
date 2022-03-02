package simulator.factories;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder{

	private final static String NEW_INTER_CITY_ROAD = "new_inter_city_road";
	
	public NewInterCityRoadEventBuilder() {
		super(NEW_INTER_CITY_ROAD);
	}

	@Override
	Event createTheRoad() {
		return new NewInterCityRoadEvent(time, id, src, dest, length, co2Limit, maxSpeed, weather);
	}
	
}
