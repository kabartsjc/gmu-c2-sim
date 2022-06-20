package edu.gmu.c2sim.core.plan;

import java.util.List;

import edu.gmu.c2sim.core.orders.IOrder;

public interface IPlan {

	public List<IOrder> getOrders();

	public void addOrder(IOrder task);

	public void deleteOrder(String orderId);

	public IOrder getNextOrder();

	public String getID();

	public IOrder getCurrentTask ();
}
