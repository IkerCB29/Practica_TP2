package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {

	private TrafficSimulator trafficSimulator;
	private Factory<Event> eventsFactory;

	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory){
		if(sim == null) throw new IllegalArgumentException();
		if(eventsFactory == null) throw new IllegalArgumentException();
		
		trafficSimulator = sim;
		this.eventsFactory = eventsFactory;
	}

	//TODO: Mirar lo de la excepcion
	public void loadEvents(InputStream in){
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray ja = jo.getJSONArray("events");
		for(int i = 0; i < ja.length(); i++){
			trafficSimulator.addEvent(eventsFactory.createInstance(ja.getJSONObject(i)));
		}
	}

	//TODO, METER EN EL OUT revisar
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
}
