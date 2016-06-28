package com.hhm.hibernate_08_component;

import java.io.Serializable;

/**
 * 这是作为组件的类，不需要声明id，只是作为辅助功能，帮助完善Friend的信息，协助Friend持久化
 * 
 * @author 黄帅哥
 * 
 */
public class Address implements Serializable {
	private String province = null;
	private String city = null;

	//主类对象
	private Friend friend = null;

	
	//注意，空构造器不能丢
	public Address() {
		super();
	}

	public Address(String province, String city) {
		super();
		this.province = province;
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Friend getFriend() {
		return friend;
	}

	public void setFriend(Friend friend) {
		this.friend = friend;
	}

}
