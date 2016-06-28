package com.hhm.hibernate_05_guanLian_one2one;

import java.io.Serializable;

public class Room implements Serializable {
	private int id;
	private String address;
	// 另外一个一端的对象
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
