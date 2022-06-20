package edu.gmu.c2sim.core.sim;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import edu.gmu.c2sim.core.entities.Command;
import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.IEntity.TEAM;
import edu.gmu.c2sim.core.gui.ICoreGuiObserver;
import edu.gmu.c2sim.core.gui.run.SimulationRunnerPanel;
import edu.gmu.c2sim.core.physics.WindEffector;

import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;

public class Exercise extends Observable implements Runnable {

	private String id;
	private int simu_speed_sec; // 1min, 5min, 10min
	private long simu_duration;

	private List<IEntity> redTeam;

	private List<IEntity> blueTeam;
	private List<IEntity> greenTeam;

	private Hashtable<String, Command> commandDb;

	private WindEffector windEffector;
	private long currentTime;
	private boolean running;

	private SimulationRunnerPanel simuRunner;

	public Exercise(String id, int simu_speed_min, long simu_duration_min) {
		this.id = id;
		this.simu_speed_sec = simu_speed_min * 60;
		this.simu_duration = simu_duration_min * 60;

		currentTime = 0;
		running = false;

		redTeam = new CopyOnWriteArrayList<>();
		blueTeam = new CopyOnWriteArrayList<>();
		greenTeam = new CopyOnWriteArrayList<>();
		commandDb = new Hashtable<>();

	}

	public void configure(String dist, double[] params) {
		this.windEffector = WindEffector.getInstance();
		this.windEffector.configure(dist, params);

		for (IEntity red : redTeam)
			red.init(windEffector, dist, params);
		for (IEntity blue : blueTeam)
			blue.init(windEffector, dist, params);
		for (IEntity green : greenTeam)
			green.init(windEffector, dist, params);
	}

	public void setMainGUI(SimulationRunnerPanel simuRunner) {
		this.simuRunner = simuRunner;
	}

	public String getId() {
		return id;
	}

	public int getSimu_speed_sec() {
		return simu_speed_sec;
	}

	public long getSimu_duration() {
		return simu_duration;
	}

	public WindEffector getWindEffector() {
		return windEffector;
	}

	public long getCurrentTime() {
		return currentTime;
	}

	public boolean isRunning() {
		return running;
	}

	public List<IEntity> getAllEntities() {
		List<IEntity> allEntry = new ArrayList<>();

		for (IEntity ent : getBlueTeam())
			allEntry.add(ent);

		for (IEntity ent : getRedTeam())
			allEntry.add(ent);

		for (IEntity ent : getGreenTeam())
			allEntry.add(ent);

		return allEntry;
	}

	public List<IEntity> getRedTeam() {
		return redTeam;
	}

	/*
	 * private Hashtable<String, IEntity> getRedTeamDb() { return null; }
	 */

	public List<IEntity> getBlueTeam() {
		return blueTeam;
	}

	/*
	 * public Hashtable<String, IEntity> getBlueTeamDb() { return blueTeam; }
	 */

	public List<IEntity> getGreenTeam() {
		return greenTeam;
	}

	/*
	 * public Hashtable<String, IEntity> getGreenTeamDb() { return greenTeam; }
	 */

	public IEntity getEntity(String alias) {

		for (IEntity ent : blueTeam) {
			if (ent.getAlias().equals(alias))
				return ent;
		}

		for (IEntity ent : redTeam) {
			if (ent.getAlias().equals(alias))
				return ent;
		}

		for (IEntity ent : greenTeam) {
			if (ent.getAlias().equals(alias))
				return ent;
		}

		return null;
	}

	public Command getCommand(String name) {
		return commandDb.get(name);
	}

	public void addCommand(Command command) {
		this.commandDb.put(command.getName(), command);
	}

	public void addTeam(IEntity ent, TEAM team) {
		if (team == TEAM.BLUE)
			blueTeam.add(ent);
		else if (team == TEAM.RED)
			redTeam.add(ent);
		else
			greenTeam.add(ent);
	}

	@Override
	public void run() {
		running = true;
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (running) {

			long lastUpdate = currentTime;
			currentTime = currentTime + simu_speed_sec;
			long interval = currentTime - lastUpdate;

			System.out.println("current simulation time is " + currentTime);

			windEffector.update();

			for (IEntity bEnt : blueTeam) {
				bEnt.update(interval, redTeam);
			}

			for (IEntity bEnt : redTeam) {
				bEnt.update(interval, blueTeam);
			}

			for (IEntity bEnt : greenTeam) {
				bEnt.update(interval, redTeam);
			}

			if (currentTime > this.simu_duration)
				finishSimulation();

			simuRunner.updateGUI(this.currentTime, this.getAllEntities());
			// mainGui.updateGUI(blueTeam, redTeam, greenTeam);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// setChanged();
			// notifyObservers();

		}

		finishSimulation();
	}

	public void addObservers(ICoreGuiObserver obj) {
		super.addObserver(obj);
	}

	private void finishSimulation() {
		running = false;
		// TODO: implement close logs

	}

}
