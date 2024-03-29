package simulator.model;

import java.util.List;

import org.json.JSONObject;
import simulator.misc.SortedArrayList;

public class TrafficSimulator {

	private final int INITIAL_TICKS = 0;

	private RoadMap roads;
	private List<Event> events;
	private int ticks;

	public TrafficSimulator(){
		roads = new RoadMap();
		events = new SortedArrayList<Event>();
		ticks = INITIAL_TICKS;
	}

	public void addEvent(Event e){
		events.add(e);
	}
	public void advance(){
		ticks++;
		executeEvents();
		advanceJunctions();
		advanceRoads();
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
	}

	public JSONObject report(){
		JSONObject jo = new JSONObject();

		jo.put("time", ticks);
		jo.put("state", roads.report());
		return jo;
	}
}
