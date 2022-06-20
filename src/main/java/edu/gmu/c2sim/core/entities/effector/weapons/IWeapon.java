package edu.gmu.c2sim.core.entities.effector.weapons;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.StringTokenizer;

import edu.gmu.c2sim.core.entities.effector.IEffector;

public interface IWeapon extends IEffector{
	public boolean isReusable();
	public double getIntensity();
	public String getType();
	
	public double calculateEffectApplied();
	
	public void setRandomParams(String distName, double[] params);
	
	
	
	public static List<String> convert(String wpLS ){
		List<String> wpL = new ArrayList<>();
		StringTokenizer str = new StringTokenizer(wpLS, ",");
		
		while (str.hasNext())
			wpL.add(str.nextToken());
		return wpL;
	}
	
	public static String convert(List<IWeapon> wpL) {
		String result = "";
		
		for (int i=0; i< wpL.size()-1; i++) {
			result = result+wpL.get(i).getId()+",";
			
		}
		
		result = result + wpL.get(wpL.size()-1).getId();
		
		return result;
	}
	
	
}
