
package edu.gmu.c2sim.core.gui.components;


import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

/**
 * A waypoint that also has a color and a label
 * @author Martin Steiger
 */
public class MyWaypoint extends DefaultWaypoint
{
    private final String label;
    private String iconName;
    
    /**
     * @param label the text
     * @param color the color
     * @param coord the coordinate
     */
    public MyWaypoint(String label, GeoPosition coord, String iconName)
    {
        super(coord);
        this.label = label;
        this.iconName=iconName;
    }
    
    public MyWaypoint(String label, GeoPosition coord)
    {
        super(coord);
        this.label = label;
        this.iconName="waypoint_white.png";
    }
    
    

    /**
     * @return the label text
     */
    public String getLabel()
    {
        return label;
    }

    
    public String getIconName() {
    	return iconName;
    }

}
