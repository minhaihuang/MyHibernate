package com.hhm.hibernate_09_securityLock;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * 这是测试类。 解决更改数据库的并发问题，用乐观锁机制
 * 
 * @author 黄帅哥
 * 
 */
public class DAO {
	private static Configuration configuration = null;
	private static SessionFactory sFactory = null;

	static {
		configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		configuration.addClass(Student.class);

		sFactory = configuration.buildSessionFactory();

	}

	/**
	 * 增加一个学生
	 */
	public static void addStudent() {
		// 准备数据
		Student student = new Student();
		student.setGender("male");
		student.setName("hzy");
		student.setDepartment("b3-703b");
		student.setMajor("computer");

		// 打开回话对象
		Session session = sFactory.openSession();

		// 准备提交数据
		Transaction transaction = session.beginTransaction();
		// 保存数据到session中
		session.save(student);
		// 提交数据，执行插入数据库操作
		transaction.commit();

		// 关闭会话对象
		session.close();
	}

	/**
	 * 测试并发问题发生的原因，后面的更新会覆盖前面的更新，丢失更新的内容
	 */
	public static void testProblem() {
		// 开启两个session
		Session session1 = sFactory.openSession();
		Session session2 = sFactory.openSession();

		// 两个事务提交器
		Transaction ts1 = null;
		Transaction ts2 = null;

		// 两个session获取的都是同一个内容
		Student s1 = (Student) session1.get(Student.class, 1);
		Student s2 = (Student) session2.get(Student.class, 1);

		// session1将学生的名字改成hhm1,session2将学生的名字改成hhm2。
		//后提交的会覆盖前面提交的
		s1.setName("hhm1");
		s2.setName("hhm2");

		// ts1先提交
		ts1 = session1.beginTransaction();
		session1.update(s1);
		ts1.commit();

		// ts1先提交
		ts2 = session2.beginTransaction();
		session2.update(s2);
		ts2.commit();
		
		
		session1.close();
		session2.close();
	}

	/**
	 * 解决并发问题，在POJO中定义一个int类型的字段version，在映射文件中注册该字段，在property标签前面，用Version标签
	 */
	public static void solveProblem(){
		// 开启两个session
				Session session1 = sFactory.openSession();
				Session session2 = sFactory.openSession();

				// 两个事务提交器
				Transaction ts1 = null;
				Transaction ts2 = null;

				// 两个session获取的都是同一个内容
				Student s1 = (Student) session1.get(Student.class, 1);
				Student s2 = (Student) session2.get(Student.class, 1);

				// session1将学生的名字改成hhm1,session2将学生的名字改成hhm2。
				//后提交的会覆盖前面提交的
				s1.setName("hhm1");
				s2.setName("hhm2");

				// ts1先提交
				ts1 = session1.beginTransaction();
				session1.update(s1);
				ts1.commit();

				// ts1先提交
				ts2 = session2.beginTransaction();
				session2.update(s2);
				ts2.commit();
				
				
				session1.close();
				session2.close();
	}
	
	public static void main(String[] args) {
		//addStudent();
		//testProblem();
		solveProblem();
	}
}
