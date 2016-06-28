package com.hhm.hibernate_04_guanLian_many2many;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Student implements Serializable{
	private int sid;
	private String sname;
	
	//另外一个多端的对象的集合
	Set<Course> courses=new HashSet<Course>();

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public Student() {
		super();
	}

	public Student(String sname) {
		super();
		this.sname = sname;
	}
	
	
}
