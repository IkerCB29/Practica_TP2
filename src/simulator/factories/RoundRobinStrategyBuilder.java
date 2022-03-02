package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> 
{
	private final static String ROUND_ROBIN_LSS = "round_robin_lss";
	
	private final static String TIME_SLOT_KEY = "timeslot";
	
	public RoundRobinStrategyBuilder() {
		super(ROUND_ROBIN_LSS);
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int timeSlot = 1;
		if(data.has(TIME_SLOT_KEY))
			timeSlot = data.getInt(TIME_SLOT_KEY);
		return new RoundRobinStrategy(timeSlot);
	}

}
