package edu.gmu.c2sim.core.gui;


import org.jxmapviewer.viewer.GeoPosition;

public abstract class ICoreGui {
	public abstract void setPosition(GeoPosition pos) ;
	public abstract void createAndShowGUI();
	public abstract void exit();
	

}
