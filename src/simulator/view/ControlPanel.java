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
	private JButton loadEvents;
	private JButton changeContamination;
	private JButton changeWeatherCondition;
	private JButton start;
	private JButton stop;
	private JSpinner ticks;
	private JButton exit;
	private ChangeCO2ClassDialog changeCO2Class;
	
	
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
		
		changeCO2Class = new ChangeCO2ClassDialog(ctrl);
		
		JToolBar controls = new JToolBar();
		
		createLoadEventsButton();
		controls.add(loadEvents);
		controls.addSeparator();
		
		createChangeContaminationButton();
		controls.add(changeContamination);
		createChangeWeatherConditionButton();
		controls.add(changeWeatherCondition);
		controls.addSeparator();
		
		createStartButton();
		controls.add(start);
		createStopButton();
		controls.add(stop);
		createTicksButton();
		controls.add(new JLabel(" Ticks: "));
		controls.add(ticks);
		
		controls.add(new JSeparator(SwingConstants.VERTICAL));
		createExitButton();
		controls.add(exit);
		
		this.add(controls, BorderLayout.CENTER);
	}
		
	private void createLoadEventsButton() {
		loadEvents = new JButton();
		loadEvents.setIcon(new ImageIcon("resources\\icons\\open.png"));
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
					catch (FileNotFoundException err) {
						err.printStackTrace();
					}
				}
			}
		});
	}
	
	private void createChangeContaminationButton() {
		changeContamination = new JButton();
		changeContamination.setIcon(new ImageIcon("resources\\icons\\co2class.png"));
		changeContamination.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeCO2Class.setVisible(!changeCO2Class.isVisible());
			}
		});
	}
	
	private void createChangeWeatherConditionButton() {
		changeWeatherCondition = new JButton();
		changeWeatherCondition.setIcon(new ImageIcon("resources\\icons\\weather.png"));
	}
	
	private void createStartButton() {
		start = new JButton();
		start.setIcon(new ImageIcon("resources\\icons\\run.png"));
	}
	
	private void createStopButton() {
		stop = new JButton();
		stop.setIcon(new ImageIcon("resources\\icons\\stop.png"));
	}
	
	private void createTicksButton() {
		ticks= new JSpinner(new SpinnerNumberModel(1,1,99999,1));
		ticks.setMaximumSize(new Dimension(100,50));
	}
	
	private void createExitButton() {
		exit = new JButton();
		exit.setIcon(new ImageIcon("resources\\icons\\exit.png"));
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
	}
	
}
