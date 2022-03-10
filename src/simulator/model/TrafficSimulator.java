package simulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.json.JSONObject;
import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{

	private final int INITIAL_TICKS = 0;

	private RoadMap roads;
	private List<TrafficSimObserver> observers;
	private List<Event> events;
	private int ticks;

	public TrafficSimulator(){
		roads = new RoadMap();
		events = new SortedArrayList<Event>();
		observers = new ArrayList<TrafficSimObserver>();
		ticks = INITIAL_TICKS;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {		
		observers.add(o);
		
		for(TrafficSimObserver a : observers ) {
			a.onRegister(roads, events, ticks);
		}
		
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		observers.remove(o);
	}

	public void addEvent(Event e){
		events.add(e);
		for(TrafficSimObserver a : observers ) {
			a.onEventAdded(roads, events, e, ticks);
		}
		
	}
	
	public void advance(){
		ticks++;
		
		for(TrafficSimObserver a : observers ) {
			a.onAdvanceStart(roads, events, ticks);
		}
		
		executeEvents();
		advanceJunctions();
		advanceRoads();
		
		for(TrafficSimObserver a : observers ) {
			a.onAdvanceEnd(roads, events, ticks);
		}
	}

	void executeEvents(){
		List<Event> aux = new SortedArrayList<Event>();
		for(Event v : events){
			if(v.getTime() == ticks)
				v.execute(roads);
			else
				aux.add(v);
		}
		events = aux;
	}

	void advanceJunctions(){
		for(Junction j : roads.getJunctions()){
			j.advance(ticks);
		}
	}

	void advanceRoads(){
		for(Road r : roads.getRoads()){
			r.advance(ticks);
		}
	}

	public void reset(){
		roads.reset();
		events.clear();
		ticks = INITIAL_TICKS;
		
		for(TrafficSimObserver a : observers ) {
			a.onReset(roads, events, ticks);
		}
	}

	public JSONObject report(){
		JSONObject jo = new JSONObject();

		jo.put("time", ticks);
		jo.put("state", roads.report());
		return jo;
	}
	
	public List<Vehicle> getVehicles(){
		return roads.getVehicles();
	}
	
}
