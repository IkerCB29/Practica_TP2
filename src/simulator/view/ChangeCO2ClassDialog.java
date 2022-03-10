package simulator.view;

import java.awt.Dimension;

import javax.swing.JDialog;

import simulator.control.Controller;

public class ChangeCO2ClassDialog extends JDialog {

	private static final long serialVersionUID = 8309966873726870455L;
	
	private Controller ctrl;

	public ChangeCO2ClassDialog(Controller c) {
		super();
		ctrl = c;
		initGUI();
	}
	
	private void initGUI() {
		this.setSize(new Dimension(500,200));
		this.setVisible(false);
	}
	
}
