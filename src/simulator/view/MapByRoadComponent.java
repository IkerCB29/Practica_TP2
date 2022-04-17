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
		} 
		else 
			drawRoads(g);
	}
	
	private void drawRoads(Graphics g) {
		int i = 0;
		for (Road r : _map.getRoads()) {
			//Draw road
			int x1 = 50;
			int x2 = getWidth() - 150;
			int y = (i + 1) * 50;
			g.setColor(ROAD_COLOR);
			g.drawLine(x1, y, x2, y);
			
			//Road id
			g.setColor(ROAD_LABEL_COLOR);
			g.drawString(r.toString(), x1 - 25, y);
			
			//src junction
			drawJunction(g, JUNCTION_COLOR, x1, y, r);
			
			//dest junction
			if(r.getDest().getGreenRoadId().equals(r.toString()))
				drawJunction(g, GREEN_LIGHT_COLOR, x2, y, r);
			else
				drawJunction(g, RED_LIGHT_COLOR, x2, y, r);
			
			i++;
			
			//draw vehicles in road
			for(Vehicle v : r.getVehicles())
				drawVehicle(g, v, x1, x2, y);
			
			//draw weather conditions
			g.drawImage(getWeatherConditionsImage(r), x2 + 20, y - 16, 32, 32, this);
			
			//draw contamination
			g.drawImage(getContaminationImage(r), x2 + 60, y - 16, 32, 32, this);
		}
	}
	
	private void drawJunction(Graphics g, Color color, int x, int y, Road r) {
		g.setColor(color);
		g.fillOval(x, y - 3, 6, 6);
		g.setColor(JUNCTION_LABEL_COLOR);
		g.drawString(r.getSrc().toString(), x, y - 6);
	}
	
	private void drawVehicle(Graphics g, Vehicle v, int x1, int x2, int y) {
		int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
		g.setColor(new Color(0, vLabelColor, 0));
		int x = x1 + (int) ((x2 - x1) * (double) v.getLocation() / (double) v.getRoad().getLength());
		g.drawImage(_car, x, y - 8, 16, 16, this);
		g.drawString(v.getId(), x, y - 6);
	}
	
	private Image getWeatherConditionsImage(Road road) {
		switch (road.getWeather()){
		case SUNNY :
			return loadImage("sun.png");
		case CLOUDY :
			return loadImage("cloud.png");
		case RAINY :
			return loadImage("rain.png");
		case WINDY :
			return loadImage("wind.png");
		case STORM :
			return loadImage("storm.png");
		default:
			return null;
		}
	}
	
	private Image getContaminationImage(Road road) {
		int c = (int) Math.floor(Math.min((double) road.getTotalCO2()/(1.0 + (double) road.getContLimit()),1.0) / 0.19);
		switch(c) {
		case 0:
			return loadImage("cont_0.png");
		case 1:
			return loadImage("cont_1.png");
		case 2:
			return loadImage("cont_2.png");
		case 3:
			return loadImage("cont_3.png");
		case 4:
			return loadImage("cont_4.png");
		case 5:
			return loadImage("cont_5.png");
		default:
			return null;
		}
	}
	
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
