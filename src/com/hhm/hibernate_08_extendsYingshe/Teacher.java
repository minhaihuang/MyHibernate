package com.hhm.hibernate_08_extendsYingshe;

import java.io.Serializable;

/**
 * 这是Teacher类，拥有特殊的属性title
 * 
 * @author 黄帅哥
 * 
 */
public class Teacher extends Person implements Serializable {
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
