package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;


public class ControlPanel extends JPanel implements TrafficSimObserver{

	private final static String FILE_NOT_FOUND = "File not found";
	private final static String LOAD_EVENTS_ERROR_MESSAGE = "Error loading events. Check file is correct";
	
	private final static String BASE_DIRECTORY_PATH = "resources";
	private final static String LOAD_EVENTS_ICON_DIR = BASE_DIRECTORY_PATH + "\\icons\\open.png";
	private final static String CHANGE_CONT_ICON_DIR =  BASE_DIRECTORY_PATH + "\\icons\\co2class.png";
	private final static String CHANGE_WEATHER_ICON_DIR = BASE_DIRECTORY_PATH + "\\icons\\weather.png";
	private final static String START_ICON_DIR = BASE_DIRECTORY_PATH + "\\icons\\run.png";
	private final static String STOP_ICON_DIR = BASE_DIRECTORY_PATH + "\\icons\\stop.png";
	private final static String EXIT_ICON_DIR = BASE_DIRECTORY_PATH + "\\icons\\exit.png";
	
	private final static int TICKS_INI_VALUE = 1;
	private final static int TICKS_MIN_VALUE = 1;
	private final static int TICKS_MAX_VALUE = 99999;
	private final static int TICKS_INCREASE_VALUE = 1;
	
	private final static int DELAY_INI_VALUE = 0;
	private final static int DELAY_MIN_VALUE = 0;
	private final static int DELAY_MAX_VALUE = 1000;
	private final static int DELAY_INCREASE_VALUE = 1;
	
	private RunWorker runWorker;
	
	private Controller ctrl;
	private ChangeCO2ClassDialog changeCO2ClassDialog;
	private ChangeWeatherDialog changeWeatherDialog;
	private JButton loadEvents;
	private JButton changeContamination;
	private JButton changeWeatherCondition;
	private JButton start;
	private JButton stop;
	private JSpinner ticksSelection;
	private JSpinner delaySelection;
	private JButton exit;
	
	private int numVehicles;
	private int numRoads;
	
	private final static long serialVersionUID = -4423199850333010661L;

	ControlPanel(Controller c) {
		c.addObserver(this);
		ctrl = c;
		changeCO2ClassDialog = new ChangeCO2ClassDialog(
				ctrl,
				SwingUtilities.getWindowAncestor(this)
		);
		changeWeatherDialog = new ChangeWeatherDialog(
				ctrl,
				SwingUtilities.getWindowAncestor(this)
		);
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		runWorker = null;
		
		JToolBar controls = new JToolBar();
		controls.setFloatable(false);
		
		loadEvents = createLoadEventsButton();
		controls.add(loadEvents);
		controls.addSeparator();
		
		changeContamination = createChangeContaminationButton();
		controls.add(changeContamination);
		changeWeatherCondition = createChangeWeatherConditionButton();
		controls.add(changeWeatherCondition);
		controls.addSeparator();
		
		start = createStartButton();
		controls.add(start);
		stop = createStopButton();
		controls.add(stop);
		ticksSelection = createTicksSelection();
		controls.add(new JLabel("  Ticks: "));
		controls.add(ticksSelection);
		delaySelection = createDelaySelection();
		controls.add(new JLabel("  Delay: "));
		controls.add(delaySelection);
		controls.add(new JSeparator(SwingConstants.VERTICAL));
		
		exit = createExitButton();
		controls.add(exit);
		
		this.add(controls, BorderLayout.CENTER);
	}
		
	private JButton createLoadEventsButton() {
		JButton loadEvents = new JButton();
		loadEvents.setIcon(new ImageIcon(LOAD_EVENTS_ICON_DIR));
		loadEvents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(BASE_DIRECTORY_PATH);
		        fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSON file", "json"));
				int select = fileChooser.showOpenDialog(ControlPanel.this);
				if(select == JFileChooser.APPROVE_OPTION) {
					try {
						File file = fileChooser.getSelectedFile();
						ctrl.reset();
						ctrl.loadEvents(new FileInputStream(file));
					} 
					catch (FileNotFoundException fileErr) {
						JOptionPane.showMessageDialog(null, FILE_NOT_FOUND);
					}
					catch (Exception err) {
						JOptionPane.showMessageDialog(null, LOAD_EVENTS_ERROR_MESSAGE);
						ctrl.reset();
					}
				}
			}
		});
		return loadEvents;
	}

	private JButton createChangeContaminationButton() {
		JButton changeContamination = new JButton();
		changeContamination.setIcon(new ImageIcon(CHANGE_CONT_ICON_DIR));
		changeContamination.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(numVehicles > 0)
					changeCO2ClassDialog.setVisible(true);
				else
					JOptionPane.showMessageDialog(null, "No vehicles to change contamination");
			}
		});
		return changeContamination;
	}
	
	private JButton createChangeWeatherConditionButton() {
		JButton changeWeatherCondition = new JButton();
		changeWeatherCondition.setIcon(new ImageIcon(CHANGE_WEATHER_ICON_DIR));
		changeWeatherCondition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(numRoads > 0)
					changeWeatherDialog.setVisible(true);
				else
					JOptionPane.showMessageDialog(null, "No roads to change weather");
			}
		});
		return changeWeatherCondition;
	}
	
	private JButton createStartButton() {
		JButton start = new JButton();
		start.setIcon(new ImageIcon(START_ICON_DIR));
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enableToolBar(false);
				runWorker = new RunWorker((Integer) ticksSelection.getValue(), (Integer) delaySelection.getValue());
				runWorker.execute();
			}
		});
		return start;
	}
	
	private void enableToolBar(boolean state) {
		loadEvents.setEnabled(state);;
		changeContamination.setEnabled(state);
		changeWeatherCondition.setEnabled(state);
		start.setEnabled(state);
		ticksSelection.setEnabled(state);
		delaySelection.setEnabled(state);
		exit.setEnabled(state);
	}
	
	private JButton createStopButton() {
		JButton stop = new JButton();
		stop.setIcon(new ImageIcon(STOP_ICON_DIR));
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		return stop;
	}
	
	private void stop() {
		if(runWorker != null) {
			runWorker.cancel(true);
			runWorker = null;
		}
	}
	
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
	
	private JSpinner createDelaySelection() {
		JSpinner delaySelection = new JSpinner(
				new SpinnerNumberModel(
						DELAY_INI_VALUE,
						DELAY_MIN_VALUE,
						DELAY_MAX_VALUE,
						DELAY_INCREASE_VALUE
		));
		delaySelection.setMaximumSize(new Dimension(100,30));
		return delaySelection;
	}
	
	private JButton createExitButton() {
		JButton exit = new JButton();
		exit.setIcon(new ImageIcon(EXIT_ICON_DIR));
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(
						null, 
						"Do you want to close the simulation", 
						"Exit", 
						JOptionPane.YES_NO_OPTION
				);
				if(action == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		return exit;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		getNumSimulatedObjects(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		getNumSimulatedObjects(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		getNumSimulatedObjects(map);
	}

	@Override
	public void onError(String err) {}
	
	private void getNumSimulatedObjects(RoadMap map) {
		numVehicles = map.getVehicles().size();
		numRoads = map.getRoads().size();
	}
	
	private class RunWorker extends SwingWorker<Void, Void> {
		private int ticks;
		private int delay;
		
		public RunWorker(int ticks, int delay) {
			this.ticks = ticks;
			this.delay = delay;
		}
		
		@Override
		protected Void doInBackground() throws Exception {
			while(ticks > 0 && !Thread.interrupted()) {
				try {
					ctrl.run(1);
					Thread.sleep(delay);
				} 
				catch (Exception e) {
					break;
				}
				--ticks;
			}
			return null;
		}
		
		@Override
		protected void done() {
			enableToolBar(true);
		}
	}
	
}
