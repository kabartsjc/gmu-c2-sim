package edu.gmu.c2sim.core.plan;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.gmu.c2sim.core.orders.IOrder;

public class Plan implements IPlan {
	private String id;
	private IOrder currentTask;
	private LinkedList<IOrder> tasks;
	

	public Plan(String id) {
		this.id = id;
		this.tasks = new LinkedList<>();
	}
	
	@Override
	public List<IOrder> getOrders() {
		List<IOrder> taskL = new ArrayList<>(tasks);
		return taskL;
	}

	@Override
	public void addOrder(IOrder task) {
		this.tasks.add(task);
	}
	
	@Override
	public void deleteOrder(String orderId) {
		List<IOrder> orderL = getOrders();
		LinkedList <IOrder> ntasks = new LinkedList<>();
		for (IOrder order: orderL) {
			String id = order.getURI();
			if (id.equals(orderId)==false)
				ntasks.add(order);
			
		}
		
		tasks = ntasks;
		
		System.out.println(tasks.size());
		
	};


	@Override
	public IOrder getNextOrder() {
		currentTask = tasks.poll();
		return currentTask;
	}

	@Override
	public String getID() {
		return id;
	}
	
	
	public IOrder getCurrentTask () {
		return currentTask;
	}

}
