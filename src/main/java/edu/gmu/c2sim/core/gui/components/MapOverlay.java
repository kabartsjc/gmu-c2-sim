package edu.gmu.c2sim.core.gui.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;

import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.IEntity.TEAM;
import edu.gmu.c2sim.core.geo.SimCoordinate;

public class MapOverlay {
	
	public void initComponents(JXMapViewer mapView, Hashtable<String, IEntity> entDb) {
		List<Painter<JXMapViewer>> painters = loadPainters(mapView, entDb);
		
		CompoundPainter<JXMapViewer> cp = new CompoundPainter<JXMapViewer>(painters);
		cp.setCacheable(false);
		mapView.setOverlayPainter(cp);

		mapView.revalidate();
		mapView.repaint();

	}
	
	public void updateScenario(JXMapViewer mapView, Hashtable<String, IEntity> entDb) {
		List<Painter<JXMapViewer>> painters = loadPainters(mapView, entDb);
		
		CompoundPainter<JXMapViewer> cp = new CompoundPainter<JXMapViewer>(painters);
		cp.setCacheable(false);
		mapView.setOverlayPainter(cp);

		mapView.revalidate();
		mapView.repaint();

	}
	
	private List<Painter<JXMapViewer>> loadPainters(JXMapViewer mapView, Hashtable<String, IEntity> entDb){
		List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
		
		Set<MyWaypoint> wpSet = new HashSet<>();
		
		for (IEntity ent : new ArrayList<>(entDb.values())) {
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
			
			wpSet.add(wp);
						
		}
		
		WaypointPainter<MyWaypoint> wpDb = new WaypointPainter<>();
		wpDb.setWaypoints(wpSet);
		wpDb.setRenderer(new FancyWaypointRenderer());
		painters.add(wpDb);
		
		return painters;
	}
	
	

	

}
