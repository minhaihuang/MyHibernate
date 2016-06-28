package com.hhm.hibernate_05_guanLian_one2one;

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
		configuration.addClass(User.class);
		configuration.addClass(Room.class);
		
		sFactory=configuration.buildSessionFactory();
	}
	
	/**
	 * 增加的方法
	 */
	public static void add(){
		Session session=sFactory.openSession();
		Transaction ts=null;
		
		//准备数据
		Room room=new Room();
		room.setAddress("china");
		
		User user=new User();
		user.setUserName("hhm");
		
		//双向关联
		room.setUser(user);
		user.setRoom(room);
		
		try {
			//保存操作
			ts=session.beginTransaction();
			session.save(room);
			
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
//			User user=(User) session.get(User.class, 1);
//			System.out.println(user.getRoom().getAddress());
//			Room room=(Room)session.get(Room.class, 1);
//			System.out.println(room.getUser().getUserName());
			
			
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
