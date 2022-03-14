package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

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
		
		mainPanel.add(new ControlPanel(ctrl), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(ctrl), BorderLayout.PAGE_END);
		
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		
		//tables
		JTable eventsTable = new JTable(new EventsTableModel(ctrl));
		eventsTable.setShowGrid(false);
		JPanel eventsView =
				createViewPanel(eventsTable, "Events");
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(eventsView);
		
		JTable vehiclesTable = new JTable(new VehiclesTableModel(ctrl));
		vehiclesTable.setShowGrid(false);
		JPanel vehiclesView =
				createViewPanel(vehiclesTable, "Vehicles");
		vehiclesView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(vehiclesView);
		
		JTable roadsTable = new JTable(new RoadsTableModel(ctrl));
		roadsTable.setShowGrid(false);
		JPanel roadsView =
				createViewPanel(roadsTable, "Roads");
		roadsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(roadsView);
		
		JTable junctionTable = new JTable(new JunctionsTableModel(ctrl));
		junctionTable.setShowGrid(false);
		JPanel junctionsView =
				createViewPanel(junctionTable, "Junctions");
		junctionsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(junctionsView);
		
		// maps
		JPanel mapView = createViewPanel(new MapComponent(ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);

		JPanel mapByRoadView = createViewPanel(new MapByRoadComponent(ctrl), "Map by Road");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setVisible(true);
	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel( new BorderLayout());
		Border b = BorderFactory.createLineBorder(Color.black, 2);
		p.setBorder(BorderFactory.createTitledBorder(b, title));
		p.add(new JScrollPane(c));
		return p;
	}

}
