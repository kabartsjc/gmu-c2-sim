package edu.gmu.c2sim.core.gui.components;

import javax.swing.ImageIcon;


public class ImageIconUtil {
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	public static ImageIcon createImageIcon(String name, String team) {
		// https://en.wikipedia.org/wiki/NATO_Joint_Military_Symbolog
		java.net.URL imgURL = null;
		String imgFile = "";
		if (team.equals("BLUE")) {
			imgFile = imgFile + "b_" + name;
		}

		else if (team.equals("RED")) {
			imgFile = imgFile + "r_" + name;
		}

		else {
			imgFile = imgFile + "g_" + name;
		}

		imgURL = ImageIconUtil.class.getClass().getResource("/edu/gmu/c2sim/core/gui/ico/entities/" + imgFile);

		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + imgFile);
			return null;
		}
	}


}
