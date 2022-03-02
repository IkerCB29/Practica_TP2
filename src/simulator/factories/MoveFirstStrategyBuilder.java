package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy>{

	private final static String MOVE_FIRST_DQS = "move_first_dqs";
	
	public MoveFirstStrategyBuilder() {
		super(MOVE_FIRST_DQS);
	}

	@Override
	protected DequeuingStrategy createTheInstance(JSONObject data) {
		return new MoveFirstStrategy();
	}

}
