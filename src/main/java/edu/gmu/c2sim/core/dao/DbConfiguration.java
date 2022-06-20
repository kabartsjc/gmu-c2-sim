package edu.gmu.c2sim.core.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.gmu.c2sim.utils.Variables;

import java.util.logging.Logger;

public class DbConfiguration {

	
	private static String SQL_FILE = Variables.CONFIG_FOLDER + "/scripts/c2sim.sql";
	private String URL;
	private String USER; 
	private String PWD;

	private static DbConfiguration instance = null;
	private Connection conn = null;

	private DbConfiguration() {
		loadParams();
		configureConnections();
	}

	public static DbConfiguration getInstance() {
		if (instance == null)
			instance = new DbConfiguration();
		return instance;
	}

	private void configureConnections() {
		try {
			conn = DriverManager.getConnection(URL, USER, PWD);

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DbConfiguration.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

	public Connection getConnection() {
		return this.conn;

	}
	
	private void loadParams() {
		 String config = Variables.CONFIG_FOLDER +  "db-config.json";
		 try {
				Object obj = new JSONParser().parse(new FileReader(config));
				JSONObject jo = (JSONObject)obj;
				URL = (String) jo.get("URL");
				USER = (String) jo.get("DB_USER");
				PWD = (String) jo.get("DB_PASSWORD");

			} catch (IOException | ParseException e) {
				System.out.println("Error to parse and load the json file");
				e.printStackTrace();
			}
		 
		 
	}

	public void createDBConfigurations() {
		try {
			if (conn==null)
				conn= DriverManager.getConnection(URL, USER, PWD);

			ScriptRunner sr = new ScriptRunner(conn);
			// Creating a reader object
			Reader reader = new BufferedReader(new FileReader(SQL_FILE));
			// Running the script
			sr.runScript(reader);

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DbConfiguration.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} catch (FileNotFoundException ex) {
			Logger lgr = Logger.getLogger(DbConfiguration.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}

	}


}
