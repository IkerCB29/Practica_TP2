package simulator.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.Weather;

public class ChangeWeatherDialog extends ChangeConditionDialog{

	private final static String TITLE = "Change Road Weather";
	private final static String DESCRIPTION = " Schedule an event to change "
			+ "weather of a road after a given number of\n"
			+ " simulation ticks from now";
	
	private final static long serialVersionUID = 8182884865787377877L;
	
	public ChangeWeatherDialog(Controller c, JFrame f) {
		super(c, f);
		this.setTitle(TITLE);
		this.setVisible(true);
	}

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
		JSpinner roadSelection = new JSpinner(
				new SpinnerListModel(
						ctrl.getRoads()
		));
		roadSelection.setMaximumSize(new Dimension(100,30));
		return roadSelection;
	}

	@Override
	protected String setConditionSelectionText() {
		return " Weather ";
	}

	@Override
	protected JSpinner createConditionSelection() {
		JSpinner WeatherSelection = new JSpinner(
				new SpinnerListModel(
						Weather.values()
		));
		WeatherSelection.setMaximumSize(new Dimension(100,30));
		return WeatherSelection;
	}

	@Override
	protected JButton createBuildEventButton() {
		JButton buildEvent = new JButton("OK");
		buildEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Pair<String, Weather>> ws = new ArrayList<>();
				ws.add(new Pair<String, Weather>(
						simulatedObjectSelection.getValue().toString(),
						Weather.valueOf(conditionSelection.getValue().toString())
				));
				ctrl.addChangeWeatherEvent(
						Integer.parseInt(ticksSelection.getValue().toString()), 
						ws
				);
			}
		});
		return buildEvent;
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

}
