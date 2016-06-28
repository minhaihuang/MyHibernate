package com.hhm.hibernate_04_guanLian_many2many;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Course implements Serializable {
	private int cid;
	private String cname;
	
	//另外一个多端的对象集合
	private Set<Student> students=new HashSet<Student>();

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

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Course() {
		super();
	}

	public Course(String cname) {
		super();
		this.cname = cname;
	}
	
	
}
