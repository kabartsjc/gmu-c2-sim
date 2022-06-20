package edu.gmu.c2sim.core.gui.editor;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;

import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.IEntity.TEAM;
import edu.gmu.c2sim.core.geo.SimCoordinate;
import edu.gmu.c2sim.core.gui.ICoreGui;
import edu.gmu.c2sim.core.gui.components.FancyWaypointRenderer;
import edu.gmu.c2sim.core.gui.components.MyWaypoint;
import edu.gmu.c2sim.core.gui.components.SelectionAdapter;
import edu.gmu.c2sim.core.gui.components.SelectionPainter;
import edu.gmu.c2sim.core.orders.IOrder;
import edu.gmu.c2sim.core.plan.IPlan;

public class MapEditorPanel extends JXMapViewer {
	private static final long serialVersionUID = -1248296922602480848L;
	
	private ICoreGui scenPanel;

	private Hashtable<String, IEntity> entDb;

	public void configure(GeoPosition initPosition, ICoreGui scenPanel) {
		this.scenPanel = scenPanel;
		
		entDb = new Hashtable<>();

		final List<TileFactory> factories = new ArrayList<TileFactory>();

		TileFactoryInfo osmInfo = new OSMTileFactoryInfo();
		TileFactoryInfo veInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);

		factories.add(new DefaultTileFactory(osmInfo));
		factories.add(new DefaultTileFactory(veInfo));

		TileFactory firstFactory = factories.get(0);
		setTileFactory(firstFactory);

		setZoom(10);
		setAddressLocation(initPosition);

		SelectionAdapter sa = new SelectionAdapter(this);
		SelectionPainter sp = new SelectionPainter(sa);

		configureListeners(sa, sp);
	}
	
	
	public void setPosition(GeoPosition pos) {
		this.scenPanel.setPosition(pos);
	}
	
	
	public void updateEntityList(List<IEntity> entL) {
		Set<MyWaypoint> waypoints = generateSet(entL);

		WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
		waypointPainter.setWaypoints(waypoints);

		waypointPainter.setRenderer(new FancyWaypointRenderer());
		
		setOverlayPainter(waypointPainter);
	}

	
	public void updateEntity(IEntity entity) {
		String alias = entity.getAlias();

		if (entDb.get(alias)!=null) {
			entDb.remove(entity.getAlias());
		}
		entDb.put(entity.getAlias(), entity);

		Set<MyWaypoint> waypoints = generateSet();

		WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
		waypointPainter.setWaypoints(waypoints);

		waypointPainter.setRenderer(new FancyWaypointRenderer());

		setOverlayPainter(waypointPainter);

	}

	public void deleteEntity(IEntity entity) {
		entDb.remove(entity.getAlias());

		Set<MyWaypoint> waypoints = generateSet();

		WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
		waypointPainter.setWaypoints(waypoints);

		waypointPainter.setRenderer(new FancyWaypointRenderer());

		setOverlayPainter(waypointPainter);
	}

	public void updateOrder(List<IOrder> orderL, IEntity selectEntity, List<IEntity> entL) {
		Set<MyWaypoint> waypoints = generateSet(orderL, selectEntity, entL);

		WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
		waypointPainter.setWaypoints(waypoints);

		waypointPainter.setRenderer(new FancyWaypointRenderer());

		setOverlayPainter(waypointPainter);
	}

	public void initPlanMap(IEntity selEnt, List<IEntity> allEntL) {
		IPlan plan = selEnt.getPlan();
		List<IOrder> orderL = new ArrayList<>();
		if (plan != null) {
			if (plan.getOrders().size() > 0)
				orderL = plan.getOrders();
		}
		updateOrder(orderL, selEnt, allEntL);

		BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();

		drawCenteredCircle(g2, 2, 44, 100);

	}

	private void configureListeners(SelectionAdapter sa, SelectionPainter sp) {
		// Add interactions
		MouseInputListener mia = new PanMouseInputListener(this);
		addMouseListener(mia);
		addMouseMotionListener(mia);

		addMouseListener(new CenterMapListener(this));

		addMouseWheelListener(new ZoomMouseWheelListenerCursor(this));

		addMouseListener(sa);
		addMouseMotionListener(sa);

		setOverlayPainter(sp);
	}

	
	private void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
		x = x - (r / 2);
		y = y - (r / 2);
		g.fillOval(x, y, r, r);

		this.paint(g);
	}

	private Set<MyWaypoint> generateSet(List<IOrder> orderL, IEntity selEnt, List<IEntity> allEntL) {
		Set<MyWaypoint> waypoints = new HashSet<>();

		MyWaypoint selwp = getWaypoint(selEnt);
		waypoints.add(selwp);

		List<IEntity> entL = new ArrayList<>();

		if (selEnt.getTeam() == TEAM.BLUE) {
			for (IEntity ent : allEntL) {
				if (ent.getTeam() == TEAM.RED)
					entL.add(ent);
			}
		}

		else if (selEnt.getTeam() == TEAM.RED) {
			for (IEntity ent : allEntL) {
				if (ent.getTeam() == TEAM.BLUE || ent.getTeam() == TEAM.GREEN)
					entL.add(ent);
			}
		}

		for (IEntity ent : new ArrayList<>(entL)) {
			MyWaypoint wp = getWaypoint(ent);
			waypoints.add(wp);
		}

		for (IOrder order : orderL) {
			String label = order.getURI();
			SimCoordinate coord = order.getMissionLocation();
			GeoPosition pos = coord.convert();

			MyWaypoint wp = new MyWaypoint(label, pos);

			waypoints.add(wp);
		}

		return waypoints;

	}

	private MyWaypoint getWaypoint(IEntity ent) {
		SimCoordinate simPos = ent.getInitialPosition();
		GeoPosition pos = simPos.convert();

		String label = ent.getAlias();
		TEAM team = ent.getTeam();

		String iconName = "";
		String name = ent.getModel().getType().getImageName();

		if (team == TEAM.BLUE) {
			iconName = iconName + "b_" + name;
		}

		else if (team == TEAM.RED) {
			iconName = iconName + "r_" + name;
		}

		else {
			iconName = iconName + "g_" + name;
		}

		MyWaypoint wp = new MyWaypoint(label, pos, iconName);
		return wp;
	}

	private Set<MyWaypoint> generateSet() {
		Set<MyWaypoint> waypoints = new HashSet<>();

		for (IEntity ent : new ArrayList<>(entDb.values())) {
			SimCoordinate simPos = ent.getInitialPosition();
			GeoPosition pos = simPos.convert();

			String label = ent.getAlias();
			TEAM team = ent.getTeam();

			String iconName = "";
			String name = ent.getModel().getType().getImageName();

			if (team == TEAM.BLUE) {
				iconName = iconName + "b_" + name;
			}

			else if (team == TEAM.RED) {
				iconName = iconName + "r_" + name;
			}

			else {
				iconName = iconName + "g_" + name;
			}

			MyWaypoint wp = new MyWaypoint(label, pos, iconName);

			waypoints.add(wp);

		}

		return waypoints;

	}
	
	
	private Set<MyWaypoint> generateSet(List<IEntity> entL) {
		Set<MyWaypoint> waypoints = new HashSet<>();

		for (IEntity ent : entL) {
			SimCoordinate simPos = ent.getCurrentPosition();
			GeoPosition pos = simPos.convert();

			String label = ent.getAlias();
			TEAM team = ent.getTeam();

			String iconName = "";
			String name = ent.getModel().getType().getImageName();

			if (team == TEAM.BLUE) {
				iconName = iconName + "b_" + name;
			}

			else if (team == TEAM.RED) {
				iconName = iconName + "r_" + name;
			}

			else {
				iconName = iconName + "g_" + name;
			}

			MyWaypoint wp = new MyWaypoint(label, pos, iconName);

			waypoints.add(wp);

		}

		return waypoints;

	}

}
