package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class MapByRoadComponent  extends JPanel implements TrafficSimObserver {

	private static final Color BG_COLOR = Color.WHITE;
	private static final Color ROAD_COLOR = Color.BLACK;
	private static final Color  ROAD_LABEL_COLOR = Color.BLACK;
	private static final Color JUNCTION_COLOR = Color.BLUE;
	private static final Color JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color RED_LIGHT_COLOR = Color.RED;

	private static final long serialVersionUID = -1241524094400052053L;
	
	private RoadMap _map;

	private Image _car;
	
	MapByRoadComponent(Controller ctrl){
		this.setPreferredSize (new Dimension (300, 200));
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		_car = loadImage("car.png");
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getRoads().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			//updatePrefferedSize();
			drawMap(g);
		}
	}

	private void drawMap(Graphics g) {
		drawRoads(g);
	}
	
	private void drawRoads(Graphics g) {
		int i = 0;
		for (Road r : _map.getRoads()) {
			//Draw road
			int x1 = 50;
			int x2 = getWidth() - 100;
			int y = (i + 1) * 50;
			g.setColor(ROAD_COLOR);
			g.drawLine(x1, y, x2, y);
			
			//Road id
			g.setColor(ROAD_LABEL_COLOR);
			g.drawString(r.toString(), x1 - 25, y);
			
			//src junction
			g.setColor(JUNCTION_COLOR);
			g.fillOval(x1, y - 3, 6, 6);
			g.setColor(JUNCTION_LABEL_COLOR);
			g.drawString(r.getSrc().toString(), x1, y - 6);
			
			//dest junction
			if(r.getDest().getGreenRoadId().equals(r.toString()))
				g.setColor(GREEN_LIGHT_COLOR);
			else
				g.setColor(RED_LIGHT_COLOR);
			g.fillOval(x2, y - 3, 6, 6);
			g.setColor(JUNCTION_LABEL_COLOR);
			g.drawString(r.getDest().toString(), x2, y - 6);
			i++;
			
			//draw vehicles in road
			for(Vehicle v : r.getVehicles())
				drawVehicle(g, v, x1, x2, y);
			
			//draw vehicles in junction
			for(Vehicle v : r.getDest().getQueues().get(r))
				drawVehicleInJunction(g, v, x2, y);
		}
	}
	
	private void drawVehicle(Graphics g, Vehicle v, int x1, int x2, int y) {
		int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
		g.setColor(new Color(0, vLabelColor, 0));
		int x = x1 + (int) ((x2 - x1) * (double) v.getLocation() / (double) v.getRoad().getLength());
		g.drawImage(_car, x, y - 8, 16, 16, this);
		g.drawString(v.getId(), x, y - 6);
	}
	
	private void drawVehicleInJunction(Graphics g, Vehicle v, int x2, int y) {
		int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
		g.setColor(new Color(0, vLabelColor, 0));
		g.drawImage(_car, x2, y - 8, 16, 16, this);
		g.drawString(v.getId(), x2, y - 6);
	}
	
	/*
	private void updatePrefferedSize() {
		int maxW = 200;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		if (maxW > getWidth() || maxH > getHeight()) {
			setPreferredSize(new Dimension(maxW, maxH));
			setSize(new Dimension(maxW, maxH));
		}
	}
	*/
	
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} 
		catch (IOException e) {}
		return i;
	}

	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_map = map;
			repaint();
		});
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onError(String err) {}

}
