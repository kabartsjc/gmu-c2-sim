package edu.gmu.c2sim.core.gui.editor;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.BorderLayout;

import java.awt.Color;
import java.util.List;

import javax.swing.border.EtchedBorder;

import org.jxmapviewer.viewer.GeoPosition;

import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.IEntityModel;
import edu.gmu.c2sim.core.gui.ICoreGui;
import edu.gmu.c2sim.core.gui.StartGUI;
import edu.gmu.c2sim.core.sim.Exercise;


public class ScenarioEditorPanel extends ICoreGui{

	private JFrame mainFrame;
	
	private MapEditorPanel mapPanel;
	private EntityEditorPanel editEntityPanel;
	private PalletPanel palletPanel;
	private ExercisePanel exePanel;
	
	private StartGUI startGui;
	
	private IEntityModel selectEntity=null;
	
	
	public ScenarioEditorPanel(StartGUI startGUI, GeoPosition centralPos, Exercise exe) {
		this.startGui=startGUI;
		initialize(centralPos, exe);
		
	}
	
	/**
	 * Create the application.
	 * @param startGUI 
	 */
	public ScenarioEditorPanel(StartGUI startGUI, GeoPosition centralPos) {
		this.startGui=startGUI;
		initialize(centralPos, null);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(GeoPosition centralPos, Exercise exe) {
		
		mainFrame = new JFrame();
		mainFrame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		// frame.setBounds(100, 100, 893, 582);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//NORTH AREA
		editEntityPanel = new EntityEditorPanel(this);
		editEntityPanel.setBorder(
	            BorderFactory.createTitledBorder(
	            BorderFactory.createEtchedBorder(
	                    EtchedBorder.RAISED, Color.GRAY
	                    , Color.DARK_GRAY), "Entity Detail"));
		//editEntityPanel.setPreferredSize(new Dimension(10, 150));
		mainFrame.getContentPane().add(editEntityPanel, BorderLayout.NORTH);
	

		//WEST AREA
		//entPanel = new EntitiesPalletPanel(this);
		palletPanel=new PalletPanel(this);
		//entPanel.configure();
		palletPanel.setBorder(
	            BorderFactory.createTitledBorder(
	            BorderFactory.createEtchedBorder(
	                    EtchedBorder.RAISED, Color.GRAY
	                    , Color.DARK_GRAY), "Entity Pallete"));
		
		mainFrame.getContentPane().add(palletPanel, BorderLayout.WEST);
		
		
		//CENTER AREA
		mapPanel = new MapEditorPanel();
		mapPanel.configure(centralPos, this);
		mainFrame.getContentPane().add(mapPanel, BorderLayout.CENTER);
		
		exePanel = new ExercisePanel(this);
		mainFrame.getContentPane().add(exePanel, BorderLayout.SOUTH);
		
		if (exe!=null) {
			this.exePanel.configure(exe);
			
			this.editEntityPanel.configure(exe);
		}
		
		
	}
	
	public void setSelectedEntity(IEntityModel model) {
		this.selectEntity = model;
		this.editEntityPanel.update(model);
		System.out.println("selected entity = "+selectEntity);
	}
	
	public IEntityModel getSelectedEntity() {
		return selectEntity;
	}
	
	public List<IEntity> getEntities(){
		return this.editEntityPanel.getEntities();
	}
	
	
	public void setPosition(GeoPosition pos) {
		this.editEntityPanel.setCurrentPosition(pos);
	}
	
	public void updateMap(IEntity entity, String ops) {
		if (ops.equals("add"))
			mapPanel.updateEntity(entity);
		
		else
			mapPanel.deleteEntity(entity);
	}
	
	
	
	
	public void createAndShowGUI() {
		mainFrame.setVisible(true);
	}

	

	public void exit() {
		this.startGui.updateScenarios();
		mainFrame.setVisible(false);
	}


}
