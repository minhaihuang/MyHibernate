package com.hhm.hibernate_06_guanLian_querystategy;

import java.io.Serializable;

/**
 * 员工类，这是多端，要有一个一端的对象
 * 
 * @author 黄帅哥
 * 
 */
public class Employee implements Serializable {
	private int eid;
	private String name;
	private int salary;
	// 一端的对象
	private Department department;

	

	public Employee(String name, int salary) {
		super();
		this.name = name;
		this.salary = salary;
	}

	public Employee() {
		super();
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	@Override
	public String toString() {
		
		return "id:"+eid+","+"name:"+name+","+"salary:"+salary;
	}
}
