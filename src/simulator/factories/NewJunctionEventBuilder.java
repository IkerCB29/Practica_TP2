package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event>{

	private Factory<LightSwitchingStrategy> lssF;
	private Factory<DequeuingStrategy> dqF;

	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction");
		lssF = lssFactory;
		dqF = dqsFactory;
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		JSONArray coord = data.getJSONArray("coor");
		int x = coord.getInt(0);
		int y = coord.getInt(1);

		return new NewJunctionEvent(time, id, lssF.createInstance(data.getJSONObject("ls_strategy")),	dqF.createInstance(data.getJSONObject("dq_strategy")), x, y);

	}
	
}
