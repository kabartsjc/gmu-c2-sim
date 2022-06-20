package edu.gmu.c2sim.core.gui.run;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.EtchedBorder;

import org.jxmapviewer.viewer.GeoPosition;

import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.gui.ICoreGui;
import edu.gmu.c2sim.core.gui.StartGUI;
import edu.gmu.c2sim.core.gui.editor.MapEditorPanel;
import edu.gmu.c2sim.core.sim.Exercise;

public class SimulationRunnerPanel extends ICoreGui {//implements ICoreGuiObserver  {
	private JFrame mainFrame;
	
	private MapEditorPanel mapPanel;
	private TopHeaderPanel headerPanel;
	private EntityDetailPanel entityPanel;
	private ExerciseTablePanel tablePanel;
	
	private Exercise exe;

	private StartGUI startGui;

	public SimulationRunnerPanel(StartGUI startGUI, GeoPosition centralPos, Exercise exe) {
		this.startGui=startGUI;
		initialize(centralPos, exe);
	}
	
	public void initialize(GeoPosition centralPos, Exercise exe) {
		this.exe=exe;
		
		mainFrame = new JFrame();
		mainFrame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		// frame.setBounds(100, 100, 893, 582);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//set north
		this.headerPanel = new TopHeaderPanel();
		headerPanel.configure(exe.getId(), exe.getSimu_duration(), exe.getCurrentTime());
		
		headerPanel.setBorder(
	            BorderFactory.createTitledBorder(
	            BorderFactory.createEtchedBorder(
	                    EtchedBorder.RAISED, Color.GRAY
	                    , Color.DARK_GRAY), "Entity Detail"));
		//editEntityPanel.setPreferredSize(new Dimension(10, 150));
		mainFrame.getContentPane().add(headerPanel, BorderLayout.NORTH);
		
		//set west
		entityPanel = new EntityDetailPanel();
		entityPanel.setBorder(
	            BorderFactory.createTitledBorder(
	            BorderFactory.createEtchedBorder(
	                    EtchedBorder.RAISED, Color.GRAY
	                    , Color.DARK_GRAY), "Entity Pallete"));
		
		mainFrame.getContentPane().add(entityPanel, BorderLayout.WEST);
		
		//set center area
		mapPanel = new MapEditorPanel();
		mapPanel.configure(centralPos, this);
		
		
		mainFrame.getContentPane().add(mapPanel, BorderLayout.CENTER);
		
		//set south
		tablePanel = new ExerciseTablePanel(this);
		tablePanel.configure(exe.getAllEntities());
		tablePanel.setBorder(
	            BorderFactory.createTitledBorder(
	            BorderFactory.createEtchedBorder(
	                    EtchedBorder.RAISED, Color.GRAY
	                    , Color.DARK_GRAY), "Exercise Panel"));
		mainFrame.getContentPane().add(tablePanel, BorderLayout.SOUTH);
		
		List<IEntity> entL = exe.getAllEntities();
		
		for (IEntity ent : entL) {
			mapPanel.updateEntity(ent);
		}
	}
	
	public void createAndShowGUI() {
		mainFrame.setVisible(true);
	}
	

	@Override
	public void setPosition(GeoPosition pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit() {
		this.startGui.updateScenarios();
		mainFrame.setVisible(false);
		
	}

	/*@Override
	public void update(Observable obs, Object arg) {
		if (obs instanceof Exercise) {
			Exercise exe = (Exercise)obs;
			long currentTime = exe.getCurrentTime();
			this.headerPanel.update(currentTime);
			
			List<IEntity> entL = exe.getAllEntities();
			this.tablePanel.update(entL);
			
			mapPanel.updateEntityList(entL);
			
		}
	}*/

	public void loadEntityInformation(String alias) {
		IEntity ent = exe.getEntity(alias);
		entityPanel.loadInfo(ent);
		
	}

	public void updateGUI(long currentTime , List<IEntity> entL) {
		this.headerPanel.update(currentTime);
		
		this.tablePanel.update(entL);
		
		mapPanel.updateEntityList(entL);
		
	}
	
	

}
