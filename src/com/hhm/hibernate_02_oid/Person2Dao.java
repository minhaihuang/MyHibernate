package com.hhm.hibernate_02_oid;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.hhm.utils.Utils;
/**
 * 测试各种属性id的情形
 * @author 黄帅哥
 *
 */
public class Person2Dao {
	private static SessionFactory sFactory=Utils.getSessionFactory(Person2.class);
	
	
	/**
	 * 当主键的类型为uuid时，主键将会变成全球唯一的一个字符串，由IP地址、JVM的启动时间（精确到1/4秒）、
	 * 系统时间、一个计数器值（在JVM中唯一）组成
	 */
/*	public static void addPersonWithUUID(){
		Transaction ts=null;
		Session session=null;
		//准备数据
		Person2 person=new Person2();
		//person.setId(001);
		person.setName("hhm02");
		person.setAge(21);
		person.setGender("man");
		
		try {
			//打开session对象
			session=sFactory.openSession();
			//开始事务提交器
			ts=session.beginTransaction();
			
			//保存数据,会返回当前数据库最大的id数+1的数字
			System.out.println(session.save(person));
			
			//提交数据
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();//回滚操作
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	*/
	
	/**
	 * 当主键为复合主键时
	 */
	public static void addPersonWithCompisteKey(){
		Transaction ts=null;
		Session session=null;
		//准备数据
		Person2 person=new Person2();
		person.setFirstName("zhan");
		person.setLastName("san");
		person.setAge(21);
		person.setGender("man");
		
		try {
			//打开session对象
			session=sFactory.openSession();
			//开始事务提交器
			ts=session.beginTransaction();
			
			//保存数据,会返回当前数据库最大的id数+1的数字
			System.out.println(session.save(person));
			
			//提交数据
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();//回滚操作
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	public static void main(String[] args) {
		//addPersonWithUUID();
		addPersonWithCompisteKey();
	}
}
