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
import simulator.model.RoadMap;
import simulator.model.SimulatedObject;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends ChangeConditionDialog{
	
	private final static String TITLE = "Change CO2 Class";
	private final static String CHANGE_CONT_ICON_DIR = "resources\\icons\\co2class.png";
	private final static String DESCRIPTION = " Schedule an event to change "
			+ "the CO2 class of a vehicle after a given number of\n"
			+ " simulation ticks from now";

	private final static int CO2_NUM_VALUES = 10;
	
	private final static long serialVersionUID = -8875771187454739902L;
	
	ChangeCO2ClassDialog (Controller c, Window w) {
		super(c, w);
		this.setTitle(TITLE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(CHANGE_CONT_ICON_DIR));
	}

	@Override
	protected String setDescription() {
		return DESCRIPTION;
	}

	@Override
	protected String setSimulatedObjectSelectionText() {
		return " Vehicle: ";
	}

	@Override
	protected String setConditionSelectionText() {
		return " CO2 : ";
	}
	
	@Override
	protected Object[] getConditionValuesArray() {
		Object[] values = new Integer [CO2_NUM_VALUES];
		for(int i = 0; i < CO2_NUM_VALUES; i++) {
			values[i] = new Integer(i + 1);
		}
		return values;
	}

	@Override
	protected JButton createBuildEventButton() {
		JButton buildEvent = new JButton("OK");
		buildEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Pair<String, Integer>> cs = new ArrayList<>();
				cs.add(new Pair<String, Integer>(
						simulatedObjectSelection.getSelectedItem().toString(),
						(Integer) conditionSelection.getSelectedItem()
				));
				ctrl.addChangeCO2Event(
						Integer.parseInt(ticksSelection.getValue().toString()), 
						cs
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
		List<Vehicle> vehicleList = map.getVehicles();
		int size = vehicleList.size();
		ids = new Vehicle[size];
		for(int i = 0; i < size; i++)
			ids[i] = vehicleList.get(i);
		simulatedObjectSelection.setModel(new DefaultComboBoxModel<SimulatedObject>(ids));
	}
	
}
