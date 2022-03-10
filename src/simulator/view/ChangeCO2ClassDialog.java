package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

import simulator.control.Controller;

public class ChangeCO2ClassDialog extends JDialog{
	
	private final static String TITLE = "Change CO2 Class";
	private final static boolean MODAL = true;
	
	private final static String DESCRIPTION = "Schedule an event to change "
			+ "the CO2 class of a vehicle after a given number of\n"
			+ "simulation ticks from now";
	
	private final static int WIDTH = 500;
	private final static int HEIGHT = 200;
	
	private Controller ctrl;
	private JSpinner vehicleSelection;
	
	private static final long serialVersionUID = -8875771187454739902L;
	
	public ChangeCO2ClassDialog (Controller c, JFrame f) {
		super(f, TITLE, MODAL);
		ctrl = c;
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		JTextArea description = new JTextArea(DESCRIPTION);
		description.setEditable(false);
		this.add(description, BorderLayout.NORTH);
		
		JPanel selectOptions = new JPanel();
		createVehicleSelection();
		
		this.add(selectOptions, BorderLayout.CENTER);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.pack();
		this.setLocation(screenSize.width / 2 - WIDTH / 2, screenSize.height / 2 - HEIGHT / 2);
		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void createVehicleSelection() {
		
	}
	
}
