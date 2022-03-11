package simulator.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends ChangeConditionDialog{
	
	private final static String TITLE = "Change CO2 Class";
	private final static String CHANGE_CONT_ICON_DIR = "resources\\icons\\co2class.png";
	private final static String DESCRIPTION = " Schedule an event to change "
			+ "the CO2 class of a vehicle after a given number of\n"
			+ " simulation ticks from now";

	private final static int CO2_NUM_VALUES = 10;
	
	private final static long serialVersionUID = -8875771187454739902L;
	
	ChangeCO2ClassDialog (Controller c, JFrame f) {
		super(c, f);
		this.setTitle(TITLE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(CHANGE_CONT_ICON_DIR));
		this.setVisible(true);
	}

	@Override
	protected String setDescription() {
		return DESCRIPTION;
	}

	@Override
	protected String setSimulatedObjectSelectionText() {
		return " Vehicle: ";
	}
	
	protected String [] getSimulatedObjectIdsArray() {
		List<Vehicle> vehicleList = ctrl.getVehicles();
		int size = vehicleList.size();
		String [] ids = new String [size];
		for(int i = 0; i < size; i++)
			ids[i] = vehicleList.get(i).getId();
		return ids;
	}

	@Override
	protected String setConditionSelectionText() {
		return " CO2 : ";
	}
	
	@Override
	protected String[] getConditionValuesArray() {
		String[] values = new String [CO2_NUM_VALUES];
		for(int i = 0; i < CO2_NUM_VALUES; i++) {
			values[i] = Integer.toString(i + 1);
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
						Integer.parseInt(conditionSelection.getSelectedItem().toString())
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
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onError(String err) {}
	
}
