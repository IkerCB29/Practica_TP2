package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event>{

	public SetContClassEventBuilder() {
		super("set_cont_class");
		
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		List<Pair<String, Integer>> ls = new ArrayList<>();
		JSONObject aux;
		for(int i = 0; i < data.getJSONArray("info").length(); i++){
			aux = data.getJSONArray("info").getJSONObject(i);
			
			ls.add(new Pair<String,Integer>(aux.getString("vehicle"), aux.getInt("class")));
		}

		return new SetContClassEvent(data.getInt("time"), ls);
	}
	
}
