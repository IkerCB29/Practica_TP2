package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.SimulatedObject;
import simulator.model.TrafficSimObserver;

public abstract class ChangeConditionDialog extends JDialog implements TrafficSimObserver {

	private final static int TICKS_INI_VALUE = 1;
	private final static int TICKS_MIN_VALUE = 1;
	private final static int TICKS_MAX_VALUE = 99999;
	private final static int TICKS_INCREASE_VALUE = 1;
	
	private final static int WIDTH = 460;
	private final static int HEIGHT = 200;
	
	private final static long serialVersionUID = 3201187660527024578L;
	
	protected Controller ctrl;
	
	protected JToolBar selectOptions;
	protected JComboBox<SimulatedObject> simulatedObjectSelection;
	protected JComboBox<Object> conditionSelection;
	protected JSpinner ticksSelection;
	protected JButton buildEvent;
	protected JButton cancel;
	
	protected SimulatedObject [] ids;
	
	ChangeConditionDialog(Controller c, Window w) {
		super((Frame) w, true);
		ctrl = c;
		initGUI();
		c.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
			
		JTextArea description = new JTextArea(setDescription());
		description.setEditable(false);
		this.add(description, BorderLayout.NORTH);
			
		JToolBar selectOptions = new JToolBar();
		selectOptions.setFloatable(false);
			
		simulatedObjectSelection = createSimulatedObjectSelection ();
		selectOptions.add(new JLabel (setSimulatedObjectSelectionText()));
		selectOptions.add(simulatedObjectSelection);
			
		conditionSelection = createConditionSelection();
		selectOptions.add(new JLabel (setConditionSelectionText()));
		selectOptions.add(conditionSelection);
			
		ticksSelection = createTicksSelection();
		selectOptions.add(new JLabel ("  Ticks: "));
		selectOptions.add(ticksSelection);
			
		this.add(selectOptions, BorderLayout.CENTER);
			
		JPanel buttons = new JPanel();
		cancel = createCancelButton();
		buttons.add(cancel);
		buildEvent = createBuildEventButton();
		buttons.add(buildEvent);
		this.add(buttons, BorderLayout.SOUTH);
			
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.pack();
		this.setLocation(screenSize.width / 2 - WIDTH / 2, screenSize.height / 2 - HEIGHT / 2);
		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.setResizable(false);
		this.setVisible(false);
	}
	
	protected abstract String setDescription();

	protected abstract String setSimulatedObjectSelectionText();
	protected JComboBox<SimulatedObject> createSimulatedObjectSelection() {
		JComboBox<SimulatedObject> simulatedObjectSelection = new JComboBox<>();		
		simulatedObjectSelection.setMaximumSize(new Dimension(100,30));
		return simulatedObjectSelection;
	}
	
	protected abstract String setConditionSelectionText();
	protected JComboBox<Object> createConditionSelection() {
		JComboBox<Object> conditionSelection = new JComboBox<>(
				getConditionValuesArray()
		);		
		conditionSelection.setMaximumSize(new Dimension(100,30));
		return conditionSelection ;
	}
	protected abstract Object[] getConditionValuesArray();
	
	private JSpinner createTicksSelection() {
		JSpinner ticksSelection = new JSpinner(
				new SpinnerNumberModel(
						TICKS_INI_VALUE,
						TICKS_MIN_VALUE,
						TICKS_MAX_VALUE,
						TICKS_INCREASE_VALUE
		));
		ticksSelection.setMaximumSize(new Dimension(100,30));
		return ticksSelection;
	}
	
	private JButton createCancelButton() {
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeConditionDialog.this.closeWindow();
			}
		});
		return cancel;
	}
	
	private void closeWindow() {
		this.setVisible(false);
	}
	
	protected abstract JButton createBuildEventButton();
	
	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		closeWindow();
	}
	
}
