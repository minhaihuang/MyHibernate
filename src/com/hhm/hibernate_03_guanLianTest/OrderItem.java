package com.hhm.hibernate_03_guanLianTest;

import java.io.Serializable;

public class OrderItem implements Serializable {
	// OrderItemId,productName,price,quantity
	private int orderItemId;
	private String productName;
	private double price;
	private String quantity;
	// 一个一端的对象
	private Order order = null;
	

	public OrderItem(String productName, double price, String quantity) {
		super();
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
	}

	public OrderItem() {
		super();
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
