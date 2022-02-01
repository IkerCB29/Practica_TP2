package simulator.model;

public class NewInterCityRoadEvent extends Event{

	private InterCityRoad r;
	public NewInterCityRoadEvent(int time, String id, Junction srcJun,
							Junction destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		r = new InterCityRoad(id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	void execute(RoadMap map) {
		map.addRoad(r);		
	}
	
}
