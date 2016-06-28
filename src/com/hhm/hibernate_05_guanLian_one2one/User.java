package com.hhm.hibernate_05_guanLian_one2one;

import java.io.Serializable;

public class User implements Serializable {
	private int id;
	private String userName;
	// 另外一个一端的对象
	private Room room;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

}
