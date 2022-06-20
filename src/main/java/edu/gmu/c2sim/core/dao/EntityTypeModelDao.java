package edu.gmu.c2sim.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.gmu.c2sim.core.entities.EntityType;
import edu.gmu.c2sim.core.entities.IEntityType;

public class EntityTypeModelDao {

	public static Hashtable<String,IEntityType> load(){
		DbConfiguration dbCon = DbConfiguration.getInstance();
		Connection conn = dbCon.getConnection();
		
		Hashtable<String,IEntityType> entTypeDb = new Hashtable<>();
		if (conn != null) {
			String query = "select * from entity_type;";
			PreparedStatement pst;
			try {
				pst = conn.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
				
				while (rs.next()) {
					String id = rs.getString(1);
					String domainS = rs.getString(2);
					
					String imageName = rs.getString(3);
					System.out.println(id+":"+domainS+":"+imageName);
					IEntityType.DOMAIN domain = IEntityType.getDomain(domainS);
					IEntityType type = new EntityType(id, domain, imageName);
					entTypeDb.put(id, type);
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(IEntityType.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}
	
		}
		
		return entTypeDb;
	}

}
