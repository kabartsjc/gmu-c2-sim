package edu.gmu.c2sim.core.sim;

import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.text.StringTokenizer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jxmapviewer.viewer.GeoPosition;

import edu.gmu.c2sim.core.dao.DbConfiguration;
import edu.gmu.c2sim.core.dao.ExerciseDao;
import edu.gmu.c2sim.core.gui.StartGUI;
import edu.gmu.c2sim.core.gui.run.SimulationRunnerPanel;
import edu.gmu.c2sim.utils.Variables;

public class SimulationManager {
	private long mode;
	private String exeName;

	private GeoPosition initPos;
	
	private StartGUI startGui;

	private String dist;
	private double[] distParams;

	private static SimulationManager instance=null;
	
	public static SimulationManager getInstance () {
		if (instance ==null)
			instance = new SimulationManager();
		return instance;
	}
	
	private SimulationManager() {
		loadConfig();
	}
	
	private void loadConfig() {
		String config = Variables.CONFIG_FOLDER + "sim-config.json";

		try {
			Object obj = new JSONParser().parse(new FileReader(config));
			JSONObject jo = (JSONObject) obj;

			mode = (Long) jo.get("mode");
			
			exeName = (String) jo.get("exe-name");


			double latit = (double) jo.get("latitude-init");
			double longit = (double) jo.get("longitude-init");
			this.initPos = new GeoPosition(latit, longit);

			this.dist = (String) jo.get("distribution");

			String paramS = (String) jo.get("params");
			StringTokenizer str = new StringTokenizer(paramS, ",");
			this.distParams = new double[str.size()];

			int i = 0;
			while (str.hasNext()) {
				distParams[i] = Double.parseDouble(str.next());
				i++;
			}

		} catch (IOException | ParseException e) {
			System.out.println("Error to parse and load the json file");
			e.printStackTrace();
		}

	}
	
	
	public void init() {
		if (mode==0) {
			System.out.println("Creating Database and other infrastructure");
			DbConfiguration dbCon = DbConfiguration.getInstance();
			dbCon.createDBConfigurations();
		}
		
		else if (mode==1) {
			System.out.println("starting simulation environment -- mode GUI");
			runGUIMode();
		}
		
		else if (mode==2) {
			System.out.println("starting simulation environment -- text mode");
			runSimulation(exeName);
		}
		
		else {
			System.out.println("check your config-file (sim-config.json)");
		}
		
		
		
	}
	
	public void runSimulation(String exeName) {
		if (this.exeName==null) {
			System.out.println("There is no exercise selected to run...");
			return;
		}
		
		Exercise exe = ExerciseDao.load(exeName);
		exe.configure(dist, distParams);
		
		Thread th = new Thread(exe);
		th.start();
		
		if ( mode==1) {
			System.out.println("starintg GUI...");
			
			SimulationRunnerPanel simRunnerGui=new SimulationRunnerPanel(this.startGui, initPos, exe);
			exe.setMainGUI(simRunnerGui);
			simRunnerGui.createAndShowGUI();
			
		}
		
	}
	
	private void runGUIMode() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				startGui = new StartGUI(initPos);
				startGui.createAndShowGUI();
			}
		});
	}
	

	public static void main(String args[]) {
		SimulationManager simMan =  SimulationManager.getInstance();
		simMan.init();
	}


}
