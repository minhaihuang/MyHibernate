package com.hhm.hibernate_01_test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * 测试session中的缓存处理机制
 * @author 黄帅哥
 *
 */
public class TestCache {

	//准备对象
	private static SessionFactory sFactory=null;
	private static Configuration configuration=null;
	
	static{
		//初始化对象
		configuration=new Configuration();
		//读取配置文件
		configuration.configure("hibernate.cfg.xml");
		//加载类的配置信息
		configuration.addClass(Student.class);
		
		//获取工厂对象
		sFactory=configuration.buildSessionFactory();
	}
	
	/**
	 * 测试刷新时的缓存处理机制，因为是查询的方法，所以不需要事务提交器，仅仅是查询的方法特殊而已
	 * 总结：
	 */
	public static void testFlush(){
		//打开session对象
	Session session=sFactory.openSession();
		
		//准备sql语句，保存到session中,这句代码执行后将会产生，并执行sql语句
		Student student=(Student) session.get(Student.class, 3);
		
		System.out.println(student.getName());
		//刷新一下，因为session中已经缓存了sql语句，所以不会重新产生新的sql语句
		session.flush();
		
		//关闭session
		session.close();
	}
	
	/**
	 * 测试刷新时的缓存处理机制，因为不是查询的方法，所以需要事务提交器
	 * 总结：
	 */
	public static void testFlush02(){
		Student student=new Student();
		student.setId(006);
		student.setName("hhc");
		
		//打开session对象
		Session session=sFactory.openSession();
		//开始事务提交器
		Transaction ts=session.beginTransaction();
		
		//保存数据到session中，会返回一个数字，当前数据库中最大的id数加1
		System.out.println(session.save(student));

		//改变student的数据
		student.setName("hhc");
		
		//刷新，这时，会先产生一条插入的语句->执行插入语句->产生更新语句->执行更新语句
		session.flush();
		
		//提交事务
		ts.commit();
		//关闭session
		session.close();
	}
	
	
	/**
	 * refresh,重新刷新对象
	 * 总结：
	 */
	public static void testRefresh(){
		//打开session对象
		Session session=sFactory.openSession();
		//开始事务提交器
		Transaction ts=session.beginTransaction();
		
		//获取对象
		Student student=(Student) session.get(Student.class, 2);
		

		//改变student的数据
		student.setName("hhc02");
		
		//refresh
		session.refresh(student);
		
		//提交事务
		ts.commit();
		//关闭session
		session.close();
	}
	
	
	/**
	 * clear,清理缓存
	 * 总结：
	 */
	public static void testClear(){
		//打开session对象
		Session session=sFactory.openSession();
		//开始事务提交器
		Transaction ts=session.beginTransaction();
		
		//获取对象
		Student student=(Student) session.get(Student.class, 2);
		
		//还没有清空缓存，不会重新生成sql语句
		Student student2=(Student) session.get(Student.class, 2);
		
		//清空缓存
		session.clear();
		
		//将会重新准备sql语句
		Student student3=(Student) session.get(Student.class, 2);
		
		//提交事务
		ts.commit();
		//关闭session
		session.close();
	}
	
	public static void main(String[] args) {
//		testRefresh();
		testClear();
	}
}
