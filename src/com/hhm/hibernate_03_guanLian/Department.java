package com.hhm.hibernate_03_guanLian;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 部门类。这是一端，要求有一个多端对象的set集合
 * 
 * @author 黄帅哥
 * 
 */
public class Department implements Serializable {
	private int did;
	private String name;
	private String brief;// 部门职能
	// 多端对象的set集合
	private Set<Employee> employees =new HashSet<Employee>();

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		
		return "name->"+name+";"+"brief->"+brief;
	}
}
