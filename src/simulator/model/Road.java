package simulator.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public abstract class Road extends SimulatedObject {
	
	private static final String INVALID_LOCATION = "location must be 0";
	
	private static final String INVALID_SPEED = "speed must be 0";
	
	private Junction source, destination;
	
	protected int length;
	
	protected int maxSpeed;
	
	protected int speedLimit;
	
	protected int contLimit;
	
	protected Weather weather;
	
	protected int totalCO2;
	
	private Comparator<Vehicle> cmp;
	
	private List<Vehicle> vehicles;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather) {
		super(id);
		
		if(maxSpeed <= 0)
			throw new IllegalArgumentException("maxSpeed " + NO_POSITIVE_VALUE);
		if(contLimit < 0)
			throw new IllegalArgumentException("contLimit " + INVALID_NEGATIVE_VALUE);
		if(length <= 0)
			throw new IllegalArgumentException("length " + NO_POSITIVE_VALUE);
		if(srcJunc == null)
			throw new NullPointerException("srcJunc " + NULL_POINTER_MSG);
		if(destJunc == null)
			throw new NullPointerException("destJunc " + NULL_POINTER_MSG);
		if(weather == null)
			throw new NullPointerException("weather " + NULL_POINTER_MSG);
		
		source = srcJunc;
		destination = destJunc;
		this.length = length;
		this.maxSpeed = maxSpeed;
		speedLimit = maxSpeed;
		this.contLimit = contLimit;
		this.weather = weather;
		totalCO2 = 0;
		cmp = Comparator.comparing(
				Vehicle::getLocation, 
				(s1,s2) -> {return s2.compareTo(s1); }
			  );
		vehicles = new SortedArrayList<Vehicle>(cmp);
		
		source.addOutGoingRoad(this);
		destination.addIncomingRoad(this);
	}

	void enter(Vehicle v) {
		if(v.getLocation() != 0)
			throw new IllegalArgumentException(INVALID_LOCATION);
		if(v.getSpeed() != 0)
			throw new IllegalArgumentException(INVALID_SPEED);
		vehicles.add(v);
	}
	
	void exit(Vehicle v) {
		vehicles.remove(v);
	}
	
	void setWeather(Weather w) {
		if(w == null)
			throw new IllegalArgumentException("weather " + NULL_POINTER_MSG);
		weather = w;
	}
	
	void addContamination(int c) {
		if(c < 0)
			throw new IllegalArgumentException("contamination " + INVALID_NEGATIVE_VALUE);
		totalCO2 += c;
	}
	
	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);
	
	@Override
	void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
		for(Vehicle v : vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		vehicles.sort(cmp);
	}

	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("id", _id);
		jo.put("speedlimit", speedLimit);
		jo.put("weather", weather.toString());
		jo.put("co2", totalCO2);
		JSONArray ja = new JSONArray();
		for(Vehicle v : vehicles) {
			ja.put(v.getId());
		}
		jo.put("vehicles", ja);
		return jo;
	}
	
	public int getLength() {
		return length;
	}
	
	public Junction getDest() {
		return destination;
	}
	
	public Junction getSrc() {
		return source;
	}
	
	public Weather getWeather() {
		return weather;
	}

	public int getContLimit() {
		return contLimit;
	}
	
	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	public int getTotalCO2() {
		return totalCO2;
	}
	
	public int getSpeedLimit() {
		return speedLimit;
	}
	
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(vehicles);
	}
	
}
