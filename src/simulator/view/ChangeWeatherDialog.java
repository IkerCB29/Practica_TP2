package simulator.view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSpinner;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;

public class ChangeWeatherDialog extends ChangeConditionDialog{

	private final static String TITLE = "Change Road Weather";
	
	private final static String DESCRIPTION = " Schedule an event to change "
			+ "weather of a road after a given number of\n"
			+ " simulation ticks from now";
	
	private static final long serialVersionUID = 8182884865787377877L;
	
	public ChangeWeatherDialog(Controller c, JFrame f) {
		super(c, f);
		this.setTitle(TITLE);
		this.setVisible(true);
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onError(String err) {}

	@Override
	protected String setDescription() {
		return DESCRIPTION;
	}

	@Override
	protected String setSimulatedObjectSelectionText() {
		return " Road: ";
	}

	@Override
	protected JSpinner createSimulatedObjectSelection() {
		return new JSpinner();
	}

	@Override
	protected String setConditionSelectionText() {
		return " Weather ";
	}

	@Override
	protected JSpinner createConditionSelection() {
		return new JSpinner();
	}

	@Override
	protected JButton createBuildEventButton() {
		return new JButton();
	}

}
