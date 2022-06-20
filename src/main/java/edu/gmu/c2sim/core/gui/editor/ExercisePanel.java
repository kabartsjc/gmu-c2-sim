package edu.gmu.c2sim.core.gui.editor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.gmu.c2sim.core.dao.ExerciseDao;
import edu.gmu.c2sim.core.dao.PlanDao;
import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.sim.Exercise;

import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;

public class ExercisePanel extends JPanel {

	private static final long serialVersionUID = -7757086639029156368L;

	private JTextField tfExeName;
	private JComboBox<String> txtSimuSpeed;
	private JTextField txtSimDuration;
	private ScenarioEditorPanel scen;

	public ExercisePanel(ScenarioEditorPanel scen) {
		configure(scen);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void configure(ScenarioEditorPanel scen) {
		this.scen = scen;

		JLabel lblName = new JLabel("EXE NAME:");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setBounds(16, 31, 77, 16);
		add(lblName);

		tfExeName = new JTextField();
		tfExeName.setBounds(95, 26, 329, 26);
		add(tfExeName);
		tfExeName.setColumns(10);

		JLabel lblSpeed = new JLabel("SIMU SPEED (min):");
		lblSpeed.setBounds(267, 75, 77, 16);
		add(lblSpeed);

		String values[] = { "1", "5", "10" };
		txtSimuSpeed = new JComboBox<String>(values);
		txtSimuSpeed.setBounds(347, 70, 77, 26);
		add(txtSimuSpeed);

		JLabel lblSimDuration = new JLabel("SIMU DURATION (min):");
		lblSimDuration.setBounds(16, 76, 155, 16);
		add(lblSimDuration);

		txtSimDuration = new JTextField();
		txtSimDuration.setBounds(166, 71, 85, 26);
		add(txtSimDuration);
		txtSimDuration.setColumns(10);

		JButton btPersistExe = new JButton("Save Exercise");
		btPersistExe.setBounds(48, 230, 336, 29);
		add(btPersistExe);
		btPersistExe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveExe();
			}
		});

		JButton btExit = new JButton("Exit");
		btExit.setBounds(48, 230, 336, 29);
		add(btExit);
		btExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
	}
	
	public void configure(Exercise exe) {
		tfExeName.setText(exe.getId());
		
		int speed = exe.getSimu_speed_sec()/60;
		this.txtSimuSpeed.setSelectedItem(speed);
		
		long sim_dur = exe.getSimu_duration()/60;
		txtSimDuration.setText(Long.toString(sim_dur));
	}

	private void exit() {
		this.scen.exit();
	}

	private void saveExe() {
		String exeName = this.tfExeName.getText();

		String simuTimeS = this.txtSimDuration.getText();
		long simuTime = 0;
		try {
			simuTime = Long.parseLong(simuTimeS);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Simulation Time is a long!", "InfoBox: " + "Error!!!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		String speedSimS = (String) this.txtSimuSpeed.getSelectedItem();
		int speedSim = 0;
		try {
			speedSim = Integer.parseInt(speedSimS);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Simulation Speed is a int!", "InfoBox: " + "Error!!!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		List<IEntity> entL = this.scen.getEntities();
		
		int result = ExerciseDao.save(exeName, speedSim, simuTime, entL);
		
		if (result == -1) {
			JOptionPane.showMessageDialog(null, "Error in the save process in the database!", "InfoBox: " + "Error!!!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		else {

			result = PlanDao.save(exeName,entL);

			if (result == 0) {
				JOptionPane.showMessageDialog(null, "Your exercise was correctly saved in database!",
						"InfoBox: " + "Sucessfull!!!", JOptionPane.INFORMATION_MESSAGE);
			}

			else {
				JOptionPane.showMessageDialog(null, "There is an exercise in the database with the same id!",
						"InfoBox: " + "Error!!!", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
}
