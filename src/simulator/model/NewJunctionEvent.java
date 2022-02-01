package simulator.model;

public class NewJunctionEvent extends Event{

	private Junction jun;

	NewJunctionEvent(int time, String id, LighSwitchingStrategy 
						lsStrategy, DequeingStrategy dqStrategy, int xCoor, int yCoord) {
		super(time);
		jun = new Junction(id, lsStrategy, dqStrategy, xCoor, yCoord);
	}

	@Override
	void execute(RoadMap map) {
		map.addJunction(jun);	
	}
	
}
