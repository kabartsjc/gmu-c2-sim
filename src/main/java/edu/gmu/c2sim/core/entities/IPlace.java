package edu.gmu.c2sim.core.entities;

import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.gmu.c2sim.core.entities.IEntity.TEAM;
import edu.gmu.c2sim.core.geo.SimCoordinate;
import edu.gmu.c2sim.utils.Variables;

public interface IPlace {
	
	public String getId() ;
	
	public TEAM getTeam();
	
	public SimCoordinate getPosition() ;


	public static Hashtable<String, IPlace> loadPlaceDB(String exeName){
		 Hashtable<String, IPlace> placeDB = new Hashtable<>();
		 String exeFolder = Variables.SRC_FOLDER + exeName + "/";
		 String placeFile = exeFolder +  "scenario_locations.json";
			try {
				Object obj = new JSONParser().parse(new FileReader(placeFile));
				JSONArray joArr = (JSONArray) obj;
				
				for (int i=0; i<joArr.size();i++) {
					JSONObject jo = (JSONObject)joArr.get(i);
					String id = (String) jo.get("id");
					String teamS = (String) jo.get("team");
					TEAM team = IEntity.parseTeam(teamS);
					String locationS = (String) jo.get("location");
					SimCoordinate location = SimCoordinate.createCoordinate(locationS); 
					
					Place place = new Place(id, team, location);
					
					placeDB.put(id, place);
				}

			} catch (IOException | ParseException e) {
				System.out.println("Error to parse and load the json file");
				e.printStackTrace();
			}

		 
		 return placeDB;
	}
}
