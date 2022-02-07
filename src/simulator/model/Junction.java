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
	
	private List<Road> srcRoads;
	
	private Map<Junction, Road> destRoads;
	
	private List<List<Vehicle>> qs;
	
	private Map<Road, List<Vehicle>> roadQueue;
	
	private int currGreen;
	
	private int lastSwitchingTime;
	
	private LightSwitchingStrategy lsStrategy;
	
	private DequeuingStrategy dqStrategy;
	
	private int xCoor, yCoor;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy 
			dqStrategy, int xCoor, int yCoor) {
		super(id);
		
		if(lsStrategy == null)
			throw new IllegalArgumentException("lsStrategy " + NULL_POINTER_MSG);
		if(dqStrategy == null)
			throw new IllegalArgumentException("dqStrategy " + NULL_POINTER_MSG);
		if(xCoor < 0 || yCoor < 0)
			throw new IllegalArgumentException(INVALID_POSITION);
		
		srcRoads = new ArrayList<Road>();
		destRoads = new HashMap<Junction, Road>();
		qs = new ArrayList<List<Vehicle>>();
		roadQueue = new HashMap<Road, List<Vehicle>>();
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
		roadQueue.put(r, queue);
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
		List<Vehicle>queue = roadQueue.get(v.getRoad());
		queue.add(v);
	}
	
	Road roadTo(Junction j) {
		return destRoads.get(j);
	}

	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
	}

	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("id", _id);
		jo.put("green", srcRoads.get(currGreen).getId());
		JSONArray ja = new JSONArray();
		for(Map.Entry<Road, List<Vehicle>> entry : roadQueue.entrySet()) {
			JSONArray vehicles = new JSONArray();
			for(Vehicle v: roadQueue.get(entry.getKey())) {
				vehicles.put(v.getId());
			}
			JSONObject roadQueue = new JSONObject();
			roadQueue.put(entry.getKey().getId(), vehicles);
			ja.put(roadQueue);
		}
		jo.put("queues", ja);
		return jo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) 
			return true;
        if (obj == null) 
        	return false;
        if (!(obj instanceof Junction)) 
        	return false;
        final Junction other = (Junction)obj;
        if(_id != other._id)
        	return false;
        return true;
	}
	
}
