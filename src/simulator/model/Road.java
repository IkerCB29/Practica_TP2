package simulator.model;

import simulator.misc.SortedArrayList;

public class Road extends SimulatedObject {
	
	private final String INVALID_MAX_SPEED = "maxSpeed must be a positive value";

	private final String INVALID_CONT_LIMIT = "contLimit can't be a negative value";
	
	private final String INVALID_LENGTH = "length must be a positive value";
	
	private final String NULL_POINTER_MSG = "is a null pointer";
	
	private Junction source, destination;
	
	private int length;
	
	private int maxSpeed;
	
	private int speedLimit;
	
	private int contLimit;
	
	private Weather weather;
	
	private int totalCO2;
	
	private SortedArrayList<Vehicle> vehicles;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather) {
		super(id);
		
		if(maxSpeed <= 0)
			throw new IllegalArgumentException(INVALID_MAX_SPEED);
		if(contLimit < 0)
			throw new IllegalArgumentException(INVALID_CONT_LIMIT);
		if(length <= 0)
			throw new IllegalArgumentException(INVALID_LENGTH);
		if(srcJunc == null)
			throw new IllegalArgumentException("srcJunc " + NULL_POINTER_MSG);
		if(destJunc == null)
			throw new IllegalArgumentException("destJunc " + NULL_POINTER_MSG);
		if(weather == null)
			throw new IllegalArgumentException("weather " + NULL_POINTER_MSG);
		
		source = srcJunc;
		destination = destJunc;
		this.maxSpeed = maxSpeed;
		speedLimit = maxSpeed;
		this.contLimit = contLimit;
		this.weather = weather;
		totalCO2 = 0;
		vehicles = new SortedArrayList<Vehicle>();
		
		// TODO Add road to srcJunc and destJunc
	}

	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

}
