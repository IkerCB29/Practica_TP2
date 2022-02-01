package simulator.model;

public class CityRoad extends Road{

	private final int WINDY_STORM_IMPACT_ON_CONTAMINATION = 10;
	
	private final int OTHER_WEATHER_IMPACT_ON_CONTAMINATION = 2;
	
	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		int weatherImpact = calculateWeatherImpactOnContamination();
		totalCO2 = Math.max(totalCO2 - weatherImpact, 0);
	}

	private int calculateWeatherImpactOnContamination() {
		if(weather == Weather.WINDY || weather == Weather.CLOUDY)
			return WINDY_STORM_IMPACT_ON_CONTAMINATION;
		else
			return OTHER_WEATHER_IMPACT_ON_CONTAMINATION;
	}
	
	@Override
	void updateSpeedLimit() {}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		return ((11 - v.getContClass()) * speedLimit) / 11 ;
	}

}
