package com.hhm.hibernate_08_extendsYingshe;

import java.io.Serializable;
/**
 * 这是student类，拥有特殊的属性grade
 * @author 黄帅哥
 *
 */
public class Student extends Person implements Serializable {
	private String grade;

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	
}
