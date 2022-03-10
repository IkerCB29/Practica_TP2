package simulator.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver{

	private static final long serialVersionUID = -7853057030893328962L;

	private Controller ctrl;
	private JLabel timeValue;
	private JLabel eventInfo;
	
	public StatusBar(Controller c) {
		ctrl = c;
		initGUI();
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		JPanel timeInfo = new JPanel();
		JLabel timeText = new JLabel(" Time: ");
		timeInfo .add(timeText);
		timeValue = new JLabel("70");
		timeInfo .add(timeValue);

		this.add(timeInfo, BorderLayout.WEST);
		
		eventInfo = new JLabel("La informacion del evento  ");
		this.add(eventInfo, BorderLayout.EAST);
	}

}
