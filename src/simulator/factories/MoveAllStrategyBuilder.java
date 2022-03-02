package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy>{

	private final static String MOVE_ALL_DQS = "move_all_dqs";
	
	public MoveAllStrategyBuilder() {
		super(MOVE_ALL_DQS);
	}

	@Override
	protected DequeuingStrategy createTheInstance(JSONObject data) {
		return new MoveAllStrategy();
	}

}