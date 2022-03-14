package simulator.view;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SimulatedObject;
import simulator.model.Weather;

public class ChangeWeatherDialog extends ChangeConditionDialog{

	private final static String TITLE = "Change Road Weather";
	private final static String CHANGE_WEATHER_ICON_DIR = "resources\\icons\\weather.png";
	private final static String DESCRIPTION = " Schedule an event to change "
			+ "weather of a road after a given number of\n"
			+ " simulation ticks from now";
	
	private final static long serialVersionUID = 8182884865787377877L;
	
	ChangeWeatherDialog(Controller c, Window w) {
		super(c, w);
		this.setTitle(TITLE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(CHANGE_WEATHER_ICON_DIR));
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
	protected String setConditionSelectionText() {
		return " Weather ";
	}
	
	@Override
	protected Object[] getConditionValuesArray() {
		Object[] values = new Weather [Weather.values().length];
		int i = 0;
		for(Weather w : Weather.values()) {
			values[i] = w;
			i++;
		}
		return values;
	}

	@Override
	protected JButton createBuildEventButton() {
		JButton buildEvent = new JButton("OK");
		buildEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Pair<String, Weather>> ws = new ArrayList<>();
				ws.add(new Pair<String, Weather>(
						simulatedObjectSelection.getSelectedItem().toString(),
						(Weather) conditionSelection.getSelectedItem()
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
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		updateSimulatedObjectArray(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		updateSimulatedObjectArray(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		updateSimulatedObjectArray(map);
	}

	@Override
	public void onError(String err) {}

	private void updateSimulatedObjectArray(RoadMap map) {
		List<Road> roadList = map.getRoads();
		int size = roadList.size();
		ids = new Road[size];
		for(int i = 0; i < size; i++)
			ids[i] = roadList.get(i);
		simulatedObjectSelection.setModel(new DefaultComboBoxModel<SimulatedObject>(ids));
	}

}
