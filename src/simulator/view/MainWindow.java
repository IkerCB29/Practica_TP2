package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

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
		
		mainPanel.add(new ControlPanel(ctrl, this), BorderLayout.PAGE_START);
		mainPanel.add(new JPanel(), BorderLayout.PAGE_END);
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setVisible(true);
	}
	
}
