package com.hhm.hibernate_08_extendsYingshe;

import java.io.Serializable;

/**
 * 这是Teacher和Student的父类，拥有他们共同的属性name和id
 * 
 * @author 黄帅哥
 * 
 */
public class Person implements Serializable {
	private Integer id;
	private String name;

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

}
