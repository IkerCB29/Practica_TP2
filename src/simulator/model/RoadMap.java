package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	
	private final static String JUNCTION_IN_MAP = "junction is already in map";
	private final static String ROAD_IN_MAP = "road is already in map";
	private final static String INVALID_JUNCTION = "junction is not valid";
	private final static String VEHICLE_IN_MAP = "vehicle is already in map";
	private final static String NO_ROAD = "no road between junctions";
	
	private List<Junction> junList;
	private List<Road> roadList;
	private List<Vehicle> vehicleList;
	private Map<String, Junction> junMap;
	private Map<String, Road> roadMap;
	private Map<String, Vehicle> vehicleMap;

	RoadMap(){
		junList = new ArrayList<>();
		roadList = new ArrayList<>();
		vehicleList = new ArrayList<>();
		junMap = new HashMap<>();
		roadMap = new HashMap<>();
		vehicleMap = new HashMap<>();
	}

	void addJunction(Junction j){
		if(junMap.containsKey(j.getId())) throw new IllegalArgumentException(j.getId() + " : " + JUNCTION_IN_MAP);

		junList.add(j);
		junMap.put(j.getId(), j);
	}

	void addRoad(Road r){
		if(roadMap.containsKey(r.getId()))
			throw new IllegalArgumentException(r.getId() + " : " + ROAD_IN_MAP);
		if(!junMap.containsKey(r.getSrc().getId()))
			throw new IllegalArgumentException("Src junction : " + INVALID_JUNCTION);
		if(!junMap.containsKey(r.getDest().getId()))
			throw new IllegalArgumentException("Dest junction : " + INVALID_JUNCTION);
		roadList.add(r);
		roadMap.put(r.getId(), r);
	}

	void addVehicle(Vehicle v){

		if(vehicleMap.containsKey(v.getId())) 
			throw new IllegalArgumentException(v.getId() + " : " + VEHICLE_IN_MAP);
		for(int i = 0; i < v.getItinerary().size() - 1; i++) {
			if(v.getItinerary().get(i).roadTo(v.getItinerary().get(i+1)) == null)
				throw new IllegalArgumentException(NO_ROAD + " " + v.getItinerary().get(i) + "-" +
						v.getItinerary().get(i + 1));
		}
		vehicleList.add(v);
		vehicleMap.put(v.getId(), v);
	}

	public JSONObject report(){
		JSONObject jo = new JSONObject();
		jo.put("junctions", junListReport());
		jo.put("roads", roadListReport());
		jo.put("vehicles", vehicleListReport());

		return jo;
	}

	private JSONArray junListReport(){
		JSONArray ja = new JSONArray();
		for(Junction d : junList) ja.put(d.report());
		return ja;
	}

	private JSONArray roadListReport(){
		JSONArray ja = new JSONArray();
		for(Road r : roadList) ja.put(r.report());
		return ja;
	}

	private JSONArray vehicleListReport(){
		JSONArray ja = new JSONArray();
		for(Vehicle v : vehicleList) ja.put(v.report());
		return ja;
	}

	void reset(){
		junList.clear();
		roadList.clear();
		vehicleList.clear();
		junMap.clear();
		roadMap.clear();
		vehicleMap.clear();
	}

	public Junction getJunction(String j){
		return junMap.get(j);
	}
	public Road getRoad(String r){
		return roadMap.get(r);
	}
	public Vehicle getVehicle(String v){
		return vehicleMap.get(v);
	}
	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(junList);
	}
	public List<Road> getRoads(){
		return Collections.unmodifiableList(roadList);
	}
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(vehicleList);
		
	}
}
