package edu.gmu.c2sim.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.gmu.c2sim.core.entities.IEntityType;
import edu.gmu.c2sim.core.entities.IEntityType.DOMAIN;
import edu.gmu.c2sim.core.entities.effector.sensors.ISensor;
import edu.gmu.c2sim.core.entities.effector.sensors.Sensor;

public class SensorDao {

	public static Hashtable<String,ISensor>  getSensors() {
		DbConfiguration dbCon = DbConfiguration.getInstance();
		Connection conn = dbCon.getConnection();
		
		Hashtable<String,ISensor> sensorDb = new Hashtable<>();

		if (conn != null) {
			String query = "select * from sensor_type;";
			PreparedStatement pst;
			try {
				pst = conn.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
				
				while (rs.next()) {
					String sensorID = rs.getString(1);
					double range = rs.getDouble(2);
					String domainS = rs.getString(3);
					DOMAIN domain = IEntityType.getDomain(domainS);
					double precision = rs.getDouble(4);
					
					System.out.println(sensorID+":"+range);
					
					ISensor sensor = new Sensor(sensorID, domain, precision, range);
					sensorDb.put(sensorID, sensor);
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(ISensor.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}

		}

		return sensorDb;
	}
}
