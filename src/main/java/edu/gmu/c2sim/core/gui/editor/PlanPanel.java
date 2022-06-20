package edu.gmu.c2sim.core.gui.editor;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import org.jxmapviewer.viewer.GeoPosition;

import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.IEntity.TEAM;
import edu.gmu.c2sim.core.entities.effector.sensors.ISensor;
import edu.gmu.c2sim.core.entities.effector.weapons.IWeapon;
import edu.gmu.c2sim.core.geo.SimCoordinate;
import edu.gmu.c2sim.core.orders.FindOrder;
import edu.gmu.c2sim.core.orders.IOrder;
import edu.gmu.c2sim.core.plan.IPlan;
import edu.gmu.c2sim.core.plan.Plan;
import edu.gmu.c2sim.utils.GeoUtils;

import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PlanPanel extends JPanel {

	private static final long serialVersionUID = -7348675378267386461L;

	private PlanEditorPanel scenPane;

	private Hashtable<String, IEntity> blueTeam;
	private Hashtable<String, IEntity> redTeam;
	private Hashtable<String, IEntity> greenTeam;

	private IEntity selectEntity;

	private JLabel lblTarget;
	private JComboBox<Object> cbTarget;

	private JTextField txtEntType;

	private JLabel lblTypeOrder;
	private JComboBox<Object> cbTypeOrder;

	private JLabel lblDistance;
	private JTextField txtDistance;

	private JLabel lblWeaponRange;
	private JTextField txtWeaponRange;

	private JLabel lblSensorRange;
	private JTextField txtSensorRange;

	private JLabel lblInitialOrderPosition;
	private JTextField txtInitialOrderPosition;

	private JLabel lblStartTime;
	private JTextField txtStartTime;

	private JLabel lblTimeOnTarget;
	private JTextField txtTimeOnTarget;

	private JLabel lblEffectDuration;
	private JTextField txtEffectDuration;

	private JLabel lblOptional;
	private JComboBox<Object> cbOptional;

	private JButton btAdd;

	private JButton btDelete;

	private JButton btEdit;
	
	private JButton btUpdate;
	
	private JButton btSavePlan;

	private JTable jtable;

	int selectLine = 0;
	/**
	 * Create the application.
	 */
	public PlanPanel(PlanEditorPanel scenPane, IEntity ent, List<IEntity> entL) {
		super(new MigLayout());
		this.scenPane = scenPane;
		this.selectEntity = ent;
		blueTeam = new Hashtable<>();
		redTeam = new Hashtable<>();
		greenTeam = new Hashtable<>();
		loadTeamList(entL);
		initialize();
	}

	private void loadTeamList(List<IEntity> entityL) {
		blueTeam = new Hashtable<>();
		redTeam = new Hashtable<>();
		greenTeam = new Hashtable<>();

		for (IEntity ent : entityL) {
			TEAM team = ent.getTeam();

			if (team == TEAM.BLUE)
				blueTeam.put(ent.getAlias(), ent);
			else if (team == TEAM.GREEN)
				greenTeam.put(ent.getAlias(), ent);
			else
				redTeam.put(ent.getAlias(), ent);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// linha 1
		lblTarget = new JLabel("TARGET:");
		add(lblTarget, "align label");

		cbTarget = new JComboBox<>();
		add(cbTarget, "align label");
		cbTarget.setEnabled(false);
		cbTarget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateEntitytType();
			}
		});

		this.txtEntType = new JTextField();
		add(txtEntType, "align label");
		txtEntType.setColumns(10);
		txtEntType.setEnabled(false);

		lblTypeOrder = new JLabel("ORDER TYPE:");
		add(lblTypeOrder, "align label");
		
		String orderTypeS[] = IOrder.getOrderTypes(selectEntity.getModel().getType().getDomain());
		cbTypeOrder = new JComboBox<>(orderTypeS);
		add(cbTypeOrder, "align label");
		cbTypeOrder.setEnabled(false);
		cbTypeOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateEntitytType();
			}
		});

		lblDistance = new JLabel("DISTANCE:");
		add(lblDistance, "align label");

		this.txtDistance = new JTextField();
		add(txtDistance, "align label");
		txtDistance.setColumns(10);
		txtDistance.setText("0");
		txtDistance.setEnabled(false);

		// linha 2

		lblWeaponRange = new JLabel("WEAPON RANGE:");
		add(lblWeaponRange, "align label");

		this.txtWeaponRange = new JTextField(0);
		add(txtWeaponRange, "align label");
		txtWeaponRange.setColumns(10);
		txtWeaponRange.setText("0");
		txtWeaponRange.setEnabled(false);

		lblSensorRange = new JLabel("SENSOR RANGE:");
		add(lblSensorRange, "align label");

		this.txtSensorRange = new JTextField(0);
		add(txtSensorRange, "wrap");
		txtSensorRange.setColumns(10);
		txtSensorRange.setText("0");
		txtSensorRange.setEnabled(false);

		// LINHA 3

		lblInitialOrderPosition = new JLabel("INIT POSITION:");
		add(lblInitialOrderPosition, "align label");

		this.txtInitialOrderPosition = new JTextField();
		add(txtInitialOrderPosition, "align label");
		txtInitialOrderPosition.setColumns(10);
		txtInitialOrderPosition.setText("0,0");
		txtInitialOrderPosition.setEnabled(false);

		lblTimeOnTarget = new JLabel("TIME ON TARGET (min):");
		add(lblTimeOnTarget, "align label");

		this.txtTimeOnTarget = new JTextField();
		add(txtTimeOnTarget, "align label");
		txtTimeOnTarget.setColumns(10);
		txtTimeOnTarget.setText("0");
		txtTimeOnTarget.setEnabled(false);

		// LINHA 4

		lblEffectDuration = new JLabel("EFFECT DURATION (min):");
		add(lblEffectDuration, "align label");

		this.txtEffectDuration = new JTextField();
		add(txtEffectDuration, "align label");
		txtEffectDuration.setColumns(10);
		txtEffectDuration.setText(new String("0"));
		txtEffectDuration.setEnabled(false);

		lblStartTime = new JLabel("START-TIME (min):");
		add(lblStartTime, "align label");

		this.txtStartTime = new JTextField();
		add(txtStartTime, "align label");
		txtStartTime.setColumns(10);
		
		txtStartTime.setText(new String(new String("0")));
		txtStartTime.setEnabled(false);

		lblOptional = new JLabel("OPTIONAL:");
		add(lblOptional, "align label");

		String[] optionS = { "true", "false" };
		cbOptional = new JComboBox<>(optionS);
		add(cbOptional, "wrap");
		cbOptional.setEnabled(false);

		// create a seperator
		JSeparator s = new JSeparator();

		// set layout as vertical
		s.setOrientation(SwingConstants.HORIZONTAL);
		add(s, "span");

		String column[] = { "ORDER-ID", "ORDER-TYPE", "TARGET", "INIT-POS", "START-TIME(min)", "ToT(min)", "EFF-DUR(min)",
				"OPTIONAL" };
		Object[][] data = new Object[][] {};

		DefaultTableModel model = new DefaultTableModel(data, column);
		jtable = new JTable(model);

		JScrollPane sp = new JScrollPane(jtable);
		sp.setPreferredSize(new Dimension(10, 150));
		sp.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.GRAY, Color.DARK_GRAY),
				"Scenario Entities"));
		add(sp, "south, gaptop 15, h 150");

		// create a seperator
		JSeparator s1 = new JSeparator();

		// set layout as vertical
		s1.setOrientation(SwingConstants.HORIZONTAL);
		add(s1, "align label");

		btAdd = new JButton("Add");
		btAdd.setPreferredSize(new Dimension(100, 10));
		btAdd.setBackground(Color.LIGHT_GRAY);
		btAdd.setBorder(new BevelBorder(BevelBorder.RAISED));
		btAdd.setIcon(new ImageIcon(PlanPanel.class.getResource("/edu/gmu/c2sim/core/gui/ico/add.jpg")));
		add(btAdd, "tag Add, span, split 5, sizegroup bttn");
		btAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addOrder();
			}
		});

		btEdit = new JButton("Edit");
		btEdit.setPreferredSize(new Dimension(100, 10));
		btEdit.setBackground(Color.LIGHT_GRAY);
		btEdit.setBorder(new BevelBorder(BevelBorder.RAISED));
		btEdit.setIcon(new ImageIcon(PlanPanel.class.getResource("/edu/gmu/c2sim/core/gui/ico/edit.jpg")));
		add(btEdit, "tag Edit, sizegroup bttn");
		btEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editOrder();
			}
		});

		btDelete = new JButton("Delete");
		btDelete.setPreferredSize(new Dimension(100, 10));
		btDelete.setBackground(Color.LIGHT_GRAY);
		btDelete.setBorder(new BevelBorder(BevelBorder.RAISED));
		btDelete.setIcon(new ImageIcon(PlanPanel.class.getResource("/edu/gmu/c2sim/core/gui/ico/delete.png")));
		add(btDelete, "tag Delete, sizegroup bttn");
		btDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteOrder();
			}
		});
		
		
		btUpdate = new JButton("Update");
		btUpdate.setPreferredSize(new Dimension(100, 10));
		btUpdate.setBackground(Color.LIGHT_GRAY);
		btUpdate.setBorder(new BevelBorder(BevelBorder.RAISED));
		btUpdate.setIcon(new ImageIcon(PlanPanel.class.getResource("/edu/gmu/c2sim/core/gui/ico/update.png")));
		add(btUpdate, "tag Delete, sizegroup bttn");
		btUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateOrder();
			}
		});

		btSavePlan = new JButton("Save Plan");
		btSavePlan.setPreferredSize(new Dimension(100, 10));
		btSavePlan.setBackground(Color.LIGHT_GRAY);
		btSavePlan.setBorder(new BevelBorder(BevelBorder.RAISED));
		btSavePlan.setIcon(new ImageIcon(PlanPanel.class.getResource("/edu/gmu/c2sim/core/gui/ico/save.png")));
		add(btSavePlan, "tag savePlan, sizegroup bttn");
		btSavePlan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				savePlan();
			}
		});

		initPane(this.selectEntity);

	}

	private void updateEntitytType() {
		String entName = (String) this.cbTarget.getSelectedItem();
		String model = null;

		IEntity target = null;

		if (this.selectEntity.getTeam() == TEAM.BLUE) {
			target = this.redTeam.get(entName);
			model = target.getModel().getID();
		}

		else if (this.selectEntity.getTeam() == TEAM.RED) {
			target = this.blueTeam.get(entName);
			if (target == null)
				target = this.greenTeam.get(entName);
			model = target.getModel().getID();
		}

		this.txtEntType.setText(model);

		DecimalFormat df = new DecimalFormat("0.000");

		double distance = GeoUtils.calculateHDistance(selectEntity.getInitialPosition(), target.getInitialPosition());
		txtDistance.setText(df.format(distance));

		double wpRange = weaponRange(selectEntity);
		txtWeaponRange.setText(df.format(wpRange));

		double sensRange = sensorRange(selectEntity);
		txtSensorRange.setText(df.format(sensRange));
	}

	private void savePlan() {
		scenPane.savePlan();
	}
	
	private void addOrder() {
		int number = 0;
		
		String entName = (String) this.cbTarget.getSelectedItem();
		IEntity target = null;
		

		if (this.selectEntity.getTeam() == TEAM.BLUE) {
			target = this.redTeam.get(entName);
		}

		else if (this.selectEntity.getTeam() == TEAM.RED) {
			target = this.blueTeam.get(entName);
			if (target == null)
				target = this.greenTeam.get(entName);
		}
		
		String orderType = (String) cbTypeOrder.getSelectedItem();
		IOrder order = null;
		
		if (selectEntity.getPlan()!=null) {
			if (selectEntity.getPlan().getOrders().size()>0) {
				for (IOrder od : selectEntity.getPlan().getOrders()) {
					String uri = od.getURI();
					int uriI = Integer.parseInt(uri);
					if (uriI>number)
						number = uriI;
				}
				number = number+1;
			}
		}
		
		String uri = Integer.toString(number);

		String initPos = txtInitialOrderPosition.getText();
		SimCoordinate missionLocation = SimCoordinate.createCoordinate(initPos);

		String startTimeS = txtStartTime.getText();
		long startTime = Long.parseLong(startTimeS);

		String tot = txtTimeOnTarget.getText();
		long timeOverTarget = Long.parseLong(tot);

		String effDur = txtEffectDuration.getText();
		long effectDuration = Long.parseLong(effDur);

		String optionalS = (String) cbOptional.getSelectedItem();
		boolean optional = Boolean.parseBoolean(optionalS);
		
		if (orderType.equals("FindOrder")) {
			order = new FindOrder(uri, selectEntity, target, missionLocation, startTime, timeOverTarget, effectDuration,
					optional);
		}
		
		
		selectEntity.getPlan().addOrder(order);
		
		DefaultTableModel model = (DefaultTableModel) jtable.getModel();
		//"ORDER-ID", "ORDER-TYPE", "TARGET", "INIT-POS", "START-TIME(min)", "ToT(min)", "EFF-DUR(min)","OPTIONAL"
		model.addRow(
				new Object[] { uri, orderType, target.getAlias(), initPos, startTime, tot, effDur, optionalS });
		number++;
		
		
		scenPane.printWP();


		/*if (orderType.equals("FindOrder")) {
			
			if (selectEntity.getPlan()!=null) {
				if (selectEntity.getPlan().getOrders().size()>0) {
					//number = selectEntity.getPlan().getOrders().size()+1;
					for (IOrder od : selectEntity.getPlan().getOrders()) {
						String uri = od.getURI();
						int uriI = Integer.parseInt(uri);
						if (uriI>number)
							number = uriI;
					}
					number = number+1;
				}
			}
			
			String uri = Integer.toString(number);

			String initPos = txtInitialOrderPosition.getText();
			SimCoordinate missionLocation = new SimCoordinate(initPos);

			String startTimeS = txtStartTime.getText();
			long startTime = Long.parseLong(startTimeS);

			String tot = txtTimeOnTarget.getText();
			long timeOverTarget = Long.parseLong(tot);

			String effDur = txtEffectDuration.getText();
			long effectDuration = Long.parseLong(effDur);

			String optionalS = (String) cbOptional.getSelectedItem();
			boolean optional = Boolean.parseBoolean(optionalS);
			
			order = new FindOrder(uri, selectEntity, target, missionLocation, startTime, timeOverTarget, effectDuration,
					optional);

			selectEntity.getPlan().addOrder(order);
			

			DefaultTableModel model = (DefaultTableModel) jtable.getModel();
			//"ORDER-ID", "ORDER-TYPE", "TARGET", "INIT-POS", "START-TIME(min)", "ToT(min)", "EFF-DUR(min)","OPTIONAL"
			model.addRow(
					new Object[] { uri, orderType, target.getAlias(), initPos, startTime, tot, effDur, optionalS });
			number++;
		}
		
		scenPane.printWP();*/

	}

	private void updateOrder() {
		String entName = (String) this.cbTarget.getSelectedItem();
		IEntity target = null;
		
		if (this.selectEntity.getTeam() == TEAM.BLUE) {
			target = this.redTeam.get(entName);
		}

		else if (this.selectEntity.getTeam() == TEAM.RED) {
			target = this.blueTeam.get(entName);
			if (target == null)
				target = this.greenTeam.get(entName);
		}
		
		String orderType = (String) cbTypeOrder.getSelectedItem();
		IOrder order = null;

		if (orderType.equals("FindOrder")) {
			String uri = (String) jtable.getValueAt(selectLine, 0);
			
			String initPos = txtInitialOrderPosition.getText();
			SimCoordinate missionLocation = SimCoordinate.createCoordinate(initPos);

			String startTimeS = txtStartTime.getText();
			long startTime = Long.parseLong(startTimeS);

			String tot = txtTimeOnTarget.getText();
			long timeOverTarget = Long.parseLong(tot);

			String effDur = txtEffectDuration.getText();
			long effectDuration = Long.parseLong(effDur);

			String optionalS = (String) cbOptional.getSelectedItem();
			boolean optional = Boolean.parseBoolean(optionalS);
			
			if (selectEntity.getPlan()!=null) {
				List<IOrder> orderL = selectEntity.getPlan().getOrders();
				boolean hasOrder = false;
				
				for (int i=0; i<orderL.size();i++) {
					IOrder od = orderL.get(i);
					if (od.getURI().equals(uri)) {
						hasOrder=true;
						od.setUri(uri);
						od.setTarget(target);
						od.setMissionLocation(missionLocation);
						od.setStartTime(startTime);
						od.setTimeOnTarget(timeOverTarget);
						od.setEffectDuration(effectDuration);
						od.setOptional(optional);
					}
				}
				
				if (hasOrder==false) {
					order = new FindOrder(uri, selectEntity, target, missionLocation, startTime, timeOverTarget, 
							effectDuration,
							optional);
					selectEntity.getPlan().addOrder(order);
				}
				
				
			}
			
						
			//"ORDER-ID", "ORDER-TYPE", "TARGET", "INIT-POS", "START-TIME(min)", "ToT(min)", "EFF-DUR(min)","OPTIONAL"
			
			DefaultTableModel model = (DefaultTableModel) jtable.getModel();
			model.setValueAt(uri, selectLine, 0);
			model.setValueAt(orderType, selectLine, 1);
			model.setValueAt(target.getAlias(), selectLine, 2);
			model.setValueAt(initPos, selectLine, 3);
			model.setValueAt(startTime, selectLine, 4);
			model.setValueAt(tot, selectLine, 5);
			model.setValueAt(effDur, selectLine, 6);
			model.setValueAt(optionalS, selectLine, 7);
			
		}

		scenPane.printWP();
	}

	private void editOrder() {
		//// "ORDER-ID","ORDER-TYPE","TARGET", "INIT-POS", "START-TIME(s)","ToT(s)",
		//// "EFF-DUR(s)","OPTIONAL"

		selectLine = jtable.getSelectedRow();
		
		String type = (String) jtable.getValueAt(selectLine, 1);
		cbTypeOrder.setSelectedItem(type);

		String target = (String) jtable.getValueAt(selectLine, 2);
		cbTarget.setSelectedItem(target);

		IEntity targetE = null;

		if (selectEntity.getTeam() == TEAM.BLUE) {
			targetE = redTeam.get(target);
		}

		else if (selectEntity.getTeam() == TEAM.RED) {
			targetE = blueTeam.get(target);
			if (targetE == null)
				targetE = greenTeam.get(target);
		}

		String model = targetE.getModel().getID();

		txtEntType.setText(model);

		DecimalFormat df = new DecimalFormat("0.000");

		double distance = GeoUtils.calculateHDistance(selectEntity.getInitialPosition(), targetE.getInitialPosition());
		txtDistance.setText(df.format(distance));

		double wpRange = weaponRange(selectEntity);
		txtWeaponRange.setText(df.format(wpRange));

		double sensRange = sensorRange(selectEntity);
		txtSensorRange.setText(df.format(sensRange));

		String initPos = (String) jtable.getValueAt(selectLine, 3);
		txtInitialOrderPosition.setText(initPos);

		//Long startTime = (Long) jtable.getValueAt(selectLine, 4);
		
		Object startTime =  (Object)jtable.getValueAt(selectLine, 4);
		try {
			String stS = (String)(startTime);
			txtStartTime.setText(stS);
		} catch (ClassCastException e) {
			Long stS = (Long)(startTime);
			txtStartTime.setText(stS.toString());
		}
		

		String tot = (String) jtable.getValueAt(selectLine, 5);
		txtTimeOnTarget.setText(tot);

		String effDur = (String) jtable.getValueAt(selectLine, 6);
		txtEffectDuration.setText(effDur);

		String optional = (String) jtable.getValueAt(selectLine, 7);
		cbOptional.setSelectedItem(optional);

		scenPane.printWP();
	}

	private void deleteOrder() {
		int selectLine = jtable.getSelectedRow();
		String orderID = (String) jtable.getValueAt(selectLine, 0);

		selectEntity.getPlan().deleteOrder(orderID);

		int modelIndex = this.jtable.convertRowIndexToModel(selectLine); // converts the row index in the view to the
																			// appropriate index in the model
		DefaultTableModel model = (DefaultTableModel) jtable.getModel();
		model.removeRow(modelIndex);

		int rowcount = jtable.getRowCount();
		if (rowcount > 0) {
			selectLine = 0;
			String type = (String) jtable.getValueAt(selectLine, 1);
			cbTypeOrder.setSelectedItem(type);

			String target = (String) jtable.getValueAt(selectLine, 2);
			cbTarget.setSelectedItem(target);

			IEntity targetE = null;

			if (selectEntity.getTeam() == TEAM.BLUE) {
				targetE = redTeam.get(target);
			}

			else if (selectEntity.getTeam() == TEAM.RED) {
				targetE = blueTeam.get(target);
				if (targetE == null)
					targetE = greenTeam.get(target);
			}

			txtEntType.setText(target);

			DecimalFormat df = new DecimalFormat("0.000");

			double distance = GeoUtils.calculateHDistance(selectEntity.getInitialPosition(),
					targetE.getInitialPosition());
			txtDistance.setText(df.format(distance));

			double wpRange = weaponRange(selectEntity);
			txtWeaponRange.setText(df.format(wpRange));

			double sensRange = sensorRange(selectEntity);
			txtSensorRange.setText(df.format(sensRange));

			String initPos = (String) jtable.getValueAt(selectLine, 3);
			txtInitialOrderPosition.setText(initPos);

			String tot = (String) jtable.getValueAt(selectLine, 5);
			txtTimeOnTarget.setText(tot);
			
			Object startTime =  (Object)jtable.getValueAt(selectLine, 4);
			try {
				String stS = (String)(startTime);
				txtStartTime.setText(stS);
			} catch (ClassCastException e) {
				Long stS = (Long)(startTime);
				txtStartTime.setText(stS.toString());
			}
			
			String effDur = (String) jtable.getValueAt(selectLine, 6);
			txtEffectDuration.setText(effDur);

			String optional = (String) jtable.getValueAt(selectLine, 7);
			cbOptional.setSelectedItem(optional);
		}
		
		

		scenPane.printWP();
	}

	public void setPosition(GeoPosition pos) {
		this.txtInitialOrderPosition.setText(SimCoordinate.convertToString(pos));
	}

	public void initPane(IEntity entity) {

		cbTarget.removeAllItems();
		cbTarget.setEnabled(true);
		if (entity.getTeam() == TEAM.BLUE) {
			List<IEntity> redTeamL = new ArrayList<>(redTeam.values());
			for (IEntity ent : redTeamL) {
				cbTarget.addItem(ent.getAlias());
			}
		}

		else if (entity.getTeam() == TEAM.RED) {
			List<IEntity> blueL = new ArrayList<>(blueTeam.values());
			List<IEntity> greenL = new ArrayList<>(greenTeam.values());

			for (IEntity ent : blueL)
				cbTarget.addItem(ent.getAlias());

			for (IEntity ent : greenL)
				cbTarget.addItem(ent.getAlias());

		}

		txtDistance.setEnabled(true);
		txtWeaponRange.setEnabled(true);
		txtSensorRange.setEnabled(true);

		txtInitialOrderPosition.setEnabled(true);

		txtStartTime.setEnabled(true);

		txtTimeOnTarget.setEnabled(true);

		txtEffectDuration.setEnabled(true);

		cbOptional.setEnabled(true);

		cbTypeOrder.setEnabled(true);

		// load database
		
		IPlan plan = entity.getPlan();
	
		if (entity.getPlan() != null) {
			
			if (plan.getOrders().size()>0) {
				IOrder order = entity.getPlan().getOrders().get(0);
				IEntity target = order.getTarget();

				DecimalFormat df = new DecimalFormat("0.000");

				double distance = GeoUtils.calculateHDistance(entity.getInitialPosition(), target.getInitialPosition());
				txtDistance.setText(df.format(distance));

				double wpRange = weaponRange(entity);
				txtWeaponRange.setText(df.format(wpRange));

				double sensRange = sensorRange(entity);
				txtSensorRange.setText(df.format(sensRange));

				updateTable(entity.getPlan().getOrders());
			}
		}
		else {
			plan = new Plan("P_" + entity.getAlias());
			entity.setPlan(plan);
		}

		

	}

	private double weaponRange(IEntity ent) {
		double range = 0.0;
		for (IWeapon wp : ent.getWeaponList()) {
			double value = wp.getRange_m();
			if (range < value)
				range = value;
		}
		return range;
	}

	private double sensorRange(IEntity ent) {
		double range = 0.0;
		for (ISensor wp : ent.getSensorList()) {
			double value = wp.getRange_m();
			if (range < value)
				range = value;
		}
		return range;
	}

	private void updateTable(List<IOrder> orderL) {
		DefaultTableModel model = (DefaultTableModel) jtable.getModel();

		for (IOrder order : orderL) {
			//"ORDER-ID", "ORDER-TYPE", "TARGET", "INIT-POS", "START-TIME(min)", "ToT(min)", "EFF-DUR(min)","OPTIONAL"
			
			String id = order.getURI();
			String initPos = SimCoordinate.convertToString(order.getMissionLocation());
			String startTime = Long.toString(order.getStartTime());
			String type = order.getType();
			String tot = Long.toString(order.getTimeOnTarget());
			String effDur = Long.toString(order.getEffectTimeDuration());
			String optional = Boolean.toString(order.isOptional());
			String target = order.getTarget().getAlias();
			model.addRow(new Object[] { id, type,target, initPos, startTime, tot, effDur, optional });
		}
	}

	public void setInitialOrderPosition(GeoPosition geo) {
		String posS = SimCoordinate.convertToString(geo);
		this.txtInitialOrderPosition.setText(posS);
	}

	/*
	 * private void loadEntity() { String entID =
	 * (String)this.cbEntList.getSelectedItem(); IEntity ent =
	 * this.entityDB.get(entID); this.txtTarget.setText(entID);
	 * 
	 * String model = ent.getModel().getID(); this.textModel.setText(model);
	 * 
	 * String behavior = ent.getBehavior();
	 * this.cbBehavior.setSelectedItem(behavior);
	 * 
	 * String team = IEntity.parseTeam(ent.getTeam());
	 * this.comboTeam.setSelectedItem(team);
	 * 
	 * String command = ent.getCommand(); this.textCommand.setText(command);
	 * 
	 * IWeapon wp = ent.getWeapon("CANNON"); if (wp!=null) { String cannon =
	 * wp.getId(); this.comboBoxCannon.setSelectedItem(cannon);
	 * 
	 * }
	 * 
	 * wp = ent.getWeapon("MISSILE"); if (wp!=null) { String missile = wp.getId();
	 * this.comboBoxMissile.setSelectedItem(missile); }
	 * 
	 * 
	 * wp = ent.getWeapon("BOMB"); if (wp!=null) { String bombS = wp.getId();
	 * this.comboBoxBomb.setSelectedItem(bombS); }
	 * 
	 * wp = ent.getWeapon("EW"); if (wp!=null) { String ew =
	 * ent.getWeapon("EW").getId(); this.comboBoxEW.setSelectedItem(ew); }
	 * 
	 * long start_time = ent.getInitialTime();
	 * this.textSimulationTime.setText(Long.toString(start_time));
	 * 
	 * SimCoordinate init_pos = ent.getInitialPosition();
	 * this.textInitialPosition.setText(SimCoordinate.convertToString(init_pos));
	 * 
	 * ISensor sens = ent.getSensorList().get(0); if (sens!=null) { String sensor
	 * =sens.getId(); this.comboBoxSensor.setSelectedItem(sensor); }
	 * 
	 * this.updateIcon(ent.getModel());
	 * 
	 * }
	 * 
	 * 
	 * private void deleteEntity() { String entID =
	 * (String)this.cbEntList.getSelectedItem(); this.entityDB.remove(entID);
	 * 
	 * this.cbEntList.removeItem(entID);
	 * 
	 * entID = (String)this.cbEntList.getItemAt(0); IEntity ent =
	 * this.entityDB.get(entID); this.txtTarget.setText(entID);
	 * 
	 * String model = ent.getModel().getID(); this.textModel.setText(model);
	 * 
	 * String behavior = ent.getBehavior();
	 * this.cbBehavior.setSelectedItem(behavior);
	 * 
	 * String team = IEntity.parseTeam(ent.getTeam());
	 * this.comboTeam.setSelectedItem(team);
	 * 
	 * String command = ent.getCommand(); this.textCommand.setText(command);
	 * 
	 * IWeapon wp = ent.getWeapon("CANNON"); if (wp!=null) { String cannon =
	 * wp.getId(); this.comboBoxCannon.setSelectedItem(cannon);
	 * 
	 * }
	 * 
	 * wp = ent.getWeapon("MISSILE"); if (wp!=null) { String missile = wp.getId();
	 * this.comboBoxMissile.setSelectedItem(missile); }
	 * 
	 * 
	 * wp = ent.getWeapon("BOMB"); if (wp!=null) { String bombS = wp.getId();
	 * this.comboBoxBomb.setSelectedItem(bombS); }
	 * 
	 * wp = ent.getWeapon("EW"); if (wp!=null) { String ew =
	 * ent.getWeapon("EW").getId(); this.comboBoxEW.setSelectedItem(ew); }
	 * 
	 * long start_time = ent.getInitialTime();
	 * this.textSimulationTime.setText(Long.toString(start_time));
	 * 
	 * SimCoordinate init_pos = ent.getInitialPosition();
	 * this.textInitialPosition.setText(SimCoordinate.convertToString(init_pos));
	 * 
	 * ISensor sens = ent.getSensorList().get(0); if (sens!=null) { String sensor
	 * =sens.getId(); this.comboBoxSensor.setSelectedItem(sensor); }
	 * 
	 * this.updateIcon(ent.getModel());
	 * 
	 * }
	 * 
	 * private void saveEntity() { IEntity entity = null; String alias =
	 * this.txtTarget.getText();
	 * 
	 * if (this.selectModel==null) { JOptionPane.showMessageDialog(null,
	 * "It is required to select a model!", "InfoBox: " + "Error!!!",
	 * JOptionPane.ERROR_MESSAGE); return; }
	 * 
	 * if (alias.equals("")) { JOptionPane.showMessageDialog(null,
	 * "It is required to fill a name!", "InfoBox: " + "Error!!!",
	 * JOptionPane.ERROR_MESSAGE); return; }
	 * 
	 * String simTimeS = textSimulationTime.getText(); long initialTime = 0;
	 * 
	 * try { initialTime = Long.parseLong(simTimeS);
	 * 
	 * } catch (Exception e) { JOptionPane.showMessageDialog(null,
	 * "Initial Simulation Time is a long!", "InfoBox: " + "Error!!!",
	 * JOptionPane.ERROR_MESSAGE); return; }
	 * 
	 * String initPosS = textInitialPosition.getText(); SimCoordinate initPos =
	 * null; try { initPos = new SimCoordinate(initPosS); } catch (Exception e) {
	 * JOptionPane.showMessageDialog(null, "Initial Coordination cannot be null!",
	 * "InfoBox: " + "Error!!!", JOptionPane.ERROR_MESSAGE); return; }
	 * 
	 * String teamS = (String) this.comboTeam.getSelectedItem(); TEAM team =
	 * IEntity.parseTeam(teamS); String command = textCommand.getText();
	 * IEntityModel model = this.selectModel;
	 * 
	 * String behavior= (String) this.cbBehavior.getSelectedItem();
	 * 
	 * 
	 * DOMAIN domain = selectModel.getType().getDomain();
	 * 
	 * if (domain == DOMAIN.AIR) { entity = new AirForceUnit(alias, model, team,
	 * command, initialTime); }
	 * 
	 * else if (domain == DOMAIN.CYBER) { entity = new CyberUnit(alias, model, team,
	 * command, initialTime); }
	 * 
	 * else if (domain == DOMAIN.SPACE) { entity = new SpaceForceUnit(alias, model,
	 * team, command, initialTime); }
	 * 
	 * else if (domain == DOMAIN.LAND) { entity = new GroundForceUnit(alias, model,
	 * team, command, initialTime); }
	 * 
	 * else if (domain == DOMAIN.SEA) { entity = new SeaForceUnit(alias, model,
	 * team, command, initialTime); }
	 * 
	 * String bomb = (String) comboBoxBomb.getSelectedItem(); if (bomb!=null) {
	 * IWeapon wpBomb = getWeapon(bomb); if (wpBomb != null)
	 * entity.addWeapon(wpBomb); }
	 * 
	 * String cannon = (String) comboBoxCannon.getSelectedItem(); if (cannon!=null)
	 * { IWeapon wpCannon = getWeapon(cannon); if (wpCannon != null)
	 * entity.addWeapon(wpCannon); }
	 * 
	 * String ew = (String) comboBoxEW.getSelectedItem(); if (ew!=null) { IWeapon
	 * wpEw = getWeapon(ew); if (wpEw != null) entity.addWeapon(wpEw); }
	 * 
	 * String missile = (String) comboBoxMissile.getSelectedItem(); if
	 * (missile!=null) { IWeapon wpMissile = getWeapon(missile); if (wpMissile !=
	 * null) entity.addWeapon(wpMissile); }
	 * 
	 * String sensorS = (String) comboBoxSensor.getSelectedItem(); if
	 * (sensorS!=null) { ISensor sensor = getSensor(sensorS); if (sensor != null)
	 * entity.addSensor(sensor); }
	 * 
	 * entity.setBehavior(behavior);
	 * 
	 * entity.setInitialPosition(initPos);
	 * 
	 * addEntity(entity); }
	 * 
	 * 
	 * public void createPlan() { //TODO: }
	 * 
	 * 
	 * public void addEntity(IEntity entity) { if
	 * (this.entityDB.contains(entity.getAlias())) {
	 * this.entityDB.remove(entity.getAlias()); }
	 * 
	 * this.entityDB.put(entity.getAlias(), entity);
	 * 
	 * 
	 * this.cbEntList.removeAllItems(); for (IEntity ent : new
	 * ArrayList<>(entityDB.values())) { cbEntList.addItem(ent.getAlias()); }
	 * 
	 * scenPane.updateMap(entity); }
	 * 
	 * 
	 * public void update(IEntityModel model) { this.selectModel = model;
	 * textModel.setEditable(false); textModel.setText(model.getID());
	 * 
	 * txtTarget.setEnabled(true);
	 * 
	 * this.comboTeam.setEnabled(true);
	 * 
	 * textCommand.setEnabled(true);
	 * 
	 * textSimulationTime.setEnabled(true);
	 * 
	 * // textInitialPosition.setEnabled(true); comboBoxSensor.setEnabled(true);
	 * 
	 * this.cbEntList.setEnabled(true);
	 * 
	 * cbBehavior.setEnabled(true);
	 * 
	 * loadCombos(model); updateIcon(model); }
	 * 
	 * 
	 * public void updateIcon(IEntityModel model) { String name =
	 * model.getType().getImageName(); ImageIcon icon = createImageIcon(name,
	 * (String) comboTeam.getSelectedItem()); lblIcon.setIcon(icon); }
	 * 
	 * /** Returns an ImageIcon, or null if the path was invalid.
	 */
	/*
	 * protected ImageIcon createImageIcon(String name, String team) { //
	 * https://en.wikipedia.org/wiki/NATO_Joint_Military_Symbolog java.net.URL
	 * imgURL = null; String imgFile = ""; if (team.equals("BLUE")) { imgFile =
	 * imgFile + "b_" + name; }
	 * 
	 * else if (team.equals("RED")) { imgFile = imgFile + "r_" + name; }
	 * 
	 * else { imgFile = imgFile + "g_" + name; }
	 * 
	 * imgURL = PlanPanel.class.getClass().getResource(
	 * "/edu/gmu/c2sim/core/gui/ico/entities/" + imgFile);
	 * 
	 * if (imgURL != null) { return new ImageIcon(imgURL); } else {
	 * System.err.println("Couldn't find file: " + imgFile); return null; } }
	 * 
	 * @Override public void itemStateChanged(ItemEvent evt) { if
	 * (evt.getStateChange() == ItemEvent.SELECTED) { updateIcon(selectModel); } }
	 * 
	 * 
	 * 
	 * public IWeapon getWeapon(String weaponS) { if (weaponS.equals("")) return
	 * null;
	 * 
	 * for (IWeapon wp : weaponL) { if (wp.getId().equals(weaponS)) return wp; }
	 * 
	 * return null; }
	 * 
	 * public ISensor getSensor(String sensorS) { if (sensorS.equals("")) return
	 * null;
	 * 
	 * for (ISensor wp : sensorL) { if (wp.getId().equals(sensorS)) return wp; }
	 * return null; }
	 * 
	 * 
	 * public List<IEntity>getEntities(){ return new ArrayList<>(entityDB.values());
	 * }
	 */

}
