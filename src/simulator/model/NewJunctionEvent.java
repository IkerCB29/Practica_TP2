package simulator.model;

public class NewJunctionEvent extends Event{

	private Junction jun;
	private int xCoor, yCoord;
	private String id;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;

	public NewJunctionEvent(int time, String id, LightSwitchingStrategy 
						lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoord) {
		super(time);
		
	}

	@Override
	void execute(RoadMap map) {
		jun = new Junction(id, lsStrategy, dqStrategy, xCoor, yCoord);
		map.addJunction(jun);	
	}
	@Override
	public String toString() {
		return "New Junction '" + id + "'";
	}
	
}
