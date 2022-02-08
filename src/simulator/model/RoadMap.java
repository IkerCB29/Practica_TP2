package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	
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
		if(junMap.containsKey(j.getId())) throw new IllegalArgumentException();

		junList.add(j);
		junMap.put(j.getId(), j);
	}

	void addRoad(Road r){
		if(roadMap.containsKey(r.getId()) || !junMap.containsKey(r.getSrc().getId())  || !junMap.containsKey(r.getDest().getId()))
			throw new IllegalArgumentException();
		
		roadList.add(r);
		roadMap.put(r.getId(), r);
	}

	//TODO Añadir comprobacion del punto 2 de esta funcion
	void addVehicle(Vehicle v){

		if(vehicleMap.containsKey(v.getId())) throw new IllegalArgumentException();

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
