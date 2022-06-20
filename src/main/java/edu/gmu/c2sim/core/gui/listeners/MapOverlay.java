package edu.gmu.c2sim.core.gui.listeners;

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
import edu.gmu.c2sim.core.entities.IEntity.STATUS;
import edu.gmu.c2sim.core.geo.SimCoordinate;
import edu.gmu.c2sim.core.gui.components.FancyWaypointRenderer;
import edu.gmu.c2sim.core.gui.components.MyWaypoint;
import edu.gmu.c2sim.core.sim.Exercise;

public class MapOverlay {
	public void initScenario(JXMapViewer mapView, Exercise exe) {
		List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
		
		List<IEntity> blueTeam = exe.getBlueTeam();
		convertEntities(blueTeam, "blue", painters);
		
		List<IEntity> redTeam = exe.getRedTeam();
		convertEntities(redTeam, "red", painters);

		List<IEntity> greenTeam = exe.getGreenTeam();
		convertEntities(greenTeam, "green", painters);

		CompoundPainter<JXMapViewer> cp = new CompoundPainter<JXMapViewer>(painters);
		cp.setCacheable(false);
		mapView.setOverlayPainter(cp);

		mapView.revalidate();
		mapView.repaint();

	}
	
	public void updateScenario(JXMapViewer mapView, 
			List<IEntity> blueTeam, List<IEntity> redTeam,
			List<IEntity> greenTeam) {
		List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
		
		convertEntities(blueTeam, "blue", painters);
		
		convertEntities(redTeam, "red", painters);

		convertEntities(greenTeam, "green", painters);

		CompoundPainter<JXMapViewer> cp = new CompoundPainter<JXMapViewer>(painters);
		cp.setCacheable(false);
		mapView.setOverlayPainter(cp);

		mapView.revalidate();
		mapView.repaint();

	}

	private void convertEntities(List<IEntity>entityList,
			String team,
			List<Painter<JXMapViewer>> painter){
		Hashtable<String, Set<MyWaypoint>> entityDB =new Hashtable<>();
		for (IEntity ent : entityList) {
			entityDB = new Hashtable<>();
			
			SimCoordinate coord = ent.getInitialPosition();
			double latit = coord.getLatitude();
			double longit = coord.getLongitude();
			
			MyWaypoint simuEnt = new MyWaypoint(ent.getAlias(),
					new GeoPosition(latit, longit), ent.getModel().getType().getImageName());
			String type = ent.getModel().getType().getName();
			
			STATUS status = ent.getStatus();
			if(status==STATUS.DESTROYED) {
				if (entityDB.containsKey("DESTROYED")) {
					Set<MyWaypoint> entSet =entityDB.get(type);
					entSet.add(simuEnt);
				}
				
				else {
					Set<MyWaypoint> entSet = new HashSet<>();
					entSet.add(simuEnt);
					entityDB.put(type, entSet);
				}
			}
			
			else {
				if (entityDB.containsKey(type)) {
					Set<MyWaypoint> entSet =entityDB.get(type);
					entSet.add(simuEnt);
				}
				
				else {
					Set<MyWaypoint> entSet = new HashSet<>();
					entSet.add(simuEnt);
					entityDB.put(type, entSet);
				}
			}
			
		}
		
		List<String> keysTypeEntity = new ArrayList<String>( entityDB.keySet() );
		
		for (String type : keysTypeEntity) {
			Set<MyWaypoint> entSet =entityDB.get(type);
			WaypointPainter<MyWaypoint> entityWP = new WaypointPainter<MyWaypoint>();
			entityWP.setWaypoints(entSet);
			entityWP.setRenderer(new FancyWaypointRenderer());
			painter.add(entityWP);
		}
		
	}

}
