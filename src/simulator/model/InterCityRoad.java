package simulator.model;

public class InterCityRoad extends Road{
	
	private static final int SUNNY_IMPACT_ON_CONTAMINATION = 2;
	private static final int CLOUDY_IMPACT_ON_CONTAMINATION = 3;
	private static final int RAINY_IMPACT_ON_CONTAMINATION = 10;
	private static final int WINDY_IMPACT_ON_CONTAMINATION = 15;
	private static final int STORM_IMPACT_ON_CONTAMINATION = 20;

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		int weatherImpact = calculateWeatherImpactOnContamination();
		totalCO2 = ((100 - weatherImpact) * totalCO2 ) / 100;
	}
	
	private int calculateWeatherImpactOnContamination() {
		switch(weather) {
		case SUNNY :
			return SUNNY_IMPACT_ON_CONTAMINATION;
		case CLOUDY :
			return CLOUDY_IMPACT_ON_CONTAMINATION;
		case RAINY:
			return RAINY_IMPACT_ON_CONTAMINATION;
		case WINDY:
			return WINDY_IMPACT_ON_CONTAMINATION;
		case STORM:
			return STORM_IMPACT_ON_CONTAMINATION;
		default:
			return 0;
		}
	}

	@Override
	void updateSpeedLimit() {
		if(totalCO2 > contLimit)
			speedLimit = maxSpeed / 2;
		else
			speedLimit = maxSpeed;
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		if(v.getStatus() == VehicleStatus.WAITING)
			return 0;
		else if(weather == Weather.STORM)
			return (speedLimit * 8) / 10;
		else
			return speedLimit;
	}

}
