package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver{

	private final static int NUM_COLUMNS = 7;
	
	private String[] columnNames = {"Id", "Length", "Weather", "Max. Speed",
			"Speed Limit", "Total CO2", "CO2 Limit"};
	
	private static final long serialVersionUID = -4962359104624141137L;
	
	private List<Road> roads;

	RoadsTableModel (Controller c) {
		roads = new ArrayList<>();
		c.addObserver(this);
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public int getRowCount() {
		return roads.size();
	}

	@Override
	public int getColumnCount() {
		return NUM_COLUMNS;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0:
			return roads.get(rowIndex).getId();
		case 1:
			return roads.get(rowIndex).getLength();
		case 2:
			return roads.get(rowIndex).getWeather();
		case 3:
			return roads.get(rowIndex).getMaxSpeed();
		case 4:
			return roads.get(rowIndex).getSpeedLimit();
		case 5:
			return roads.get(rowIndex).getTotalCO2();
		case 6:
			return roads.get(rowIndex).getContLimit();
		default:
			return null;
		}
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		roads = map.getRoads();
		for(int i = 0; i < roads.size(); i++) {
			this.setValueAt(roads.get(i).getId(), i, 0);
			this.setValueAt(roads.get(i).getLength(), i, 1);
			this.setValueAt(roads.get(i).getWeather(), i, 2);
			this.setValueAt(roads.get(i).getMaxSpeed(), i, 3);
			this.setValueAt(roads.get(i).getSpeedLimit(), i, 4);
			this.setValueAt(roads.get(i).getTotalCO2(), i, 5);
			this.setValueAt(roads.get(i).getContLimit(), i, 6);
		}
		this.fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		int size = roads.size();
		roads = new ArrayList<>();
		this.fireTableRowsDeleted(0, size);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onError(String err) {}

}
