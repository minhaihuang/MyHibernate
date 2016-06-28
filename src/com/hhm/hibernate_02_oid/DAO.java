package com.hhm.hibernate_02_oid;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.hhm.utils.Utils;

/**
 * 测试各种不同的属性的OID（主键）的差异
 * @author 黄帅哥
 *
 */
public class DAO {

	//获取sessionFactory
	private static SessionFactory sFactory=Utils.getSessionFactory(Person.class);
	
	/**
	 * 当主键的类型为increment时，表示自动递增
	 */
	public static void addPersonWithIncerement(){
		Transaction ts=null;
		Session session=null;
		//准备数据
		Person person=new Person();
		person.setName("hhm01");
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
	
	/**
	 * OID 必须为 long, int 或 short 类型
	 * 当主键的类型为identity时，会自动选择主键，当主键类型为int时，会自动将其变为自动递增
	 */
	public static void addPersonWithIdentity(){
		Transaction ts=null;
		Session session=null;
		//准备数据
		Person person=new Person();
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
	
	/**
	 * OID 必须为 long, int 或 short 类型
	 * 当主键的类型为native时，会自动选择主键，当主键类型为int时，会自动将其变为自动递增
	 */
	public static void addPersonWithNative(){
		Transaction ts=null;
		Session session=null;
		//准备数据
		Person person=new Person();
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
	
	/**
	 * 当主键的类型为assigned时，必须在外部有开发者分配主键，否则无法插入
	 */
	public static void addPersonWithAssigned(){
		Transaction ts=null;
		Session session=null;
		//准备数据
		Person person=new Person();
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
	
	public static void main(String[] args) {
		//addPersonWithIncerement();
		//addPersonWithIdentity();
		//addPersonWithAssigned();
	}
}
