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

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver{

	private Controller ctrl;
	private JButton loadEventsButton;
	private JButton changeContaminationButton;
	private JButton changeWeatherConditionButton;
	private JButton startButton;
	private JButton stopButton;
	private JSpinner ticksButton;
	private JButton exitButton;
	
	private static final long serialVersionUID = -4423199850333010661L;

	public ControlPanel(Controller c) {
		ctrl = c;
		initGUI();
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
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		JToolBar controls = new JToolBar();
		
		createLoadEventsButton();
		controls.add(loadEventsButton);
		controls.addSeparator();
		
		createChangeContaminationButton();
		controls.add(changeContaminationButton);
		createChangeWeatherConditionButton();
		controls.add(changeWeatherConditionButton);
		controls.addSeparator();
		
		createStartButton();
		controls.add(startButton);
		createStopButton();
		controls.add(stopButton);
		createTicksButton();
		controls.add(new JLabel(" Ticks: "));
		controls.add(ticksButton);
		
		controls.add(new JSeparator(SwingConstants.VERTICAL));
		createExitButton();
		controls.add(exitButton);
		
		this.add(controls, BorderLayout.CENTER);
	}
		
	private void createLoadEventsButton() {
		loadEventsButton = new JButton();
		loadEventsButton.setIcon(new ImageIcon("resources\\icons\\open.png"));
		loadEventsButton.addActionListener(new ActionListener() {
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
					catch (FileNotFoundException err) {
						err.printStackTrace();
					}
				}
			}
		});
	}
	
	private void createChangeContaminationButton() {
		changeContaminationButton = new JButton();
		changeContaminationButton.setIcon(new ImageIcon("resources\\icons\\co2class.png"));
	}
	
	private void createChangeWeatherConditionButton() {
		changeWeatherConditionButton = new JButton();
		changeWeatherConditionButton.setIcon(new ImageIcon("resources\\icons\\weather.png"));
	}
	
	private void createStartButton() {
		startButton = new JButton();
		startButton.setIcon(new ImageIcon("resources\\icons\\run.png"));
	}
	
	private void createStopButton() {
		stopButton = new JButton();
		stopButton.setIcon(new ImageIcon("resources\\icons\\stop.png"));
	}
	
	private void createTicksButton() {
		ticksButton = new JSpinner(new SpinnerNumberModel(1,1,99999,1));
		ticksButton.setMaximumSize(new Dimension(100,50));
	}
	
	private void createExitButton() {
		exitButton = new JButton();
		exitButton.setIcon(new ImageIcon("resources\\icons\\exit.png"));
		exitButton.addActionListener(new ActionListener() {
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
	}
	
}
