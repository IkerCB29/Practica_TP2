package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver{

	private String[] columnNames = {"Time", "Description"};
	
	private final static int NUM_COLUMNS = 2;
	
	private final static long serialVersionUID = -9070754042327367519L;
	
	private List<Event> events;
	
	EventsTableModel (Controller c) {
		events = new ArrayList<>();
		c.addObserver(this);
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public int getRowCount() {
		return events.size();
	}

	@Override
	public int getColumnCount() {
		return NUM_COLUMNS;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0:
			return events.get(rowIndex).getTime();
		case 1:
			return events.get(rowIndex).toString();
		default:
			return null;
		}
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(events);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(events);
	}
	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		int size = this.events.size();
		this.events = new ArrayList<>();
		this.fireTableRowsDeleted(0, size);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(events);
	}

	@Override
	public void onError(String err) {}
	
	private void update(List<Event> events) {
		this.events = events;
		this.fireTableDataChanged();
	}
	
}
