package simulator.model;

import java.util.Map;

import simulator.misc.SortedArrayList;

public class RoadMap {
	private SortedArrayList<Junction> junList;
	private SortedArrayList<Road> roadList;
	private SortedArrayList<Vehicle> vehicleList;
	private Map<String, Junction> junMap;
	private Map<String, Road> roadMap;
	private Map<String, Vehicle> vehicleMap;

	RoadMap(){
		junList = new SortedArrayList<Junction>();
		roadList = new SortedArrayList<Road>();
		vehicleList = new SortedArrayList<Vehicle>();
		junMap = new Map<String, Junction>();
		roadMap = new Map<String, Road>();
		vehicleMap = new Map<String, Vehicle>();
	
	}

	void addJunction(Junction j){
		junList.add
	}





}
