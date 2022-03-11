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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver{

	private final static String LOAD_EVENTS_ICON_DIR = "resources\\icons\\open.png";
	private final static String CHANGE_CONT_ICON_DIR = "resources\\icons\\co2class.png";
	private final static String CHANGE_WEATHER_ICON_DIR = "resources\\icons\\weather.png";
	private final static String START_ICON_DIR = "resources\\icons\\run.png";
	private final static String STOP_ICON_DIR = "resources\\icons\\stop.png";
	private final static String EXIT_ICON_DIR = "resources\\icons\\exit.png";
	
	private final static int TICKS_INI_VALUE = 1;
	private final static int TICKS_MIN_VALUE = 1;
	private final static int TICKS_MAX_VALUE = 99999;
	private final static int TICKS_INCREASE_VALUE = 1;
	
	private Controller ctrl;

	private JFrame frame;
	private JButton loadEvents;
	private JButton changeContamination;
	private JButton changeWeatherCondition;
	private JButton start;
	private JButton stop;
	private JSpinner ticksSelection;
	private JButton exit;
	
	private final static long serialVersionUID = -4423199850333010661L;

	public ControlPanel(Controller c, JFrame f) {
		ctrl = c;
		frame = f;
		initGUI();
		c.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
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
		ticksSelection = createTicksButton();
		controls.add(new JLabel(" Ticks: "));
		controls.add(ticksSelection);
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
				JFileChooser fileChooser = new JFileChooser();
				int select = fileChooser.showOpenDialog(ControlPanel.this);
				if(select == JFileChooser.APPROVE_OPTION) {
					try {
						File file = fileChooser.getSelectedFile();
						ctrl.reset();
						ctrl.loadEvents(new FileInputStream(file));
					} 
					catch (FileNotFoundException fileErr) {
						JOptionPane.showMessageDialog(null, "File not found");
					}
					catch (Exception err) {
						JOptionPane.showMessageDialog(null, "Error loading events. Check file is correct");
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
				new ChangeCO2ClassDialog(ctrl, frame);
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
				new ChangeWeatherDialog(ctrl, frame);
			}
		});
		return changeWeatherCondition;
	}
	
	private JButton createStartButton() {
		JButton start = new JButton();
		start.setIcon(new ImageIcon(START_ICON_DIR));
		return start;
	}
	
	private JButton createStopButton() {
		JButton stop = new JButton();
		stop.setIcon(new ImageIcon(STOP_ICON_DIR));
		return stop;
	}
	
	private JSpinner createTicksButton() {
		ticksSelection = new JSpinner(
				new SpinnerNumberModel(
						TICKS_INI_VALUE,
						TICKS_MIN_VALUE,
						TICKS_MAX_VALUE,
						TICKS_INCREASE_VALUE
		));
		ticksSelection.setMaximumSize(new Dimension(100,30));
		return ticksSelection;
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
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onError(String err) {}
	
}
