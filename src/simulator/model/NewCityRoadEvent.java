package simulator.model;

public class NewCityRoadEvent extends Event{

	private CityRoad r;
	public NewCityRoadEvent(int time, String id, Junction srcJun, Junction
					destJunc, int length, int co2Limit, int maxSpeed, Weather weather){

		super(time);
		r = new CityRoad(id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	void execute(RoadMap map) {
		map.addRoad(r);
	}
	
}
