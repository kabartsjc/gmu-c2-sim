package edu.gmu.c2sim.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.IEntityModel;
import edu.gmu.c2sim.core.entities.IEntity.TEAM;
import edu.gmu.c2sim.core.geo.SimCoordinate;
import edu.gmu.c2sim.core.orders.FindOrder;
import edu.gmu.c2sim.core.orders.IOrder;
import edu.gmu.c2sim.core.plan.IPlan;
import edu.gmu.c2sim.core.plan.Plan;
import edu.gmu.c2sim.core.sim.Exercise;

public class PlanDao {

	public static int drop(Connection conn, String exeID, List<IEntity> entL) {
		int result = 0;

		for (IEntity ent : entL) {
			try {
				Statement statement = conn.createStatement();
				// delete from order_plan where actor = 'navy1';
				result = statement.executeUpdate("DELETE FROM order_plan WHERE actor='" + ent.getAlias() + "';");
			} catch (SQLException ex1) {
				Logger lgr = Logger.getLogger(IPlan.class.getName());
				lgr.log(Level.SEVERE, ex1.getMessage(), ex1);
				return -1;
			}
		}

		return result;
	}

	public static int save(String exeID, List<IEntity> entL) {
		DbConfiguration dbCon = DbConfiguration.getInstance();
		Connection conn = dbCon.getConnection();

		int result = drop(conn, exeID, entL);

		if (result != -1) {
			for (IEntity ent : entL) {

				IPlan plan = ent.getPlan();

				if (plan != null) {
					for (IOrder order : plan.getOrders()) {
						String uri = order.getURI();

						String type = null;
						if (order instanceof FindOrder)
							type = "find-order";

						String actor = order.getActor().getAlias();
						String target = order.getTarget().getAlias();
						long effDur = order.getEffectTimeDuration();
						long startTime = order.getStartTime();
						long tot = order.getTimeOnTarget();

						boolean opt = order.isOptional();

						String pos = SimCoordinate.convertToString(order.getMissionLocation());

						if (conn != null) {
							try {
								Statement statement = conn.createStatement();
								result = statement.executeUpdate(
										"INSERT INTO order_plan(uri, type, actor, target,effect_time_duration,start_time"
												+ ",time_on_target,initial_position,optional, exe_id) " + "VALUES ('"
												+ uri + "','" + type + "','" + actor + "','" + target + "'" + ","
												+ effDur + "," + startTime + "," + tot + ",'" + pos + "'," + opt + ",'"
												+ exeID + "')");
							} catch (SQLException ex) {
								Logger lgr = Logger.getLogger(IPlan.class.getName());
								lgr.log(Level.SEVERE, ex.getMessage(), ex);
								return -1;
							}

						}

					}
				}

			}
		}

		return result;
	}

	public static IPlan load(IEntity ent, TEAM team, Connection conn, Exercise exe) {
		IPlan plan = null;

		try {
			String alias = ent.getAlias();

			plan = new Plan("pln_" + alias);

			String query = "select * from order_plan where actor = '" + alias + "';";
			PreparedStatement pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				IOrder order = null;
				String uri = rs.getString(2);
				String type = rs.getString(3);

				String targetS = rs.getString(5);
				IEntity target = null;
				
				target = exe.getEntity(targetS);

/*				if (team == TEAM.BLUE || team == TEAM.GREEN) {
					target = exe.getRedTeamDb().get(targetS);
				}

				else if (team == TEAM.RED) {
					target = exe.getGreenTeamDb().get(targetS);
					if (target == null)
						target = exe.getBlueTeamDb().get(targetS);
				}*/

				long effectDuration = rs.getLong(6);
				long startTime = rs.getLong(7);
				long timeOverTarget = rs.getLong(8);
				String initPos = rs.getString(9);
				boolean optional = rs.getBoolean(10);

				SimCoordinate missionLocation = SimCoordinate.createCoordinate(initPos);

				if (type.equals("find-order")) {
					order = new FindOrder(uri, ent, target, missionLocation, startTime, timeOverTarget, effectDuration,
							optional);
					if (order != null)
						plan.addOrder(order);
				}
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(IEntityModel.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			return null;
		}

		return plan;
	}

}
