package edu.gmu.c2sim.core.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jxmapviewer.viewer.GeoPosition;

import edu.gmu.c2sim.core.dao.ExerciseDao;
import edu.gmu.c2sim.core.gui.editor.ScenarioEditorPanel;
import edu.gmu.c2sim.core.sim.Exercise;
import edu.gmu.c2sim.core.sim.SimulationManager;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;

public class StartGUI {

	private JFrame frame;
	
	private JComboBox <String>comboBox;
	
	private GeoPosition initPos;
	
	public StartGUI(GeoPosition initPos) {
		this.initPos=initPos;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frame = new JFrame("C2 Simulator");
		frame.setBounds(100, 100, 480, 190);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Exercise Name:");
		lblNewLabel.setBounds(136, 26, 117, 16);
		frame.getContentPane().add(lblNewLabel);
		
		String comboBoxItems[] = loadExistentExercises();
		comboBox = new JComboBox(comboBoxItems);
		
		JButton loadExeButton = new JButton("Edit exercise");
		loadExeButton.setBounds(122, 71, 170, 29);
		loadExeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editExercise();
			}
		}); 
		frame.getContentPane().add(loadExeButton);
		
		JButton exitButton = new JButton("Start Exercise");
		exitButton.setBounds(132, 112, 342, 29);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startScenario();
			}
		}); 
		frame.getContentPane().add(exitButton);
		
		JButton logoButton = new JButton();
		logoButton.setIcon(new ImageIcon(StartGUI.class.getResource("/edu/gmu/c2sim/core/gui/ico/mason-logo.png")));
		logoButton.setBounds(7, 21, 117, 117);
		frame.getContentPane().add(logoButton);
		
		JButton newScenarioButton = new JButton("Create a Scenario");
		newScenarioButton.setEnabled(true);
		newScenarioButton.setBounds(285, 71, 189, 29);
		newScenarioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createScenario();
			}
		}); 
		frame.getContentPane().add(newScenarioButton);
		
		comboBox.setBounds(265, 22, 209, 27);
		frame.getContentPane().add(comboBox);
		
	}
	
	private String[] loadExistentExercises() {
		List<String> exercises = new ArrayList<>();
		exercises = ExerciseDao.getExercises();
		String exeArr [] = new String[exercises.size()];
		
		for (int i=0; i<exercises.size();i++) {
			String exe = exercises.get(i);
			exeArr[i] = exe;
		}
		return exeArr;
	};
	
	
	private void createScenario() {
		ScenarioEditorPanel scen = new ScenarioEditorPanel(this, this.initPos);
		scen.createAndShowGUI();
	}
	
	private void startScenario() {
		String exeName = (String)comboBox.getSelectedItem();
		SimulationManager simMan = SimulationManager.getInstance();
		simMan.runSimulation(exeName);
	}
	
	
	private void editExercise() {
		String exerciseName = (String)comboBox.getSelectedItem();
		Exercise exe = ExerciseDao.load(exerciseName);
		ScenarioEditorPanel scen = new ScenarioEditorPanel(this, initPos, exe);
		scen.createAndShowGUI();
	}
	
	
	public void createAndShowGUI() {
		this.getFrame().setVisible(true);
	}
	
	public void updateScenarios() {
		List <String> exeNames = ExerciseDao.getExercises();
		this.comboBox.removeAllItems();
		for (String name : exeNames)
			this.comboBox.addItem(name);
	}
	
	
	public JFrame getFrame() {
		return this.frame;
	}
	
}
