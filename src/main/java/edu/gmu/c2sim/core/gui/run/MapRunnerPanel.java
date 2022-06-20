package edu.gmu.c2sim.core.gui.run;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;

import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.gui.ICoreGui;
import edu.gmu.c2sim.core.gui.components.MapListener;
import edu.gmu.c2sim.core.gui.components.MapOverlay;
import edu.gmu.c2sim.core.gui.components.SelectionAdapter;
import edu.gmu.c2sim.core.gui.components.SelectionPainter;

public class MapRunnerPanel extends JPanel {
	private static final long serialVersionUID = -1248296922602480848L;
	
	private JXMapKit jXMapKit;
	private JXMapViewer mapViewer;
	private JFrame frame;
	
	private ICoreGui scenPanel;
	private Hashtable<String, IEntity> entDb;
	
	private MapOverlay overlay;
	
	public MapRunnerPanel() {
		super(new BorderLayout());
	}
	
	
	public void configure(GeoPosition initPosition, ICoreGui scenPanel,List<IEntity> entL) {
		this.scenPanel = scenPanel;
		
		entDb = new Hashtable<>();
		for (IEntity ent : entL) {
			entDb.put(ent.getAlias(), ent);
		}
		
		// Setup JXMapViewer
		jXMapKit = new JXMapKit();


		final List<TileFactory> factories = new ArrayList<TileFactory>();

		TileFactoryInfo osmInfo = new OSMTileFactoryInfo();
		TileFactoryInfo veInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);

		factories.add(new DefaultTileFactory(osmInfo));
		factories.add(new DefaultTileFactory(veInfo));

		TileFactory firstFactory = factories.get(0);
		jXMapKit.setTileFactory(firstFactory);
		
		

		jXMapKit.setZoom(10);
		jXMapKit.setAddressLocation(initPosition);
		
		mapViewer = jXMapKit.getMainMap();
		this.frame = new JFrame("ATC Sim v2.0");

		SelectionAdapter sa = new SelectionAdapter(mapViewer);
		SelectionPainter sp = new SelectionPainter(sa);

		configureListeners(sa, sp);
		
		//init_components maps
		overlay = new MapOverlay();
		overlay.initComponents(mapViewer, this.entDb);
		
		add(mapViewer);

	}
	
	private void configureListeners(SelectionAdapter sa, SelectionPainter sp) {
		// Add interactions
		mapViewer.addMouseListener(new MapListener(frame, mapViewer));

		mapViewer.addMouseMotionListener(sa);
		mapViewer.setOverlayPainter(sp);
			
	}
	
	public void updateMap(List<IEntity> entL) {
		entDb = new Hashtable<>();
		for (IEntity ent : entL) {
			entDb.put(ent.getAlias(), ent);
		}
		
		overlay.updateScenario(mapViewer, entDb);
		
	}

	

	
	public void setPosition(GeoPosition pos) {
		this.scenPanel.setPosition(pos);
	}

	



}
