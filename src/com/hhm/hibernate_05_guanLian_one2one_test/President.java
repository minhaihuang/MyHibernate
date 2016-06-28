package com.hhm.hibernate_05_guanLian_one2one_test;

import java.io.Serializable;

public class President implements Serializable {
	// President类有pid,pname
	private int pid;
	private String pname;

	private Country country;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}
