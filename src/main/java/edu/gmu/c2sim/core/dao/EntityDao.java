package edu.gmu.c2sim.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.gmu.c2sim.core.entities.Command;
import edu.gmu.c2sim.core.entities.EntityFactory;
import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.IEntityModel;
import edu.gmu.c2sim.core.entities.IEntity.TEAM;
import edu.gmu.c2sim.core.entities.effector.sensors.ISensor;
import edu.gmu.c2sim.core.entities.effector.weapons.IWeapon;
import edu.gmu.c2sim.core.geo.SimCoordinate;
import edu.gmu.c2sim.core.sim.Exercise;

public class EntityDao {

	public static int drop(Connection conn, List<IEntity> entL, String exeName) {
		int rowsAffected = -1;

		if (conn != null) {

			for (IEntity ent : entL) {

				String SQL = "DELETE FROM entity WHERE alias_name = ?";

				PreparedStatement pst;
				try {
					pst = conn.prepareStatement(SQL);
					String alias = ent.getAlias();
					pst.setString(1, alias);

					rowsAffected = pst.executeUpdate();

				} catch (SQLException e) {
					return -1;
				}
			}
		}

		return rowsAffected;
	}

	public static int save(List<IEntity> entL, String exeName) {
		DbConfiguration dbCon = DbConfiguration.getInstance();
		Connection conn = dbCon.getConnection();

		int rowsAffected = drop(conn, entL, exeName);
		if (rowsAffected != -1) {
			try {
				for (IEntity ent : entL) {
					String alias = ent.getAlias();
					String model = ent.getModel().getID();
					String team = IEntity.parseTeam(ent.getTeam());
					String command = ent.getCommand().getName();
					String behavior = ent.getBehavior();
					List<IWeapon> weaponL = ent.getWeaponList();
					String weaponLS = "";
					if (weaponL != null) {
						if (weaponL.size() > 0) {
							weaponLS = weaponLS + weaponL.get(0).getId();
							for (int i = 1; i < weaponL.size(); i++) {
								IWeapon wp = weaponL.get(i);
								weaponLS = weaponLS + "," + wp.getId();
							}
						}
					}

					String sensorS = "";
					List<ISensor> sensorL = ent.getSensorList();
					if (sensorL != null) {
						if (sensorL.size() > 0) {
							sensorS = sensorS + sensorL.get(0).getId();
						}
					}

					String init_pos = SimCoordinate.convertToString(ent.getInitialPosition());

					String init_time = Long.toString(ent.getInitialTime());

					Statement statement = conn.createStatement();

					String stm = "INSERT INTO entity (exe_id,alias_name, model_id,behavior,team,command,weapon_list,sensor,initial_position, initial_time)"
							+ "VALUES ('" + exeName + "','" + alias + "','" + model + "','" + behavior + "','" + team
							+ "','" + command + "','" + weaponLS + "','" + sensorS + "','" + init_pos + "'," + init_time
							+ ");";
					rowsAffected = statement.executeUpdate(stm);
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(IEntity.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
				return -1;
			}
		}
		return rowsAffected;
	}

	public static int load(Connection conn, Exercise exe) {
		// retrieve entity
		String query = "select * from entity where exe_id = '" + exe.getId() + "';";
		Hashtable<String, IEntityModel> modelDb = IEntityModel.loadEntityModelHashtable();
		Hashtable<String, ISensor> sensorDb = SensorDao.getSensors();
		Hashtable<String, IWeapon> wpDb = WeaponsDao.getWeapons();
		
		try {
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				String alias = rs.getString(3);

				String modelIdS = rs.getString(4);
				IEntityModel model = modelDb.get(modelIdS);

				String teamS = rs.getString(6);
				TEAM team = IEntity.parseTeam(teamS);

				String commandName = rs.getString(7);
				Command command = null;
				if (exe.getCommand(commandName) != null) {
					command = exe.getCommand(commandName);
				}

				else {
					command = new Command(commandName);
					exe.addCommand(command);
				}

				int initialTime = rs.getInt(11);

				IEntity entity = EntityFactory.createEntity(model.getType().getName(), alias, model, team, command,
						initialTime);

				command.addCommandedForces(entity);

				String behavior = rs.getString(5);
				entity.setBehavior(behavior);

				String initialPosS = rs.getString(10);
				SimCoordinate initialPos = SimCoordinate.createCoordinate(initialPosS);
				entity.setInitialPosition(initialPos);

				String weaponLS = rs.getString(8);
				List<String> wpLS = IWeapon.convert(weaponLS);

				for (String wpS : wpLS) {
					IWeapon wp = wpDb.get(wpS);
					entity.addWeapon(wp);
				}

				String sensorS = rs.getString(9);
				ISensor sensor = sensorDb.get(sensorS);
				entity.addSensor(sensor);

				exe.addTeam(entity, team);
			}
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(IEntityModel.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			return -1;
		}
		
		return 0;
	}

}
