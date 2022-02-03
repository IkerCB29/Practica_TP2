package simulator.model;

public class NewJunctionEvent extends Event{

	private Junction jun;

	public NewJunctionEvent(int time, String id, LightSwitchingStrategy 
						lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoord) {
		super(time);
		jun = new Junction(id, lsStrategy, dqStrategy, xCoor, yCoord);
	}

	@Override
	void execute(RoadMap map) {
		map.addJunction(jun);	
	}
	
}
