package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject{
	
	private static final String INVALID_POSITION = "coordinates aren't valid";
	private static final String INVALID_ROAD = "invalid road for the requested junction";
	private static final String NO_CURRENT_GREEN_ROAD = "none";
	
	private List<Road> srcRoads;
	private Map<Junction, Road> destRoads;
	private List<List<Vehicle>> qs;
	private Map<Road, List<Vehicle>> road_Queue;
	private int currGreen;
	private int lastSwitchingTime;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor, yCoor;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy 
			dqStrategy, int xCoor, int yCoor) {
		super(id);
		
		if(lsStrategy == null)
			throw new NullPointerException("lsStrategy " + NULL_POINTER_MSG);
		if(dqStrategy == null)
			throw new NullPointerException("dqStrategy " + NULL_POINTER_MSG);
		if(xCoor < 0 || yCoor < 0)
			throw new IllegalArgumentException(INVALID_POSITION);
		
		srcRoads = new ArrayList<Road>();
		destRoads = new HashMap<Junction, Road>();
		qs = new ArrayList<List<Vehicle>>();
		road_Queue = new HashMap<Road, List<Vehicle>>();
		currGreen = -1;
		lastSwitchingTime = 0;
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
	}
	
	void addIncomingRoad(Road r) {
		if(r.getDest() != this)
			throw new IllegalArgumentException(INVALID_ROAD);
		srcRoads.add(r);
		List<Vehicle> queue = new LinkedList<Vehicle>();
		qs.add(queue);
		road_Queue.put(r, queue);
	}
	
	void addOutGoingRoad(Road r) {
		if(isThereRoadToJunction(r.getDest()) || r.getSrc() != this)
			throw new IllegalArgumentException(INVALID_ROAD);
		destRoads.put(r.getDest(), r);
	}
	
	private boolean isThereRoadToJunction(Junction j) {
		for(Junction entry : destRoads.keySet()) {
			if(entry == j)
				return true;
		}
		return false;
	}
	
	void enter(Vehicle v) {
		List<Vehicle>queue = road_Queue.get(v.getRoad());
		queue.add(v);
	}
	
	Road roadTo(Junction j) {
		return destRoads.get(j);
	}

	@Override
	void advance(int time) {
		if(currGreen != -1) {
			List<Vehicle> vehiclesToMove = dqStrategy.dequeue(qs.get(currGreen));
			for(Vehicle v: vehiclesToMove) {
				v.moveToNextRoad();
				qs.get(currGreen).remove(v);
			}
		}
		int nextGreen = lsStrategy.chooseNextGreen(srcRoads, qs, currGreen, lastSwitchingTime, time);
		if(nextGreen != currGreen) {
			currGreen = nextGreen;
			lastSwitchingTime = time;
		}
	}

	@Override
	public JSONObject report() {
		
		JSONObject jo = new JSONObject();
		jo.put("id", _id);
		jo.put("green", getGreenRoad());
		JSONArray ja = new JSONArray();
		int i = 0;
		for(Road r : srcRoads) {
			JSONObject roadQ = new JSONObject();
			roadQ.put("road", r.getId());
			JSONArray vehicles = new JSONArray();
			
			List<Vehicle> q = qs.get(i);
			for(Vehicle v : q) {
				vehicles.put(v.getId());
			}
			
			i++;
			roadQ.put("vehicles", vehicles);
			ja.put(roadQ);
		}
		
		jo.put("queues", ja);
		return jo;
	}
	
	private String getGreenRoad() {
		if(currGreen != - 1)
			return srcRoads.get(currGreen).getId();
		else
			return NO_CURRENT_GREEN_ROAD;
	}
	
}
