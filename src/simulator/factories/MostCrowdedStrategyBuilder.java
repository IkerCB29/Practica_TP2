package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

	private static final String MOST_CROWDED_LSS = "most_crowded_lss";
	
	private static final String TIME_SLOT_KEY = "timeslot";
	
	public MostCrowdedStrategyBuilder() {
		super(MOST_CROWDED_LSS);
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int timeSlot = data.getInt(TIME_SLOT_KEY);
		return new MostCrowdedStrategy(timeSlot);
	}

}
