package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{

	private int timeSlot;
	
	public MostCrowdedStrategy(int timeSlot){
		this.timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if(roads.size() == 0)
			return -1;
		if(currGreen == -1)
			return mostCrowdedPos(0, qs);
		if(currTime - lastSwitchingTime < timeSlot)
			return currGreen;
		return mostCrowdedPos((currGreen + 1) % roads.size(), qs);
	}
	
	private int mostCrowdedPos(int iniPos, List<List<Vehicle>> qs) {
		int mostCrowdedPos = iniPos;
		for(int i = 1; i < qs.size(); i++) {
			int look = (iniPos + i) % qs.size();
			if(qs.get(mostCrowdedPos).size() < qs.get(look).size())
				mostCrowdedPos = look;
		}
		return mostCrowdedPos;
	}
	

}
