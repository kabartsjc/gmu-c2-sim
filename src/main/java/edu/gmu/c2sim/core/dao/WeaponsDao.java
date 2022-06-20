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
import edu.gmu.c2sim.core.entities.effector.weapons.IWeapon;
import edu.gmu.c2sim.core.entities.effector.weapons.Weapon;

public class WeaponsDao {

	public static Hashtable<String,IWeapon>  getWeapons() {
		DbConfiguration dbCon = DbConfiguration.getInstance();
		Connection conn = dbCon.getConnection();
		
		Hashtable<String,IWeapon> weaponDb = new Hashtable<>();

		if (conn != null) {
			String query = "select * from weapon_type;";
			PreparedStatement pst;
			try {
				pst = conn.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
				
				while (rs.next()) {
					String weaponId = rs.getString(1);
					String type = rs.getString(2);
					boolean reusable = rs.getBoolean(3);
					double range = rs.getDouble(4);
					String domainS = rs.getString(5);
					DOMAIN domain = IEntityType.getDomain(domainS);
					double precision = rs.getDouble(6);
					
					double intensity = rs.getDouble(7);
					System.out.println(weaponId+":"+range);
					
					IWeapon weapon = new Weapon(weaponId, type,reusable, precision, domain, range, intensity);
					weaponDb.put(weaponId, weapon);
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(IWeapon.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}

		}

		return weaponDb;
	}

}
