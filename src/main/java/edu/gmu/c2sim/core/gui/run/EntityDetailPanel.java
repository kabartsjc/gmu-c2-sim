package edu.gmu.c2sim.core.gui.run;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.orders.IOrder;
import edu.gmu.c2sim.core.plan.IPlan;
import edu.gmu.c2sim.utils.GeoUtils;
import net.miginfocom.swing.MigLayout;

public class EntityDetailPanel extends JPanel implements ItemListener{
	private static final long serialVersionUID = 1L;
	
	private JLabel lblIcon; 
	private JTextField tfAlias;
	private JTextField tfStatus;
	private JTextField tfModel;
	private JTextField tfTeam;
	private JTextField tfBehavior;
	private JTextField tfTarget;
	private JTextField tfTargetDist;
	private JComboBox<String> cbOrderList;
	private JTextField tfCurrentOrder;
	
	
	public EntityDetailPanel() {
		super(new MigLayout());
		initialize();
	}

	
	private void initialize() {
		
		lblIcon = new JLabel();
		add(lblIcon, "cell 0 0");
		
		JLabel lbAlias = new JLabel("ALIAS:");
		add(lbAlias, "cell 0 1");
		
		tfAlias = new JTextField();
		add(tfAlias, "cell 2 1,growx");
		tfAlias.setColumns(10);
		
		JLabel lbStatus = new JLabel("STATUS:");
		add(lbStatus, "cell 0 2");
		
		tfStatus = new JTextField();
		add(tfStatus, "cell 2 2,growx");
		tfStatus.setColumns(10);
		
		
		JLabel lbModel = new JLabel("MODEL:");
		add(lbModel, "cell 0 3");
		
		tfModel = new JTextField();
		add(tfModel, "cell 2 3,growx");
		tfModel.setColumns(10);
		
		JLabel lbTeam = new JLabel("TEAM:");
		add(lbTeam, "cell 0 4");
		
		tfTeam = new JTextField();
		add(tfTeam, "cell 2 4,growx");
		tfTeam.setColumns(10);
		
		
		JLabel lblBehavior = new JLabel("BEHAVIOR:");
		add(lblBehavior, "cell 0 5");
		
		tfBehavior = new JTextField();
		add(tfBehavior, "cell 2 5,growx");
		tfBehavior.setColumns(10);
		
		
		JLabel lbTarget = new JLabel("TARGET:");
		add(lbTarget, "cell 0 6");
		
		tfTarget = new JTextField();
		add(tfTarget, "cell 2 6,growx");
		tfTarget.setColumns(10);
		
		
		JLabel lbDistanceTarget = new JLabel("DISTANCE TARGET:");
		add(lbDistanceTarget, "cell 0 7");
		
		tfTargetDist = new JTextField();
		add(tfTargetDist, "cell 2 7,growx");
		tfTargetDist.setColumns(10);
		
		JLabel lbOrdersList = new JLabel("ORDER LIST:");
		add(lbOrdersList, "cell 0 8");
		
		cbOrderList = new JComboBox<String>();
		add(cbOrderList, "cell 2 8,growx");
		
		JLabel lbCurrentOrder = new JLabel("CURRENT ORDER:");
		add(lbCurrentOrder, "cell 0 9");
		
		tfCurrentOrder = new JTextField();
		add(tfCurrentOrder, "cell 2 9,growx");
		tfCurrentOrder.setColumns(10);
		
	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void loadInfo(IEntity ent) {
		String alias = ent.getAlias();
		tfAlias.setText(alias);
		
		String status = IEntity.parseStatus(ent.getStatus());
		tfStatus.setText(status);
		
		String model = ent.getModel().getID();
		tfModel.setText(model);
		
		String team = IEntity.parseTeam(ent.getTeam());
		tfTeam.setText(team);
		
		String behavior = ent.getBehavior();
		tfBehavior.setText(behavior);
		
		IPlan plan = ent.getPlan();
		if (plan!=null) {
			IOrder order = plan.getCurrentTask();
			if (order!=null) {
				IEntity target  = order.getTarget();
				String targetAlias = order.getTarget().getAlias();
				tfTarget.setText(targetAlias);
				DecimalFormat df2 = new DecimalFormat("#.###");
		        df2.setRoundingMode(RoundingMode.UP);
				
				double dist = GeoUtils.calculateHDistance(ent.getCurrentPosition(),target.getCurrentPosition() );
				String distS = df2.format(dist);
				tfTargetDist.setText(distS);
				
				String currentOrder = order.getType();
				tfCurrentOrder.setText(currentOrder);
			}
			
			List<IOrder> orderL = plan.getOrders();
			
			if (orderL!=null) {
				cbOrderList.removeAll();
				for (IOrder orderI : orderL) {
					String item = orderI.getType()+ "-" + orderI.getTarget().getAlias();
					cbOrderList.addItem(item);
				}
					
			}
		}
		
	}

}
