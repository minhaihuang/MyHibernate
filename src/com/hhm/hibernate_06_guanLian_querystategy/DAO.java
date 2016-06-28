package com.hhm.hibernate_06_guanLian_querystategy;

import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * 当lazy="true"和lazy="false"两种情况的不同
 * 这是测试类，类级别的检索策略，以及关联模式下的检索策略
 * 
 * @author 黄帅哥
 * 
 */
public class DAO {
	private static Configuration configuration = null;
	private static SessionFactory sFactory = null;

	// 读取配置文件
	static {
		configuration = new Configuration();
		// 读取总配置文件；
		configuration.configure("hibernate.cfg.xml");
		// 加载类映射文件
		configuration.addClass(Department.class);
		configuration.addClass(Employee.class);
		// 获取工厂对象
		sFactory = configuration.buildSessionFactory();
	}

	/**
	 * 当class中的lazy属性为true，并且使用load方法来获取值时，才会实现延时加载，意思是说只有执行到打印出部门名字的时侯才去查询数据库。
	 * 其他的情况都是立即加载，包括lzay="false"和使用get方法获取数据两种情况
	 */
	public static void queryByClassLazy(){
		Session session=sFactory.openSession();
		//Department department=(Department) session.get(Department.class,2);
		Department department=(Department) session.get(Department.class,2);
		System.out.println(department.getDid());
		System.out.println(department.getName());
		
	}


	
	
	public static void main(String[] args) {
		queryByClassLazy();
	}
}
