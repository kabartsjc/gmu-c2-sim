package edu.gmu.c2sim.core.gui.editor;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Window;
import java.util.List;

import javax.swing.border.EtchedBorder;

import org.jxmapviewer.viewer.GeoPosition;

import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.gui.ICoreGui;
import edu.gmu.c2sim.core.orders.IOrder;


public class PlanEditorPanel extends ICoreGui{

	private JFrame mainFrame;
	
	private MapEditorPanel mapPanel;
	private PlanPanel planPanel;
	
	private IEntity selectEntity=null;
	
	private List<IEntity> entL;
	
	
	/**
	 * Create the application.
	 */
	public PlanEditorPanel(GeoPosition centralPos, IEntity ent, List<IEntity> entL) {
		this.selectEntity = ent;
		this.entL=entL;
		initialize(centralPos);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(GeoPosition centralPos) {
		mainFrame = new JFrame();
		mainFrame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		// frame.setBounds(100, 100, 893, 582);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		//NORTH AREA
		planPanel = new PlanPanel(this,this.selectEntity,entL);
		planPanel.setBorder(
	            BorderFactory.createTitledBorder(
	            BorderFactory.createEtchedBorder(
	                    EtchedBorder.RAISED, Color.GRAY
	                    , Color.DARK_GRAY), "Plan Editor"));
		//editEntityPanel.setPreferredSize(new Dimension(10, 150));
		mainFrame.getContentPane().add(planPanel, BorderLayout.NORTH);
		
		
		
		//CENTER AREA
		mapPanel = new MapEditorPanel();
		mapPanel.configure(centralPos, this);
		mainFrame.getContentPane().add(mapPanel, BorderLayout.CENTER);
		
		
	}

	
	public void createAndShowGUI() {
		this.getFrame().setVisible(true);
		mapPanel.initPlanMap(selectEntity, entL);
	}

	private Window getFrame() {
		return mainFrame;
	}
	
	public void printWP() {
		List<IOrder> orderL = this.selectEntity.getPlan().getOrders();
		mapPanel.updateOrder(orderL, this.selectEntity, this.entL);
	}
	
	
	@Override
	public void setPosition(GeoPosition pos) {
		this.planPanel.setInitialOrderPosition(pos);
		
	}

	public void savePlan() {
		exit();
	}

	@Override
	public void exit() {
		this.getFrame().setVisible(false);
	}
	
	
/*	public void setSelectedEntity(IEntityModel model) {
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
	
	public void updateMap(IEntity entity) {
		mapPanel.updateEntity(entity);
	}
	
	
	

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
	/*	javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});*/
	//}

}
