package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.*;

public class Vehicle extends SimulatedObject{

	private final String INVALID_MAX_SPEED = "maxSpeed must be a positive value";
	
	private final String INVALID_CONT_CLASS = "contClass must be a value between 0 and 10";
	
	private final String INVALID_ITINERARY = "itinerary must have at least 2 junctions";
	
	private final String INVALID_SPEED = "speed can't be a negative value";
	
	private List<Junction> itinerary;
	
	private int maxSpeed;
	
	private int speed;
	
	private VehicleStatus status;
	
	private Road myRoad;
	
	private int location;
	
	private int contClass;
	
	private int totalCO2;
	
	private int distanceTraveled;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		
		if(maxSpeed <= 0)
			throw new IllegalArgumentException(INVALID_MAX_SPEED);
		if(contClass < 0 || contClass > 10 )
			throw new IllegalArgumentException(INVALID_CONT_CLASS);
		if(itinerary.size() < 2)
			throw new IllegalArgumentException(INVALID_ITINERARY);
		
		this.maxSpeed = maxSpeed;
		speed = 0;
		status = VehicleStatus.PENDING;
		myRoad = null;
		location = 0;
		this.contClass = contClass;
		totalCO2 = 0;
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		distanceTraveled = 0;
	}
	
	void setSpeed(int s) {
		if(s < 0)
			throw new IllegalArgumentException(INVALID_SPEED);
		speed = Math.min(s, maxSpeed);
	}
	
	void setContamination(int c) {
		if(c < 0 || c > 10)
			throw new IllegalArgumentException(INVALID_CONT_CLASS);
		contClass = c;
	}
	
	@Override
	void advance(int time) {
		if(status == VehicleStatus.TRAVELING) {
			int previousLocation = location;
			location = Math.min(location + speed, /*TODO get roadLength*/);
			int contamination = (location - previousLocation) *  contClass;
			totalCO2 += contamination;
			//TODO addContamination
			//TODO enter in junction;
				
		}
	}
	
	//TODO writeFunction
	void moveToNextRoad() {
		
	}

	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("id", _id);
		jo.put("speed", speed);
		jo.put("distance", distanceTraveled);
		jo.put("co2", totalCO2);
		jo.put("class", contClass);
		jo.put("status", status.toString());
		return jo;
	}
	
	public int getLocation() {
		return location;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	public int getContClass() {
		return contClass;
	}
	
	public VehicleStatus getStatus() {
		return status;
	}
	
	public int getTotalCO2() {
		return totalCO2;
	}
	
	public List<Junction> getItinerary(){
		return itinerary;
	}
	
	public Road getRoad() {
		return myRoad;
	}
	
}
