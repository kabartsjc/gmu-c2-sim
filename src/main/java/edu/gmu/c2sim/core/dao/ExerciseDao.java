package edu.gmu.c2sim.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.IEntityModel;
import edu.gmu.c2sim.core.entities.IEntity.TEAM;
import edu.gmu.c2sim.core.plan.IPlan;
import edu.gmu.c2sim.core.sim.Exercise;

public class ExerciseDao {
	public static int drop(Connection conn, String exeName) {
		int result = 0;
		
		try {
			Statement statement = conn.createStatement();
			result = statement.executeUpdate("DELETE FROM exe WHERE exe_id='" + exeName + "';");
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Exercise.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			return -1;
		}

		return result;
	}
	
	public static int save(String exeName, int simu_speed, long simu_time, List<IEntity> entL) {
		DbConfiguration dbCon = DbConfiguration.getInstance();
		Connection conn = dbCon.getConnection();
		int result =0;

		if (conn != null) {
			result = drop(conn, exeName);
			
			try {
				Statement statement = conn.createStatement();
				result = statement.executeUpdate("INSERT INTO exe(exe_id, simu_time, speed_sim) " + "VALUES ('" + exeName + "',"
						+ simu_time + "," + simu_speed + ")");
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(Exercise.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
				return -1;
			}

			result = EntityDao.save(entL, exeName);

			if (result == -1) {
				EntityDao.drop(conn, entL, exeName);
				drop(conn, exeName);
				return -1;
			}
		}

		return result;

	}
	
	
	public static Exercise load(String exeName) {
		DbConfiguration dbCon = DbConfiguration.getInstance();
		Connection conn = dbCon.getConnection();
		Exercise exe = null;
		if (conn != null) {
			// retrieve exercise
			String query = "select * from exe where exe_id = '" + exeName + "';";
			PreparedStatement pst;
			try {
				pst = conn.prepareStatement(query);
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					String exeId = rs.getString(1);
					long simuTime = rs.getLong(2);
					int simuSpeed = rs.getInt(3);
					
					exe = new Exercise(exeId, simuSpeed, simuTime);
					
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(IEntityModel.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
				return null;
			}
			
			int result = EntityDao.load(conn, exe); 
			
			if (result==0) {
				for (IEntity ent : exe.getBlueTeam()) {
					IPlan plan = PlanDao.load(ent, TEAM.BLUE, conn, exe);
					ent.setPlan(plan);
				}
				
				for (IEntity ent : exe.getGreenTeam()) {
					IPlan plan = PlanDao.load(ent, TEAM.GREEN, conn,exe);
					ent.setPlan(plan);
				}

				for (IEntity ent : exe.getRedTeam()) {
					IPlan plan = PlanDao.load(ent, TEAM.RED, conn,exe);
					ent.setPlan(plan);
				}
			}
			
		}

		return exe;
	}
	
	
	public static List<String> getExercises() {
		List<String> exeL = new ArrayList<>();

		DbConfiguration dbCon = DbConfiguration.getInstance();
		Connection conn = dbCon.getConnection();

		if (conn != null) {
			String query = "select exe_id from exe;";
			PreparedStatement pst;
			try {
				pst = conn.prepareStatement(query);
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					String exe_id = rs.getString(1);
					exeL.add(exe_id);
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(IEntityModel.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}

		}
		return exeL;

	}
	

}
