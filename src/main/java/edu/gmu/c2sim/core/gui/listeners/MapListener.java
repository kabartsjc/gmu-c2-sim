package edu.gmu.c2sim.core.gui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import org.jxmapviewer.JXMapViewer;

public class MapListener implements MouseListener{
	JFrame frame;
	JXMapViewer mapViewer;
	
	public MapListener (JFrame frame, JXMapViewer mapViewer) {
		this.frame=frame;
		this.mapViewer=mapViewer;
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	//	updateWindowTitle();//
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	protected void updateWindowTitle() {
		/*double lat = mapViewer.getCenterPosition().getLatitude();
		double lon = mapViewer.getCenterPosition().getLongitude();
		int zoom = mapViewer.getZoom();
		frame.setTitle(String.format("ATC Sim v2.0 (%.2f / %.2f) - Zoom: %d", lat, lon, zoom));*/
	}
	

}
