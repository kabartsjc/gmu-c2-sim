package edu.gmu.c2sim.core.gui.editor;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.jxmapviewer.viewer.GeoPosition;

import edu.gmu.c2sim.core.dao.SensorDao;
import edu.gmu.c2sim.core.dao.WeaponsDao;
import edu.gmu.c2sim.core.entities.Command;
import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.IEntity.TEAM;
import edu.gmu.c2sim.core.entities.IEntityModel;
import edu.gmu.c2sim.core.entities.IEntityType.DOMAIN;
import edu.gmu.c2sim.core.entities.domain.air.AirForceUnit;
import edu.gmu.c2sim.core.entities.domain.cyber.CyberUnit;
import edu.gmu.c2sim.core.entities.domain.land.GroundForceUnit;
import edu.gmu.c2sim.core.entities.domain.sea.SeaForceUnit;
import edu.gmu.c2sim.core.entities.domain.space.SpaceForceUnit;
import edu.gmu.c2sim.core.entities.effector.sensors.ISensor;
import edu.gmu.c2sim.core.entities.effector.weapons.IWeapon;
import edu.gmu.c2sim.core.geo.SimCoordinate;
import edu.gmu.c2sim.core.gui.components.ImageIconUtil;
import edu.gmu.c2sim.core.sim.Exercise;

import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class EntityEditorPanel extends JPanel implements ItemListener {

	private static final long serialVersionUID = -7348675378267386461L;

	private ScenarioEditorPanel scenPane;
	
	private JLabel lblAlias;
	private JTextField textAlias;
	private JLabel lblModel;
	private JTextField textModel;
	private JLabel lblTeam;
	private JComboBox<Object> comboTeam;
	private JLabel lblCommand;
	private JTextField textCommand;
	private JLabel lbCannon;
	private JComboBox<Object> comboBoxCannon;
	private JLabel lblMissile;
	private JComboBox<Object> comboBoxMissile;
	private JLabel lblBomb;
	private JComboBox<Object> comboBoxBomb;
	private JLabel lblStartTime;
	private JTextField textSimulationTime;
	private JLabel lblInitPosition;
	private JTextField textInitialPosition;
	private JLabel lblBehavior;
	private JComboBox<Object> cbBehavior;
	
	private JLabel lblIcon;
	private JLabel lblSensor;
	private JComboBox<Object> comboBoxSensor;
	private JLabel lblEW;
	private JComboBox<Object> comboBoxEW;

	private List<IWeapon> weaponL;
	private List<ISensor> sensorL;

	private IEntityModel selectModel;

	private JButton btSave;
	
	private Hashtable<String, IEntity> entityDB;
	
	
	private JComboBox <Object>cbEntList ;
	
	private JButton btLoadEntity;
	
	private JButton btDeleteEntity;
	
	private JButton btCreatePlan;
	
	
	private IEntity currentEntity ;
	
	private Hashtable<String,Command> commandDb;


	/**
	 * Create the application.
	 */
	public EntityEditorPanel(ScenarioEditorPanel scenPane) {
		super(new MigLayout("", "[][grow][grow][][][grow][][grow]", "[][][][][]"));
		weaponL = new ArrayList<>(WeaponsDao.getWeapons().values());
		sensorL = new ArrayList<>(SensorDao.getSensors().values());
		this.scenPane=scenPane;
		this.entityDB=new Hashtable<>();
		commandDb = new Hashtable<>();
		initialize();
	}
	
	public void configure(Exercise exe) {
		List<IEntity> blueTeam = exe.getBlueTeam();
		for (IEntity ent : blueTeam)
			addEntity(ent);
		
		List<IEntity> redTeam = exe.getRedTeam();
		for (IEntity ent : redTeam)
			addEntity(ent);
		
		List<IEntity> greenTeam = exe.getGreenTeam();
		for (IEntity ent : greenTeam)
			addEntity(ent);
		
		//this.cbEntList.setEnabled(true);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		lblIcon = new JLabel();
		add(lblIcon, "cell 2 0 2 1");

		lblAlias = new JLabel("ALIAS:");
		add(lblAlias, "cell 0 1,alignx trailing");

		this.textAlias = new JTextField();
		add(textAlias, "cell 1 1,growx");
		textAlias.setColumns(10);
		textAlias.setEnabled(false);

		lblModel = new JLabel("MODEL:");
		add(lblModel, "cell 2 1,alignx trailing");

		this.textModel = new JTextField("");
		add(textModel, "cell 3 1,growx");
		textModel.setColumns(10);
		textModel.setEnabled(false);

		String teamL[] = { "BLUE", "RED", "GREEN" };

		this.lblTeam = new JLabel("TEAM:");
		add(lblTeam, "cell 4 1,alignx trailing");
		this.comboTeam = new JComboBox<Object>(teamL);
		this.comboTeam.setEnabled(false);
		add(comboTeam, "cell 5 1,growx");
		comboTeam.addItemListener(this);

		this.lblCommand = new JLabel("COMMAND:");
		add(lblCommand, "cell 6 1,alignx trailing");

		this.textCommand = new JTextField();
		add(textCommand, "cell 7 1,growx");
		textCommand.setColumns(10);
		textCommand.setEnabled(false);

		this.lbCannon = new JLabel("CANNON:");
		add(this.lbCannon, "cell 0 2,alignx trailing");

		comboBoxCannon = new JComboBox<>();
		comboBoxCannon.setEnabled(false);
		add(comboBoxCannon, "cell 1 2 3 1,growx");

		lblMissile = new JLabel("MISSILE:");
		add(lblMissile, "cell 4 2,alignx trailing");

		comboBoxMissile = new JComboBox<>();
		add(comboBoxMissile, "cell 5 2,growx");
		comboBoxMissile.setEnabled(false);

		lblStartTime = new JLabel("START TIME (min):");
		add(lblStartTime, "cell 6 2,alignx trailing");

		textSimulationTime = new JTextField();
		add(textSimulationTime, "cell 7 2,growx");
		textSimulationTime.setColumns(10);
		textSimulationTime.setEnabled(false);

		lblBomb = new JLabel("BOMB:");
		add(lblBomb, "cell 0 3,alignx trailing");

		comboBoxBomb = new JComboBox<>();
		comboBoxBomb.setEnabled(false);
		add(comboBoxBomb, "cell 1 3 3 1,growx");

		lblInitPosition = new JLabel("INITIAL POSITION:");
		add(lblInitPosition, "cell 4 3,alignx trailing");

		textInitialPosition = new JTextField();
		add(textInitialPosition, "cell 5 3,growx");
		textInitialPosition.setColumns(10);
		textInitialPosition.setEnabled(false);
		
		lblBehavior= new JLabel("BEHAVIOR:");
		add(lblBehavior, "cell 6 3,alignx trailing");
		
		String[] behavS = {"PLAN","NEUTRAL","AGRESSIVE"};
		cbBehavior = new JComboBox<>(behavS);
		cbBehavior.setEnabled(false);
		add(cbBehavior, "cell 7 3,growx");

		lblEW = new JLabel("EW:");
		add(lblEW, "cell 0 4,alignx trailing");

		comboBoxEW = new JComboBox<>();
		add(comboBoxEW, "cell 1 4 3 1,growx");
		comboBoxEW.setEnabled(false);

		lblSensor = new JLabel("SENSOR:");
		add(lblSensor, "cell 4 4");

		comboBoxSensor = new JComboBox<>();
		add(comboBoxSensor, "cell 5 4,growx");
		comboBoxSensor.setEnabled(false);
		
		
		JLabel lblEntitiesDb = new JLabel("ENTITIES:");
		add(lblEntitiesDb, "cell 0 5,alignx trailing");
		
		cbEntList =  new JComboBox<>();
		add(cbEntList, "cell 1 5,growx");
		cbEntList.setEnabled(false);

		btSave = new JButton("Save/Update");
		btSave.setBackground(Color.LIGHT_GRAY);
        btSave.setBorder(new BevelBorder(BevelBorder.RAISED));
		btSave.setIcon(new ImageIcon(EntityEditorPanel.class.getResource("/edu/gmu/c2sim/core/gui/ico/save.png")));
		add(btSave, "cell 1 5,growx");
		btSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveEntity();
			}
		});

		
		btLoadEntity = new JButton("Load");
		btLoadEntity.setBackground(Color.LIGHT_GRAY);
		btLoadEntity.setBorder(new BevelBorder(BevelBorder.RAISED));
		btLoadEntity.setIcon(new ImageIcon(EntityEditorPanel.class.getResource("/edu/gmu/c2sim/core/gui/ico/load.png")));
		add(btLoadEntity, "cell 2 5,growx");
		btLoadEntity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadEntity();
			}
		});
		
		btDeleteEntity = new JButton("Delete");
		btDeleteEntity.setBackground(Color.LIGHT_GRAY);
		btDeleteEntity.setBorder(new BevelBorder(BevelBorder.RAISED));
		btDeleteEntity.setIcon(new ImageIcon(EntityEditorPanel.class.getResource("/edu/gmu/c2sim/core/gui/ico/delete.jpg")));
		add(btDeleteEntity, "cell 3 5,growx");
		btDeleteEntity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteEntity();
			}
		});
		
		
		btCreatePlan = new JButton("Create Plan");
		btCreatePlan.setBackground(Color.LIGHT_GRAY);
		btCreatePlan.setBorder(new BevelBorder(BevelBorder.RAISED));
		btCreatePlan.setIcon(new ImageIcon(EntityEditorPanel.class.getResource("/edu/gmu/c2sim/core/gui/ico/plan.png")));
		add(btCreatePlan, "cell 4 5,growx");
		btCreatePlan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createPlan();
			}
		});
		
		

	}
	
	
	public void setCurrentPosition(GeoPosition geo) {
		String posS = SimCoordinate.convertToString(geo);
		this.textInitialPosition.setText(posS);
	}
	
	private void loadEntity() {
		String entID = (String)this.cbEntList.getSelectedItem();
		IEntity ent = this.entityDB.get(entID);
		currentEntity=ent;
		this.textAlias.setText(entID);
		
		String model = ent.getModel().getID();
		this.textModel.setText(model);
		
		String behavior = ent.getBehavior();
		this.cbBehavior.setSelectedItem(behavior);
		
		String team = IEntity.parseTeam(ent.getTeam());
		this.comboTeam.setSelectedItem(team);
		
		String command = ent.getCommand().getName();
		this.textCommand.setText(command);
		
		IWeapon wp = ent.getWeapon("CANNON");
		if (wp!=null) {
			String cannon = wp.getId();
			this.comboBoxCannon.setSelectedItem(cannon);
			
		}
		
		wp = ent.getWeapon("MISSILE");
		if (wp!=null) {
			String missile = wp.getId();
			this.comboBoxMissile.setSelectedItem(missile);
		}
		
		
		wp = ent.getWeapon("BOMB");
		if (wp!=null) {
			String bombS = wp.getId();
			this.comboBoxBomb.setSelectedItem(bombS);
		}
		
		wp = ent.getWeapon("EW");
		if (wp!=null) {
			String ew = ent.getWeapon("EW").getId();
			this.comboBoxEW.setSelectedItem(ew);
		}
		
		long start_time = ent.getInitialTime();
		this.textSimulationTime.setText(Long.toString(start_time));
		
		SimCoordinate init_pos = ent.getInitialPosition();
		this.textInitialPosition.setText(SimCoordinate.convertToString(init_pos));
		
		ISensor sens = ent.getSensorList().get(0);
		if (sens!=null) {
			String sensor =sens.getId();
			this.comboBoxSensor.setSelectedItem(sensor);
		}
		
		this.updateIcon(ent.getModel());
		
	}
	
	
	private void deleteEntity() {
		String entID = (String)this.cbEntList.getSelectedItem();
		
		IEntity delEnt = this.entityDB.get(entID);
		this.entityDB.remove(entID);
		
		
		this.cbEntList.removeItem(entID);
		
		entID = (String)this.cbEntList.getItemAt(0);
		IEntity ent = this.entityDB.get(entID);
		this.textAlias.setText(entID);
		
		String model = ent.getModel().getID();
		this.textModel.setText(model);
		
		String behavior = ent.getBehavior();
		this.cbBehavior.setSelectedItem(behavior);
		
		String team = IEntity.parseTeam(ent.getTeam());
		this.comboTeam.setSelectedItem(team);
		
		String command = ent.getCommand().getName();
		this.textCommand.setText(command);
		
		IWeapon wp = ent.getWeapon("CANNON");
		if (wp!=null) {
			String cannon = wp.getId();
			this.comboBoxCannon.setSelectedItem(cannon);
			
		}
		
		wp = ent.getWeapon("MISSILE");
		if (wp!=null) {
			String missile = wp.getId();
			this.comboBoxMissile.setSelectedItem(missile);
		}
		
		
		wp = ent.getWeapon("BOMB");
		if (wp!=null) {
			String bombS = wp.getId();
			this.comboBoxBomb.setSelectedItem(bombS);
		}
		
		wp = ent.getWeapon("EW");
		if (wp!=null) {
			String ew = ent.getWeapon("EW").getId();
			this.comboBoxEW.setSelectedItem(ew);
		}
		
		long start_time = ent.getInitialTime();
		this.textSimulationTime.setText(Long.toString(start_time));
		
		SimCoordinate init_pos = ent.getInitialPosition();
		this.textInitialPosition.setText(SimCoordinate.convertToString(init_pos));
		
		ISensor sens = ent.getSensorList().get(0);
		if (sens!=null) {
			String sensor =sens.getId();
			this.comboBoxSensor.setSelectedItem(sensor);
		}
		
		this.updateIcon(ent.getModel());
		
		delEntity(delEnt);
		

	}

	private void saveEntity() {
		IEntity entity = null;
		String alias = this.textAlias.getText();
		
		if (this.selectModel==null) {
			JOptionPane.showMessageDialog(null, "It is required to select a model!", "InfoBox: " + "Error!!!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (alias.equals("")) {
			JOptionPane.showMessageDialog(null, "It is required to fill a name!", "InfoBox: " + "Error!!!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String simTimeS = textSimulationTime.getText();
		long initialTime = 0;

		try {
			initialTime = Long.parseLong(simTimeS);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Initial Simulation Time is a long!", "InfoBox: " + "Error!!!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		String initPosS = textInitialPosition.getText();
		SimCoordinate initPos = null;
		try {
			initPos = SimCoordinate.createCoordinate(initPosS); 
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Initial Coordination cannot be null!", "InfoBox: " + "Error!!!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String teamS = (String) this.comboTeam.getSelectedItem();
		TEAM team = IEntity.parseTeam(teamS);
		String commandS = textCommand.getText();
		Command command=null;
		if (commandDb.contains(commandS)) {
			command = commandDb.get(commandS);
		}
		
		else {
			command = new Command(commandS);
			commandDb.put(commandS, command);
		}
		
		IEntityModel model = this.selectModel;
		
		String behavior= (String) this.cbBehavior.getSelectedItem();
		
		DOMAIN domain = selectModel.getType().getDomain();

		if (domain == DOMAIN.AIR) {
			entity = new AirForceUnit(alias, model, team, command, initialTime);
		}

		else if (domain == DOMAIN.CYBER) {
			entity = new CyberUnit(alias, model, team, command, initialTime);
		}

		else if (domain == DOMAIN.SPACE) {
			entity = new SpaceForceUnit(alias, model, team, command, initialTime);
		}

		else if (domain == DOMAIN.LAND) {
			entity = new GroundForceUnit(alias, model, team, command, initialTime);
		}

		else if (domain == DOMAIN.SEA) {
			entity = new SeaForceUnit(alias, model, team, command, initialTime);
		}

		String bomb = (String) comboBoxBomb.getSelectedItem();
		if (bomb!=null) {
			IWeapon wpBomb = getWeapon(bomb);
			if (wpBomb != null)
				entity.addWeapon(wpBomb);
		}
		
		String cannon = (String) comboBoxCannon.getSelectedItem();
		if (cannon!=null) {
			IWeapon wpCannon = getWeapon(cannon);
			if (wpCannon != null)
				entity.addWeapon(wpCannon);
		}
		
		String ew = (String) comboBoxEW.getSelectedItem();
		if (ew!=null) {
			IWeapon wpEw = getWeapon(ew);
			if (wpEw != null)
				entity.addWeapon(wpEw);
		}
		
		String missile = (String) comboBoxMissile.getSelectedItem();
		if (missile!=null) {
			IWeapon wpMissile = getWeapon(missile);
			if (wpMissile != null)
				entity.addWeapon(wpMissile);
		}
		
		String sensorS = (String) comboBoxSensor.getSelectedItem();
		if (sensorS!=null) {
			ISensor sensor = getSensor(sensorS);
			if (sensor != null)
				entity.addSensor(sensor);
		}
		
		entity.setBehavior(behavior);
		
		entity.setInitialPosition(initPos);
		this.currentEntity=entity;
		
		addEntity(entity);
	}
	
	
	private void createPlan() {
		SimCoordinate pos = SimCoordinate.createCoordinate(textInitialPosition.getText());
		List<IEntity> entL = new ArrayList<>(entityDB.values());
		PlanEditorPanel planPanel = new PlanEditorPanel(pos.convert(), currentEntity,entL);
		planPanel.createAndShowGUI();
	}
	
	public void delEntity(IEntity entity) {
		if (this.entityDB.containsKey(entity.getAlias())) {
			this.entityDB.remove(entity.getAlias());
		}
		scenPane.updateMap(entity, "del");
	}
	
	public void addEntity(IEntity entity) {
		if (this.entityDB.containsKey(entity.getAlias())) {
			this.entityDB.remove(entity.getAlias());
		}
		
		this.entityDB.put(entity.getAlias(), entity);
		
		this.cbEntList.removeAllItems();
		for (IEntity ent : new ArrayList<>(entityDB.values())) {
			cbEntList.addItem(ent.getAlias());
		}
		scenPane.updateMap(entity, "add");
	}
	

	public void update(IEntityModel model) {
		this.selectModel = model;
		textModel.setEditable(false);
		textModel.setText(model.getID());

		textAlias.setEnabled(true);

		this.comboTeam.setEnabled(true);

		textCommand.setEnabled(true);

		textSimulationTime.setEnabled(true);

		// textInitialPosition.setEnabled(true);
		comboBoxSensor.setEnabled(true);
		
		this.cbEntList.setEnabled(true);
		
		cbBehavior.setEnabled(true);

		loadCombos(model);
		updateIcon(model);
	}

	private void loadCombos(IEntityModel model) {
		this.comboBoxBomb.removeAllItems();
		comboBoxCannon.removeAllItems();
		comboBoxEW.removeAllItems();
		comboBoxMissile.removeAllItems();
		comboBoxSensor.removeAllItems();

		comboBoxCannon.setEnabled(true);
		comboBoxBomb.setEnabled(true);
		comboBoxEW.setEnabled(true);
		comboBoxMissile.setEnabled(true);
		comboBoxSensor.setEnabled(true);
		
		cbBehavior.setEditable(true);

		DOMAIN modelDomain = model.getType().getDomain();

		if (model.getType().getName().equals("HQ") == false) {
			for (IWeapon weapon : weaponL) {
				DOMAIN weaponDomain = weapon.getDomain();
				if ((weaponDomain == modelDomain) || weaponDomain == DOMAIN.ALL) {
					String weaponType = weapon.getType();
					System.out.println(weaponType);
					if (weaponType.equals("CANNON")) {
						this.comboBoxCannon.addItem(weapon.getId());
					}

					else if (weaponType.equals("MISSILE")) {
						this.comboBoxMissile.addItem(weapon.getId());
					}

					else if (weaponType.equals("BOMB")) {
						this.comboBoxBomb.addItem(weapon.getId());
					}

					else if (weaponType.equals("EW")) {
						this.comboBoxEW.addItem(weapon.getId());
					}
				}
			}

			for (ISensor sensor : sensorL) {
				DOMAIN sensorDomain = sensor.getDomain();
				if ((sensorDomain == modelDomain) || sensorDomain == DOMAIN.ALL) {
					this.comboBoxSensor.addItem(sensor.getId());
				}
			}
		}

	}

	public void updateIcon(IEntityModel model) {
		String name = model.getType().getImageName();
		ImageIcon icon = ImageIconUtil.createImageIcon(name, (String) comboTeam.getSelectedItem());
		lblIcon.setIcon(icon);
	}

	
	@Override
	public void itemStateChanged(ItemEvent evt) {
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			updateIcon(selectModel);
		}
	}

	

	public IWeapon getWeapon(String weaponS) {
		if (weaponS.equals(""))
			return null;

		for (IWeapon wp : weaponL) {
			if (wp.getId().equals(weaponS))
				return wp;
		}

		return null;
	}

	public ISensor getSensor(String sensorS) {
		if (sensorS.equals(""))
			return null;

		for (ISensor wp : sensorL) {
			if (wp.getId().equals(sensorS))
				return wp;
		}
		return null;
	}
	
	
	public List<IEntity>getEntities(){
		return new ArrayList<>(entityDB.values());
	}

}
