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

	private JLabel timeValue;
	private JLabel eventInfo;
	
	StatusBar(Controller c) {
		initGUI();
		c.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		JPanel timeInfo = new JPanel();
		JLabel timeText = new JLabel(" Time: ");
		timeInfo.add(timeText);
		timeValue = new JLabel("0");
		timeInfo.add(timeValue);

		this.add(timeInfo, BorderLayout.WEST);
		
		eventInfo = new JLabel();
		this.add(eventInfo, BorderLayout.EAST);
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		timeValue.setText(Integer.toString(time));
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		eventInfo.setText("Event added (" + e.toString() + ")  ");
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onError(String err) {}

}
