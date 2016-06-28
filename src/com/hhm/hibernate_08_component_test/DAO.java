package com.hhm.hibernate_08_component_test;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * 练习：
 * 设计一个类Customer，包括cid,cname,mobile,email,qq,其中mobile,email,qq放在一个组件类Contact中。
 * 实现Customer类的CRUD操作
 * @author 黄帅哥
 *
 */
public class DAO {
	private static Configuration configuration=null;
	private static SessionFactory sFactory=null;
	
	static{
		configuration=new Configuration();
		configuration.configure("hibernate.cfg.xml");
		configuration.addClass(Customer.class);
		
		sFactory=configuration.buildSessionFactory();
		
	}
	
	/**
	 * 测试增加的方法
	 */
	public static void add(){
		Session session=sFactory.openSession();
		Transaction ts=null;
		//准备数据
		Customer customer=new Customer("hhm");
		
		Contact contact=new Contact();
		contact.setMobile("18814111689");
		contact.setEmail("sds");
		contact.setQq("1533xxx");
		
		//双向关联数据
		customer.setContact(contact);
		contact.setCustomer(customer);
		
		try {
			//保存数据
			ts=session.beginTransaction();
			session.save(customer);
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	public static void main(String[] args) {
		add();
	}
}
