package com.hhm.hibernate_03_guanLianTest;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 * 订单类，注意，表名不能是order，因为mysql中有一个order关键字，表名不能是关键字
 * @author 黄帅哥
 *
 */
public class Order implements Serializable {
	// orderId,orderSN,orderTime,orderPerson
	private int orderId;
	private String orderSN;
	private Date orderTime;
	private String orderPerson;

	// 一个OrderItem类（多端）的对象集合，
	Set<OrderItem> orderItems = new HashSet<OrderItem>();

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderSN() {
		return orderSN;
	}

	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}


	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderPerson() {
		return orderPerson;
	}

	public void setOrderPerson(String orderPerson) {
		this.orderPerson = orderPerson;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
}
