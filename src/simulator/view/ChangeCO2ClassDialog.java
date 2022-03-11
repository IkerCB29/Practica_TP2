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
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.RoadMap;

public class ChangeCO2ClassDialog extends ChangeConditionDialog{
	
	private final static String TITLE = "Change CO2 Class";
	
	private final static String DESCRIPTION = " Schedule an event to change "
			+ "the CO2 class of a vehicle after a given number of\n"
			+ " simulation ticks from now";

	private final static int CONTAMINATION_INI_VALUE = 1;
	private final static int CONTAMINATION_MIN_VALUE = 1;
	private final static int CONTAMINATION_MAX_VALUE = 10;
	private final static int CONTAMINATION_INCREASE_VALUE = 1;
	
	private static final long serialVersionUID = -8875771187454739902L;
	
	public ChangeCO2ClassDialog (Controller c, JFrame f) {
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
		return " Vehicle: ";
	}

	@Override
	protected JSpinner createSimulatedObjectSelection() {
		JSpinner vehicleSelection = new JSpinner(
				new SpinnerListModel(
					ctrl.getVehicles()
		));
		vehicleSelection.setMaximumSize(new Dimension(100,30));
		return vehicleSelection;
	}

	@Override
	protected String setConditionSelectionText() {
		return " CO2 : ";
	}

	@Override
	protected JSpinner createConditionSelection() {
		JSpinner CO2Selection = new JSpinner(
				new SpinnerNumberModel(
						CONTAMINATION_INI_VALUE,
						CONTAMINATION_MIN_VALUE,
						CONTAMINATION_MAX_VALUE,
						CONTAMINATION_INCREASE_VALUE
		));
		CO2Selection.setMaximumSize(new Dimension(100,30));
		return CO2Selection;
	}

	@Override
	protected JButton createBuildEventButton() {
		JButton buildEvent = new JButton("OK");
		buildEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Pair<String, Integer>> cs = new ArrayList<>();
				cs.add(new Pair<String, Integer>(
						simulatedObjectSelection.getValue().toString(),
						Integer.parseInt(conditionSelection.getValue().toString())
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
