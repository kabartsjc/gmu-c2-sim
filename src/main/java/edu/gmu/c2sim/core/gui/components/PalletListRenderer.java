package edu.gmu.c2sim.core.gui.components;

import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.IEntity.TEAM;
import edu.gmu.c2sim.core.entities.IEntityModel;


public class PalletListRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;

	private Font font = new Font("helvitica", Font.BOLD, 10);

	private Map<String, ImageIcon> imageMap = null;
	
	public PalletListRenderer(List<IEntityModel> entModelL) {
		imageMap = createImageMap(entModelL);
	}

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		label.setIcon(imageMap.get((String) value));
		label.setHorizontalTextPosition(JLabel.RIGHT);
		label.setFont(font);
		return label;
	}
	
	
	private Map<String, ImageIcon> createImageMap(List<IEntityModel> entModelL) {
		Map<String, ImageIcon> map = new HashMap<>();
		
		for (IEntityModel model : entModelL) {
			String name = model.getID();
			String imgName = model.getType().getImageName();
			ImageIcon icon = ImageIconUtil.createImageIcon(imgName, IEntity.parseTeam(TEAM.BLUE));
			map.put(name, icon);
		}
		return map;
	}


}
