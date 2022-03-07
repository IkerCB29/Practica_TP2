package simulator.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame{
	
	private static final long serialVersionUID = -417261336973419340L;
	
	private Controller ctrl;
	
	public MainWindow(Controller c) {
		super("Traffic Simulator");
		ctrl = c;
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
}
