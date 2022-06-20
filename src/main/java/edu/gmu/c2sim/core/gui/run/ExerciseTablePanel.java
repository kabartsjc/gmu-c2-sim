package edu.gmu.c2sim.core.gui.run;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.geo.SimCoordinate;
import net.miginfocom.swing.MigLayout;

public class ExerciseTablePanel extends JPanel {
	private static final long serialVersionUID = -5251164047683247097L;

	private JTable jtable;
	private ListSelectionModel listSelectionModel;
	
	private SimulationRunnerPanel simuPanel;

	public ExerciseTablePanel(SimulationRunnerPanel simuPanel) {
		super(new MigLayout());
		this.simuPanel = simuPanel;
	}

	public void configure(List<IEntity> entList) {
		String column[] = { "ALIAS", "MODEL", "TEAM", "BEHAVIOR", "POSITION", "ORDER", "TARGET", "STATUS" };
		Object[][] data = new Object[][] {};

		DefaultTableModel model = new DefaultTableModel(data, column);
		jtable = new JTable(model);
		listSelectionModel = jtable.getSelectionModel();
		listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
		jtable.setSelectionModel(listSelectionModel);

		JScrollPane sp = new JScrollPane(jtable);
		sp.setPreferredSize(new Dimension(10000, 150));
		sp.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.GRAY, Color.DARK_GRAY),
				"Simulation Information"));

		setTable(entList);
		add(sp, "south, gaptop 1, h 150");
	}

	private void setTable(List<IEntity> entL) {
		DefaultTableModel model = (DefaultTableModel) jtable.getModel();
		for (IEntity ent : entL) {
			// "ALIAS", "MODEL", "TEAM","BEHAVIOR", "POSITION", "ORDER","TARGET", "STATUS"
			String alias = ent.getAlias();
			String modelName = ent.getModel().getID();
			String team = IEntity.parseTeam(ent.getTeam());
			String behavior = ent.getBehavior();
			String pos = SimCoordinate.convertToString(ent.getCurrentPosition());
			String order = ent.getPlan().getCurrentTask().getType();
			String target = ent.getPlan().getCurrentTask().getTarget().getAlias();
			String status = IEntity.parseStatus(ent.getStatus());
			model.addRow(new Object[] { alias, modelName, team, behavior, pos, order, target, status });
		}
	}

	public void update(List<IEntity> entL) {
		DefaultTableModel model = (DefaultTableModel) jtable.getModel();
		for (int i = 0; i < entL.size(); i++) {
			IEntity ent = entL.get(i);
			// "ALIAS", "MODEL", "TEAM","BEHAVIOR", "POSITION", "ORDER","TARGET", "STATUS"
			String alias = ent.getAlias();
			String modelName = ent.getModel().getID();
			String team = IEntity.parseTeam(ent.getTeam());
			String behavior = ent.getBehavior();
			String pos = SimCoordinate.convertToString(ent.getCurrentPosition());
			String order = ent.getPlan().getCurrentTask().getType();
			String target = ent.getPlan().getCurrentTask().getTarget().getAlias();
			String status = IEntity.parseStatus(ent.getStatus());

			model.setValueAt(alias, i, 0);
			model.setValueAt(modelName, i, 1);
			model.setValueAt(team, i, 2);
			model.setValueAt(behavior, i, 3);
			model.setValueAt(pos, i, 4);
			model.setValueAt(order, i, 5);
			model.setValueAt(target, i, 6);
			model.setValueAt(status, i, 7);
		}
	}

	private void retrieveEntity(int line) {
		DefaultTableModel model = (DefaultTableModel) jtable.getModel();
		String alias = (String)model.getValueAt(line, 0);
		simuPanel.loadEntityInformation(alias);
	}

	class SharedListSelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			int line = jtable.getSelectedRow();
			retrieveEntity(line);
		}
	}

}
