package com.hhm.hibernate_05_guanLian_one2one_test;

import java.io.Serializable;

public class Country implements Serializable {
	private int cid;
	private String cname;

	private President president;

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public President getPresident() {
		return president;
	}

	public void setPresident(President president) {
		this.president = president;
	}

}
