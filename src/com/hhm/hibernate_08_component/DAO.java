package com.hhm.hibernate_08_component;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * 这是持久化类，因为Address组件类，所以配置其映射文件以及加载它的class
 * @author 黄帅哥
 *
 */
public class DAO {
	private static Configuration configuration=null;
	private static SessionFactory sFactory=null;
	
	static{
		configuration=new Configuration();
		configuration.configure("hibernate.cfg.xml");
		configuration.addClass(Friend.class);
		
		sFactory=configuration.buildSessionFactory();
		
	}
	
	/**
	 * 测试增加的方法
	 */
	public static void add(){
		Session session=sFactory.openSession();
		Transaction ts=null;
		//准备数据
		Friend friend=new Friend("cqc");
		Address address=new Address("GuangDong", "guangzhou");
		
		//关联数据
		friend.setAddress(address);
		address.setFriend(friend);
		
		try {
			//保存数据
			ts=session.beginTransaction();
			session.save(friend);
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
