package edu.gmu.c2sim.core.gui.editor;

import java.util.Hashtable;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.gmu.c2sim.core.entities.IEntityModel;
import edu.gmu.c2sim.core.gui.components.PalletListRenderer;

import java.awt.Dimension;
import java.awt.FlowLayout;

public class PalletPanel extends JPanel  implements ListSelectionListener{
private static final long serialVersionUID = -1640427669915833332L;
	
	private JList <String>list = null;
	
	private String[] iconNames = null;

	private Hashtable<String,IEntityModel> modelDB=null;
	private ScenarioEditorPanel scenarioPanel;

	
	public PalletPanel(ScenarioEditorPanel scenarioPanel) {
		this.scenarioPanel=scenarioPanel;
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		modelDB = IEntityModel.loadEntityModelHashtable();
		
		initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		List<IEntityModel> modelL = IEntityModel.loadEntityModel();
		
		
    	PalletListRenderer modelpl = new PalletListRenderer(modelL);

    	iconNames = IEntityModel.getEntityModelNames(modelL);
    	
    	list = new JList<>(iconNames);
    	list.setCellRenderer(modelpl);
    	list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	list.setSelectedIndex(0);
    	list.setVisibleRowCount(30);
    	list.addListSelectionListener(this);
    	
    	JScrollPane listScrollPane = new JScrollPane(list);
    	listScrollPane.setPreferredSize(new Dimension(200, 600));
		add(listScrollPane, "3, 4, 9, 3, fill, fill");
		
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		@SuppressWarnings("unchecked")
		JList<String> list = (JList<String>) e.getSource();
		int index = list.getSelectedIndex();
		String name = iconNames[index];
		updateLabel(name);
	}
	
	protected void updateLabel(String name) {
		IEntityModel model = this.modelDB.get(name);
		this.scenarioPanel.setSelectedEntity(model);
	}


}
