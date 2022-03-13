package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver{

	private final static int NUM_COLUMNS = 8;
	
	private String[] columnNames = {"Id", "Location", "Itinerary", "CO2Class",
			"Max. Speed", "Speed", "Total CO2", "Distance"};
	
	private static final long serialVersionUID = 8039718311001039451L;
	
	private List<Vehicle> vehicles;
	
	VehiclesTableModel (Controller c) {
		vehicles = new ArrayList<>();
		c.addObserver(this);
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public int getRowCount() {
		return vehicles.size();
	}

	@Override
	public int getColumnCount() {
		return NUM_COLUMNS;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0:
			return vehicles.get(rowIndex).getId();
		case 1:
			return vehicles.get(rowIndex).getLocation();
		case 2:
			return vehicles.get(rowIndex).getItinerary();
		case 3:
			return vehicles.get(rowIndex).getContClass();
		case 4:
			return vehicles.get(rowIndex).getMaxSpeed();
		case 5:
			return vehicles.get(rowIndex).getSpeed();
		case 6:
			return vehicles.get(rowIndex).getTotalCO2();
		case 7:
			return vehicles.get(rowIndex).getDistance();
		default:
			return null;
		}
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		vehicles = map.getVehicles();
		this.fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		int size = vehicles.size();
		vehicles = new ArrayList<>();
		this.fireTableRowsDeleted(0, size);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onError(String err) {}

}
