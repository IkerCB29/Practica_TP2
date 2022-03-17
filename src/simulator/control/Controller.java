package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;
import simulator.model.Weather;

public class Controller {

	private final static String NO_TRAFFIC_SIMULATOR = "Traffic simulator not initialized";
	private final static String NO_EVENT_FACTORY = "Event factory not initialized";
	
	private TrafficSimulator trafficSimulator;
	private Factory<Event> eventsFactory;

	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory){
		if(sim == null) throw new IllegalArgumentException(NO_TRAFFIC_SIMULATOR );
		if(eventsFactory == null) throw new IllegalArgumentException(NO_EVENT_FACTORY);
		
		trafficSimulator = sim;
		this.eventsFactory = eventsFactory;
	}

	public void loadEvents(InputStream in){
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray ja = jo.getJSONArray("events");
		for(int i = 0; i < ja.length(); i++){
			trafficSimulator.addEvent(eventsFactory.createInstance(ja.getJSONObject(i)));
		}
	}

	public void run(int n) {
		for(int i = 0; i < n; i++) {
			trafficSimulator.advance();
		}
	}
	
	public void run(int n, OutputStream out){
		JSONArray ja = new JSONArray();
		for(int i = 0; i < n; i++){
			trafficSimulator.advance();
			ja.put(trafficSimulator.report());
		}
		JSONObject jo = new JSONObject();
		jo.put("states", ja);
		PrintStream p = new PrintStream(out);
		p.print(jo.toString());
	}

	public void reset(){
		trafficSimulator.reset();
	}
	
	public void addObserver(TrafficSimObserver o) {		
		trafficSimulator.addObserver(o);
	}
	
	public void removeObserver(TrafficSimObserver o) {
		trafficSimulator.removeObserver(o);
	}
	
	public void addChangeCO2Event(int time, List<Pair<String, Integer>> cs) {
		trafficSimulator.addEvent(
				new SetContClassEvent(time + trafficSimulator.getTicks(), cs)
		);
	}
	
	public void addChangeWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		trafficSimulator.addEvent(
				new SetWeatherEvent(time + trafficSimulator.getTicks(), ws)
		);
	}
	
}
