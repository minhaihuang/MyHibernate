package com.hhm.hibernate_08_component_test;

import java.io.Serializable;

public class Contact implements Serializable {
	// mobile,email,qq,
	private String mobile = null;
	private String email = null;
	private String qq = null;

	//主类对象
	private Customer customer=null;
	public Contact() {
		super();
	}

	public Contact(String mobile, String email, String qq) {
		super();
		this.mobile = mobile;
		this.email = email;
		this.qq = qq;
	}

	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

}
