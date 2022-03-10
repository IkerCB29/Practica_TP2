package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ChangeCO2ClassDialog extends JDialog implements TrafficSimObserver{
	
	private final static String TITLE = "Change CO2 Class";
	private final static boolean MODAL = true;
	
	private final static String DESCRIPTION = " Schedule an event to change "
			+ "the CO2 class of a vehicle after a given number of\n"
			+ " simulation ticks from now";

	private final static int CONTAMINATION_INI_VALUE = 1;
	private final static int CONTAMINATION_MIN_VALUE = 1;
	private final static int CONTAMINATION_MAX_VALUE = 10;
	private final static int CONTAMINATION_INCREASE_VALUE = 1;
	
	private final static int TICKS_INI_VALUE = 1;
	private final static int TICKS_MIN_VALUE = 1;
	private final static int TICKS_MAX_VALUE = 99999;
	private final static int TICKS_INCREASE_VALUE = 1;
	
	private final static int WIDTH = 460;
	private final static int HEIGHT = 200;
	
	private Controller ctrl;
	private JSpinner vehicleSelection;
	private JSpinner CO2Selection;
	private JSpinner ticksSelection;
	private JButton buildEvent;
	private JButton cancel;
	
	private static final long serialVersionUID = -8875771187454739902L;
	
	public ChangeCO2ClassDialog (Controller c, JFrame f) {
		super(f, TITLE, MODAL);
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
		closeWindow();
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
		try {
			this.setLayout(new BorderLayout());
			
			JTextArea description = new JTextArea(DESCRIPTION);
			description.setEditable(false);
			this.add(description, BorderLayout.NORTH);
			
			JToolBar selectOptions = new JToolBar();
			selectOptions.setFloatable(false);
			
			createVehicleSelection();
			selectOptions.add(new JLabel (" Vehicle: "));
			selectOptions.add(vehicleSelection);
			
			createCO2Selection();
			selectOptions.add(new JLabel ("  CO2: "));
			selectOptions.add(CO2Selection);
			
			createTicksSelection();
			selectOptions.add(new JLabel ("  Ticks: "));
			selectOptions.add(ticksSelection);
			
			this.add(selectOptions, BorderLayout.CENTER);
			
			JPanel buttons = new JPanel();
			createCancelButton();
			buttons.add(cancel);
			createBuildEventButton();
			buttons.add(buildEvent);
			this.add(buttons, BorderLayout.SOUTH);
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			this.pack();
			this.setLocation(screenSize.width / 2 - WIDTH / 2, screenSize.height / 2 - HEIGHT / 2);
			this.setSize(new Dimension(WIDTH, HEIGHT));
			this.setResizable(false);
			this.setVisible(true);
		}
		catch(IllegalArgumentException err) {
			JOptionPane.showMessageDialog(null, "No vehicle to set contamination");
		}
	}
	
	private void createVehicleSelection() {
		vehicleSelection = new JSpinner(
				new SpinnerListModel(
					ctrl.getVehicles()
		));
		vehicleSelection.setMaximumSize(new Dimension(100,30));
	}
	
	private void createCO2Selection() {
		CO2Selection = new JSpinner(
				new SpinnerNumberModel(
						CONTAMINATION_INI_VALUE,
						CONTAMINATION_MIN_VALUE,
						CONTAMINATION_MAX_VALUE,
						CONTAMINATION_INCREASE_VALUE
		));
		CO2Selection.setMaximumSize(new Dimension(100,30));
	}
	
	private void createTicksSelection() {
		ticksSelection = new JSpinner(
				new SpinnerNumberModel(
						TICKS_INI_VALUE,
						TICKS_MIN_VALUE,
						TICKS_MAX_VALUE,
						TICKS_INCREASE_VALUE
		));
		ticksSelection.setMaximumSize(new Dimension(100,30));
	}
	
	private void createBuildEventButton() {
		buildEvent = new JButton("OK");
		buildEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Pair<String, Integer>> cs = new ArrayList<>();
				cs.add(new Pair<String, Integer>(
						vehicleSelection.getValue().toString(),
						Integer.parseInt(CO2Selection.getValue().toString())
				));
				ctrl.addChangeCO2Event(
						Integer.parseInt(ticksSelection.getValue().toString()), 
						cs
				);
			}
		});
	}
	
	private void createCancelButton() {
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeCO2ClassDialog.this.closeWindow();
			}
		});
	}
	
	private void closeWindow() {
		ChangeCO2ClassDialog.this.setVisible(false);
		ChangeCO2ClassDialog.this.dispose();
	}
	
}
