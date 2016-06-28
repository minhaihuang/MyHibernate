package com.hhm.hibernate_08_component;

import java.io.Serializable;
/**
 *这是主类
 * @author 黄帅哥
 *
 */
public class Friend implements Serializable{
	private Integer id;
	private String name;
	
	//组件类对象
	private Address address=null;
	

	public Friend(String name) {
		super();
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	
}
