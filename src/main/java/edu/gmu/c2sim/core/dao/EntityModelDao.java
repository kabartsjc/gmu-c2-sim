package edu.gmu.c2sim.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.gmu.c2sim.core.entities.EntityModel;
import edu.gmu.c2sim.core.entities.IEntityModel;
import edu.gmu.c2sim.core.entities.IEntityType;

public class EntityModelDao {

	public static Hashtable<String, IEntityModel> load(Hashtable<String, IEntityType> entTypeDb) {
		DbConfiguration dbCon = DbConfiguration.getInstance();
		Connection conn = dbCon.getConnection();
	
		Hashtable<String, IEntityModel> entityModelDb = new Hashtable<>();
	
		if (conn != null) {
			String query = "select * from entity_model;";
			PreparedStatement pst;
			try {
				pst = conn.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
	
				while (rs.next()) {
					String modelId = rs.getString(1);
					String type_idS = rs.getString(2);
					IEntityType modelType = entTypeDb.get(type_idS);
					int numberOfPods = rs.getInt(3);
					double speedMs = rs.getDouble(4);
					double climbRate = rs.getDouble(5);
					double combatRange_m = rs.getDouble(6);
					double destructionFactor = rs.getDouble(7);
					double neutralizationFactor = rs.getDouble(8);
					double supressionFactor = rs.getDouble(9);
					double vulnerabilityFactor = rs.getDouble(10);
					System.out.println(modelId + ":" + modelType.getName());
	
					IEntityModel model = new EntityModel(modelId, modelType, numberOfPods, speedMs, climbRate,
							combatRange_m, destructionFactor, neutralizationFactor, supressionFactor,
							vulnerabilityFactor);
					entityModelDb.put(modelId, model);
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(IEntityModel.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}
	
		}
	
		return entityModelDb;
	}

}
