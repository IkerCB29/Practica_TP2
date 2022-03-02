package simulator.factories;


import simulator.model.Event;
import simulator.model.NewCityRoadEvent;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder{

	private final static String NEW_CITY_ROAD = "new_city_road";
	
	public NewCityRoadEventBuilder() {
		super(NEW_CITY_ROAD);
	}
	
	@Override
	Event createTheRoad() {
		return new NewCityRoadEvent(time, id, src, dest, length, co2Limit, maxSpeed, weather);
	}
	
}
