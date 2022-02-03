package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event>{

	SetContClassEventBuilder() {
		super("set_cont_class");
		
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		List<Pair<String, Integer>> ls = new ArrayList<>();
		JSONObject aux;
		for(int i = 0; i < data.getJSONArray("info").length(); i++){
			aux = data.getJSONArray("info").getJSONObject(i);
			
			ls.add(new Pair<String,Integer>(aux.getString("road"), aux.getInt("class")));
		}

		return new NewSetContClassEvent(data.getInt("time"), ls);
	}
	
}
