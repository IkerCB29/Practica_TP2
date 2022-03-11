package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
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
	
	protected JComboBox<String> simulatedObjectSelection;
	protected JComboBox<String> conditionSelection;
	protected JSpinner ticksSelection;
	protected JButton buildEvent;
	protected JButton cancel;
	
	ChangeConditionDialog(Controller c, JFrame f) {
		super(f, true);
		ctrl = c;
		c.addObserver(this);
		initGUI();
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
	}
	
	protected abstract String setDescription();

	protected abstract String setSimulatedObjectSelectionText();
	protected JComboBox<String> createSimulatedObjectSelection() {
		JComboBox<String> simulatedObjectSelection = new JComboBox<>(
				getSimulatedObjectIdsArray()
		);		
		simulatedObjectSelection.setMaximumSize(new Dimension(100,30));
		return simulatedObjectSelection;
	}
	protected abstract String[] getSimulatedObjectIdsArray();
	
	protected abstract String setConditionSelectionText();
	protected JComboBox<String> createConditionSelection() {
		JComboBox<String> conditionSelection = new JComboBox<>(
				getConditionValuesArray()
		);		
		conditionSelection.setMaximumSize(new Dimension(100,30));
		return conditionSelection ;
	}
	protected abstract String[] getConditionValuesArray();
	
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
		this.dispose();
	}
	
	protected abstract JButton createBuildEventButton();
	
	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		closeWindow();
	}
	
}
