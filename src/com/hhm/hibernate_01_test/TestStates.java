package com.hhm.hibernate_01_test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.hhm.utils.Utils;

/**
 * 测试三种状态，临时态（new），持久态（与session关联），游离态（从session中分离出去）；
 * @author 黄帅哥
 *
 */
public class TestStates {
	//获取工厂对象
	private static SessionFactory sFactory=Utils.getSessionFactory(Student.class);
	
	
	/**
	 * 测试三种状态之间的转换
	 */
	public static void statesTransport(){
		//打开session对象
		Session session=sFactory.openSession();
		
		//准备数据
		Student student=new Student();//临时状态
		student.setName("xijinping");//临时状态
		student.setMobile("110");//临时状态
		student.setProvince("hunan");//临时状态
		
		//开启事务提交器
		Transaction ts=session.beginTransaction();
		//保存数据到session中
		session.save(student);//持久状态
		
		ts.commit();
		
		session.close();//清理了缓存，把student从session中清理出来，student变成了游离状态
		
		//再次打开session
		session=sFactory.openSession();
		ts=session.beginTransaction();
		
		session.update(student);//再次将student变成持久状态
		
		ts.commit();
	}
	
	public static void main(String[] args) {
		statesTransport();
	}
}
