package com.hhm.hibernate_08_component_test;

import java.io.Serializable;

public class Customer implements Serializable {
	private Integer cid=null;
	private String cname = null;

	//组件类对象
	private Contact contact=null;
	
	
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Customer() {
		super();
	}

	public Customer(String cname) {
		super();
		this.cname = cname;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}
