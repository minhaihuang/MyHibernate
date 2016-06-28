package com.hhm.hibernate_01_test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * 测试类
 * @author 黄帅哥
 *
 */
public class TestDAO {
	public static void main(String[] args) {
		addStudent();
	}
	//准备SessionFactory对象
	private static SessionFactory sFactory=null;
	
	//初始化，读取各种配置文件，获取sessionFactory对象
	static{
		Configuration configuration=new Configuration();
		//读取总配置文件
		configuration.configure("hibernate.cfg.xml");
		//读取分配置文件
		configuration.addClass(Student.class);
		//获取sessionFactory对象
		
		sFactory=configuration.buildSessionFactory();
	}
	
	/**
	 * 增加一个学生
	 */
	public static void addStudent(){
		//准备数据
		Student student=new Student();
		student.setId(3);
		student.setGender("male");
		student.setName("hzy");
		student.setDepartment("b3-703b");
		student.setMajor("computer");
		
		//打开回话对象
		Session session=sFactory.openSession();
		
		//准备提交数据
		Transaction transaction=session.beginTransaction();
		//保存数据到session中
		session.save(student);
		//提交数据，执行插入数据库操作
		transaction.commit();
		
		//关闭会话对象
		session.close();
	}
	
}
