package edu.gmu.c2sim.core.gui.run;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TopHeaderPanel extends JPanel{
	private static final long serialVersionUID = 2339987914275803549L;
	
	private JTextField tfExeName;
	private JTextField txtCurrenTime;
	private JTextField txtSimuDuration;
	
	public TopHeaderPanel() {
		
	}
	
	public void configure(String exeName, long duration, long currentTime) {
		JLabel lblName = new JLabel("EXE NAME:");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setBounds(16, 31, 77, 16);
		add(lblName);
		
		tfExeName = new JTextField(exeName);
		tfExeName.setBounds(95, 26, 329, 26);
		tfExeName.setEditable(false);
		add(tfExeName);
		tfExeName.setColumns(10);
		
		JLabel lblSimDuration = new JLabel("SIMU DURATION (min):");
		lblSimDuration.setBounds(16, 76, 155, 16);
		add(lblSimDuration);

		txtSimuDuration = new JTextField(new Long(duration).toString());
		txtSimuDuration.setBounds(166, 71, 85, 26);
		txtSimuDuration.setEditable(false);
		add(txtSimuDuration);
		txtSimuDuration.setColumns(10);
		
		
		JLabel lblCurrentTime = new JLabel("CURRENT TIME:");
		lblCurrentTime.setBounds(16, 76, 155, 16);
		add(lblCurrentTime);

		String fCurrentTime = formatTime(currentTime);
		txtCurrenTime = new JTextField(fCurrentTime);
		txtCurrenTime.setBounds(166, 71, 85, 26);
		txtCurrenTime.setEditable(false);
		add(txtCurrenTime);
		txtCurrenTime.setColumns(10);
		
	}
	
	
	public static String formatTime (long time_sec) {
		long p1 = time_sec % 60;
        long p2 = time_sec / 60;
        long p3 = p2 % 60;
        p2 = p2 / 60;
        
        String p2S=Long.toString(p2);
        String p1S = Long.toString(p1);
        String p3S = Long.toString(p3);
        
        if (p2<10) {
        	p2S = "0"+p2;
        }
        
        if (p1<10) {
        	p1S = "0"+p1;
        }
        
        if (p3<10) {
        	p3S = "0"+p3;
        }
        
        String time =  p2S + ":" + p3S + ":" + p1S;
        return time;
	}
	
	
	
	public void update (long currentTime_sec) {
		String fTime = formatTime(currentTime_sec);
		txtCurrenTime.setText(fTime);
	}

}
