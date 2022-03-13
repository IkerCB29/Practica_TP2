package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private final static int NUM_COLUMNS = 3;
	
	private String[] columnNames = {"Id", "Green", "Queues"};
	
	private static final long serialVersionUID = 5203065099024018166L;
	
	private List<Junction> junctions;

	JunctionsTableModel (Controller c) {
		junctions = new ArrayList<>();
		c.addObserver(this);
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public int getRowCount() {
		return junctions.size();
	}

	@Override
	public int getColumnCount() {
		return NUM_COLUMNS;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0:
			return junctions.get(rowIndex).getId();
		case 1:
			return junctions.get(rowIndex).getGreenRoadId();
		case 2:
			return junctions.get(rowIndex).getQueues();
		default:
			return null;
		}
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		junctions = map.getJunctions();
		for(int i = 0; i < junctions.size(); i++) {
			this.setValueAt(junctions.get(i).getId(), i, 0);
			this.setValueAt(junctions.get(i).getGreenRoadId(), i, 1);
			this.setValueAt(junctions.get(i).getQueues(), i, 2);
		}
		this.fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		int size = junctions.size();
		junctions = new ArrayList<>();
		this.fireTableRowsDeleted(0, size);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onError(String err) {}
	
}
