package com.hhm.hibernate_05_guanLian_one2one_test;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * DAO类，在这里测试一对一关联模式下的增删改查操作
 * @author 黄帅哥
 *
 */
public class DAO {
	private static Configuration configuration=null;
	private static SessionFactory sFactory=null;
	
	static{
		configuration=new Configuration();
		
		configuration.configure("hibernate.cfg.xml");
		configuration.addClass(President.class);
		configuration.addClass(Country.class);
		
		sFactory=configuration.buildSessionFactory();
	}
	
	/**
	 * 增加的方法
	 */
	public static void add(){
		Session session=sFactory.openSession();
		Transaction ts=null;
		
		//准备数据
		President president=new President();
		president.setPname("hhm");
		
		Country country=new Country();
		country.setCname("china");
		
	
		
		//双向关联
		president.setCountry(country);
		country.setPresident(president);
		
		try {
			//保存操作
			ts=session.beginTransaction();
			
			//不管选择保存哪一个对象，都是先插入被控方，都会插入两个对象
			
			//Hibernate: insert into President (pname, pid) values (?, ?)
			//Hibernate: insert into Country (cname, cid) values (?, ?)
			//session.save(country);
			
			//Hibernate: insert into President (pname, pid) values (?, ?)
			//Hibernate: insert into Country (cname, cid) values (?, ?)
			session.save(president);
			
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
	
	
	/**
	 * 查询的方法
	 */
	public static void query(){
		Session session=sFactory.openSession();
		Transaction ts=null;
		
		
		try {
			//保存操作
			
			//都可以
			ts=session.beginTransaction();
			
			//左外连接查询，president在前
//			President president=(President) session.get(President.class, 1);
//			System.out.println(president.getCountry().getCname());//china
			
			
			//左外连接查询，country在前
			Country country=(Country)session.get(Country.class, 1);
			System.out.println(country.getPresident().getPname());//hhm
			
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
		//add();
		query();
	}
}
