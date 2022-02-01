package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

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
		junMap = new Map<String, Junction>();
		roadMap = new Map<String, Road>();
		vehicleMap = new Map<String, Vehicle>();
	
	}

	//TODO
	void addJunction(Junction j){
		junList.add(junList.size()-1, j);
	}

	void addRoad(Road r){
		if(roadMap.containsKey(r.getId()) || !junMap.containsKey(r.getSrc().getId())  || !junMap.containsKey(r.getDest().getId()))
			throw new IllegalArgumentException();
		
		roadList.add(roadList.size()-1, r);
		roadMap.put(r.getId(), r);
	}

	//TODO Añadir comprobacion del punto 2 de esta funcion
	void addVehicle(Vehicle v){

		if(vehicleMap.containsKey(v.getId()))

		vehicleList.add(vehicleList.size()-1, v);
		vehicleMap.put(v.getId(), v);
	}

	void reset(){
		junList.clear();
		roadList.clear();
		vehicleList.clear();
		junMap.clear();
		roadMap.clear();
		vehicleMap.clear();
	}

	public JSONObject report(){
		JSONObject jo = new JSONObject();
		jo.put("junctions", junList);
		jo.put("road", roadList);
		jo.put("vehicles", vehicleList);

		return jo;
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
	public List<Vehicle> getVehicle(){
		return Collections.unmodifiableList(vehicleList);
	}



}
